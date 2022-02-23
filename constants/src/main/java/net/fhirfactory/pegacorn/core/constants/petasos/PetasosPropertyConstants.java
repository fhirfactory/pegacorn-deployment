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
package net.fhirfactory.pegacorn.core.constants.petasos;

/**
 *
 * @author Mark A. Hunter
 */

public final class PetasosPropertyConstants {

    // For assisting in monitoring expected end time in parcels, this period in milliseconds
    // can be used when monitoring a UoW. For example it can be added to the expected completion
    // time to provide an extra buffer to avoid classing a UoW as failed too early
    public final static long EXPECTED_COMPLETION_TIME_BUFFER_MILLIS = 200;

    // How often to send a heartbeat between Nodes
    public final static long HEARTBEAT_FREQUENCY_MILLIS = 100;

    // Every N heartbeats, include a status update with the heartbeat message
    public final static long HEARTBEAT_STATUS_UPDATE_FREQUENCY = 10;

    // If a heartbeat fails, try to reconnect N times before failing and marking
    // the Node as unavailable
    public final static long HEARTBEAT_NUM_RETRIES = 3;

    // If the cache is full, this is the location where the overflow is persisted
    public final static String CACHE_OVERFLOW_DIRECTORY = "/tmp";

    // Size of the cache in bytes, once exceeded entries will be written to the overflow
    // directory
    public final static long CACHE_SIZE_IN_BYTES = 1000000000;

    // The nominal period (in seconds) any single processing plant should wait before assuming
    // that a particular cache item in an infinity span cache is synchronised.
    // This is a very pessimistic number...
    public final static long PETASOS_DISTRIBUTED_CACHE_SYNCHRONISATION_WAIT = 5;

    // How long should completed content be kept within the caches?
    public final static long CACHE_ENTRY_RETENTION_PERIOD_SECONDS = 60;

    // How long should a WUP take to complete a task - worst case?
    public final static long WUP_ACTIVITY_DURATION_SECONDS = 10;

    // How long should a WUP sleep between scans for activity?
    public final static long WUP_SLEEP_INTERVAL_MILLISECONDS = 250;

    // General Workflow Exchange Objects
    public final static String WUP_TOPOLOGY_NODE_EXCHANGE_PROPERTY_NAME = "WUPTopologyNode";
    public final static String WUP_METRICS_AGENT_EXCHANGE_PROPERTY = "WUPMetricsAgent";
    public final static String WUP_PETASOS_FULFILLMENT_TASK_EXCHANGE_PROPERTY = "WUPPetasosParcel";

    @Deprecated
    public final static String WUP_JOB_CARD_EXCHANGE_PROPERTY_NAME = "WUPJobCard";
    @Deprecated
    public final static String WUP_CURRENT_UOW_EXCHANGE_PROPERTY_NAME = "WUPCurrentUnitOfWork";


    public final static String ENDPOINT_TOPOLOGY_NODE_EXCHANGE_PROPERTY = "EndpointTopologyNode";
    public final static String ENDPOINT_METRICS_AGENT_EXCHANGE_PROPERTY = "EndpointMetricsAgent";
    public final static String ENDPOINT_TASK_REPORT_AGENT_EXCHANGE_PROPERTY = "EndpointTaskReportAgent";
    public final static String ENDPOINT_PORT_VALUE = "EndpointPortValue";
    public final static String ENDPOINT_HOSTNAME = "EndpointPortName";

    public final static String WUP_INTERACT_INGRES_SOURCE_SYSTEM_NAME = "WUPInteractIngresSourceSystemName";
    public final static String WUP_INTERACT_EGRESS_SOURCE_SYSTEM_NAME = "WUPInteractEgressSourceSystemName";

    public final static String WUP_INTERACT_PORT_TYPE = "WUPInteractIngresPortType";

    public final static String DEFAULT_TIMEZONE="Australia/Sydney";

    //
    // Task Distribution and Outcome Collection Queues
    //

    public final static String TASK_DISTRIBUTION_QUEUE="seda:task_distribution_queue";
    public final static String TASK_OUTCOME_COLLECTION_QUEUE="seda:task_outcome_collection_queue";

    //
    // Inter-ProcessingPlant Task Forwarder Queue
    //

    public final static String TASK_IPC_FORWARDER_NAME = "PetasosTaskForwarderWUP";
    public final static String TASK_IPC_RECEIVER_NAME = "PetasosTaskReceiverWUP";

    //
    // Processing Plant
    //

    public final static String AUDIT_LEVEL_PARAMETER_NAME="AUDIT_LEVEL";
    public final static String SEDA_QUEUE_SIZE_PARAMETER_NAME="SEDA_QUEUE_DEFAULT_SIZE";
    public final static String SEDA_QUEUE_BLOCK_ON_FULL_PARAMETER_NAME="SEDA_QUEUE_BLOCK_ON_FULL";


    //
    // EndPoint Configurations
    //

    //
    // MLLP

    public final static String CAMEL_MLLP_RETRIES_PARAMETER_NAME="CAMEL_MLLP_RETRIES";
    public final static String CAMEL_MLLP_RETRY_PERION_PARAMETER_NAME="CAMEL_MLLP_RETRY_PERIOD";
    public final static String CAMEL_MLLP_CONNECTION_TIMEOUT_PARAMETER_NAME="CAMEL_MLLP_CONNECTION_TIMEOUT";
    public final static String CAMEL_MLLP_KEEP_ALIVE_PARAMETER_NAME="CAMEL_MLLP_KEEPALIVE";

}
