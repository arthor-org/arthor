package com.arthor.core.command;

import com.arthor.core.command.context.CommandContext;

public interface Command<I, O>  {

    CommandResult<O> execute(CommandContext commandContext, I input);

}
