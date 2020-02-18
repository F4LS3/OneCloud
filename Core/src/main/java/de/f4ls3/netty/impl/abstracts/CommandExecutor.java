package de.f4ls3.netty.impl.abstracts;

import de.f4ls3.netty.interfaces.CommandInfo;

public abstract class CommandExecutor {

    public abstract void execute(String[] args);

    public String getSyntax() {
        if(this.getClass().isAnnotationPresent(CommandInfo.class))
            return this.getClass().getAnnotation(CommandInfo.class).syntax();
        else
            return "";
    }

    public String getDescription() {
        if(this.getClass().isAnnotationPresent(CommandInfo.class))
            return this.getClass().getAnnotation(CommandInfo.class).description();
        else
            return "";
    }
}
