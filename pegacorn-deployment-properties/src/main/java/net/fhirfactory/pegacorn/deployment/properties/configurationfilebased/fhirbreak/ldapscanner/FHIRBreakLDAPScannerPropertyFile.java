package net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.fhirbreak.ldapscanner;

import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.interact.ClusteredInteractServerPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.fhirbreak.common.FHIRBreakSubsystemPropertyFile;

public class FHIRBreakLDAPScannerPropertyFile extends FHIRBreakSubsystemPropertyFile {

    private ClusteredInteractServerPortSegment ldapProxy;
    private ClusteredInteractServerPortSegment ldapServer;

    public ClusteredInteractServerPortSegment getLdapProxy() {
        return ldapProxy;
    }

    public void setLdapProxy(ClusteredInteractServerPortSegment ldapProxy) {
        this.ldapProxy = ldapProxy;
    }

    public ClusteredInteractServerPortSegment getLdapServer() {
        return ldapServer;
    }

    public void setLdapServer(ClusteredInteractServerPortSegment ldapServer) {
        this.ldapServer = ldapServer;
    }

}
