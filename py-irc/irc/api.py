import socket


class SocketClient:
    def __init__(self):
        self.socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.nickname = ""
        self.username = ""
        self.channel = ""
        self.server = ""
        self.port = -1
        self.connected = False

    def send(self, cmd: str, *args):
        s = f"{cmd} {' '.join(args)}\r\n"
        self.socket.send(s.encode("utf-8"))

    def _receive_internal(self):
        return self.socket.recv(512).decode("utf-8").strip()

    def receive(self):
        if self.connected:
            return self._receive_internal()
        else:
            return ""

    def send_nick(self):
        self.send("NICK", self.nickname)

    def send_user(self):
        self.send("USER", self.username, "8", "*", self.nickname)

    def message(self, message: str):
        self.send("PRIVMSG", self.channel, ":" + message)

    def join(self):
        self.send("JOIN", self.channel)

    def quit(self, message: str = None):
        if message is None:
            message = ""
        if self.socket is not None:
            self.send("QUIT", message)
        self.connected = False
        self.socket.close()

    def connect(self):
        self.socket.connect((self.server, self.port))
        self.send_nick()
        self.send_user()
        while not self.connected:
            r = self._receive_internal()
            if r is not None and len(r) > 0:
                print(r)
                if "366" in r:
                    self.connected = True
                elif "376" in r:
                    self.join()
                elif "No Ident response" in r:
                    self.send_nick()
                    self.send_user()
                elif "443" in r:
                    raise RuntimeError(r)
