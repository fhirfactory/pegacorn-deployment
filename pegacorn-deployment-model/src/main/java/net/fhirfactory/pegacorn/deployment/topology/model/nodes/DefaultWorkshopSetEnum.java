package net.fhirfactory.pegacorn.deployment.topology.model.nodes;

public enum DefaultWorkshopSetEnum {
    INTERACT_WORKSHOP("Interact"),
    TRANSFORM_WORKSHOP("Transform"),
    EDGE_WORKSHOP("EdgeIPC");

    private String workshop;

    private DefaultWorkshopSetEnum(String newWorkshop){
        this.workshop = newWorkshop;
    }

    public String getWorkshop(){
        return(this.workshop);
    }
}
