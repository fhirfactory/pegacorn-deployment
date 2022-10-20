package net.fhirfactory.pegacorn.deployment.topology.factories.archetypes.base.common;

import net.fhirfactory.pegacorn.core.model.componentid.SoftwareComponentTypeEnum;
import net.fhirfactory.pegacorn.core.model.componentid.TopologyNodeRDN;
import net.fhirfactory.pegacorn.util.PegacornProperties;
import org.slf4j.Logger;

import javax.inject.Inject;

public abstract class TopologyFactoryHelpersBase {

    @Inject
    private PegacornProperties pegacornProperties;

    //
    // Abstract Methods
    //

    protected abstract Logger getLogger();

    //
    // Getters (and Setters)
    //

    protected PegacornProperties getPegacornProperties(){
        return(pegacornProperties);
    }

    //
    // Node Builders
    //

    protected String getActualHostIP(){
        String actualHostIP = pegacornProperties.getProperty("MY_HOST_IP", "Unknown");
        return(actualHostIP);
    }

    protected String getActualPodIP(){
        String actualHostIP = pegacornProperties.getProperty("MY_POD_IP", "Unknown");
        return(actualHostIP);
    }

    public TopologyNodeRDN createSimpleNodeRDN(String nodeName, String nodeVersion, SoftwareComponentTypeEnum nodeType){
        getLogger().debug(".createNodeRDN: Entry, nodeName->{}, nodeVersion->{}, nodeType->{}", nodeName, nodeVersion, nodeType);
        TopologyNodeRDN newRDN = new TopologyNodeRDN(nodeType, nodeName, nodeVersion);
        getLogger().debug(".createNodeRDN: Exit, newRDN->{}", newRDN);
        return (newRDN);
    }
}
