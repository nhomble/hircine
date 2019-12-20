package io.github.hombro.irc.core;

import java.io.IOException;
import java.util.Optional;

public interface IRCClient {
    void listUsers();

    void listChannels();

    void changeChannel(String channel);

    void sendMessage(String message);

    void quit();

    Optional<String> check();

    String getChannel();

    String getNickName();

    void login() throws IOException;

    boolean isConnected();
}
