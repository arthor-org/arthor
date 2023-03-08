package com.arthor.core.command.factory;

import com.arthor.core.command.*;

public class UncoupledCommandFactory implements CommandFactory {

    @Override
    public UncoupledCreateDeployCommand createDeployCommand() {
        return new UncoupledCreateDeployCommand();
    }

    @Override
    public UncoupledReplaceDeployCommand replaceDeployCommand() {
        return new UncoupledReplaceDeployCommand();
    }

    @Override
    public AbstractUpdateBlueGreenDeployCommand updateBlueGreenDeployCommand() {
        return new NativeUpdateBlueGreenDeployCommand();
    }

    @Override
    public AbstractDeleteDeployCommand deleteDeployCommand() {
        return new UncoupledDeleteDeployCommand();
    }

    @Override
    public AbstractRollbackDeployCommand createRollbackDeployCommand() {
        return new UncoupledRollbackDeployCommand();
    }

    @Override
    public AbstractPromoteDeployCommand createPromoteDeployCommand() {
        return new UncoupledPromoteDeployCommand();
    }

    @Override
    public AbstractScaleDeployCommand createScaleDeployCommand() {
        return new UncoupledScaleDeployCommand();
    }

    @Override
    public DefaultDispatchExecuteCommand dispatchExecuteCommand() {
        return new DefaultDispatchExecuteCommand();
    }

    @Override
    public UncoupledDeployExecuteCommand deployExecuteCommand() {
        return new UncoupledDeployExecuteCommand();
    }

    @Override
    public AbstractDeleteExecuteCommand deleteExecuteCommand() {
        return new UncoupledDeleteExecuteCommand();
    }

}
