/*
 * Copyright (c) 2020 Mark A. Hunter
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
package net.fhirfactory.pegacorn.deployment.properties.codebased.ladon;

public class LadonDefaultDeploymentProperties {
    private static int CACHE_AGE_THRESHOLD_LONG = 600;
    private static int CACHE_AGE_THRESHOLD_NORMAL = 30;
    private static int CACHE_AGE_THRESHOLD_SHORT = 2;

    public int getCareTeamCacheAgeThreshold(){
        return(CACHE_AGE_THRESHOLD_NORMAL);
    }
    public int getCommunicationCacheAgeThreshold(){ return(CACHE_AGE_THRESHOLD_SHORT);}
    public int getCommunicationRequestCacheAgeThreshold(){return(CACHE_AGE_THRESHOLD_NORMAL);}
    public int getEncounterCacheAgeThreshold(){return(CACHE_AGE_THRESHOLD_SHORT);}
    public int getEndpointCacheAgeThreshold(){return(CACHE_AGE_THRESHOLD_SHORT);}
    public int getGroupCacheAgeThreshold(){return(CACHE_AGE_THRESHOLD_SHORT);}
    public int getHealthcareServiceCacheAgeThreshold(){return(CACHE_AGE_THRESHOLD_NORMAL);}
    public int getLocationCacheAgeThreshold(){return(CACHE_AGE_THRESHOLD_LONG);}
    public int getOrganizationCacheAgeThreshold(){return(CACHE_AGE_THRESHOLD_LONG);}
    public int getPatientCacheAgeThreshold(){return(CACHE_AGE_THRESHOLD_SHORT);}
    public int getPractitionerCacheAgeThreshold(){return(CACHE_AGE_THRESHOLD_SHORT);}
    public int getPractitionerRoleCacheAgeThreshold(){return(CACHE_AGE_THRESHOLD_NORMAL);}
    public int getProcedureCacheAgeThreshold(){return(CACHE_AGE_THRESHOLD_SHORT);}
    public int getTaskCacheAgeThreshold(){return(CACHE_AGE_THRESHOLD_SHORT);}
    public int getValueSetCacheAgeThreshold(){return(CACHE_AGE_THRESHOLD_LONG);}
}
