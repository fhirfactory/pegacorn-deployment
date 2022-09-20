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

package net.fhirfactory.pegacorn.deployment.topology.factories.processingplant;

import net.fhirfactory.pegacorn.deployment.topology.factories.base.PetasosBasedSystemTopologyFactory;
import net.fhirfactory.pegacorn.deployment.topology.factories.base.endpoints.MLLPTopologyEndpointFactory;

import javax.inject.Inject;
import net.fhirfactory.dricats.model.configuration.filebased.archetypes.subsystems.mitaf.MITaFSubsystemPropertyFile;
import net.fhirfactory.dricats.model.configuration.filebased.segments.endpoints.mllp.MLLPReceiverEndpointSegment;
import net.fhirfactory.dricats.model.configuration.filebased.segments.endpoints.mllp.MLLPSenderEndpointSegment;
import net.fhirfactory.dricats.model.topology.endpoints.mllp.MLLPClientETN;
import net.fhirfactory.dricats.model.topology.endpoints.mllp.MLLPServerETN;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.workunitprocessors.mllp.MLLPReceiverProcessorTN;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.workunitprocessors.mllp.MLLPSenderProcessorTN;

/**
 * 
 * @author Mark A Hunter
 *
 */
public abstract class MITaFSubsystemTopologyFactory extends PetasosBasedSystemTopologyFactory {

    @Inject
    private MLLPTopologyEndpointFactory mllpTopologyEndpointFactory;

    //
    // Build an MLLP Server Endpoint
    //

    protected MLLPServerETN newMLLPServerEndpoint(MLLPReceiverProcessorTN mllpReceiverProcessor, String endpointFunctionName, MLLPReceiverEndpointSegment mllpServerPort){
        getLogger().debug(".createMLLPServerEndpoint(): Entry, mllpReceiverProcessor->{}, mllpServerPort->{}", mllpReceiverProcessor, mllpServerPort);
        if(mllpServerPort == null){
            getLogger().debug(".createMLLPServerEndpoint(): Exit, no port to add");
            return(null);
        }
        if(!(getSubsystemPropertyFile() instanceof MITaFSubsystemPropertyFile)){
            getLogger().error(".createMLLPServerEndpoint(): Exit, configuration is not suitable for MLLP Endpoint!");
            return(null);  
        }
        MITaFSubsystemPropertyFile mitafPropertyFile = (MITaFSubsystemPropertyFile)getSubsystemPropertyFile();
        MLLPServerETN mllpServerEndpoint = mllpTopologyEndpointFactory.newMLLPServerEndpoint(mitafPropertyFile, mllpReceiverProcessor, endpointFunctionName, mllpServerPort);
        getLogger().info(".createMLLPServerEndpoint(): Exit, endpoint added->{}", mllpServerEndpoint);
        return(mllpServerEndpoint);
    }

    //
    // Build an MLLP Client Endpoint
    //

    protected MLLPClientETN newMLLPClientEndpoint(MLLPSenderProcessorTN mllpSenderProcessor, String endpointFunctionName, MLLPSenderEndpointSegment mllpClientPort){
        getLogger().debug(".newMLLPClientEndpoint(): Entry, mllpSenderProcessor->{}, mllpClientPort->{}", mllpSenderProcessor, mllpClientPort);
        if(mllpClientPort == null){
            getLogger().debug(".newMLLPClientEndpoint(): Exit, no port to add");
            return(null);
        }
        if(!(getSubsystemPropertyFile() instanceof MITaFSubsystemPropertyFile)){
            getLogger().error(".createMLLPServerEndpoint(): Exit, configuration is not suitable for MLLP Endpoint!");
            return(null);  
        }
        MITaFSubsystemPropertyFile mitafPropertyFile = (MITaFSubsystemPropertyFile)getSubsystemPropertyFile();
        MLLPClientETN mllpClientEndpoint = mllpTopologyEndpointFactory.newMLLPClientEndpoint(mitafPropertyFile, mllpSenderProcessor, endpointFunctionName, mllpClientPort);
        getLogger().debug(".newMLLPClientEndpoint(): Exit, endpoint added");
        return(mllpClientEndpoint);
    }
}
