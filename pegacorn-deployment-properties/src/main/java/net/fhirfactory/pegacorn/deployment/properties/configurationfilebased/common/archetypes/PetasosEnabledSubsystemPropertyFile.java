package net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.archetypes;

import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.ipc.HTTPIPCPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.ipc.JGroupsIPCPortSegment;

public class PetasosEnabledSubsystemPropertyFile extends ClusterServiceDeliverySubsystemPropertyFile{
    private JGroupsIPCPortSegment edgeIPCJGroups;
    private HTTPIPCPortSegment edgeAnswer;
    private HTTPIPCPortSegment edgeReceive;
    private HTTPIPCPortSegment petasosReplication;
    private HTTPIPCPortSegment petasosStatus;

    public PetasosEnabledSubsystemPropertyFile(){
        super();
        edgeAnswer = new HTTPIPCPortSegment();
        edgeReceive = new HTTPIPCPortSegment();
        petasosReplication = new HTTPIPCPortSegment();
        petasosStatus = new HTTPIPCPortSegment();
        edgeIPCJGroups = new  JGroupsIPCPortSegment();
    }

    public JGroupsIPCPortSegment getEdgeIPCJGroups() {
        return edgeIPCJGroups;
    }

    public void setEdgeIPCJGroups(JGroupsIPCPortSegment edgeIPCJGroups) {
        this.edgeIPCJGroups = edgeIPCJGroups;
    }

    public HTTPIPCPortSegment getEdgeAnswer() {
        return edgeAnswer;
    }

    public void setEdgeAnswer(HTTPIPCPortSegment edgeAnswer) {
        this.edgeAnswer = edgeAnswer;
    }

    public HTTPIPCPortSegment getEdgeReceive() {
        return edgeReceive;
    }

    public void setEdgeReceive(HTTPIPCPortSegment edgeReceive) {
        this.edgeReceive = edgeReceive;
    }

    public HTTPIPCPortSegment getPetasosReplication() {
        return petasosReplication;
    }

    public void setPetasosReplication(HTTPIPCPortSegment petasosReplication) {
        this.petasosReplication = petasosReplication;
    }

    public HTTPIPCPortSegment getPetasosStatus() {
        return petasosStatus;
    }

    public void setPetasosStatus(HTTPIPCPortSegment petasosStatus) {
        this.petasosStatus = petasosStatus;
    }

    @Override
    public String toString() {
        return "PetasosEnabledSubsystemPropertyFile{" +
                "edgeIPCJGroups=" + edgeIPCJGroups +
                ", edgeAnswer=" + edgeAnswer +
                ", edgeReceive=" + edgeReceive +
                ", petasosReplication=" + petasosReplication +
                ", petasosStatus=" + petasosStatus +
                "," + super.toString() +
                '}';
    }
}
