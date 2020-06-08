# -*- coding: utf8 -*-

import logging
from typing import List
from collections.abc import Iterable, Iterator

from .abstract_command import AbstractCommand
from exceptions import DuplicateCommandIdException, InvalidArgumentException

PipelineId = str
CommandId = str


class Pipeline:
    def __init__(self, pipeline_id: PipelineId, commands=None):
        self.pipeline_id = pipeline_id
        if commands is None:
            commands = []
        else:
            for cmd in commands:
                if not isinstance(cmd, AbstractCommand):
                    raise InvalidArgumentException(
                        "elements of `commands` should be "
                        "an instance of `AbstractCommand`")
        self.commands = commands.copy()

    def add(self, inp):
        if isinstance(inp, AbstractCommand):
            inp = [inp]
        if isinstance(inp, (Iterable, Iterator)):
            commands_new = []
            for cmd in inp:
                if self._is_duplicate_command_id(
                        cmd.command_id):
                    raise DuplicateCommandIdException(
                        f"command ID '{cmd.command_id}' already exists")
                commands_new.append(cmd)
            self.commands += commands_new
        else:
            raise InvalidArgumentException(
                "argument `inp` should be an instance of "
                "`Iterable`, `Iterator` or `AbstractCommand`")

    def _is_duplicate_command_id(self, command_id):
        for cmd in self.commands:
            if command_id == cmd.command_id:
                return True
        return False

    def remove(self, command_id: CommandId):
        target_idx = len(self.commands)
        for idx, cmd in enumerate(self.commands):
            if command_id == cmd.command_id:
                target_idx = idx
                break
        if -len(self.commands) <= target_idx \
                < len(self.commands):
            return self.commands.pop(target_idx)
        else:
            return None

    def rearrange(self, command_id_list: List[CommandId], save=True):
        """Rearrange commands according to `command_id_list`

        Args:
            command_id_list:
            save: if `True`, those commands not mentioned in `command_id_list`
                will be placed at the end of the rearranged list, else they will
                be remove. Default: `True`

        Examples:
            >>> p = Pipeline("pipeline-1")
            ...
            >>> p.commands
            ["cmd-1", "cmd-2", "cmd-3", "cmd-4"]
            >>> p.rearrange(["cmd-2", "cmd-3", "cmd-1", "cmd-4"])
            >>> p.commands
            ["cmd-2", "cmd-3", "cmd-1", "cmd-4"]
            >>> p.rearrange(["cmd-4", "cmd-1", "cmd-3"], save=True)
            >>> p.commands
            ["cmd-4", "cmd-1", "cmd-3", "cmd-2"]
            >>> p.rearrange(["cmd-1", "cmd-2", "cmd-3"], save=False)
            >>> p.commands
            ["cmd-1", "cmd-2", "cmd-3"]
        """
        commands_new = []
        for cmd_id in command_id_list:
            cmd = self.remove(cmd_id)
            if cmd is not None:
                commands_new.append(cmd)
        if save:
            commands_new += self.commands
        self.commands = commands_new

    def run(self, **kwargs):
        to_be_passed = kwargs
        for cmd in self.commands:
            logging.info(f"Running {cmd.command_id} ...")
            to_be_passed = cmd(**to_be_passed)
            logging.info(f"{cmd.command_id} done!")

    def __str__(self):
        return f"{self.__class__.__name__}" \
               f"({self.commands})"

    def __repr__(self):
        return str(self)
