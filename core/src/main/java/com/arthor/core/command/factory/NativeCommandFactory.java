package com.arthor.core.command.factory;

import com.arthor.core.command.*;

public class NativeCommandFactory implements CommandFactory {

    @Override
    public NativeCreateDeployCommand createDeployCommand() {
        return new NativeCreateDeployCommand();
    }

    @Override
    public NativeReplaceDeployCommand replaceDeployCommand() {
        return new NativeReplaceDeployCommand();
    }

    @Override
    public NativeUpdateBlueGreenDeployCommand updateBlueGreenDeployCommand() {
        return new NativeUpdateBlueGreenDeployCommand();
    }

    @Override
    public NativeDeleteDeployCommand deleteDeployCommand() {
        return new NativeDeleteDeployCommand();
    }

    @Override
    public NativeRollbackDeployCommand createRollbackDeployCommand() {
        return new NativeRollbackDeployCommand();
    }

    @Override
    public NativePromoteDeployCommand createPromoteDeployCommand() {
        return new NativePromoteDeployCommand();
    }

    @Override
    public NativeScaleDeployCommand createScaleDeployCommand() {
        return new NativeScaleDeployCommand();
    }

    @Override
    public DefaultDispatchExecuteCommand dispatchExecuteCommand() {
        return new DefaultDispatchExecuteCommand();
    }

    @Override
    public NativeDeployExecuteCommand deployExecuteCommand() {
        return new NativeDeployExecuteCommand();
    }

    @Override
    public NativeDeleteExecuteCommand deleteExecuteCommand() {
        return new NativeDeleteExecuteCommand();
    }

}
