package fit.d6.candy.api.command;

import org.jetbrains.annotations.NotNull;

public interface Arguments {

    @NotNull
    static ArgumentType stringArg() {
        return CommandService.getService().getArgumentManager().stringArg();
    }

    @NotNull
    static ArgumentType wordStringArg() {
        return CommandService.getService().getArgumentManager().wordStringArg();
    }

    @NotNull
    static ArgumentType greedyStringArg() {
        return CommandService.getService().getArgumentManager().greedyStringArg();
    }

    @NotNull
    static ArgumentType booleanType() {
        return CommandService.getService().getArgumentManager().booleanType();
    }

    @NotNull
    static ArgumentType integerArg() {
        return CommandService.getService().getArgumentManager().integerArg();
    }

    @NotNull
    static ArgumentType integerArg(int min) {
        return CommandService.getService().getArgumentManager().integerArg(min);
    }

    @NotNull
    static ArgumentType integerArg(int min, int max) {
        return CommandService.getService().getArgumentManager().integerArg(min, max);
    }

    @NotNull
    static ArgumentType doubleArg() {
        return CommandService.getService().getArgumentManager().doubleArg();
    }

    @NotNull
    static ArgumentType doubleArg(double min) {
        return CommandService.getService().getArgumentManager().doubleArg(min);
    }

    @NotNull
    static ArgumentType doubleArg(double min, double max) {
        return CommandService.getService().getArgumentManager().doubleArg(min, max);
    }

    @NotNull
    static ArgumentType longArg() {
        return CommandService.getService().getArgumentManager().longArg();
    }

    @NotNull
    static ArgumentType longArg(long min) {
        return CommandService.getService().getArgumentManager().longArg(min);
    }

    @NotNull
    static ArgumentType longArg(long min, long max) {
        return CommandService.getService().getArgumentManager().longArg(min, max);
    }

    @NotNull
    static ArgumentType floatArg() {
        return CommandService.getService().getArgumentManager().floatArg();
    }

    @NotNull
    static ArgumentType floatArg(float min) {
        return CommandService.getService().getArgumentManager().floatArg(min);
    }

    @NotNull
    static ArgumentType floatArg(float min, float max) {
        return CommandService.getService().getArgumentManager().floatArg(min, max);
    }

    @NotNull
    static ArgumentType singleEntity() {
        return CommandService.getService().getArgumentManager().singleEntity();
    }

    @NotNull
    static ArgumentType entities() {
        return CommandService.getService().getArgumentManager().entities();
    }

    @NotNull
    static ArgumentType singlePlayer() {
        return CommandService.getService().getArgumentManager().singlePlayer();
    }

    @NotNull
    static ArgumentType players() {
        return CommandService.getService().getArgumentManager().players();
    }

    @NotNull
    static ArgumentType angle() {
        return CommandService.getService().getArgumentManager().angle();
    }

    @NotNull
    static ArgumentType component() {
        return CommandService.getService().getArgumentManager().component();
    }

    @NotNull
    static ArgumentType world() {
        return CommandService.getService().getArgumentManager().world();
    }

    @NotNull
    static ArgumentType message() {
        return CommandService.getService().getArgumentManager().message();
    }

    @NotNull
    static ArgumentType gameMode() {
        return CommandService.getService().getArgumentManager().gameMode();
    }

    @NotNull
    static ArgumentType uuid() {
        return CommandService.getService().getArgumentManager().uuid();
    }

    @NotNull
    static ArgumentType vector(boolean centerIntegers) {
        return CommandService.getService().getArgumentManager().vector(centerIntegers);
    }

    @NotNull
    static ArgumentType particle() {
        return CommandService.getService().getArgumentManager().particle();
    }

    @NotNull
    static ArgumentType block() {
        return CommandService.getService().getArgumentManager().block();
    }

    @NotNull
    static ArgumentType item() {
        return CommandService.getService().getArgumentManager().item();
    }

    @NotNull
    static ArgumentType itemPredicate() {
        return CommandService.getService().getArgumentManager().itemPredicate();
    }

    @NotNull
    static ArgumentType enchantment() {
        return CommandService.getService().getArgumentManager().enchantment();
    }

    @NotNull
    static ArgumentType entityType() {
        return CommandService.getService().getArgumentManager().entityType();
    }

    @NotNull
    static ArgumentType summonableEntityType() {
        return CommandService.getService().getArgumentManager().summonableEntityType();
    }

    @NotNull
    static ArgumentType potionEffectType() {
        return CommandService.getService().getArgumentManager().potionEffectType();
    }

    @NotNull
    static ArgumentType playerProfiles() {
        return CommandService.getService().getArgumentManager().playerProfiles();
    }

    @NotNull
    static ArgumentType nbt() {
        return CommandService.getService().getArgumentManager().nbt();
    }

    @NotNull
    static ArgumentType nbtCompound() {
        return CommandService.getService().getArgumentManager().nbtCompound();
    }

    @NotNull
    static ArgumentType attribute() {
        return CommandService.getService().getArgumentManager().attribute();
    }

    @NotNull
    static ArgumentType blockPosition() {
        return CommandService.getService().getArgumentManager().blockPosition();
    }

    @NotNull
    static ArgumentType rotation() {
        return CommandService.getService().getArgumentManager().rotation();
    }

    @NotNull
    static ArgumentType duration() {
        return CommandService.getService().getArgumentManager().duration();
    }
    
}
