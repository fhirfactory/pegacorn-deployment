package net.fhirfactory.pegacorn.deployment.topology.model.nodes.common;

import net.fhirfactory.pegacorn.deployment.topology.model.common.TopologyNode;
import net.fhirfactory.pegacorn.deployment.topology.model.mode.ConcurrencyModeEnum;
import net.fhirfactory.pegacorn.deployment.topology.model.mode.ResilienceModeEnum;

public abstract class PegacornSubsystemBase extends TopologyNode {

    private ConcurrencyModeEnum concurrencyMode;
    private ResilienceModeEnum resilienceMode;

    public ConcurrencyModeEnum getConcurrencyMode() {
        return concurrencyMode;
    }

    public void setConcurrencyMode(ConcurrencyModeEnum concurrencyMode) {
        this.concurrencyMode = concurrencyMode;
    }

    public ResilienceModeEnum getResilienceMode() {
        return resilienceMode;
    }

    public void setResilienceMode(ResilienceModeEnum resilienceMode) {
        this.resilienceMode = resilienceMode;
    }
}
