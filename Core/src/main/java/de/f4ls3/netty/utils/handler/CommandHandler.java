package de.f4ls3.netty.utils.handler;

import de.f4ls3.netty.impl.abstracts.CommandExecutor;
import de.f4ls3.netty.interfaces.CommandInfo;
import de.f4ls3.netty.impl.abstracts.Handler;
import de.f4ls3.netty.utils.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandHandler extends Handler {

    private static HashMap<String, CommandExecutor> commandMap = new HashMap<>();
    private static boolean isRunning = false;

    public static String arrayBuilder(String[] array) {
        StringBuilder builder = new StringBuilder();

        builder.append("[");
        for (int i = 0; i < array.length; i++) {
            if((i + 1) == array.length) {
                builder.append(array[i]);
            } else {
                builder.append(array[i] + ", ");
            }
        }
        builder.append("]");

        return builder.toString();
    }

    public void registerCommand(CommandExecutor commandExecutor) {
        if(commandExecutor == null) return;

        if(commandExecutor.getClass().isAnnotationPresent(CommandInfo.class)) {
            CommandInfo commandInfo = commandExecutor.getClass().getAnnotation(CommandInfo.class);
            List<String> l = new ArrayList<>();

            l.add(commandInfo.name());
            for (String alias : commandInfo.aliases()) {
                l.add(alias);
            }
            for (String s : l) {
                commandMap.put(s, commandExecutor);
            }

            Logger.log("Registered Command \"" + commandInfo.name() + "\" with " + commandInfo.aliases().length + " aliases " + arrayBuilder(commandInfo.aliases()));
        }
    }

    @Override
    public void handle() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String line;
            this.isRunning = true;
            System.out.print("$ ");
            while((line = reader.readLine().toLowerCase()) != null && this.isRunning) {
                if(line.length() == 0) continue;
                String[] args = line.replace("$ ", "").split(" ");

                if(commandMap.containsKey(args[0])) {
                    CommandExecutor commandExecutor = commandMap.get(args[0]);
                    commandExecutor.execute(args);

                } else {
                    Logger.warn("Command \"" + args[0].toUpperCase() + "\" not found!");
                }
                System.out.print("$ ");
            }

        } catch (Exception e) {
            Logger.log("Please repeat the command you just entered");
            handle();
        }
    }

    public static void stop() {
        isRunning = false;
    }

    public static HashMap<String, CommandExecutor> getCommandMap() {
        return commandMap;
    }
}
