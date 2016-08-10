package com.divae.ageto.hybris.install;

import java.io.File;
import java.util.Arrays;

import com.divae.ageto.hybris.install.extensions.Core;
import com.divae.ageto.hybris.install.extensions.Extension;
import com.divae.ageto.hybris.install.task.TaskChainTask;
import com.divae.ageto.hybris.install.task.TaskContext;
import com.divae.ageto.hybris.version.HybrisVersion;

/**
 * @author Klaus Hauschild
 */
public class InstallHybrisArtifacts {

    private final TaskContext   taskContext;
    private final TaskChainTask installTasks;

    public InstallHybrisArtifacts(final File hybrisDirectory) {
        final HybrisVersion hybrisVersion = HybrisVersion.of(hybrisDirectory);
        taskContext = new TaskContext(hybrisVersion, hybrisDirectory);
        installTasks = new TaskChainTask(InstallStrategy.getInstallTasks(taskContext, Arrays.<Extension>asList(new Core())));
    }

    public void execute() {
        installTasks.execute(taskContext);
    }

    public TaskContext getTaskContext() {
        return taskContext;
    }

}
