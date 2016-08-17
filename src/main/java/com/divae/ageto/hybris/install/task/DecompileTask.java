package com.divae.ageto.hybris.install.task;

import java.io.File;
import java.util.logging.LogManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.google.common.base.Stopwatch;
import com.strobel.assembler.InputTypeLoader;
import com.strobel.decompiler.CommandLineOptions;
import com.strobel.decompiler.DecompilationOptions;
import com.strobel.decompiler.DecompilerDriver;
import com.strobel.decompiler.DecompilerSettings;

/**
 * @author Klaus Hauschild
 */
public class DecompileTask extends AbstractWorkDirectoryTask {

    private static final Logger LOGGER    = LoggerFactory.getLogger(DecompileTask.class);

    private static final String DECOMPILE = "decompile";

    private final File          sourceFile;
    private final File          destinationDirectory;

    DecompileTask(final File sourceFile, final File destinationDirectory) {
        this.sourceFile = sourceFile;
        this.destinationDirectory = destinationDirectory;
    }

    static boolean isEnabled(final TaskContext taskContext) {
        final Boolean decompile = (Boolean) taskContext.getParameter(DECOMPILE);
        if (decompile == null) {
            return false;
        }
        return decompile;
    }

    public static void activate(final TaskContext taskContext) {
        taskContext.setParameter(DECOMPILE, true);
    }

    @Override
    protected void execute(final TaskContext taskContext, final File workDirectory) {
        if (!isEnabled(taskContext)) {
            return;
        }
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        // java.util.logging.Logger.getLogger("global").setLevel(Level.FINEST);

        try {
            final File jarFile = new File(taskContext.getHybrisDirectory(), sourceFile.toString());
            LOGGER.debug(String.format("Decompiling %s", jarFile));
            final Stopwatch stopwatch = Stopwatch.createStarted();
            final CommandLineOptions commandLineOptions = new CommandLineOptions();
            final DecompilationOptions decompilationOptions = new DecompilationOptions();
            final DecompilerSettings decompilerSettings = decompilationOptions.getSettings();
            decompilerSettings.setOutputDirectory(new File(workDirectory, destinationDirectory.toString()).getAbsolutePath());
            decompilerSettings.setTypeLoader(new InputTypeLoader());
            DecompilerDriver.decompileJar(jarFile.getAbsolutePath(), commandLineOptions, decompilationOptions);
            LOGGER.debug(String.format("... took %s", stopwatch));
        } catch (final Exception exception) {
            throw new RuntimeException(exception);
        }
    }

}
