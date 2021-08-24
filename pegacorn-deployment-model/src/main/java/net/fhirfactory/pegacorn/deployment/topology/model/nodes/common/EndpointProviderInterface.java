package net.fhirfactory.pegacorn.deployment.topology.model.nodes.common;

import net.fhirfactory.pegacorn.common.model.componentid.PetasosNodeFDN;
import net.fhirfactory.pegacorn.common.model.componentid.PetasosNodeFunctionFDN;
import net.fhirfactory.pegacorn.common.model.componentid.PetasosNodeRDN;
import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeTypeEnum;
import net.fhirfactory.pegacorn.deployment.topology.model.mode.ConcurrencyModeEnum;
import net.fhirfactory.pegacorn.deployment.topology.model.mode.ResilienceModeEnum;

public interface EndpointProviderInterface {
    public void addEndpoint(PetasosNodeFDN endpointFDN);
    public PetasosNodeRDN getNodeRDN();
    public PetasosNodeFDN getNodeFDN();
    public PetasosNodeFunctionFDN getNodeFunctionFDN();
    public TopologyNodeTypeEnum getComponentType();
    public ResilienceModeEnum getResilienceMode();
    public ConcurrencyModeEnum getConcurrencyMode();
}
