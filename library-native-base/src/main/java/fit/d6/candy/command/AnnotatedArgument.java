package fit.d6.candy.command;

import fit.d6.candy.api.command.ArgumentType;

public class AnnotatedArgument {

    private final ArgumentType argumentTypes;
    private final String name;
    private final String[] suggestion;

    public AnnotatedArgument(ArgumentType argumentType, String name, String[] suggestion) {
        this.argumentTypes = argumentType;
        this.name = name;
        this.suggestion = suggestion;
    }

    public ArgumentType getArgumentType() {
        return argumentTypes;
    }

    public String getName() {
        return name;
    }

    public String[] getSuggestion() {
        return suggestion;
    }

}
