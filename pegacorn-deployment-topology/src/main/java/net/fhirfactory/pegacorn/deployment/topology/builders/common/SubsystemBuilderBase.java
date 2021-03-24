package net.fhirfactory.pegacorn.deployment.topology.builders.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.archetypes.BaseSubsystemPropertyFile;
import org.slf4j.Logger;

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
