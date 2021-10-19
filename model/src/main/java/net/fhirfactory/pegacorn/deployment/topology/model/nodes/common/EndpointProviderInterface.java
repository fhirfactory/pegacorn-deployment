package net.fhirfactory.pegacorn.deployment.topology.model.nodes.common;

import net.fhirfactory.pegacorn.common.model.componentid.*;
import net.fhirfactory.pegacorn.deployment.topology.model.mode.ConcurrencyModeEnum;
import net.fhirfactory.pegacorn.deployment.topology.model.mode.ResilienceModeEnum;

public interface EndpointProviderInterface {
    public void addEndpoint(ComponentIdType endpointFDN);
    public TopologyNodeRDN getNodeRDN();
    public ComponentIdType getComponentId();
    public ComponentTypeType getComponentType();
    public ResilienceModeEnum getResilienceMode();
    public ConcurrencyModeEnum getConcurrencyMode();
}
