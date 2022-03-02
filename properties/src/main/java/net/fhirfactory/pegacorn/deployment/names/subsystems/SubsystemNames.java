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
package net.fhirfactory.pegacorn.deployment.names.subsystems;

import net.fhirfactory.pegacorn.core.interfaces.auditing.PetasosAuditEventServiceProviderNameInterface;
import net.fhirfactory.pegacorn.core.interfaces.oam.subscriptions.PetasosSubscriptionReportingServiceProviderNameInterface;
import net.fhirfactory.pegacorn.core.interfaces.oam.topology.PetasosTopologyReportingServiceProviderNameInterface;
import net.fhirfactory.pegacorn.core.interfaces.tasks.PetasosTaskRepositoryServiceProviderNameInterface;

public abstract class SubsystemNames
        implements PetasosTaskRepositoryServiceProviderNameInterface,
        PetasosTopologyReportingServiceProviderNameInterface,
        PetasosSubscriptionReportingServiceProviderNameInterface,
        PetasosAuditEventServiceProviderNameInterface {

    abstract protected String specifyDeploymentShortName();

    public String getLadonIM(){
        String ladonName =  specifyDeploymentShortName() + "-ladon-im";
        return(ladonName);
    }

    public String getLadonIMParticipantName(){
        String ladonName =  "Ladon.IM";
        return(ladonName);
    }

    public String getFHIRPlaceIM(){
        String ladonNexusName =  specifyDeploymentShortName() + "-fhirplace-im";
        return(ladonNexusName);
    }

    public String getHestiaDAMIM(){
        String hestiaDAMIM = specifyDeploymentShortName() + "-hestia-dam-im";
        return(hestiaDAMIM);
    }

    //
    // Hestia Audit Information Manager
    //

    public String getHestiaAuditIMParticipant(){
        String hestiaAuditIM = "Hestia.Audit.IM";
        return(hestiaAuditIM);
    }

    @Override
    public String getPetasosAuditEventServiceProviderName() {
        return (getHestiaAuditIMParticipant());
    }

    //
    // ITOps Information Manager
    //

    public String getITOpsIMParticipantName(){
        String itOpsIMName = "ITOps.IM";
        return(itOpsIMName);
    }

    @Override
    public String getPetasosTopologyReportingServiceProviderName() {
        return (getITOpsIMParticipantName());
    }

    @Override
    public String getPetasosSubscriptionReportingServiceProviderName() {
        return (getITOpsIMParticipantName());
    }

    //
    // Ladon Digital Twins
    //

    public String getPatientDigitalTwinSystemName() {
        String patientDigitalTwin = specifyDeploymentShortName() + "-ladon-digitaltwin-patient";
        return(patientDigitalTwin);
    }

    //
    // Ponos
    //

    public String getPonosManagerParticipantName(){
        String ponosManagerName = "Ponos.Manager";
        return(ponosManagerName);
    }

    @Override
    public String getPetasosTaskRepositoryServiceProviderName() {
        return (getPonosManagerParticipantName());
    }

}
