package com.divae.ageto.hybris;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * @author Klaus Hauschild
 */
public abstract class AbstractHybrisDirectoryMojo extends AbstractMojo {

    @Parameter(property = "hybris.hybrisDirectory", defaultValue = ".")
    private File hybrisDirectory;

    protected File getHybrisDirectory() {
        return hybrisDirectory;
    }

}
