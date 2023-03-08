package com.arthor.core.command;

public class CommandResult<O> {

    private final boolean success;

    private String errorMessage;

    private O output;

    public CommandResult(boolean success) {
        this.success = success;
    }
    public CommandResult(O output, boolean success) {
        this.output = output;
        this.success = success;
    }

    public CommandResult(String errorMessage) {
        this.success = false;
        this.errorMessage = errorMessage;
    }

    public CommandResult(boolean success, String errorMessage) {
        this.success = success;
        this.errorMessage = errorMessage;
    }

    public static <O> CommandResult<O> success(O output) {
        return new CommandResult<>(output, Boolean.TRUE);
    }

    public static <O> CommandResult<O> success() {
        return new CommandResult<>(Boolean.TRUE);
    }

    public static <O> CommandResult<O> failure(String errorMessage) {
        return new CommandResult(errorMessage);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public O getOutput() {
        return output;
    }
}
