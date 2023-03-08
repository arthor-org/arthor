package com.arthor.core.command.context;

import com.arthor.core.command.executor.CommandExecutor;

public interface CommandContextFactory {

    CommandContext createCommandContext(CommandExecutor commandExecutor);
}
