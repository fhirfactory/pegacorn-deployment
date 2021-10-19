/*
 * Copyright (c) 2021 Mark A. Hunter (ACT Health)
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
package net.fhirfactory.pegacorn.deployment.topology.model.common.valuesets;

public enum PegacornConfigurationParameterNameEnum {
    PEGACORN_IPC_TECHNOLOGY_JROUPS_INITIAL_HOSTS("initialHosts","edge.technology.jgroups_initial_hosts"),
    PEGACORN_INTERACT_MLLP_CONSUMER_COUNT("mllpComsumers", "interact.mllp.consumer_count"),
    PEGACORN_INTERACT_MLLP_ACCEPT_TIMEOUT("acceptTimeout", "interact.mllp.server_accept_timeout"),
    PEGACORN_INTERACT_MLLP_BIND_TIMEOUT("bindTimeout", "interact.mllp.server_bind_timeout"),
    PEGACORN_PETASOS_WUP_QUEUE_SIZE("wupSedaQueueSize", "petasos.wup.sesa_queue_size"),
    PEGACORN_PETASOS_INTERCHANGE_QUEUE_SIZE("interchangeSedaQueueSize", "petasos.interchange.sesa_queue_size"),
    PEGACORN_INTERACT_HTTP_CONTEXT_PATH("contextPath", "interact.http.context_path");

    private String parameterName;
    private String parameterToken;

    private PegacornConfigurationParameterNameEnum(String name, String token) {
        this.parameterName = name;
        this.parameterToken = token;
    }

    public String getParameterName(){
        return(parameterName);
    }

    public String getParameterToken(){
        return(parameterToken);
    }
}
