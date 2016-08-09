package com.divae.ageto.hybris.codegenerator;

import java.io.File;

import com.divae.ageto.hybris.install.InstallHybrisArtifacts;
import com.divae.ageto.hybris.install.task.AbstractWorkDirectoryTask;
import com.divae.ageto.hybris.utils.EnvironmentUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * @author Klaus Hauschild
 */
public class CodeGeneratorIT {

    private File workDirectory;

    @BeforeTest
    public void before() {
        final File hybrisInstallationDirectory = EnvironmentUtils.getHybrisInstallationDirectory();
        final InstallHybrisArtifacts installHybrisArtifacts = new InstallHybrisArtifacts(hybrisInstallationDirectory);
        try {
            installHybrisArtifacts.execute();
        } catch (final Exception exception) {
            // expected exception, ignore it!
        }
        workDirectory = AbstractWorkDirectoryTask.getWorkDirectory(installHybrisArtifacts.getTaskContext());
    }

    @Test
    public void generateTest() {
        // workDirectory = new File("C:\\Users\\mhaagen\\AppData\\Local\\Temp\\1470725779461-0");
        CodeGenerator.generate(workDirectory);
    }

}
