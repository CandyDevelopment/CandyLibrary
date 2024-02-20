package fit.d6.candy.api.command;

import org.jetbrains.annotations.NotNull;

public interface ArgumentManager {

    @NotNull
    ArgumentType stringArg();

    @NotNull
    ArgumentType wordStringArg();

    @NotNull
    ArgumentType greedyStringArg();

    @NotNull
    ArgumentType booleanType();

    @NotNull
    ArgumentType integerArg();

    @NotNull
    ArgumentType integerArg(int min);

    @NotNull
    ArgumentType integerArg(int min, int max);

    @NotNull
    ArgumentType doubleArg();

    @NotNull
    ArgumentType doubleArg(double min);

    @NotNull
    ArgumentType doubleArg(double min, double max);

    @NotNull
    ArgumentType longArg();

    @NotNull
    ArgumentType longArg(long min);

    @NotNull
    ArgumentType longArg(long min, long max);

    @NotNull
    ArgumentType floatArg();

    @NotNull
    ArgumentType floatArg(float min);

    @NotNull
    ArgumentType floatArg(float min, float max);

    @NotNull
    ArgumentType singleEntity();

    @NotNull
    ArgumentType entities();

    @NotNull
    ArgumentType singlePlayer();

    @NotNull
    ArgumentType players();

    @NotNull
    ArgumentType angle();

    @NotNull
    ArgumentType component();

    @NotNull
    ArgumentType world();

    @NotNull
    ArgumentType message();

    @NotNull
    ArgumentType gameMode();

    @NotNull
    ArgumentType uuid();

    @NotNull
    ArgumentType vector(boolean centerIntegers);

    @NotNull
    ArgumentType particle();

    @NotNull
    ArgumentType block();

    @NotNull
    ArgumentType item();

    @NotNull
    ArgumentType itemPredicate();

    @NotNull
    ArgumentType enchantment();

    @NotNull
    ArgumentType entityType();

    @NotNull
    ArgumentType summonableEntityType();

    @NotNull
    ArgumentType potionEffectType();

    @NotNull
    ArgumentType playerProfiles();

    @NotNull
    ArgumentType nbt();

    @NotNull
    ArgumentType nbtCompound();

    @NotNull
    ArgumentType attribute();

    @NotNull
    ArgumentType blockPosition();

    @NotNull
    ArgumentType rotation();

    @NotNull
    ArgumentType duration();

    @NotNull
    static ArgumentManager getManager() {
        return CommandService.getService().getArgumentManager();
    }

}
