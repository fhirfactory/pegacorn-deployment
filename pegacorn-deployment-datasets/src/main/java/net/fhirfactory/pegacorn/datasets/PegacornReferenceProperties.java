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

package net.fhirfactory.pegacorn.datasets;

import net.fhirfactory.pegacorn.common.model.generalid.FDN;
import net.fhirfactory.pegacorn.deployment.topology.model.common.SystemIdentificationInterface;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 *
 * @author Mark A. Hunter
 */
@ApplicationScoped
public class PegacornReferenceProperties {

    @Inject
    private SystemIdentificationInterface systemIdentification;

    public FDN getSystemDeploymentInstanceFDN(){
        FDN systemFDN = new FDN(systemIdentification.getSystemIdentifier());
        return(systemFDN);
    }

    private static final String PEGACORN_INTERNAL_FHIR_RESOURCE_R4_PATH = "/pegacorn/internal/fhir/r4";
    private static final String PEGACORN_EXPOSED_FHIR_RESOURCE_R4_PATH = "/fhir/r4";
    private static final String PEGACORN_PETASOS_STATUS_PATH = "/pegacorn/internal/petasos/status";

    public String getSystemDeploymentName() {
        return (systemIdentification.getSystemName());
    }

    public String getPegacornInternalFhirResourceR4Path() {
        return PEGACORN_INTERNAL_FHIR_RESOURCE_R4_PATH;
    }

    public String getPegacornExposedFhirResourceR4Path() {
        return PEGACORN_EXPOSED_FHIR_RESOURCE_R4_PATH;
    }

    public String getPegacornCodeSystemSite(){
        return("http://pegacorn.fhirfactory.net/fhir/code-systems");
    }

    public String getPegacornDefaultFHIRVersion(){return("4.0.1");}

    public static String getPegacornPetasosStatusPath() {
        return PEGACORN_PETASOS_STATUS_PATH;
    }

}
