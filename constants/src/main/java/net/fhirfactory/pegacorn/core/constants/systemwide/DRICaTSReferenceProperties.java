/*
 * Copyright (c) 2020 Mark A. Hunter (ACT Health)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.fhirfactory.pegacorn.core.constants.systemwide;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 *
 * @author Mark A. Hunter
 */
@ApplicationScoped
public class DRICaTSReferenceProperties {

    @Inject
    private DeploymentSystemIdentificationInterface systemIdentification;

    private static final String DRICATS_INTERNAL_FHIR_RESOURCE_R4_PATH = "/dricats/internal/fhir/r4";
    private static final String DRICATS_EXPOSED_FHIR_RESOURCE_R4_PATH = "/fhir/r4";
    private static final String DRICATS_PETASOS_STATUS_PATH = "/dricats/internal/petasos/status";
    private static final String DRICATS_PETASOS_REPLICATION_PATH = "/dricats/internal/petasos/replication";
    private static final String DRICATS_PETASOS_IPC_PATH = "/dricats/internal/petasos/ipc";
    private static final String DRICATS_RESOURCE_DIRECTORY_R1_PATH = "/dricats/operations/directory/r1";
    private static final String DRICATS_COMMUNICATE_DIRECTORY_R1_PATH = "/dricats/communicate/directory/r1";

    public String getSystemDeploymentName() {
        return (systemIdentification.getSystemName());
    }

    public String getPegacornInternalFhirResourceR4Path() {
        return DRICATS_INTERNAL_FHIR_RESOURCE_R4_PATH;
    }

    public String getPegacornExposedFhirResourceR4Path() {
        return DRICATS_EXPOSED_FHIR_RESOURCE_R4_PATH;
    }

    public String getDRICaTSCodeSystemSite(){
        return("http://ontology.fhirfactory.net/fhir/code-systems");
    }

    public String getContainmentSeparator(){
        return("+");
    }

    public String getPegacornDefaultFHIRVersion(){return("4.0.1");}

    public static String getDricatsPetasosStatusPath() {
        return DRICATS_PETASOS_STATUS_PATH;
    }

    public static String getDricatsResourceDirectoryR1Path() {
        return DRICATS_RESOURCE_DIRECTORY_R1_PATH;
    }

    public static String getDricatsCommunicateDirectoryR1Path() {
        return DRICATS_COMMUNICATE_DIRECTORY_R1_PATH;
    }

    public static String getDricatsPetasosReplicationPath() {
        return DRICATS_PETASOS_REPLICATION_PATH;
    }

    public static String getPegacornPetasosIPCPath() {
        return DRICATS_PETASOS_IPC_PATH;
    }

    public String getLocalCodeSystemPath(){
        return("/local/fhir/code-systems");
    }

    public String getITOpsContextPath(){
        return("/dricats/internal/itops/r1");
    }

    public String getAuditDMContextPath(){
        return("/dricats/hestia/audit-event");
    }
    

    public String getTaskDMContextPath(){
        return("/dricats/hestia/task");
    }

    //
    // Audit Event Details
    //

}
