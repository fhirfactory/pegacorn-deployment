package net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.fhirbreak.ldapscanner;

import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.PegacornClusterServiceInteractPort;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.fhirbreak.common.FHIRBreakSubsystemPropertyFile;

public class FHIRBreakLDAPScannerPropertyFile extends FHIRBreakSubsystemPropertyFile {

    private PegacornClusterServiceInteractPort ldapProxy;
    private PegacornClusterServiceInteractPort ldapServer;

    public PegacornClusterServiceInteractPort getLdapProxy() {
        return ldapProxy;
    }

    public void setLdapProxy(PegacornClusterServiceInteractPort ldapProxy) {
        this.ldapProxy = ldapProxy;
    }

    public PegacornClusterServiceInteractPort getLdapServer() {
        return ldapServer;
    }

    public void setLdapServer(PegacornClusterServiceInteractPort ldapServer) {
        this.ldapServer = ldapServer;
    }

}
