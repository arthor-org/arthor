package com.arthor.core.command.executor;

import com.arthor.core.command.Command;
import com.arthor.core.command.CommandResult;

public interface CommandExecutor {

    <I, O> CommandResult<O> execute(Command<I, O> command, I input);

}
