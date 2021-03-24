package net.fhirfactory.pegacorn.deployment.names.subsystems.common;

import net.fhirfactory.pegacorn.deployment.names.common.SubsystemBaseNames;

public class FHIRDBaaSSubsystemComponentNames extends SubsystemBaseNames {
    public String getFunctionNameDBaaSPostgreSQL(){
        return("DBaaS-PostgreSQL-Server");
    }
    public String getFunctionNameFHIRJPAServer(){return("FHIR-JPA-Server-All");}
}
