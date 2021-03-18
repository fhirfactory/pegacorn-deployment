package net.fhirfactory.pegacorn.deployment.topology.model.common;

import net.fhirfactory.pegacorn.deployment.topology.model.mode.ResilienceModeEnum;

import java.util.ArrayList;

public class IPCInterface {
    private TopologyNode enablingTopologyEndpoint;
    private String instanceName;
    private ArrayList<ResilienceModeEnum> supportedDeploymentModes;
    private ArrayList<IPCInterfaceDefinition> supportedInterfaceDefinitions;

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public ArrayList<ResilienceModeEnum> getSupportedDeploymentModes() {
        return supportedDeploymentModes;
    }

    public void setSupportedDeploymentModes(ArrayList<ResilienceModeEnum> supportedDeploymentModes) {
        this.supportedDeploymentModes = supportedDeploymentModes;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IPCInterface)) {
            return false;
        }
        IPCInterface that = (IPCInterface) o;
        if(getSupportedDeploymentModes().size() != that.getSupportedDeploymentModes().size()){
            return(false);
        }
        if(getEnablingTopologyEndpoint() == null && that.getEnablingTopologyEndpoint() != null){
            return(false);
        }
        if(getEnablingTopologyEndpoint() != null && that.getEnablingTopologyEndpoint() == null){
            return(false);
        }
        if(!getEnablingTopologyEndpoint().equals(that.getEnablingTopologyEndpoint())){
            return(false);
        }
        if(!(getEnablingTopologyEndpoint().getNodeFDN().toString().contentEquals((that.getEnablingTopologyEndpoint().getNodeFDN().toString())))){
            return(false);
        }
        boolean nameSame = getInstanceName().contentEquals(that.getInstanceName());
        boolean modesSame = true;
        for(ResilienceModeEnum currentMode: getSupportedDeploymentModes()){
            boolean isInOther = false;
            for(ResilienceModeEnum currentOtherMode: that.getSupportedDeploymentModes()){
                if(currentMode.equals(currentOtherMode)){
                    isInOther = true;
                    break;
                }
            }
            if(!isInOther){
                return(false);
            }
        }
        return(true);
    }

    public TopologyNode getEnablingTopologyEndpoint() {
        return enablingTopologyEndpoint;
    }

    public void setEnablingTopologyEndpoint(TopologyNode enablingTopologyEndpoint) {
        this.enablingTopologyEndpoint = enablingTopologyEndpoint;
    }

    public ArrayList<IPCInterfaceDefinition> getSupportedInterfaceDefinitions() {
        return supportedInterfaceDefinitions;
    }

    public void setSupportedInterfaceDefinitions(ArrayList<IPCInterfaceDefinition> supportedInterfaceDefinitions) {
        this.supportedInterfaceDefinitions = supportedInterfaceDefinitions;
    }

    public ArrayList<String> getSupportInterfaceTags(){
        ArrayList<String> tags = new ArrayList<>();
        for(IPCInterfaceDefinition ipcInterface: getSupportedInterfaceDefinitions()){
            tags.add(ipcInterface.getInterfaceDefinitionTag());
        }
        return(tags);
    }

    public boolean supportsInterface(String interfaceTag ){
        for(IPCInterfaceDefinition ipcInterface: getSupportedInterfaceDefinitions()){
            if(interfaceTag.contentEquals(ipcInterface.getInterfaceDefinitionTag())){
                return(true);
            }
        }
        return(false);
    }
}
