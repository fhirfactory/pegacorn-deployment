/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.fhirfactory.pegacorn.deployment.topology.map.model;

import net.fhirfactory.pegacorn.petasos.model.topology.EndpointElementTypeEnum;

/**
 *
 * @author mhunter
 */
public class DeploymentMapEndpointElement {

    private EndpointElementTypeEnum endpointType;
    private String endpointInstanceID;
    private String endpointFunctionID;
    private String friendlyName;

    private boolean isServer;
    private boolean requiresEncryption;

    private String version;
    
    private String internalDNSEntry;
    private String externalDNSEntry;
    private String internalPortNumber;
    private String externalPortNumber;

    public EndpointElementTypeEnum getEndpointType() {
        return endpointType;
    }

    public void setEndpointType(EndpointElementTypeEnum endpointType) {
        this.endpointType = endpointType;
    }

    public String getEndpointInstanceID() {
        return endpointInstanceID;
    }

    public void setEndpointInstanceID(String endpointInstanceID) {
        this.endpointInstanceID = endpointInstanceID;
    }

    public String getEndpointFunctionID() {
        return endpointFunctionID;
    }

    public void setEndpointFunctionID(String endpointFunctionID) {
        this.endpointFunctionID = endpointFunctionID;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public boolean isIsServer() {
        return isServer;
    }

    public void setIsServer(boolean isServer) {
        this.isServer = isServer;
    }

    public boolean isRequiresEncryption() {
        return requiresEncryption;
    }

    public void setRequiresEncryption(boolean requiresEncryption) {
        this.requiresEncryption = requiresEncryption;
    }

    public String getInternalDNSEntry() {
        return internalDNSEntry;
    }

    public void setInternalDNSEntry(String internalDNSEntry) {
        this.internalDNSEntry = internalDNSEntry;
    }

    public String getExternalDNSEntry() {
        return externalDNSEntry;
    }

    public void setExternalDNSEntry(String externalDNSEntry) {
        this.externalDNSEntry = externalDNSEntry;
    }

    public String getInternalPortNumber() {
        return internalPortNumber;
    }

    public void setInternalPortNumber(String internalPortNumber) {
        this.internalPortNumber = internalPortNumber;
    }

    public String getExternalPortNumber() {
        return externalPortNumber;
    }

    public void setExternalPortNumber(String externalPortNumber) {
        this.externalPortNumber = externalPortNumber;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
