import concurrent.futures as ft
import functools
import queue
import time
from typing import Tuple

_publishers = {}
_consumers = {}
_attrs = {}


class Context:
    def __init__(self, q: queue.Queue, executor: ft.ThreadPoolExecutor, attributes: dict):
        self._q = q
        self.executor = executor
        self.attributes = attributes
        self.complete = False

    def element(self) -> Tuple:
        if self._q.empty():
            return None
        else:
            return self._q.get()

    def publish(self, t: str, element: str):
        if element is not None and len(element) > 0:
            self._q.put((t, element))


def add_to_context(name: str, attr):
    _attrs[name] = attr


def run():
    q = queue.Queue()
    with ft.ThreadPoolExecutor(max_workers=len(_publishers) + 1) as executor:
        ctx = Context(q, executor, _attrs)
        for (k, v) in _publishers.items():
            part = functools.partial(_consumer(k, v), context=ctx)
            executor.submit(part)
        part = functools.partial(_do_consumers, context=ctx)
        executor.submit(part)
        executor.shutdown(True)


def _do_consumers(context: Context = None):
    while not context.complete:
        time.sleep(.25)
        ele = context.element()

        if ele is not None:
            for (c, t) in _consumers.items():
                try:
                    if t == ele[0]:
                        c(element=ele[1], context=context)
                except Exception as e:
                    print(e)


def event_publisher(pause: float = 1):
    def register(f):
        _publishers[f] = pause
        return f

    return register


def _consumer(f, pause):
    def wrap(context: Context = None):
        while not context.complete:
            time.sleep(pause)
            try:
                f(context=context)
            except Exception as e:
                print(e)

    return wrap


def event_handler(t=None):
    def register(f):
        _consumers[f] = t
        return f

    return register
