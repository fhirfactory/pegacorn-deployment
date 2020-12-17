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

import java.util.Set;

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

    protected String getDefaultDNSEntry() {
        throw new IllegalStateException("Subclass must override this method to refer to DNS entries");        
    }
    protected int getDefaultBasePort() {
        throw new IllegalStateException("Subclass must override this method to use applciation ports");                
    }
    protected int getDefaultPetasosBasePort() {
        throw new IllegalStateException("Subclass must override this method to use petasos ports");                        
    }
    protected int getDefaultEdgeReceiverBasePort() {
        throw new IllegalStateException("Subclass must override this method to use edge receiver ports");                                
    }
    protected int getDefaultFHIRBasePort() {
        throw new IllegalStateException("Subclass must override this method to use FHIR ports");                                
    }
    
    private static final Integer DEFAULT_EXTERNAL_PORT = Integer.valueOf(443);

    protected int getDefaultBasePortInsidePod() {
        return getDefaultBasePort();
    }
    
    protected int getDefaultPetasosBasePortInsidePod() {
        return getDefaultPetasosBasePort();
    }
    
    protected int getDefaultEdgeReceiverBasePortInsidePod() {
        return getDefaultEdgeReceiverBasePort();
    }       
    
    protected int getDefaultFHIRBasePortInsidePod() {
        return getDefaultFHIRBasePort();
    }       
    
    private String getPropertyNameInItsNameSpace(String propertyName) {
        return getDefaultDNSEntry() + "_" + propertyName;
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
        
    /**
     * @param defaultValue
     * @return the external to ACT Health DNS entry
     */
    public String getExternalDNSEntry(String defaultValue) {
        return getProperty("EXTERNAL_DNS_ENTRY", getDNSEntry(defaultValue));
    }

    /**
     * @return the external to ACT Health port
     */
    public Integer getExternalPort() {
        return getExternalPort(DEFAULT_EXTERNAL_PORT);
    }
    public Integer getExternalPort(Integer defaultValue) {
        return getProperty("EXTERNAL_PORT", defaultValue);
    }

    public String getDNSEntry(String defaultValue) {
        return getProperty("DNS_ENTRY", getDNSEntryInsideKubernetes(defaultValue));
    }

    public String getDNSEntryInsideKubernetes(String defaultValue) {
        String value = getProperty("KUBERNETES_SERVICE_NAME", StringUtils.EMPTY);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }                
        return value + "." + getMandatoryProperty("KUBERNETES_NAMESPACE");
    }

    /**
     * @param offset the port offset from the base port
     * @return the exposed kubernetes service port
     */
    protected Integer getPort(int offset) {
        return getBasePort(getDefaultBasePort()) + offset;
    }
    
    private Integer getBasePort(Integer defaultValue) {
        return getProperty("BASE_PORT", defaultValue);
    }
    
    /**
     * @param offset the port offset from the base port
     * @return the internal port used on the Pod/Container
     */
    protected Integer getPortInsidePod(int offset) {
        return getProperty("BASE_PORT_INSIDE_POD", getBasePort(getDefaultBasePortInsidePod())) + offset;
    }

    /**
     * @param offset the port offset from the base port
     * @return the exposed kubernetes service port
     */
    protected Integer getEdgeReceiverPort(int offset) {
        return getEdgeReceiverBasePort(getDefaultEdgeReceiverBasePort()) + offset;
    }
        
    private Integer getEdgeReceiverBasePort(Integer defaultValue) {
        return getProperty("EDGE_RECEIVER_BASE_PORT", defaultValue);
    }
    
    /**
     * @param offset the port offset from the base port
     * @return the internal port used on the Pod/Container
     */
    protected Integer getEdgeReceiverPortInsidePod(int offset) {
        return getProperty("EDGE_RECEIVER_BASE_PORT_INSIDE_POD", getEdgeReceiverBasePort(getDefaultEdgeReceiverBasePortInsidePod())) + offset;
    }
    
    /**
     * @param offset the port offset from the base port
     * @return the exposed kubernetes service port
     */
    protected Integer getPetasosPort(int offset) {
        return getPetasosBasePort(getDefaultPetasosBasePort()) + offset;
    }
    
    private Integer getPetasosBasePort(Integer defaultValue) {
        return getProperty("PETASOS_BASE_PORT", defaultValue);
    }
    
    /**
     * @param offset the port offset from the base port
     * @return the internal port used on the Pod/Container
     */
    protected Integer getPetasosPortInsidePod(int offset) {
        return getProperty("PETASOS_BASE_PORT_INSIDE_POD", getPetasosBasePort(getDefaultPetasosBasePortInsidePod())) + offset;
    }

    /**
     * @param offset the port offset from the base port
     * @return the exposed kubernetes service port
     */
    protected Integer getFHIRPort(int offset) {
        return getFHIRBasePort(getDefaultFHIRBasePort()) + offset;
    }
        
    private Integer getFHIRBasePort(Integer defaultValue) {
        return getProperty("FHIR_BASE_PORT", defaultValue);
    }
    
    /**
     * @param offset the port offset from the base port
     * @return the internal port used on the Pod/Container
     */
    protected Integer getFHIRPortInsidePod(int offset) {
        return getProperty("FHIR_BASE_PORT_INSIDE_POD", getFHIRBasePort(getDefaultFHIRBasePortInsidePod())) + offset;
    }
}
