package com.arthor.core.command.executor;

import com.arthor.core.command.Command;
import com.arthor.core.command.CommandResult;
import com.arthor.core.command.context.CommandContext;
import com.arthor.core.command.context.CommandContextFactory;

public class DefaultCommandExecutor implements CommandExecutor {

    private final CommandContextFactory commandContextFactory;

    public DefaultCommandExecutor(CommandContextFactory commandContextFactory) {
        this.commandContextFactory = commandContextFactory;
    }

    @Override
    public <I, O> CommandResult<O> execute(Command<I, O> command, I input) {
        CommandContext commandContext = commandContextFactory.createCommandContext(this);
        return command.execute(commandContext, input);
    }
}
