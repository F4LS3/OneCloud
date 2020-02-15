package de.f4ls3.netty.impl;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Log {

    private List<Object> logContent;
    private File logOutput;

    public Log(File logOutput) {
        this.logContent = new ArrayList<>();
        this.logOutput = logOutput;
    }

    public Log append(Object o) {
        logContent.add(o);
        return this;
    }

    public Log appendAll(List<Object> objects) {
        for (Object o : objects) {
            logContent.add(o);
        }
        return this;
    }

    public void flush() {
        try {
            if(!this.getLogOutput().exists()) this.getLogOutput().createNewFile();

            FileWriter writer = new FileWriter(this.getLogOutput());
            for (Object o : this.getLogContent()) {
                writer.write(o.toString() + "\n");
            }
            writer.flush();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Object> getLogContent() {
        return logContent;
    }

    public File getLogOutput() {
        return logOutput;
    }
}
