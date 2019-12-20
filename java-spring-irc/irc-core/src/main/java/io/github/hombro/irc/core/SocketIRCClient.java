package io.github.hombro.irc.core;

import java.io.*;
import java.net.Socket;
import java.util.Optional;

public class SocketIRCClient implements IRCClient {

    private final String server;
    private final int port;

    private String channel;
    private String username;
    private String nickName;

    private Socket socket;
    private BufferedWriter writer;
    private BufferedReader reader;

    public SocketIRCClient(String server, int port, String channel, String username, String nickName) {
        this.server = server;
        this.port = port;

        this.channel = channel;
        this.username = username;
        this.nickName = nickName;
    }

    private void connect() throws IOException {
        socket = new Socket(server, port);
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void disconnect() {
        socket = null;
        writer = null;
        reader = null;
    }

    private void send(String cmd, String... msg) {
        if (isConnected() && !socket.isClosed()) {
            String joined = String.join(" ", msg);
            String full = String.format("%s %s\r\n", cmd.toUpperCase(), joined);

            try {
                writer.write(full);
                writer.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void quit() {
        quit("");
    }

    public void sendMessage(String message) {
        send("PRIVMSG", channel, ":" + message);
    }

    public void quit(String message) {
        send("QUIT", message);
        disconnect();
    }

    public void setAwayMessage(String message) {
        send("AWAY", message);
    }

    public void removeAwayMessage() {
        send("AWAY", "");
    }

    public void join(String channel) {
        send("JOIN", channel);
    }

    public void listChannels(String server) {
        send("LIST", server);
    }

    public void changeChannel(String channel) {
        this.channel = channel;
        join(channel);
    }

    public void listChannels() {
        listChannels(server);
    }

    public void listUsers() {
        listUsers(channel);
    }

    public void listUsers(String channel) {
        send("NAMES", channel);
    }

    public void setNickname(String nickname) {
        send("NICK", nickname);
    }

    public void pong(String server) {
        send("PONG", server);
    }

    public void login() throws IOException {
        connect();
        send("NICK", nickName);
        send("USER", username, "8", "*", nickName);

        try {
            String response = null;
            while ((response = reader.readLine()) != null) {
                if (response.contains("004")) {
                    break;
                } else if (response.contains("443")) {
                    throw new RuntimeException(response);
                }
            }
            join(channel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<String> check() {
        try {
            Optional<String> msg = Optional.ofNullable(reader.readLine());
            if (msg.isPresent() && msg.get().startsWith("PING ")) {
                pong(msg.get().substring(5));
                return Optional.empty();
            }
            return msg;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public String getChannel() {
        return channel;
    }

    public String getNickName() {
        return nickName;
    }

    @Override
    public boolean isConnected() {
        return socket != null;
    }
}
