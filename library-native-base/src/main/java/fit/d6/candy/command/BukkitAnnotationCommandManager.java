package fit.d6.candy.command;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fit.d6.candy.api.CandyLibrary;
import fit.d6.candy.api.command.Command;
import fit.d6.candy.api.command.*;
import fit.d6.candy.api.command.annotation.Requirement;
import fit.d6.candy.api.command.annotation.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.*;
import java.util.*;

public class BukkitAnnotationCommandManager implements AnnotationCommandManager {

    @Override
    public @NotNull Command register(@NotNull Object command) {
        return this.register("candycmd", command);
    }

    @Override
    public @NotNull Command register(@NotNull String prefix, @NotNull Object command) {
        CommandBuilder commandBuilder = this.analyseCommand(command);
        return CandyLibrary.getLibrary().getService(CommandService.class).getCommandManager().register(prefix, commandBuilder);
    }

    private CommandBuilder analyseCommand(Object command) {
        Class<?> commandClass = command.getClass();
        fit.d6.candy.api.command.annotation.Command commandAnnotation = commandClass.getAnnotation(fit.d6.candy.api.command.annotation.Command.class);
        if (commandAnnotation == null)
            throw new IllegalArgumentException("The command is not annotated with Command annotation");
        CommandBuilder baseBuilder = CandyLibrary.getLibrary().getService(CommandService.class).getCommandManager().createCommand(commandAnnotation.name());

        for (Field field : commandClass.getDeclaredFields()) {
            if (!Modifier.isPublic(field.getModifiers()))
                continue;
            SubCommand subCommandAnnotation = field.getAnnotation(SubCommand.class);
            if (subCommandAnnotation == null)
                continue;
            Class<?> subCommandClass = field.getType();
            if (subCommandClass.getAnnotation(fit.d6.candy.api.command.annotation.Command.class) == null)
                continue;

            if (!subCommandAnnotation.name().equalsIgnoreCase(subCommandClass.getAnnotation(fit.d6.candy.api.command.annotation.Command.class).name()))
                throw new IllegalArgumentException("Sub command name not matched!");

            field.setAccessible(true);

            try {
                baseBuilder.then(analyseCommand(field.get(command)));
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException("Error occurs when trying create sub command", e);
            }
        }

        for (Method method : commandClass.getDeclaredMethods()) {
            if (!Modifier.isPublic(method.getModifiers()))
                continue;
            if (method.getAnnotation(Executor.class) == null && method.getAnnotation(Requirement.class) == null)
                continue;

            if (method.getAnnotation(Requirement.class) != null && method.getParameters().length == 1 && CommandSender.class.isAssignableFrom(method.getParameters()[0].getType()) && (Objects.equals(method.getReturnType(), boolean.class) || Objects.equals(method.getReturnType(), Boolean.class))) {
                baseBuilder.requires(sender -> {
                    try {
                        return (boolean) method.invoke(command, sender);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });
                continue;
            }

            SubCommand subCmd = method.getAnnotation(SubCommand.class);

            CommandBuilder toUseBuilder = subCmd == null ? baseBuilder : CandyLibrary.getLibrary().getService(CommandService.class).getCommandManager().createCommand(subCmd.name());

            analyseExecutor(toUseBuilder, command, method);

            if (subCmd != null)
                baseBuilder.then(toUseBuilder);
        }

        return baseBuilder;
    }

    private void analyseExecutor(CommandBuilder builder, Object command, Method method) {
        CommandManager commandManager = CandyLibrary.getLibrary().getService(CommandService.class).getCommandManager();

        Parameter[] parameters = method.getParameters();

        int senderIndex = -1;
        int playerIndex = -1;
        int aliasIndex = -1;

        List<AnnotatedArgument> arguments = new ArrayList<>();

        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            if (parameter.getAnnotations().length != 1)
                throw new IllegalArgumentException("The parameter only accepts one annotation");
            if (parameter.getAnnotation(Sender.class) != null && CommandSender.class.isAssignableFrom(parameter.getType())) {
                if (senderIndex != -1)
                    throw new IllegalArgumentException("Duplicated Command Sender");
                senderIndex = i;
            } else if (parameter.getAnnotation(Sender.class) != null && method.getAnnotation(RequiresPlayer.class) != null && Player.class.isAssignableFrom(parameter.getType())) {
                if (playerIndex != -1)
                    throw new IllegalArgumentException("Duplicated Player");
                playerIndex = i;
            } else if (parameter.getAnnotation(Alias.class) != null && parameter.getType() == String.class) {
                if (aliasIndex != -1)
                    throw new IllegalArgumentException("Duplicated Alias");
                aliasIndex = i;
            } else {
                Argument argument = parameter.getAnnotation(Argument.class);
                if (argument == null) {
                    throw new IllegalArgumentException("The parameter must be annotated with a candy command annotation");
                } else {
                    detectArgument(parameter, argument.type());
                    arguments.add(new AnnotatedArgument(resolveArgumentType(argument.type()), argument.name(), argument.suggestions()));
                }
            }
        }

        if (arguments.isEmpty()) {
            int finalSenderIndex = senderIndex;
            int finalPlayerIndex = playerIndex;
            int finalAliasIndex = aliasIndex;

            CommandExecutor executor = ((context, argument) -> {
                Object[] actualParameters = new Object[parameters.length];

                for (int i = 0; i < parameters.length; i++) {
                    if (i == finalSenderIndex) {
                        actualParameters[i] = context.getSender();
                    } else if (i == finalPlayerIndex) {
                        actualParameters[i] = context.getPlayer();
                    } else if (i == finalAliasIndex) {
                        actualParameters[i] = context.getAlias();
                    }
                }

                try {
                    method.invoke(command, actualParameters);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new IllegalArgumentException("Error occurs when executing command", e);
                }
                return 1;
            });

            if (method.getAnnotation(RequiresPlayer.class) != null) {
                builder.executesPlayer(executor);
            } else {
                builder.executes(executor);
            }
        } else {
            int finalSenderIndex = senderIndex;
            int finalPlayerIndex = playerIndex;
            int finalAliasIndex = aliasIndex;

            CommandExecutor executor = ((context, argument) -> {
                int offset = 0;

                List<Object> actualArguments = new ArrayList<>();

                for (AnnotatedArgument arg : arguments) {
                    actualArguments.add(analyseArgument(argument, arg));
                }

                Object[] actualParameters = new Object[parameters.length];

                for (int i = 0; i < parameters.length; i++) {
                    if (i == finalSenderIndex) {
                        actualParameters[i] = context.getSender();
                        offset += 1;
                    } else if (i == finalPlayerIndex) {
                        actualParameters[i] = context.getPlayer();
                        offset += 1;
                    } else if (i == finalAliasIndex) {
                        actualParameters[i] = context.getAlias();
                        offset += 1;
                    } else {
                        actualParameters[i] = actualArguments.get(i - offset);
                    }
                }

                try {
                    method.invoke(command, actualParameters);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new IllegalArgumentException("Error occurs when executing command", e);
                }
                return 1;
            });
            CommandBuilder tempBuilder = null;

            for (int i = arguments.size() - 1; i >= 0; i--) {
                AnnotatedArgument arg = arguments.get(i);
                CommandBuilder newBuilder = commandManager.createCommand(arg.getName(), resolveArgumentType(arg.getArgumentType().getType()));
                if (tempBuilder == null) {
                    if (arg.getSuggestion().length > 0) {
                        newBuilder.suggests((context, argument, suggestion) -> {
                            Arrays.stream(arg.getSuggestion()).forEach(suggestion::suggests);
                        });
                    }
                    if (method.getAnnotation(RequiresPlayer.class) != null) {
                        newBuilder.executesPlayer(executor);
                    } else {
                        newBuilder.executes(executor);
                    }
                    tempBuilder = newBuilder;
                    continue;
                }
                newBuilder.then(tempBuilder);
                tempBuilder = newBuilder;
            }

            builder.then(tempBuilder);
        }
    }

    private void detectArgument(Parameter parameter, ArgumentTypes argumentTypes) {
        if (argumentTypes == ArgumentTypes.ENTITIES) {
            if (!Set.class.isAssignableFrom(parameter.getType()))
                throw new IllegalArgumentException("The parameter type not matches the type of the argument delivered");
            ParameterizedType type = (ParameterizedType) parameter.getParameterizedType();
            Type[] actualTypes = type.getActualTypeArguments();
            if (actualTypes.length != 0) {
                if (actualTypes[0] != null) {
                    if (!Entity.class.isAssignableFrom((Class<?>) actualTypes[0]))
                        throw new IllegalArgumentException("The parameter type not matches the type of the argument delivered");
                }
            }
        } else if (argumentTypes == ArgumentTypes.PLAYERS) {
            if (!Set.class.isAssignableFrom(parameter.getType()))
                throw new IllegalArgumentException("The parameter type not matches the type of the argument delivered");
            ParameterizedType type = (ParameterizedType) parameter.getParameterizedType();
            Type[] actualTypes = type.getActualTypeArguments();
            if (actualTypes.length != 0) {
                if (actualTypes[0] != null) {
                    if (!Player.class.isAssignableFrom((Class<?>) actualTypes[0]))
                        throw new IllegalArgumentException("The parameter type not matches the type of the argument delivered");
                }
            }
        } else if (argumentTypes == ArgumentTypes.PLAYER_PROFILES) {
            if (!Set.class.isAssignableFrom(parameter.getType()))
                throw new IllegalArgumentException("The parameter type not matches the type of the argument delivered");
            ParameterizedType type = (ParameterizedType) parameter.getParameterizedType();
            Type[] actualTypes = type.getActualTypeArguments();
            if (actualTypes.length != 0) {
                if (actualTypes[0] != null) {
                    if (!PlayerProfile.class.isAssignableFrom((Class<?>) actualTypes[0]))
                        throw new IllegalArgumentException("The parameter type not matches the type of the argument delivered");
                }
            }
        } else {
            if (!isSub(parameter.getType(), argumentTypes.getClasses()))
                throw new IllegalArgumentException("The parameter type not matches the type of the argument delivered");
        }
    }

    private boolean isSub(Class<?> clazz, Class[] classes) {
        List<Class> classList = List.of(classes);
        if (classList.contains(clazz))
            return true;
        for (Class clz : classList) {
            if (clazz.isAssignableFrom(clz))
                return true;
        }
        return false;
    }

    private ArgumentType resolveArgumentType(ArgumentTypes argumentTypes) {
        ArgumentManager argumentManager = CandyLibrary.getLibrary().getService(CommandService.class).getArgumentManager();
        if (argumentTypes == ArgumentTypes.STRING) {
            return argumentManager.stringArg();
        } else if (argumentTypes == ArgumentTypes.INTEGER) {
            return argumentManager.integerArg();
        } else if (argumentTypes == ArgumentTypes.LONG) {
            return argumentManager.longArg();
        } else if (argumentTypes == ArgumentTypes.FLOAT) {
            return argumentManager.floatArg();
        } else if (argumentTypes == ArgumentTypes.DOUBLE) {
            return argumentManager.doubleArg();
        } else if (argumentTypes == ArgumentTypes.BOOLEAN) {
            return argumentManager.booleanType();
        } else if (argumentTypes == ArgumentTypes.ANGLE) {
            return argumentManager.angle();
        } else if (argumentTypes == ArgumentTypes.SINGLE_ENTITY) {
            return argumentManager.singleEntity();
        } else if (argumentTypes == ArgumentTypes.ENTITIES) {
            return argumentManager.entities();
        } else if (argumentTypes == ArgumentTypes.SINGLE_PLAYER) {
            return argumentManager.singlePlayer();
        } else if (argumentTypes == ArgumentTypes.PLAYERS) {
            return argumentManager.players();
        } else if (argumentTypes == ArgumentTypes.COMPONENT) {
            return argumentManager.component();
        } else if (argumentTypes == ArgumentTypes.WORLD) {
            return argumentManager.world();
        } else if (argumentTypes == ArgumentTypes.MESSAGE) {
            return argumentManager.message();
        } else if (argumentTypes == ArgumentTypes.GAME_MODE) {
            return argumentManager.gameMode();
        } else if (argumentTypes == ArgumentTypes.UUID) {
            return argumentManager.uuid();
        } else if (argumentTypes == ArgumentTypes.VECTOR) {
            return argumentManager.vector(false);
        }
        throw new IllegalArgumentException("Unsupported argument type");
    }

    private Object analyseArgument(CommandArgumentHelper argument, AnnotatedArgument annotation) throws CommandSyntaxException {
        String name = annotation.getName();
        if (annotation.getArgumentType().getType() == ArgumentTypes.STRING) {
            return argument.getString(name);
        } else if (annotation.getArgumentType().getType() == ArgumentTypes.INTEGER) {
            return argument.getInteger(name);
        } else if (annotation.getArgumentType().getType() == ArgumentTypes.LONG) {
            return argument.getLong(name);
        } else if (annotation.getArgumentType().getType() == ArgumentTypes.FLOAT) {
            return argument.getFloat(name);
        } else if (annotation.getArgumentType().getType() == ArgumentTypes.DOUBLE) {
            return argument.getDouble(name);
        } else if (annotation.getArgumentType().getType() == ArgumentTypes.BOOLEAN) {
            return argument.getBoolean(name);
        } else if (annotation.getArgumentType().getType() == ArgumentTypes.ANGLE) {
            return argument.getAngle(name);
        } else if (annotation.getArgumentType().getType() == ArgumentTypes.SINGLE_ENTITY) {
            return argument.getSingleEntity(name);
        } else if (annotation.getArgumentType().getType() == ArgumentTypes.ENTITIES) {
            return argument.getEntities(name);
        } else if (annotation.getArgumentType().getType() == ArgumentTypes.SINGLE_PLAYER) {
            return argument.getSinglePlayer(name);
        } else if (annotation.getArgumentType().getType() == ArgumentTypes.PLAYERS) {
            return argument.getPlayers(name);
        } else if (annotation.getArgumentType().getType() == ArgumentTypes.COMPONENT) {
            return argument.getComponent(name);
        } else if (annotation.getArgumentType().getType() == ArgumentTypes.WORLD) {
            return argument.getWorld(name);
        } else if (annotation.getArgumentType().getType() == ArgumentTypes.MESSAGE) {
            return argument.getMessage(name);
        } else if (annotation.getArgumentType().getType() == ArgumentTypes.GAME_MODE) {
            return argument.getGameMode(name);
        } else if (annotation.getArgumentType().getType() == ArgumentTypes.UUID) {
            return argument.getUuid(name);
        } else if (annotation.getArgumentType().getType() == ArgumentTypes.VECTOR) {
            return argument.getVector(name);
        }
        throw new IllegalArgumentException("Unsupported argument type");
    }

}
