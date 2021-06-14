/*
 * Copyright (c) 2021 Mark A. Hunter (ACT Health)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.fhirfactory.pegacorn.deployment.topology.builders.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.archetypes.BaseSubsystemPropertyFile;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class SubsystemBuilderBase {
    protected abstract Logger getLogger();
    private static final String PROPERTY_FILENAME_EXTENSION = ".yaml";

    protected BaseSubsystemPropertyFile readPropertyFile(String fileName, Class propertyFileClass){
        getLogger().debug(".loadPropertyFile(): Entry");
        String configFilePath;
        BaseSubsystemPropertyFile propertyFile = null;
        if(fileName.contains(PROPERTY_FILENAME_EXTENSION)){
            configFilePath = fileName;
        } else {
            configFilePath = fileName + PROPERTY_FILENAME_EXTENSION;
        }
        try {
            ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
            yamlMapper.findAndRegisterModules();
            propertyFile = (BaseSubsystemPropertyFile) yamlMapper.readValue(new File(configFilePath), propertyFileClass);
            getLogger().debug(".loadPropertyFile(): Exit, file loaded");
            return(propertyFile);
        } catch(FileNotFoundException noFile){
            getLogger().error(".loadPropertyFile(): Configuraiton File --> {} is not found", configFilePath);
        } catch(IOException ioError){
            getLogger().error(".loadPropertyFile(): Configuration File --> {} could not be loaded!", configFilePath);
        }
        getLogger().debug(".loadPropertyFile(): failed to load file");
        return(null);
    }


}
