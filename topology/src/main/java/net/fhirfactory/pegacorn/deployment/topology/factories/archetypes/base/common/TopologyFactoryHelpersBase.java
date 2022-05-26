package net.fhirfactory.pegacorn.deployment.topology.factories.archetypes.base.common;

import net.fhirfactory.pegacorn.core.model.componentid.PegacornSystemComponentTypeTypeEnum;
import net.fhirfactory.pegacorn.core.model.componentid.TopologyNodeRDN;
import net.fhirfactory.pegacorn.core.model.dataparcel.DataParcelManifest;
import net.fhirfactory.pegacorn.core.model.dataparcel.valuesets.*;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.datatypes.SubscriptionListEntryType;
import net.fhirfactory.pegacorn.util.PegacornProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public abstract class TopologyFactoryHelpersBase {

    @Inject
    private PegacornProperties pegacornProperties;

    //
    // Abstract Methods
    //

    protected abstract Logger getLogger();

    //
    // Getters (and Setters)
    //

    protected PegacornProperties getPegacornProperties(){
        return(pegacornProperties);
    }

    //
    // Node Builders
    //

    protected String getActualHostIP(){
        String actualHostIP = pegacornProperties.getProperty("MY_HOST_IP", "Unknown");
        return(actualHostIP);
    }

    protected String getActualPodIP(){
        String actualHostIP = pegacornProperties.getProperty("MY_POD_IP", "Unknown");
        return(actualHostIP);
    }

    public TopologyNodeRDN createSimpleNodeRDN(String nodeName, String nodeVersion, PegacornSystemComponentTypeTypeEnum nodeType){
        getLogger().debug(".createNodeRDN: Entry, nodeName->{}, nodeVersion->{}, nodeType->{}", nodeName, nodeVersion, nodeType);
        TopologyNodeRDN newRDN = new TopologyNodeRDN(nodeType, nodeName, nodeVersion);
        getLogger().debug(".createNodeRDN: Exit, newRDN->{}", newRDN);
        return (newRDN);
    }

    public List<DataParcelManifest> subscriptionList(List<SubscriptionListEntryType> subscriptionEntries){
        getLogger().debug(".subscriptionList(): Entry, subscriptionEntries->{}", subscriptionEntries);
        List<DataParcelManifest> subList = new ArrayList<>();
        if(subscriptionEntries != null){
            for(SubscriptionListEntryType entry: subscriptionEntries){
                DataParcelManifest currentManifest = new DataParcelManifest();
                currentManifest.setContentDescriptor(entry);
                if(StringUtils.isNotEmpty(entry.getSourceProcessingPlantParticipantName())) {
                    currentManifest.setSourceProcessingPlantParticipantName(entry.getSourceProcessingPlantParticipantName());
                }
                if(StringUtils.isNotEmpty(entry.getSourceEndpointParticipantName())){
                    // do nothing
                }
                // standard stuff
                currentManifest.setDataParcelFlowDirection(DataParcelDirectionEnum.INFORMATION_FLOW_OUTBOUND_DATA_PARCEL);
                currentManifest.setDataParcelType(DataParcelTypeEnum.GENERAL_DATA_PARCEL_TYPE);
                currentManifest.setEnforcementPointApprovalStatus(PolicyEnforcementPointApprovalStatusEnum.POLICY_ENFORCEMENT_POINT_APPROVAL_POSITIVE);
                currentManifest.setNormalisationStatus(DataParcelNormalisationStatusEnum.DATA_PARCEL_CONTENT_NORMALISATION_FALSE);
                currentManifest.setValidationStatus(DataParcelValidationStatusEnum.DATA_PARCEL_CONTENT_VALIDATED_TRUE);
                currentManifest.setInterSubsystemDistributable(false);
                currentManifest.setSourceSystem(DataParcelManifest.WILDCARD_CHARACTER);
                currentManifest.setIntendedTargetSystem(DataParcelManifest.WILDCARD_CHARACTER);
                subList.add(currentManifest);
            }
        }
        getLogger().debug(".subscriptionList(): Exit, subList->{}", subList);
        return(subList);
    }

    public List<DataParcelManifest> publishingList(List<SubscriptionListEntryType> publishEntries){
        getLogger().debug(".publishingList(): Entry, publishEntries->{}", publishEntries);
        List<DataParcelManifest> pubList = new ArrayList<>();
        if(publishEntries != null){
            for(SubscriptionListEntryType entry: publishEntries){
                DataParcelManifest currentManifest = new DataParcelManifest();
                currentManifest.setContentDescriptor(entry);
                // standard stuff
                pubList.add(currentManifest);
            }
        }
        getLogger().debug(".publishingList(): Exit, pubList->{}", pubList);
        return(pubList);
    }
}
