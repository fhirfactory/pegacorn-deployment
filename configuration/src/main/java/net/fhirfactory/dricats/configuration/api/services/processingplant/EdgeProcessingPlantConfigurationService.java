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
package net.fhirfactory.dricats.configuration.api.services.processingplant;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import net.fhirfactory.dricats.configuration.api.interfaces.PetasosConfigurationFileService;
import net.fhirfactory.dricats.model.configuration.filebased.archetypes.PetasosClusterPropertyFile;
import net.fhirfactory.dricats.model.configuration.filebased.archetypes.SolutionPropertyFile;
import net.fhirfactory.dricats.model.configuration.filebased.archetypes.common.EdgeSubsystemPropertyFile;
import net.fhirfactory.dricats.model.configuration.filebased.segments.endpoints.fileshare.FileShareEndpointSegment;
import net.fhirfactory.dricats.model.configuration.filebased.segments.endpoints.http.HTTPClientEndpointSegment;
import net.fhirfactory.dricats.model.configuration.filebased.segments.endpoints.http.HTTPServerEndpointSegment;
import net.fhirfactory.dricats.model.configuration.filebased.segments.endpoints.interact.InteractClientEndpointSegment;
import net.fhirfactory.dricats.model.configuration.filebased.segments.endpoints.interact.InteractClusteredServerEndpointSegment;
import net.fhirfactory.dricats.model.configuration.filebased.segments.endpoints.interact.InteractSocketBasedClientEndpointSegment;
import net.fhirfactory.dricats.model.configuration.filebased.segments.endpoints.mllp.MLLPReceiverEndpointSegment;
import net.fhirfactory.dricats.model.configuration.filebased.segments.endpoints.mllp.MLLPSenderEndpointSegment;
import net.fhirfactory.dricats.model.configuration.filebased.segments.endpoints.tap.TAPReceiverEndpointSegment;
import net.fhirfactory.dricats.model.configuration.filebased.segments.endpoints.tap.TAPSenderEndpointSegment;
import net.fhirfactory.dricats.util.SystemPropertyHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@ApplicationScoped
public class EdgeProcessingPlantConfigurationService {
    private static final Logger LOG = LoggerFactory.getLogger(EdgeProcessingPlantConfigurationService.class);
    private EdgeSubsystemPropertyFile processingPlantPropertyFile;
    private SolutionPropertyFile solutionPropertyFile;
    private PetasosClusterPropertyFile petasosPropertyFile;

    @Inject
    private SystemPropertyHelper propertyHelper;

    //
    // Constructor
    //

    public EdgeProcessingPlantConfigurationService(){
        loadEdgeProcessingPlantConfigurationFile();
        loadSolutionConfigurationFile();
    }

    //
    // PostConstructor
    //

    //
    // Getters (and Setters)
    //

    public EdgeSubsystemPropertyFile getProcessingPlantPropertyFile(){
        return(processingPlantPropertyFile);
    }

    protected Logger getLogger(){
        return(LOG);
    }

    public SolutionPropertyFile getSolutionPropertyFile() {
        return solutionPropertyFile;
    }

    public PetasosClusterPropertyFile getPetasosPropertyFile() {
        return petasosPropertyFile;
    }

    //
    // Configuration File Loader
    //

    protected void loadEdgeProcessingPlantConfigurationFile(){
        getLogger().debug(".loadEdgeProcessingPlantConfigurationFile(): Entry");
        getLogger().debug(".loadEdgeProcessingPlantConfigurationFile(): [Deriving Configuration Filename] Start");
        String configurationFileName = propertyHelper.getMandatoryProperty("DEPLOYMENT_CONFIG_FILE");
        if(configurationFileName == null){
            throw(new RuntimeException("Cannot load configuration file!!!! (SUBSYSTEM-CONFIG_FILE="+configurationFileName+")"));
        }
        getLogger().debug(".loadEdgeProcessingPlantConfigurationFile(): [Deriving Configuration Filename] Finish, filename->{}", configurationFileName);
        try {
            getLogger().trace(".loadEdgeProcessingPlantConfigurationFile(): Establish YAML ObjectMapper");
            ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
            yamlMapper.findAndRegisterModules();
            yamlMapper.configure(JsonParser.Feature.ALLOW_MISSING_VALUES, true);
            getLogger().warn(".loadEdgeProcessingPlantConfigurationFile(): [Opening Configuration File] Start");
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Path path = Paths.get(configurationFileName);
            File file = path.toFile();
            getLogger().warn(".loadEdgeProcessingPlantConfigurationFile(): [Opening Configuration File] End");
            getLogger().warn(".loadEdgeProcessingPlantConfigurationFile(): [Importing Configuration File] Start");
            this.processingPlantPropertyFile = yamlMapper.readValue(file, EdgeSubsystemPropertyFile.class);
            getLogger().warn(".loadEdgeProcessingPlantConfigurationFile(): [Read YAML Configuration File] Finish");
        } catch(FileNotFoundException noFile){
            getLogger().error(".loadEdgeProcessingPlantConfigurationFile(): Configuration File->{} is not found, error->{}", configurationFileName, noFile.getMessage());
        } catch(IOException ioError){
            getLogger().error(".loadEdgeProcessingPlantConfigurationFile(): Configuration File->{} could not be loaded, error->{}", configurationFileName, ioError.getMessage());
        }
        getLogger().debug(".loadEdgeProcessingPlantConfigurationFile(): Exit, file loaded, propertyFile->{}", getProcessingPlantPropertyFile());
    }

    protected void loadSolutionConfigurationFile(){
        getLogger().debug(".loadSolutionConfigurationFile(): Entry");
        getLogger().debug(".loadSolutionConfigurationFile(): [Deriving Configuration Filename] Start");
        String solutionConfigFilename = getProcessingPlantPropertyFile().getConfigFiles().getSolutionConfigFilename();
        if(solutionConfigFilename == null){
            throw(new RuntimeException("Cannot load configuration file!!!! solutionConfigFilename->"+solutionConfigFilename+")"));
        }
        getLogger().debug(".loadSolutionConfigurationFile(): [Deriving Configuration Filename] Finish, solutionConfigFilename->{}", solutionConfigFilename);
        try {
            getLogger().trace(".loadSolutionConfigurationFile(): Establish YAML ObjectMapper");
            ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
            yamlMapper.findAndRegisterModules();
            yamlMapper.configure(JsonParser.Feature.ALLOW_MISSING_VALUES, true);
            getLogger().warn(".loadSolutionConfigurationFile(): [Opening Configuration File] Start");
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Path path = Paths.get(solutionConfigFilename);
            File file = path.toFile();
            getLogger().warn(".loadSolutionConfigurationFile(): [Opening Configuration File] End");
            getLogger().warn(".loadSolutionConfigurationFile(): [Importing Configuration File] Start");
            this.solutionPropertyFile = yamlMapper.readValue(file, SolutionPropertyFile.class);
            getLogger().warn(".loadSolutionConfigurationFile(): [Read YAML Configuration File] Finish");
        } catch(FileNotFoundException noFile){
            getLogger().error(".loadSolutionConfigurationFile(): Configuration File->{} is not found, error->{}", solutionConfigFilename, noFile.getMessage());
        } catch(IOException ioError){
            getLogger().error(".loadSolutionConfigurationFile(): Configuration File->{} could not be loaded, error->{}", solutionConfigFilename, ioError.getMessage());
        }
        getLogger().debug(".loadSolutionConfigurationFile(): Exit, file loaded, getSolutionPropertyFile->{}", getSolutionPropertyFile());
    }

    protected void loadPetasosClusterConfigurationFile(){
        getLogger().debug(".loadPetasosClusterConfigurationFile(): Entry");
        getLogger().debug(".loadPetasosClusterConfigurationFile(): [Deriving Configuration Filename] Start");
        String petasosClusterConfigFilename = getProcessingPlantPropertyFile().getConfigFiles().getPetasosConfigFilename();
        if(petasosClusterConfigFilename == null){
            throw(new RuntimeException("Cannot load configuration file!!!! petasosClusterConfigFilename->"+petasosClusterConfigFilename+")"));
        }
        getLogger().debug(".loadPetasosClusterConfigurationFile(): [Deriving Configuration Filename] Finish, petasosClusterConfigFilename->{}", petasosClusterConfigFilename);
        try {
            getLogger().trace(".loadPetasosClusterConfigurationFile(): Establish YAML ObjectMapper");
            ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
            yamlMapper.findAndRegisterModules();
            yamlMapper.configure(JsonParser.Feature.ALLOW_MISSING_VALUES, true);
            getLogger().warn(".loadPetasosClusterConfigurationFile(): [Opening Configuration File] Start");
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Path path = Paths.get(petasosClusterConfigFilename);
            File file = path.toFile();
            getLogger().warn(".loadPetasosClusterConfigurationFile(): [Opening Configuration File] End");
            getLogger().warn(".loadPetasosClusterConfigurationFile(): [Importing Configuration File] Start");
            this.petasosPropertyFile = yamlMapper.readValue(file, PetasosClusterPropertyFile.class);
            getLogger().warn(".loadPetasosClusterConfigurationFile(): [Read YAML Configuration File] Finish");
        } catch(FileNotFoundException noFile){
            getLogger().error(".loadPetasosClusterConfigurationFile(): Configuration File->{} is not found, error->{}", petasosClusterConfigFilename, noFile.getMessage());
        } catch(IOException ioError){
            getLogger().error(".loadPetasosClusterConfigurationFile(): Configuration File->{} could not be loaded, error->{}", petasosClusterConfigFilename, ioError.getMessage());
        }
        getLogger().debug(".loadPetasosClusterConfigurationFile(): Exit, file loaded, getPetasosPropertyFile->{}", getPetasosPropertyFile());
    }

    //
    // Configuration Access Methods
    //

    //
    // FileShare Endpoints

    public boolean hasFileShareEndpointConfig(String instanceName){
        if(getProcessingPlantPropertyFile().getFileShareSet().getFileShareSegments() == null){
            return(false);
        }
        if(getProcessingPlantPropertyFile().getFileShareSet().getFileShareSegments().isEmpty()){
            return(false);
        }
        for(FileShareEndpointSegment currentSegment: getProcessingPlantPropertyFile().getFileShareSet().getFileShareSegments()){
            if(currentSegment.getName().contentEquals(instanceName)){
                return(true);
            }
        }
        return(false);
    }

    public FileShareEndpointSegment getFileShareEndpointConfig(String instanceName){
        if(getProcessingPlantPropertyFile().getFileShareSet().getFileShareSegments() == null){
            return(null);
        }
        if(getProcessingPlantPropertyFile().getFileShareSet().getFileShareSegments().isEmpty()){
            return(null);
        }
        FileShareEndpointSegment foundSegment = null;
        for(FileShareEndpointSegment currentSegment: getProcessingPlantPropertyFile().getFileShareSet().getFileShareSegments()){
            if(currentSegment.getName().contentEquals(instanceName)){
                foundSegment = currentSegment;
                break;
            }
        }
        return(foundSegment);
    }

    //
    // HTTP Endpoints

    public boolean hasHTTPServerEndpointConfig(String instanceName){
        if(getProcessingPlantPropertyFile().getHttpEndpointSet().getHttpServers() == null){
            return(false);
        }
        if(getProcessingPlantPropertyFile().getHttpEndpointSet().getHttpServers().isEmpty()){
            return(false);
        }
        for(HTTPServerEndpointSegment currentSegment: getProcessingPlantPropertyFile().getHttpEndpointSet().getHttpServers()){
            if(currentSegment.getName().contentEquals(instanceName)){
                return(true);
            }
        }
        return(false);
    }

    public HTTPServerEndpointSegment getHTTPServerEndpointConfig(String instanceName){
        if(getProcessingPlantPropertyFile().getHttpEndpointSet().getHttpServers() == null){
            return(null);
        }
        if(getProcessingPlantPropertyFile().getHttpEndpointSet().getHttpServers().isEmpty()){
            return(null);
        }
        HTTPServerEndpointSegment foundSegment = null;
        for(HTTPServerEndpointSegment currentSegment: getProcessingPlantPropertyFile().getHttpEndpointSet().getHttpServers()){
            if(currentSegment.getName().contentEquals(instanceName)){
                foundSegment = currentSegment;
                break;
            }
        }
        return(foundSegment);
    }

    public boolean hasHTTPClientEndpointConfig(String instanceName){
        if(getProcessingPlantPropertyFile().getHttpEndpointSet().getHttpClients() == null){
            return(false);
        }
        if(getProcessingPlantPropertyFile().getHttpEndpointSet().getHttpClients().isEmpty()){
            return(false);
        }
        for(HTTPClientEndpointSegment currentSegment: getProcessingPlantPropertyFile().getHttpEndpointSet().getHttpClients()){
            if(currentSegment.getName().contentEquals(instanceName)){
                return(true);
            }
        }
        return(false);
    }

    public HTTPClientEndpointSegment getHTTPClientEndpointConfig(String instanceName){
        if(getProcessingPlantPropertyFile().getHttpEndpointSet().getHttpClients() == null){
            return(null);
        }
        if(getProcessingPlantPropertyFile().getHttpEndpointSet().getHttpClients().isEmpty()){
            return(null);
        }
        HTTPClientEndpointSegment foundSegment = null;
        for(HTTPClientEndpointSegment currentSegment: getProcessingPlantPropertyFile().getHttpEndpointSet().getHttpClients()){
            if(currentSegment.getName().contentEquals(instanceName)){
                foundSegment = currentSegment;
                break;
            }
        }
        return(foundSegment);
    }

    //
    // MLLP Endpoints

    public boolean hasMLLPSenderEndpointConfig(String instanceName){
        if(getProcessingPlantPropertyFile().getMllpEndpointSet().getMllpSenders() == null){
            return(false);
        }
        if(getProcessingPlantPropertyFile().getMllpEndpointSet().getMllpSenders().isEmpty()){
            return(false);
        }
        for(MLLPSenderEndpointSegment currentSegment: getProcessingPlantPropertyFile().getMllpEndpointSet().getMllpSenders()){
            if(currentSegment.getName().contentEquals(instanceName)){
                return(true);
            }
        }
        return(false);
    }

    public MLLPSenderEndpointSegment getMLLPSenderEndpointConfig(String instanceName){
        if(getProcessingPlantPropertyFile().getMllpEndpointSet().getMllpSenders() == null){
            return(null);
        }
        if(getProcessingPlantPropertyFile().getMllpEndpointSet().getMllpSenders().isEmpty()){
            return(null);
        }
        MLLPSenderEndpointSegment foundSegment = null;
        for(MLLPSenderEndpointSegment currentSegment: getProcessingPlantPropertyFile().getMllpEndpointSet().getMllpSenders()){
            if(currentSegment.getName().contentEquals(instanceName)){
                foundSegment = currentSegment;
                break;
            }
        }
        return(foundSegment);
    }

    public boolean hasMLLPReceiverEndpointConfig(String instanceName){
        if(getProcessingPlantPropertyFile().getMllpEndpointSet().getMllpReceivers() == null){
            return(false);
        }
        if(getProcessingPlantPropertyFile().getMllpEndpointSet().getMllpReceivers().isEmpty()){
            return(false);
        }
        for(MLLPReceiverEndpointSegment currentSegment: getProcessingPlantPropertyFile().getMllpEndpointSet().getMllpReceivers()){
            if(currentSegment.getName().contentEquals(instanceName)){
                return(true);
            }
        }
        return(false);
    }

    public MLLPReceiverEndpointSegment getMLLPReceiverEndpointConfig(String instanceName){
        if(getProcessingPlantPropertyFile().getMllpEndpointSet().getMllpReceivers() == null){
            return(null);
        }
        if(getProcessingPlantPropertyFile().getMllpEndpointSet().getMllpReceivers().isEmpty()){
            return(null);
        }
        MLLPReceiverEndpointSegment foundSegment = null;
        for(MLLPReceiverEndpointSegment currentSegment: getProcessingPlantPropertyFile().getMllpEndpointSet().getMllpReceivers()){
            if(currentSegment.getName().contentEquals(instanceName)){
                foundSegment = currentSegment;
                break;
            }
        }
        return(foundSegment);
    }

    //
    // MLLP Endpoints

    public boolean hasTAPSenderEndpointConfig(String instanceName){
        if(getProcessingPlantPropertyFile().getTapEndpointSet().getTapSenders() == null){
            return(false);
        }
        if(getProcessingPlantPropertyFile().getTapEndpointSet().getTapSenders().isEmpty()){
            return(false);
        }
        for(TAPSenderEndpointSegment currentSegment: getProcessingPlantPropertyFile().getTapEndpointSet().getTapSenders()){
            if(currentSegment.getName().contentEquals(instanceName)){
                return(true);
            }
        }
        return(false);
    }

    public TAPSenderEndpointSegment getTAPSenderEndpointConfig(String instanceName){
        if(getProcessingPlantPropertyFile().getTapEndpointSet().getTapSenders() == null){
            return(null);
        }
        if(getProcessingPlantPropertyFile().getTapEndpointSet().getTapSenders().isEmpty()){
            return(null);
        }
        TAPSenderEndpointSegment foundSegment = null;
        for(TAPSenderEndpointSegment currentSegment: getProcessingPlantPropertyFile().getTapEndpointSet().getTapSenders()){
            if(currentSegment.getName().contentEquals(instanceName)){
                foundSegment = currentSegment;
                break;
            }
        }
        return(foundSegment);
    }

    public boolean hasTAPReceiverEndpointConfig(String instanceName){
        if(getProcessingPlantPropertyFile().getTapEndpointSet().getTapReceivers() == null){
            return(false);
        }
        if(getProcessingPlantPropertyFile().getTapEndpointSet().getTapReceivers().isEmpty()){
            return(false);
        }
        for(TAPReceiverEndpointSegment currentSegment: getProcessingPlantPropertyFile().getTapEndpointSet().getTapReceivers()){
            if(currentSegment.getName().contentEquals(instanceName)){
                return(true);
            }
        }
        return(false);
    }

    public TAPReceiverEndpointSegment getTAPReceiverEndpointConfig(String instanceName){
        if(getProcessingPlantPropertyFile().getTapEndpointSet().getTapReceivers() == null){
            return(null);
        }
        if(getProcessingPlantPropertyFile().getTapEndpointSet().getTapReceivers().isEmpty()){
            return(null);
        }
        TAPReceiverEndpointSegment foundSegment = null;
        for(TAPReceiverEndpointSegment currentSegment: getProcessingPlantPropertyFile().getTapEndpointSet().getTapReceivers()){
            if(currentSegment.getName().contentEquals(instanceName)){
                foundSegment = currentSegment;
                break;
            }
        }
        return(foundSegment);
    }

    //
    // Other Interact Ports

    public boolean hasSocketBasedClientEndpointConfig(String instanceName){
        if(getProcessingPlantPropertyFile().getOtherEndpointSet().getSocketBasedClients() == null){
            return(false);
        }
        if(getProcessingPlantPropertyFile().getOtherEndpointSet().getSocketBasedClients().isEmpty()){
            return(false);
        }
        for(InteractSocketBasedClientEndpointSegment currentSegment: getProcessingPlantPropertyFile().getOtherEndpointSet().getSocketBasedClients()){
            if(currentSegment.getName().contentEquals(instanceName)){
                return(true);
            }
        }
        return(false);
    }

    public InteractSocketBasedClientEndpointSegment getSocketBasedClientEndpointConfig(String instanceName){
        if(getProcessingPlantPropertyFile().getOtherEndpointSet().getSocketBasedClients() == null){
            return(null);
        }
        if(getProcessingPlantPropertyFile().getOtherEndpointSet().getSocketBasedClients().isEmpty()){
            return(null);
        }
        InteractSocketBasedClientEndpointSegment foundSegment = null;
        for(InteractSocketBasedClientEndpointSegment currentSegment: getProcessingPlantPropertyFile().getOtherEndpointSet().getSocketBasedClients()){
            if(currentSegment.getName().contentEquals(instanceName)){
                foundSegment = currentSegment;
                break;
            }
        }
        return(foundSegment);
    }

    public boolean hasSocketBasedServerEndpointConfig(String instanceName){
        if(getProcessingPlantPropertyFile().getOtherEndpointSet().getSocketBasedServers() == null){
            return(false);
        }
        if(getProcessingPlantPropertyFile().getOtherEndpointSet().getSocketBasedServers().isEmpty()){
            return(false);
        }
        for(InteractClusteredServerEndpointSegment currentSegment: getProcessingPlantPropertyFile().getOtherEndpointSet().getSocketBasedServers()){
            if(currentSegment.getName().contentEquals(instanceName)){
                return(true);
            }
        }
        return(false);
    }

    public InteractClusteredServerEndpointSegment getSocketBasedServerEndpointConfig(String instanceName){
        if(getProcessingPlantPropertyFile().getOtherEndpointSet().getSocketBasedServers() == null){
            return(null);
        }
        if(getProcessingPlantPropertyFile().getOtherEndpointSet().getSocketBasedServers().isEmpty()){
            return(null);
        }
        InteractClusteredServerEndpointSegment foundSegment = null;
        for(InteractClusteredServerEndpointSegment currentSegment: getProcessingPlantPropertyFile().getOtherEndpointSet().getSocketBasedServers()){
            if(currentSegment.getName().contentEquals(instanceName)){
                foundSegment = currentSegment;
                break;
            }
        }
        return(foundSegment);
    }

    public boolean hasGenericClientEndpointConfig(String instanceName){
        if(getProcessingPlantPropertyFile().getOtherEndpointSet().getGenericClients() == null){
            return(false);
        }
        if(getProcessingPlantPropertyFile().getOtherEndpointSet().getGenericClients().isEmpty()){
            return(false);
        }
        for(InteractClientEndpointSegment currentSegment: getProcessingPlantPropertyFile().getOtherEndpointSet().getGenericClients()){
            if(currentSegment.getName().contentEquals(instanceName)){
                return(true);
            }
        }
        return(false);
    }

    public InteractClientEndpointSegment getGenericClientEndpointConfig(String instanceName){
        if(getProcessingPlantPropertyFile().getOtherEndpointSet().getGenericClients() == null){
            return(null);
        }
        if(getProcessingPlantPropertyFile().getOtherEndpointSet().getGenericClients().isEmpty()){
            return(null);
        }
        InteractClientEndpointSegment foundSegment = null;
        for(InteractClientEndpointSegment currentSegment: getProcessingPlantPropertyFile().getOtherEndpointSet().getGenericClients()){
            if(currentSegment.getName().contentEquals(instanceName)){
                foundSegment = currentSegment;
                break;
            }
        }
        return(foundSegment);
    }
}
