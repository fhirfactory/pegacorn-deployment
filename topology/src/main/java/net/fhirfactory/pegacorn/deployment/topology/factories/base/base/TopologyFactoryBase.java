/*
 * The MIT License
 *
 * Copyright 2022 Mark A. Hunter (ACT Health).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.fhirfactory.pegacorn.deployment.topology.factories.base.base;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import net.fhirfactory.dricats.configuration.api.services.common.PlatformInformationHelper;
import net.fhirfactory.dricats.model.component.valuesets.ComponentTypeEnum;
import net.fhirfactory.dricats.model.configuration.filebased.archetypes.SolutionPropertyFile;
import net.fhirfactory.dricats.model.configuration.filebased.archetypes.common.BaseSubsystemPropertyFile;
import net.fhirfactory.dricats.model.topology.nodes.datatypes.TopologyNodeRDN;
import org.slf4j.Logger;

/**
 *
 * @author mhunter
 */
public abstract class TopologyFactoryBase extends PlatformInformationHelper {
    
    private BaseSubsystemPropertyFile subsystemPropertyFile;
    private SolutionPropertyFile solutionPropertyFile;
    

    
    //
    // Constructor(s)
    //
    
    public TopologyFactoryBase(){
        super();
    }
    
    //
    // Abstract Methods
    //
    
    abstract protected Logger getLogger();
    abstract protected String specifyPropertyFileName();
    abstract protected Class specifyPropertyFileClass();
    
    //
    // Getters (and Setters)
    //

    public BaseSubsystemPropertyFile getSubsystemPropertyFile() {
        return subsystemPropertyFile;
    }

    public void setSubsystemPropertyFile(BaseSubsystemPropertyFile subsystemPropertyFile) {
        this.subsystemPropertyFile = subsystemPropertyFile;
    }
    
    public SolutionPropertyFile getSolutionPropertyFile() {
        return solutionPropertyFile;
    }

    public void setSolutionPropertyFile(SolutionPropertyFile solutionPropertyFile) {
        this.solutionPropertyFile = solutionPropertyFile;
    }
   
    
    //
    // Business Methods
    //
    
    protected void readSubsystemPropertyFile(){
        String propertyFileName = specifyPropertyFileName();
        Class propertyFileClass = specifyPropertyFileClass();
        String fileName = getPropertyHelper().getMandatoryProperty("")
        getLogger().warn(".readSubsystemPropertyFile(): Entry, propertyFileName->{}", propertyFileName);
        try {
            getLogger().trace(".readSubsystemPropertyFile(): Establish YAML ObjectMapper");
            ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
            yamlMapper.findAndRegisterModules();
            yamlMapper.configure(JsonParser.Feature.ALLOW_MISSING_VALUES, true);
            getLogger().warn(".readSubsystemPropertyFile(): [Opening Configuration File] Start");
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Path path = Paths.get(propertyFileName);
            File file = path.toFile();
            getLogger().warn(".readSubsystemPropertyFile(): [Opening Configuration File] End");
            getLogger().warn(".readSubsystemPropertyFile(): [Importing Configuration File] Start");
            setSubsystemPropertyFile((BaseSubsystemPropertyFile) yamlMapper.readValue(file, propertyFileClass));
            getLogger().warn(".readSubsystemPropertyFile(): [Read YAML Configuration File] Finish");
            getLogger().debug(".readSubsystemPropertyFile(): Exit, file loaded, propertyFile->{}", this.subsystemPropertyFile);
            return;
        } catch(FileNotFoundException noFile){
            getLogger().error(".readSubsystemPropertyFile(): Configuration File->{} is not found, error->{}", propertyFileName, noFile.getMessage());
        } catch(IOException ioError){
            getLogger().error(".readSubsystemPropertyFile(): Configuration File->{} could not be loaded, error->{}", propertyFileName, ioError.getMessage());
        }
        getLogger().debug(".readSubsystemPropertyFile(): failed to load file");
    }
    

    
    //
    // Node Builders Helpers
    //

    protected String getActualHostIP(){
        String actualHostIP = getPropertyHelper().getProperty("MY_HOST_IP", "Unknown");
        return(actualHostIP);
    }

    protected String getActualPodIP(){
        String actualHostIP = getPropertyHelper().getProperty("MY_POD_IP", "Unknown");
        return(actualHostIP);
    }

    public TopologyNodeRDN createSimpleNodeRDN(String nodeName, String nodeVersion, ComponentTypeEnum nodeType){
        getLogger().debug(".createNodeRDN: Entry, nodeName->{}, nodeVersion->{}, nodeType->{}", nodeName, nodeVersion, nodeType);
        TopologyNodeRDN newRDN = new TopologyNodeRDN(nodeType, nodeName, nodeVersion);
        getLogger().debug(".createNodeRDN: Exit, newRDN->{}", newRDN);
        return (newRDN);
    }
    
}
