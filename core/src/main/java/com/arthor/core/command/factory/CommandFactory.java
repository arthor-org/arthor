package com.arthor.core.command.factory;

import com.arthor.core.command.*;

public interface CommandFactory {

    AbstractCreateDeployCommand createDeployCommand();

    AbstractReplaceDeployCommand replaceDeployCommand();

    AbstractUpdateBlueGreenDeployCommand updateBlueGreenDeployCommand();

    AbstractDeleteDeployCommand deleteDeployCommand();

    AbstractRollbackDeployCommand createRollbackDeployCommand();

    AbstractPromoteDeployCommand createPromoteDeployCommand();

    AbstractScaleDeployCommand createScaleDeployCommand();

    AbstractDispatchExecuteCommand dispatchExecuteCommand();

    AbstractDeployExecuteCommand deployExecuteCommand();

    AbstractDeleteExecuteCommand deleteExecuteCommand();

}
