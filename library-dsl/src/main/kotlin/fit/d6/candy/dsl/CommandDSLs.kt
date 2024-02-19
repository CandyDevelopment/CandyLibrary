package fit.d6.candy.dsl

import fit.d6.candy.api.command.*
import org.bukkit.command.CommandSender

class DslContexter(
    val context: CommandContext,
    val argument: CommandArgumentHelper
)

fun Command(name: String, content: CommandBuilder.() -> Unit): CommandBuilder {
    return CommandService.getService().commandManager.createCommand(name).apply(content)
}

fun CommandBuilder.Literal(name: String, content: CommandBuilder.() -> Unit): CommandBuilder {
    val builder = CommandService.getService().commandManager.createCommand(name).apply(content)
    this.then(builder)
    return builder
}

fun <T> CommandBuilder.Argument(
    name: String,
    type: ArgumentType,
    content: CommandBuilder.(argument: DslContexter.() -> T) -> Unit
): CommandBuilder {
    val builder = CommandService.getService().commandManager.createCommand(name, type)
    builder.content {
        return@content resolve(type.type, name, this.argument)
    }
    this.then(builder)
    return builder
}

fun CommandBuilder.Requirement(content: (CommandSender) -> Boolean) {
    this.requires(content)
}

fun CommandBuilder.Suggester(content: DslContexter.(context: CommandContext, argument: CommandArgumentHelper, suggestion: CommandSuggestion) -> Unit) {
    this.suggests { context: CommandContext, argument: CommandArgumentHelper, suggestion: CommandSuggestion ->
        val executor = DslContexter(context, argument)
        executor.content(context, argument, suggestion)
    }
}

fun CommandBuilder.Executor(content: DslContexter.(context: CommandContext, argument: CommandArgumentHelper) -> Int) {
    this.executes { context, argument ->
        val executor = DslContexter(context, argument)
        return@executes executor.content(context, argument)
    }
}

fun CommandBuilder.PlayerExecutor(content: DslContexter.(context: CommandContext, argument: CommandArgumentHelper) -> Int) {
    this.executesPlayer { context, argument ->
        val executor = DslContexter(context, argument)
        return@executesPlayer executor.content(context, argument)
    }
}

fun commandService(): CommandService {
    return CommandService.getService()
}

fun commandManager(): CommandManager {
    return CommandService.getService().commandManager
}

fun argumentManager(): ArgumentManager {
    return CommandService.getService().argumentManager
}

fun annotationCommandManager(): AnnotationCommandManager {
    return CommandService.getService().annotationCommandManager
}

fun stringArg(): ArgumentType {
    return CommandService.getService().argumentManager.stringArg()
}

fun wordArg(): ArgumentType {
    return CommandService.getService().argumentManager.stringArg()
}

fun greedyStringArg(): ArgumentType {
    return CommandService.getService().argumentManager.stringArg()
}

fun booleanArg(): ArgumentType {
    return CommandService.getService().argumentManager.booleanType()
}

fun integerArg(): ArgumentType {
    return CommandService.getService().argumentManager.integerArg()
}

fun integerArg(min: Int): ArgumentType {
    return CommandService.getService().argumentManager.integerArg(min)
}

fun integerArg(min: Int, max: Int): ArgumentType {
    return CommandService.getService().argumentManager.integerArg(min, max)
}

fun longArg(): ArgumentType {
    return CommandService.getService().argumentManager.longArg()
}

fun longArg(min: Long): ArgumentType {
    return CommandService.getService().argumentManager.longArg(min)
}

fun longArg(min: Long, max: Long): ArgumentType {
    return CommandService.getService().argumentManager.longArg(min, max)
}

fun floatArg(): ArgumentType {
    return CommandService.getService().argumentManager.floatArg()
}

fun floatArg(min: Float): ArgumentType {
    return CommandService.getService().argumentManager.floatArg(min)
}

fun floatArg(min: Float, max: Float): ArgumentType {
    return CommandService.getService().argumentManager.floatArg(min, max)
}

fun doubleArg(): ArgumentType {
    return CommandService.getService().argumentManager.doubleArg()
}

fun doubleArg(min: Double): ArgumentType {
    return CommandService.getService().argumentManager.doubleArg(min)
}

fun doubleArg(min: Double, max: Double): ArgumentType {
    return CommandService.getService().argumentManager.doubleArg(min, max)
}

fun singleEntityArg(): ArgumentType {
    return CommandService.getService().argumentManager.singleEntity();
}

fun entitiesArg(): ArgumentType {
    return CommandService.getService().argumentManager.entities();
}

fun singlePlayerArg(): ArgumentType {
    return CommandService.getService().argumentManager.singlePlayer();
}

fun playersArg(): ArgumentType {
    return CommandService.getService().argumentManager.players();
}

fun angleArg(): ArgumentType {
    return CommandService.getService().argumentManager.angle();
}

fun componentArg(): ArgumentType {
    return CommandService.getService().argumentManager.component();
}

fun worldArg(): ArgumentType {
    return CommandService.getService().argumentManager.world();
}

fun messageArg(): ArgumentType {
    return CommandService.getService().argumentManager.message();
}

fun gameModeArg(): ArgumentType {
    return CommandService.getService().argumentManager.gameMode();
}

fun uuidArg(): ArgumentType {
    return CommandService.getService().argumentManager.uuid();
}

fun vectorArg(centerIntegers: Boolean): ArgumentType {
    return CommandService.getService().argumentManager.vector(centerIntegers);
}

fun particle(): ArgumentType {
    return CommandService.getService().argumentManager.particle();
}

fun block(): ArgumentType {
    return CommandService.getService().argumentManager.block();
}

fun item(): ArgumentType {
    return CommandService.getService().argumentManager.item();
}

fun itemPredicate(): ArgumentType {
    return CommandService.getService().argumentManager.itemPredicate();
}

fun enchantment(): ArgumentType {
    return CommandService.getService().argumentManager.enchantment();
}

fun entityType(): ArgumentType {
    return CommandService.getService().argumentManager.entityType();
}

fun summonableEntityType(): ArgumentType {
    return CommandService.getService().argumentManager.summonableEntityType();
}

fun potionEffectType(): ArgumentType {
    return CommandService.getService().argumentManager.potionEffectType();
}

fun playerProfiles(): ArgumentType {
    return CommandService.getService().argumentManager.playerProfiles();
}

fun nbt(): ArgumentType {
    return CommandService.getService().argumentManager.nbt();
}

fun nbtCompound(): ArgumentType {
    return CommandService.getService().argumentManager.nbtCompound();
}

fun attribute(): ArgumentType {
    return CommandService.getService().argumentManager.attribute();
}

fun blockPosition(): ArgumentType {
    return CommandService.getService().argumentManager.blockPosition();
}

fun rotation(): ArgumentType {
    return CommandService.getService().argumentManager.rotation();
}

@Suppress("UNCHECKED_CAST")
private fun <T> resolve(type: ArgumentTypes, name: String, helper: CommandArgumentHelper): T {
    return when (type) {
        ArgumentTypes.STRING -> {
            helper.getString(name) as T
        }

        ArgumentTypes.INTEGER -> {
            helper.getInteger(name) as T
        }

        ArgumentTypes.DOUBLE -> {
            helper.getDouble(name) as T
        }

        ArgumentTypes.LONG -> {
            helper.getLong(name) as T
        }

        ArgumentTypes.FLOAT -> {
            helper.getFloat(name) as T
        }

        ArgumentTypes.SINGLE_ENTITY -> {
            helper.getSingleEntity(name) as T
        }

        ArgumentTypes.ENTITIES -> {
            helper.getEntities(name) as T
        }

        ArgumentTypes.SINGLE_PLAYER -> {
            helper.getSinglePlayer(name) as T
        }

        ArgumentTypes.PLAYERS -> {
            helper.getPlayers(name) as T
        }

        ArgumentTypes.ANGLE -> {
            helper.getAngle(name) as T
        }

        ArgumentTypes.COMPONENT -> {
            helper.getComponent(name) as T
        }

        ArgumentTypes.WORLD -> {
            helper.getWorld(name) as T
        }

        ArgumentTypes.MESSAGE -> {
            helper.getMessage(name) as T
        }

        ArgumentTypes.GAME_MODE -> {
            helper.getGameMode(name) as T
        }

        ArgumentTypes.UUID -> {
            helper.getUuid(name) as T
        }

        ArgumentTypes.VECTOR -> {
            helper.getVector(name) as T
        }

        ArgumentTypes.PARTICLE -> {
            helper.getParticle(name) as T
        }

        ArgumentTypes.BLOCK -> {
            helper.getBlock(name) as T
        }

        ArgumentTypes.ITEM -> {
            helper.getItem(name) as T
        }

        ArgumentTypes.ITEM_PREDICATE -> {
            helper.getItemPredicate(name) as T
        }

        ArgumentTypes.ENCHANTMENT -> {
            helper.getEnchantment(name) as T
        }

        ArgumentTypes.ENTITY_TYPE -> {
            helper.getEntityType(name) as T
        }

        ArgumentTypes.SUMMONABLE_ENTITY_TYPE -> {
            helper.getSummonableEntityType(name) as T
        }

        ArgumentTypes.POTION_EFFECT_TYPE -> {
            helper.getPotionEffectType(name) as T
        }

        ArgumentTypes.PLAYER_PROFILES -> {
            helper.getPlayerProfiles(name) as T
        }

        ArgumentTypes.NBT -> {
            helper.getNbt(name) as T
        }

        ArgumentTypes.NBT_COMPOUND -> {
            helper.getNbtCompound(name) as T
        }

        ArgumentTypes.ATTRIBUTE -> {
            helper.getAttribute(name) as T
        }

        ArgumentTypes.BLOCK_POSITION -> {
            helper.getBlockPosition(name) as T
        }

        ArgumentTypes.ROTATION -> {
            helper.getRotation(name) as T
        }

        else -> {
            throw IllegalArgumentException("Unsupported argument type")
        }
    }

}