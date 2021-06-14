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
package net.fhirfactory.pegacorn.deployment.topology.builders;

import net.fhirfactory.pegacorn.deployment.topology.factories.solution.SolutionNodeTopologyFactory;
import net.fhirfactory.pegacorn.deployment.topology.manager.TopologyIM;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

public abstract class PegacornSolutionBuilder implements PegacornSolutionBuilderInterface{
    private boolean initialised;

    @Inject
    private TopologyIM topologyIM;

    public PegacornSolutionBuilder(){
        this.initialised = false;
    }

    abstract protected Logger getLogger();
    abstract protected SolutionNodeTopologyFactory specifySolutionNodeTopologyFactory();
    abstract protected void executeInitialisation();

    public SolutionNodeTopologyFactory getSolutionNodeTopologyFactory(){
        return(specifySolutionNodeTopologyFactory());
    }

    @PostConstruct
    public void initialise(){
        getLogger().debug("PegacornSolutionBuilder::initialise(): Entry");
        if(!isInitialised()) {
            getLogger().debug("PegacornSolutionBuilder::initialise(): Has not been previously initialised, so doing it now");
            getLogger().trace("PegacornSolutionBuilder::initialise(): getSolutionNodeTopologyFactory().initialise() --> Start");
            getSolutionNodeTopologyFactory().initialise();
            getLogger().trace("PegacornSolutionBuilder::initialise(): getSolutionNodeTopologyFactory().initialise() --> Finish");
            getLogger().trace("PegacornSolutionBuilder::initialise(): executeInitialisation(): --> Start");
            executeInitialisation();
            getLogger().trace("PegacornSolutionBuilder::initialise(): executeInitialisation(): --> Finish");
            this.initialised = true;
        }
        getLogger().debug("PegacornSolutionBuilder::initialise(): Exit");
    }

    @Override
    public void initialiseSubsystemTopology() {
        getLogger().debug(".initialiseSubsystemTopology(): Entry");
        if(!isInitialised()){
            initialise();
        }
        getLogger().debug(".initialiseSubsystemTopology(): Exit");
    }

    public boolean isInitialised() {
        return initialised;
    }

    public TopologyIM getTopologyIM() {
        return topologyIM;
    }
}
