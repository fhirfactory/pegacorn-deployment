package net.fhirfactory.pegacorn.deployment.topology.map.ladon.virtualdb;

import net.fhirfactory.pegacorn.deployment.names.common.SubsystemBaseNames;
import net.fhirfactory.pegacorn.deployment.topology.map.common.archetypes.fhirpersistence.im.FHIRIMSubsystemTopologyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LadonVirtualDBIMTopologyFactory extends FHIRIMSubsystemTopologyFactory {
    private static final Logger LOG = LoggerFactory.getLogger(LadonVirtualDBIMTopologyFactory.class);


    @Override
    protected SubsystemBaseNames specifySubsystemBaseNames() {
        return null;
    }

    @Override
    protected Logger getLogger() {
        return (LOG);
    }
}
