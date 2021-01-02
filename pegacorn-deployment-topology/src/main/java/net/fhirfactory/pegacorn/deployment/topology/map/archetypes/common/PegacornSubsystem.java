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

package net.fhirfactory.pegacorn.deployment.topology.map.archetypes.common;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;

import net.fhirfactory.pegacorn.petasos.model.resilience.mode.ResilienceModeEnum;
import org.apache.commons.lang3.StringUtils;

import net.fhirfactory.pegacorn.deployment.topology.map.model.DeploymentMapNodeElement;
import net.fhirfactory.pegacorn.util.PegacornProperties;

/**
 * 
 * @author Mark A Hunter
 *
 */
public abstract class PegacornSubsystem {

    abstract public void buildSubsystemNode(DeploymentMapNodeElement subsystem);
    abstract public Set<DeploymentMapNodeElement> buildConnectedSystemSet();

    abstract protected ResilienceModeEnum specifyResilienceMode();

    //
    // Service Ports (External)
    //

    abstract protected String getDefaultServiceDNSEntry();
    abstract protected int getDefaultServiceInteractBasePort();
    abstract protected int getDefaultServicePetasosBasePort();
    abstract protected int getDefaultServiceEdgeReceiveBasePort();
    abstract protected int getDefaultServiceEdgeAnswerBasePort();
    
    //
    // Processing Plant Ports (External)
    //

    abstract protected int getDefaultProcessingPlantInteractBasePort();
    abstract protected int getDefaultProcessingPlantPetasosBasePort();
    abstract protected int getDefaultProcessingPlantEdgeReceiveBasePort();
    abstract protected int getDefaultProcessingPlantEdgeAnswerBasePort();
    abstract protected String getDefaultProcessingPlantDNSEntry();

    //
    // Property Name Resolvers
    //

    private String getPropertyNameInItsNameSpace(String propertyName) {
        return getDefaultServiceDNSEntry() + "_" + propertyName;
    }
    
    public String getProperty(String propertyName, String defaultValue) {
        return PegacornProperties.getProperty(getPropertyNameInItsNameSpace(propertyName), defaultValue);        
    }
    
    public Integer getProperty(String propertyName, Integer defaultValue) {
        return PegacornProperties.getIntegerProperty(getPropertyNameInItsNameSpace(propertyName), defaultValue);        
    }

    public String getMandatoryProperty(String propertyName) {
        return PegacornProperties.getMandatoryProperty(getPropertyNameInItsNameSpace(propertyName));        
    }


    //
    // Service Ports & Values
    //

    /**
     * @return the external to ACT Health DNS entry
     */
    public String getServiceDNSEntry() {
        String defaultValue = getDefaultServiceDNSEntry();
        return getProperty("SERVICE_DNS_ENTRY", defaultValue);
    }

    /**
     * @return the Interact Service port exposed to other Pegacorn components or to any external firewall/load-balancer.
     */
    public Integer getServiceInteractBasePort() {
        Integer defaultValue = getDefaultServiceInteractBasePort();
        Integer valueTestedAgainstProperties = getProperty("SERVICE_INTERACT_BASE_PORT", defaultValue);
        return (valueTestedAgainstProperties);
    }

    /**
     * @param offset the port offset from the base port
     * @return the exposed service (kubernetes) service port
     */
    protected Integer getServiceInteractPort(int offset) {
        Integer portNo = getServiceInteractBasePort() + offset;
        return portNo;
    }

    private Integer getServiceEdgeReceiveBasePort() {
        Integer defaultValue = getDefaultServiceEdgeReceiveBasePort();
        Integer valueTestedAgainstProperties = getProperty("SERVICE_EDGE_RECEIVE_BASE_PORT", defaultValue);
        return (valueTestedAgainstProperties);
    }

    /**
     * @param offset the port offset from the base port
     * @return the exposed kubernetes service port
     */
    protected Integer getServiceEdgeReceivePort(int offset) {
        Integer portNo = getServiceEdgeReceiveBasePort() + offset;
        return (portNo);
    }

    private Integer getServicePetasosBasePort() {
        Integer defaultValue = getDefaultServicePetasosBasePort();
        Integer valueTestedAgainstProperties = getProperty("SERVICE_PETASOS_BASE_PORT", defaultValue);
        return (valueTestedAgainstProperties);
    }

    /**
     * @param offset the port offset from the base port
     * @return the exposed kubernetes service port
     */
    protected Integer getServicePetasosPort(int offset) {
        Integer portNo = getServicePetasosBasePort() + offset;
        return (portNo);
    }

    /**
     * @param offset the port offset from the base port
     * @return the exposed kubernetes service port
     */
    protected Integer getServiceEdgeAnswerPort(int offset) {
        Integer portNo = getServiceEdgeAnswerBasePort() + offset;
        return (portNo);
    }

    private Integer getServiceEdgeAnswerBasePort() {
        Integer defaultValue = getDefaultServiceEdgeAnswerBasePort();
        Integer valueTestedAgainstProperties = getProperty("SERVICE_EDGE_ANSWER_BASE_PORT", defaultValue);
        return (valueTestedAgainstProperties);
    }

    //
    // Processing Plant Ports & Values
    //

    public String getDNSEntryInsideKubernetes(String defaultValue) {
        String value = getProperty("KUBERNETES_SERVICE_NAME", StringUtils.EMPTY);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }                
        return value + "." + getMandatoryProperty("KUBERNETES_NAMESPACE");
    }

    public String getProcessingPlantDNSEntry() {
        String defaultValue = getDefaultProcessingPlantDNSEntry();
        String dnsEntry = null;
        switch(specifyResilienceMode()){
            case RESILIENCE_MODE_KUBERNETES_CLUSTERED:
            case RESILIENCE_MODE_KUBERNETES_MULTISITE:
            case RESILIENCE_MODE_KUBERNETES_STANDALONE:
                dnsEntry = getProperty("PROCESSING_PLANT_DNS_ENTRY", getDNSEntryInsideKubernetes(defaultValue));
                break;
            case RESILIENCE_MODE_CLUSTERED:
            case RESILIENCE_MODE_MULTISITE:
            case RESILIENCE_MODE_STANDALONE:
            default:
                String dnsEntryForHostname;
                try {
                    dnsEntryForHostname = InetAddress.getLocalHost().getHostName();
                } catch (UnknownHostException unknownHost){
                    dnsEntryForHostname = defaultValue;
                }
                dnsEntry = getProperty("PROCESSING_PLANT_DNS_ENTRY",dnsEntryForHostname );
        }
        return (dnsEntry);
    }

    protected Integer getProcessingPlantInteractBasePort() {
        Integer defaultValue = getDefaultProcessingPlantInteractBasePort();
        Integer valueTestedAgainstProperties = getProperty("PROCESSING_PLANT_INTERACT_BASE_PORT", defaultValue);
        return (valueTestedAgainstProperties);
    }
    
    /**
     * @param offset the port offset from the base port
     * @return the internal port used on the Pod/Container
     */
    protected Integer getProcessingPlantInteractPort(int offset) {
        Integer portNo = getProcessingPlantInteractBasePort() + offset;
        return (portNo);
    }

    private Integer getProcessingPlantEdgeReceiveBasePort() {
        Integer defaultValue = getDefaultProcessingPlantEdgeReceiveBasePort();
        Integer valueTestedAgainstProperties = getProperty("PROCESSING_PLANT_EDGE_RECEIVE_BASE_PORT", defaultValue);
        return (valueTestedAgainstProperties);
    }
    
    /**
     * @param offset the port offset from the base port
     * @return the internal port used on the Pod/Container
     */
    protected Integer getProcessingPlantEdgeReceiverPort(int offset) {
        Integer portNo = getProcessingPlantEdgeReceiveBasePort() + offset;
        return (portNo);
    }


    private Integer getProcessingPlantPetasosBasePort() {
        Integer defaultValue = getDefaultProcessingPlantPetasosBasePort();
        Integer valueTestedAgainstProperties = getProperty("PROCESSING_PLANT_PETASOS_BASE_PORT", defaultValue);
        return (valueTestedAgainstProperties);
    }

    /**
     * @param offset the port offset from the base port
     * @return the internal port used on the Pod/Container
     */
    protected Integer getProcessingPlantPetasosPort(int offset) {
        Integer portNo = getProcessingPlantPetasosBasePort() + offset;
        return (portNo);
    }

    /**
     * @param offset the port offset from the base port
     * @return the exposed kubernetes service port
     */
    protected Integer getProcessingPlantEdgeAnswerPort(int offset) {
        Integer portNo = getProcessingPlantEdgeAnswerBasePort() + offset;
        return (portNo);
    }
        
    private Integer getProcessingPlantEdgeAnswerBasePort() {
        Integer defaultValue = getDefaultProcessingPlantEdgeAnswerBasePort();
        Integer valueTestedAgainstProperties = getProperty("PROCESSING_PLANT_EDGE_ANSWER_BASE_PORT", defaultValue);
        return (valueTestedAgainstProperties);
    }

}
