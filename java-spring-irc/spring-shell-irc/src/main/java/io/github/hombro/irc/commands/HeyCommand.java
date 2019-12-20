package io.github.hombro.irc.commands;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class HeyCommand {

    @ShellMethod("first")
    public String hey() {
        return "hello";
    }
}
