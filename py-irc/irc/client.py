from irc.api import SocketClient


class IRCClient:
    def __init__(self):
        self.irc = SocketClient()

    def next_line(self):
        return self.irc.receive()
