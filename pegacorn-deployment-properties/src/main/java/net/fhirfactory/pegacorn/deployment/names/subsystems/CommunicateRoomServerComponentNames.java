package net.fhirfactory.pegacorn.deployment.names.subsystems;

import net.fhirfactory.pegacorn.deployment.names.common.SubsystemBaseNames;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CommunicateRoomServerComponentNames extends SubsystemBaseNames {
    public String getFunctionNameMatrixClientServices(){return("Matrix-ClientServices");}

    public String getFunctionNameDBaaSPostgreSQL(){
        return("DBaaS-PostgreSQL-Server");
    }
}
