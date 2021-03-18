package net.fhirfactory.pegacorn.deployment.topology.map.hestia.dam;

import net.fhirfactory.pegacorn.deployment.names.common.SubsystemBaseNames;
import net.fhirfactory.pegacorn.deployment.names.subsystems.HestiaDAMIMSubsystemComponentNames;
import net.fhirfactory.pegacorn.deployment.topology.map.common.archetypes.fhirpersistence.im.FHIRIMSubsystemTopologyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class HestiaDAMIMTopologyFactory extends FHIRIMSubsystemTopologyFactory {
    private static final Logger LOG = LoggerFactory.getLogger(HestiaDAMIMTopologyFactory.class);

    @Inject
    private HestiaDAMIMSubsystemComponentNames componentNames;

    @Override
    protected SubsystemBaseNames specifySubsystemBaseNames() {
        return (componentNames);
    }

    @Override
    protected Logger getLogger() {
        return (LOG);
    }
}
