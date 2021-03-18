package net.fhirfactory.pegacorn.deployment.topology.map.ladon.virtualdb;

import net.fhirfactory.pegacorn.deployment.names.common.SubsystemBaseNames;
import net.fhirfactory.pegacorn.deployment.names.subsystems.LadonVirtualDBDBaaSSubsystemComponentNames;
import net.fhirfactory.pegacorn.deployment.topology.map.common.archetypes.fhirpersistence.dm.JPAServerDMSubsystemTopologyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class LadonVirtualDBDMTopologyFactory extends JPAServerDMSubsystemTopologyFactory {
    private static final Logger LOG = LoggerFactory.getLogger(LadonVirtualDBDMTopologyFactory.class);

    @Inject
    private LadonVirtualDBDBaaSSubsystemComponentNames dBaaSSubsystemComponentNames;

    @Override
    protected SubsystemBaseNames specifySubsystemBaseNames() {
        return (dBaaSSubsystemComponentNames);
    }

    @Override
    protected Logger getLogger() {
        return (LOG);
    }
}
