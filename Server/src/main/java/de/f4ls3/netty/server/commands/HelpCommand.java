package de.f4ls3.netty.server.commands;

import de.f4ls3.netty.impl.abstracts.CommandExecutor;
import de.f4ls3.netty.interfaces.CommandInfo;
import de.f4ls3.netty.utils.Logger;
import de.f4ls3.netty.utils.handler.CommandHandler;

import java.util.ArrayList;
import java.util.List;

@CommandInfo(name = "help", aliases = {"?", "wtf"}, syntax = "help", description = "Zeigt eine hilfe aller Commands an")
public class HelpCommand extends CommandExecutor {

    @Override
    public void execute(String[] args) {
        if(args.length > 1) {
            Logger.warn("Syntax: " + getSyntax());
            return;
        }

        List<CommandInfo> commands = new ArrayList<>();
        CommandHandler.getCommandMap().forEach((key, value) -> {
            CommandInfo commandInfo = value.getClass().getAnnotation(CommandInfo.class);
            if(!commands.contains(commandInfo)) {
                commands.add(commandInfo);
            }
        });

        for (CommandInfo commandInfo : commands) {
            Logger.log(commandInfo.name() + " " + CommandHandler.arrayBuilder(commandInfo.aliases()) + " | " + commandInfo.description());
        }
    }
}
