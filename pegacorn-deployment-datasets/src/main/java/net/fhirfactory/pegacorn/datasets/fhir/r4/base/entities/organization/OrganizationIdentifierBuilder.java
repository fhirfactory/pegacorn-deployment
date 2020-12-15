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
package net.fhirfactory.pegacorn.datasets.fhir.r4.base.entities.organization;

import net.fhirfactory.pegacorn.datasets.fhir.r4.base.entities.endpoint.EndpointIdentifierBuilder;
import net.fhirfactory.pegacorn.datasets.fhir.r4.codesystems.PegacornIdentifierCodeSystemFactory;
import net.fhirfactory.pegacorn.datasets.fhir.r4.internal.systems.DeploymentInstanceDetailInterface;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Period;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Instant;
import java.util.Date;

@ApplicationScoped
public class OrganizationIdentifierBuilder {
    private static final Logger LOG = LoggerFactory.getLogger(EndpointIdentifierBuilder.class);
    @Inject
    private PegacornIdentifierCodeSystemFactory pegacornIdentifierCodeSystemFactory;

    @Inject
    private DeploymentInstanceDetailInterface deploymentInstanceDetailInterface;

    public Identifier constructOrganizationIdentifier(String identifierValue){
        LOG.debug(".constructOrganizationIdentifier(): Entry");
        Identifier systemSystemEndpointIDentifier = new Identifier();
        systemSystemEndpointIDentifier.setUse(Identifier.IdentifierUse.SECONDARY);
        CodeableConcept idType = new CodeableConcept();
        Coding idTypeCoding = new Coding();
        idTypeCoding.setCode("XX");
        idTypeCoding.setSystem("http://terminology.hl7.org/ValueSet/v2-0203");
        idType.getCoding().add(idTypeCoding);
        idType.setText("Organization");
        systemSystemEndpointIDentifier.setType(idType);
        systemSystemEndpointIDentifier.setSystem(deploymentInstanceDetailInterface.getDeploymentInstanceSystemEndpointSystem());
        systemSystemEndpointIDentifier.setValue(identifierValue);
        Period validPeriod = new Period();
        validPeriod.setStart(Date.from(Instant.now()));
        systemSystemEndpointIDentifier.setPeriod(validPeriod);
        systemSystemEndpointIDentifier.setAssigner(deploymentInstanceDetailInterface.getDeploymentInstanceSystemOwnerOrganization());
        LOG.debug(".constructOrganizationIdentifier(): Exit, created Identifier --> {}", systemSystemEndpointIDentifier);
        return systemSystemEndpointIDentifier;
    }
}
