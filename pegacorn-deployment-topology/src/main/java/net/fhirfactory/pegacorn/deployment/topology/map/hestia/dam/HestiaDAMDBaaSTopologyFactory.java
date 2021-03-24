package net.fhirfactory.pegacorn.deployment.topology.map.hestia.dam;

import net.fhirfactory.pegacorn.deployment.names.common.SubsystemBaseNames;
import net.fhirfactory.pegacorn.deployment.names.subsystems.HestiaDAMDBaaSSubsystemComponentNames;
import net.fhirfactory.pegacorn.deployment.topology.map.common.archetypes.fhirpersistence.dbaas.JPAServerDBaaSSubsystemTopologyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class HestiaDAMDBaaSTopologyFactory extends JPAServerDBaaSSubsystemTopologyFactory {
    private static final Logger LOG = LoggerFactory.getLogger(HestiaDAMDBaaSTopologyFactory.class);

    @Inject
    private HestiaDAMDBaaSSubsystemComponentNames componentNames;

    @Override
    protected SubsystemBaseNames specifySubsystemBaseNames() {
        return (componentNames);
    }

    @Override
    protected Logger getLogger() {
        return null;
    }
}
