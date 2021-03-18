package net.fhirfactory.pegacorn.deployment.topology.map.ladon.virtualdb;

import net.fhirfactory.pegacorn.deployment.names.common.SubsystemBaseNames;
import net.fhirfactory.pegacorn.deployment.names.subsystems.LadonVirtualDBDBaaSSubsystemComponentNames;
import net.fhirfactory.pegacorn.deployment.topology.map.common.archetypes.fhirpersistence.dbaas.JPAServerDBaaSSubsystemTopologyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class LadonVirtualDBDBaaSTopologyFactory extends JPAServerDBaaSSubsystemTopologyFactory {
    private static final Logger LOG = LoggerFactory.getLogger(LadonVirtualDBDBaaSTopologyFactory.class);

    @Inject
    private LadonVirtualDBDBaaSSubsystemComponentNames dbaasSubsystemComponentNames;

    @Override
    protected SubsystemBaseNames specifySubsystemBaseNames() {
        return (dbaasSubsystemComponentNames);
    }

    @Override
    protected Logger getLogger() {
        return (LOG);
    }
}
