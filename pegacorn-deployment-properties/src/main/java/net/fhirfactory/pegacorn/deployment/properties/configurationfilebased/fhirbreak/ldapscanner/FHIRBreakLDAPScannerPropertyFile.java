package net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.fhirbreak.ldapscanner;

import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.interact.ClusterServiceInteractPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.fhirbreak.common.FHIRBreakSubsystemPropertyFile;

public class FHIRBreakLDAPScannerPropertyFile extends FHIRBreakSubsystemPropertyFile {

    private ClusterServiceInteractPortSegment ldapProxy;
    private ClusterServiceInteractPortSegment ldapServer;

    public ClusterServiceInteractPortSegment getLdapProxy() {
        return ldapProxy;
    }

    public void setLdapProxy(ClusterServiceInteractPortSegment ldapProxy) {
        this.ldapProxy = ldapProxy;
    }

    public ClusterServiceInteractPortSegment getLdapServer() {
        return ldapServer;
    }

    public void setLdapServer(ClusterServiceInteractPortSegment ldapServer) {
        this.ldapServer = ldapServer;
    }

}
