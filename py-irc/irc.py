import sys

import events
from irc.client import IRCClient


def display(l: str):
    print(f"irc>{l}")


@events.event_publisher(.25)
def command_reader(context: events.Context = None):
    l = sys.stdin.readline()
    if l is not None:
        context.publish("stdin", l.rstrip())


@events.event_handler(t="irc")
def new_output(element=None, context: events.Context = None):
    display(element)


@events.event_handler(t="stdin")
def command_processor(element=None, context: events.Context = None):
    client: IRCClient = context.attributes["irc"]
    words = element.split(" ")
    if words[0].lower() == "/quit":
        context.complete = True
        client.irc.quit()
        sys.exit(0)
    elif words[0].lower() == "/leave":
        client.irc.quit()
    elif words[0].lower() == "/user":
        client.irc.username = words[1]
    elif words[0].lower() == "/nick":
        client.irc.nickname = words[1]
    elif words[0].lower() == "/connect-to":
        client.irc.server = words[1]
        client.irc.port = int(words[2])
        client.irc.channel = words[3]
        client.irc.connect()
    else:
        client.irc.message(element)


@events.event_publisher(.5)
def irc_reader(context: events.Context = None):
    irc = context.attributes["irc"]
    resp = irc.next_line()
    while resp is not None:
        context.publish("stdin", resp)


def main():
    events.add_to_context("irc", IRCClient())
    events.run()


if __name__ == "__main__":
    main()
