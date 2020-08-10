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

package net.fhirfactory.pegacorn.deploymentproperties;

import net.fhirfactory.pegacorn.common.model.FDN;
import net.fhirfactory.pegacorn.common.model.RDN;

import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author Mark A. Hunter
 */
@ApplicationScoped
public class SystemWideProperties {
    
    private static final String SYSTEM_DEPLOYMENT_ID="Pegacorn-Test(R1.0.0-SNAPSHOT)";

    public String getSystemDeploymentID(){
        return(SYSTEM_DEPLOYMENT_ID);
    }

    public FDN getSystemDeploymentInstanceID(){
        FDN newFDN = new FDN();
        newFDN.appendRDN(new RDN("System", getSystemDeploymentID()));
        return(newFDN);
    }

    private static final String SYSTEM_QUALIFIER = "System";
    public String getSystemQualifier(){
        return(SYSTEM_QUALIFIER);
    }

    private static final String SUBSYSTEM_QUALIFIER = "SubSystem";
    public String getSubsystemQualifier(){
        return(SUBSYSTEM_QUALIFIER);
    }

    private static final String SITE_QUALIFIER = "Site";
    public String getSiteQualifier(){
        return(SITE_QUALIFIER);
    }

    private static final String SERVICE_QUALIFIER = "KubernetesService";
    public static String getServiceQualifier(){
        return(SERVICE_QUALIFIER);
    }

    private static final String POD_QUALIFIER = "KubernetesPod";
    public static String getPodQualifier() {
        return POD_QUALIFIER;
    }

    private static final String SERVICE_MODULE_QUALIFIER = "ServiceModule";
    public static String getServiceModuleQualifier() {
        return SERVICE_MODULE_QUALIFIER;
    }

    private static final String PROCESSING_ENGINE_QUALIFIER = "ProcessingEngine";
    public static String getProcessingEngineQualifier() {
        return PROCESSING_ENGINE_QUALIFIER;
    }
}
