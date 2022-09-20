/*
 * The MIT License
 *
 * Copyright 2020 Mark A. Hunter.
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
package net.fhirfactory.pegacorn.deployment.topology.solution;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import net.fhirfactory.dricats.model.configuration.filebased.archetypes.SolutionPropertyFile;
import net.fhirfactory.pegacorn.deployment.topology.factories.interfaces.SolutionNodeFactoryInterface;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import net.fhirfactory.dricats.model.component.datatypes.ComponentTypeDefinition;
import net.fhirfactory.dricats.model.component.valuesets.ComponentTypeEnum;
import net.fhirfactory.dricats.model.topology.nodes.SolutionTopologyNode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Mark A Hunter
 *
 */
public abstract class SolutionNodeTopologyFactory implements SolutionNodeFactoryInterface {

    private boolean initialised;
    private SolutionTopologyNode solutionTopologyNode;

    private SolutionPropertyFile solutionPropertyFile;

    //
    // Default Constructor
    //

    public SolutionNodeTopologyFactory(){
        this.initialised = false;
    }

    //
    // Abstract Methods
    //

    abstract protected String specifySystemName();
    abstract protected String specifySystemVersion();
    abstract protected Logger specifyLogger();

    abstract protected String specifySolutionPropertyFilename();

    //
    // Getters (and Setters)
    //

    public String getSolutionPropertyFilename(){
        return(specifySolutionPropertyFilename());
    }

    //
    // Post Construct Initialiser
    //

    @PostConstruct @Override
    public void initialise(){
        if(!isInitialised()) {
            getLogger().debug(".initialise(): Entry");
            getLogger().debug(".initialise(): buildSolutionNode() --> Start");
            this.solutionTopologyNode = newSolutionNode();
            getLogger().debug(".initialise(): buildSolutionNode() --> Finish");
            setInitialised(true);
        }
    }

    //
    // Getters (and Setters)
    //

    public boolean isInitialised() {
        return initialised;
    }

    public void setInitialised(boolean initialised) {
        this.initialised = initialised;
    }

    @Override
    public SolutionTopologyNode getSolutionTopologyNode() {
        return solutionTopologyNode;
    }

    public Logger getLogger(){
        return(specifyLogger());
    }

    protected void setSolutionTopologyNode(SolutionTopologyNode solutionTopologyNode) {
        this.solutionTopologyNode = solutionTopologyNode;
    }

    public SolutionPropertyFile getSolutionPropertyFile() {
        return solutionPropertyFile;
    }

    protected void setSolutionPropertyFile(SolutionPropertyFile solutionPropertyFile) {
        this.solutionPropertyFile = solutionPropertyFile;
    }

    /**
     * Solution Node Builder
     *
     * @param
     * @return
     */
    public SolutionTopologyNode newSolutionNode(){
        getLogger().debug(".buildSolutionNode(): Entry");
        SolutionTopologyNode solution = new SolutionTopologyNode();
        solution.setParticipantName(specifySystemName());
        solution.setParticipantDisplayName(specifySystemName());
        ComponentTypeDefinition componentType = new ComponentTypeDefinition();
        componentType.setComponentArchetype(ComponentTypeEnum.SOLUTION);
        componentType.setTypeName("Solution");
        componentType.setDisplayTypeName("Solution-->"+solution.getParticipantName());
        solution.setNodeType(componentType);
        getLogger().debug(".buildSolutionNode(): Exit, solution ->{}", solution);
        return(solution);
    }

    protected void readSolutionPropertyFile(){
        getLogger().warn(".readSolutionPropertyFile(): Entry, getSolutionPropertyFilename()->{}", getSolutionPropertyFilename());
        try {
            getLogger().trace(".readSolutionPropertyFile(): Establish YAML ObjectMapper");
            ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
            yamlMapper.findAndRegisterModules();
            yamlMapper.configure(JsonParser.Feature.ALLOW_MISSING_VALUES, true);
            getLogger().warn(".readSolutionPropertyFile(): [Openning Configuration File] Start");
            Path path = Paths.get(getSolutionPropertyFilename());
            File file = path.toFile();
            getLogger().warn(".readSolutionPropertyFile(): [Openning Configuration File] End");
            getLogger().warn(".readSolutionPropertyFile(): [Importing Configuration File] Start");
            setSolutionPropertyFile(yamlMapper.readValue(file, SolutionPropertyFile.class));
            getLogger().warn(".readSolutionPropertyFile(): [Read YAML Configuration File] Finish");
            getLogger().debug(".readSolutionPropertyFile(): Exit, file loaded, getSolutionPropertyFile()->{}", getSolutionPropertyFile());
            return;
        } catch(FileNotFoundException noFile){
            getLogger().error(".readSolutionPropertyFile(): Configuration File->{} is not found, error->{}", getSolutionPropertyFilename(), noFile.getMessage());
        } catch(IOException ioError){
            getLogger().error(".readSolutionPropertyFile(): Configuration File->{} could not be loaded, error->{}", getSolutionPropertyFilename(), ioError.getMessage());
        }
        getLogger().debug(".readSolutionPropertyFile(): failed to load file");
    }

}
