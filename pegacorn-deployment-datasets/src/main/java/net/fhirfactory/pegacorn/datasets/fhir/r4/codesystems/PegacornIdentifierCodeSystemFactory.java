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
package net.fhirfactory.pegacorn.datasets.fhir.r4.codesystems;


import net.fhirfactory.pegacorn.datasets.PegacornReferenceProperties;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.StringType;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class PegacornIdentifierCodeSystemFactory {

    @Inject
    private PegacornReferenceProperties systemWideProperties;

    private static final String PEGACORN_IDENTIFIER_CODE_SYSTEM = "/identifier-type";

    public String getPegacornIdentifierCodeSystem() {
        String codeSystem = systemWideProperties.getPegacornCodeSystemSite() + PEGACORN_IDENTIFIER_CODE_SYSTEM;
        return (codeSystem);
    }

    public CodeableConcept buildIdentifierType(PegacornIdentifierCodeEnum identifierCode){
        CodeableConcept idType = new CodeableConcept();
        Coding idTypeCoding = new Coding();
        idTypeCoding.setCode(identifierCode.getIdentifierCode());
        idTypeCoding.setSystem(getPegacornIdentifierCodeSystem());
        idTypeCoding.setDisplayElement(codeDisplayText(identifierCode));
        idType.getCoding().add(idTypeCoding);

        idType.setText(getPegacornIdentifierCodeSystem() + " / " + codeDisplayText(identifierCode) );
        return(idType);
    }

    private StringType codeDisplayText(PegacornIdentifierCodeEnum identifierCodeEnum){
        String displayText = "";
        switch(identifierCodeEnum){
            case IDENTIFIER_CODE_FHIR_ENDPOINT_SYSTEM:
                displayText = "FHIR::Endpoint Identifier";
                break;
            case IDENTIFIER_CODE_SOURCE_OF_TRUTH_RECORD_ID:
                displayText = "Manufactured Identifier for Source-of-Truth Internal/Local Record Id (RID) for Resource";
                break;
        }
        return(new StringType(displayText));
    }

}
