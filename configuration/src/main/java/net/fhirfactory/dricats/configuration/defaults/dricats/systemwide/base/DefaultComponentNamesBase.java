/*
 * The MIT License
 *
 * Copyright 2022 Mark A. Hunter.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.fhirfactory.dricats.configuration.defaults.dricats.systemwide.base;

/**
 *
 * @author mhunter
 */
public class DefaultComponentNamesBase {
    
    
    //
    // Wildfly Component Names
    //
    
    private static final String WILDFLY_MANAGEMENT_WORKSHOP = "WildflyManagement";
    private static final String WILDFLY_PROMETHEUS_SERVER_WUP = "WildflyPrometheusServerWUP";
    private static final String WILDFLY_KUBERNETES_KEEPALIVE_SERVER_WUP = "WildflyKeepAliveServerWUP";
    private static final String WILDFLY_KUBERNETES_READINESS_SERVER_WUP = "WildflyReadinessServerWUP";

    public String getWildflyManagementWorkshop(){
        return(WILDFLY_MANAGEMENT_WORKSHOP);
    }
    
    public String getWildflyPrometheusServerWUP(){
        return(WILDFLY_PROMETHEUS_SERVER_WUP);
    }
    
    public String getWildflyKubernetesKeepaliveServerWUP(){
        return(WILDFLY_KUBERNETES_KEEPALIVE_SERVER_WUP);
    }
    
    public String getWildflyKubernetesReadinessServerWUP(){
        return(WILDFLY_KUBERNETES_READINESS_SERVER_WUP);
    }
    
    //
    // Petasos Framework Node Names
    //
    
    private static final String PETASOS_PROCESSING_PLANT_LOCAL_ROUTER_NODE = "LocalRouter";
    
    public String getPetasosProcessingPlantLocalRouterNode(){
        return(PETASOS_PROCESSING_PLANT_LOCAL_ROUTER_NODE);
    }
    
}
