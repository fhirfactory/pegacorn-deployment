/*
 *
 * Copyright 2020 ACT Health
 *
 */
package net.fhirfactory.pegacorn.deployment.topology.map.mitaf;

import net.fhirfactory.pegacorn.deployment.names.common.SubsystemBaseNames;
import net.fhirfactory.pegacorn.deployment.names.subsystems.MITaFSMSGatewayComponentNames;
import net.fhirfactory.pegacorn.deployment.topology.map.common.archetypes.MITaFSubsystemTopologyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 *
 * @author Mark A. Hunter
 */

@ApplicationScoped
public class MITaFSMSGatewayTopologyFactory extends MITaFSubsystemTopologyFactory {
	private static final Logger LOG = LoggerFactory.getLogger(MITaFSMSGatewayTopologyFactory.class);

	@Inject
	private MITaFSMSGatewayComponentNames smsGatewayComponentNames;

	@Override
	protected SubsystemBaseNames specifySubsystemBaseNames() {
		return (smsGatewayComponentNames);
	}

	@Override
	protected Logger getLogger() {
		return (LOG);
	}
}
