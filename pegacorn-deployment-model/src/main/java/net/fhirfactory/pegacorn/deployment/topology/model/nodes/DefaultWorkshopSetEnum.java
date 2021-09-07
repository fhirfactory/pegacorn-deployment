package net.fhirfactory.pegacorn.deployment.topology.model.nodes;

public enum DefaultWorkshopSetEnum {
    INTERACT_WORKSHOP("Interact"),
    TRANSFORM_WORKSHOP("Transform"),
    WORKFLOW_WORKSHOP("Workflow"),
    POLICY_ENFORCEMENT_WORKSHOP("PolicyEnforcement"),
    AUDIT_SERVICES_WORKSHOP("Audit"),
    EDGE_WORKSHOP("EdgeIPC");

    private String workshop;

    private DefaultWorkshopSetEnum(String newWorkshop){
        this.workshop = newWorkshop;
    }

    public String getWorkshop(){
        return(this.workshop);
    }
}
