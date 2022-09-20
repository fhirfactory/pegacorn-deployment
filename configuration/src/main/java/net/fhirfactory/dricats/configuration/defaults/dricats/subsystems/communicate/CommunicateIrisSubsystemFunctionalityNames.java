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
package net.fhirfactory.dricats.configuration.defaults.dricats.subsystems.communicate;

import javax.enterprise.context.ApplicationScoped;

import net.fhirfactory.dricats.configuration.defaults.dricats.systemwide.base.WildflyManagementIPCEndpointConstants;

@ApplicationScoped
public class CommunicateIrisSubsystemFunctionalityNames extends WildflyManagementIPCEndpointConstants {
	public String getFunctionNameSynapseMatrixClientServices() {
		return ("syn-appsvcs");
	}

	public String getFunctionNameSynapseAdminServices() {
		return ("syn-admin");
	}

	public String getFunctionNameIrisAppServices() {
		return ("iris-appsvcs");
	}

	public String getVersionInteractMatrixClientServices() {
		return ("0.6.1");
	}

	public String getVersionInteractMatrixApplicationServices() {
		return ("0.1.2");
	}
        
        /*

	public IPCAdapterDefinition getMatrixClientServicesPortDefinition() {
		IPCAdapterDefinition appServersPort = new IPCAdapterDefinition();
		appServersPort.setInterfaceFormalName("Matrix-ClientServicesAPI");
		appServersPort.setInterfaceFormalVersion(getVersionInteractMatrixClientServices());
		return (appServersPort);
	}

	public IPCAdapterDefinition getMatrixApplicationServicesPortDefinition() {
		IPCAdapterDefinition appServersPort = new IPCAdapterDefinition();
		appServersPort.setInterfaceFormalName("Matrix-ApplicationServicesAPI");
		appServersPort.setInterfaceFormalVersion(getVersionInteractMatrixApplicationServices());
		return (appServersPort);
	}

        */
}
