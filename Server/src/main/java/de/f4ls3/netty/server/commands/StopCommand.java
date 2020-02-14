package de.f4ls3.netty.server.commands;

import de.f4ls3.netty.impl.CommandExecutor;
import de.f4ls3.netty.impl.CommandInfo;
import de.f4ls3.netty.server.Server;
import de.f4ls3.netty.utils.Logger;
import de.f4ls3.netty.utils.handler.CommandHandler;

@CommandInfo(name = "stop", aliases = {"die", "kill", "end"}, syntax = "stop", description = "Stoppt das gesamte System")
public class StopCommand extends CommandExecutor {

    @Override
    @Deprecated
    public void execute(String[] args) {
        if(args.length > 1) {
            Logger.warn("Syntax: " + getSyntax());
            return;
        }

        Logger.log("\n  ____             _ \n" +
                " |  _ \\           | |\n" +
                " | |_) |_   _  ___| |\n" +
                " |  _ <| | | |/ _ \\ |\n" +
                " | |_) | |_| |  __/_|\n" +
                " |____/ \\__, |\\___(_)\n" +
                "         __/ |       \n" +
                "        |___/        ");

        CommandHandler.stop();
    }
}
