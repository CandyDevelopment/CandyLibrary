package fit.d6.candy.api.command;

import org.jetbrains.annotations.NotNull;

public interface Arguments {

    @NotNull
    static ArgumentType stringArg() {
        return ArgumentManager.getManager().stringArg();
    }

    @NotNull
    static ArgumentType wordStringArg() {
        return ArgumentManager.getManager().wordStringArg();
    }

    @NotNull
    static ArgumentType greedyStringArg() {
        return ArgumentManager.getManager().greedyStringArg();
    }

    @NotNull
    static ArgumentType booleanType() {
        return ArgumentManager.getManager().booleanType();
    }

    @NotNull
    static ArgumentType integerArg() {
        return ArgumentManager.getManager().integerArg();
    }

    @NotNull
    static ArgumentType integerArg(int min) {
        return ArgumentManager.getManager().integerArg(min);
    }

    @NotNull
    static ArgumentType integerArg(int min, int max) {
        return ArgumentManager.getManager().integerArg(min, max);
    }

    @NotNull
    static ArgumentType doubleArg() {
        return ArgumentManager.getManager().doubleArg();
    }

    @NotNull
    static ArgumentType doubleArg(double min) {
        return ArgumentManager.getManager().doubleArg(min);
    }

    @NotNull
    static ArgumentType doubleArg(double min, double max) {
        return ArgumentManager.getManager().doubleArg(min, max);
    }

    @NotNull
    static ArgumentType longArg() {
        return ArgumentManager.getManager().longArg();
    }

    @NotNull
    static ArgumentType longArg(long min) {
        return ArgumentManager.getManager().longArg(min);
    }

    @NotNull
    static ArgumentType longArg(long min, long max) {
        return ArgumentManager.getManager().longArg(min, max);
    }

    @NotNull
    static ArgumentType floatArg() {
        return ArgumentManager.getManager().floatArg();
    }

    @NotNull
    static ArgumentType floatArg(float min) {
        return ArgumentManager.getManager().floatArg(min);
    }

    @NotNull
    static ArgumentType floatArg(float min, float max) {
        return ArgumentManager.getManager().floatArg(min, max);
    }

    @NotNull
    static ArgumentType singleEntity() {
        return ArgumentManager.getManager().singleEntity();
    }

    @NotNull
    static ArgumentType entities() {
        return ArgumentManager.getManager().entities();
    }

    @NotNull
    static ArgumentType singlePlayer() {
        return ArgumentManager.getManager().singlePlayer();
    }

    @NotNull
    static ArgumentType players() {
        return ArgumentManager.getManager().players();
    }

    @NotNull
    static ArgumentType angle() {
        return ArgumentManager.getManager().angle();
    }

    @NotNull
    static ArgumentType component() {
        return ArgumentManager.getManager().component();
    }

    @NotNull
    static ArgumentType world() {
        return ArgumentManager.getManager().world();
    }

    @NotNull
    static ArgumentType message() {
        return ArgumentManager.getManager().message();
    }

    @NotNull
    static ArgumentType gameMode() {
        return ArgumentManager.getManager().gameMode();
    }

    @NotNull
    static ArgumentType uuid() {
        return ArgumentManager.getManager().uuid();
    }

    @NotNull
    static ArgumentType vector(boolean centerIntegers) {
        return ArgumentManager.getManager().vector(centerIntegers);
    }

    @NotNull
    static ArgumentType particle() {
        return ArgumentManager.getManager().particle();
    }

    @NotNull
    static ArgumentType block() {
        return ArgumentManager.getManager().block();
    }

    @NotNull
    static ArgumentType item() {
        return ArgumentManager.getManager().item();
    }

    @NotNull
    static ArgumentType itemPredicate() {
        return ArgumentManager.getManager().itemPredicate();
    }

    @NotNull
    static ArgumentType enchantment() {
        return ArgumentManager.getManager().enchantment();
    }

    @NotNull
    static ArgumentType entityType() {
        return ArgumentManager.getManager().entityType();
    }

    @NotNull
    static ArgumentType summonableEntityType() {
        return ArgumentManager.getManager().summonableEntityType();
    }

    @NotNull
    static ArgumentType potionEffectType() {
        return ArgumentManager.getManager().potionEffectType();
    }

    @NotNull
    static ArgumentType playerProfiles() {
        return ArgumentManager.getManager().playerProfiles();
    }

    @NotNull
    static ArgumentType nbt() {
        return ArgumentManager.getManager().nbt();
    }

    @NotNull
    static ArgumentType nbtCompound() {
        return ArgumentManager.getManager().nbtCompound();
    }

    @NotNull
    static ArgumentType attribute() {
        return ArgumentManager.getManager().attribute();
    }

    @NotNull
    static ArgumentType blockPosition() {
        return ArgumentManager.getManager().blockPosition();
    }

    @NotNull
    static ArgumentType rotation() {
        return ArgumentManager.getManager().rotation();
    }

    @NotNull
    static ArgumentType duration() {
        return ArgumentManager.getManager().duration();
    }

}
