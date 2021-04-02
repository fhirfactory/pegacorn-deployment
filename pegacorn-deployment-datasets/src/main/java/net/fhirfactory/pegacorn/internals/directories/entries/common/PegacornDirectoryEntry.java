/*
 * Copyright (c) 2021 Mark A. Hunter
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

package net.fhirfactory.pegacorn.internals.directories.entries.common;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import net.fhirfactory.pegacorn.internals.directories.entries.datatypes.IdentifierDE;
import net.fhirfactory.pegacorn.internals.directories.entries.datatypes.IdentifierDEUseEnum;
import net.fhirfactory.pegacorn.internals.directories.entries.datatypes.PegId;
import org.hl7.fhir.r4.model.IdType;

import java.util.ArrayList;
import java.util.UUID;

public abstract class PegacornDirectoryEntry {
    private PegId id;
    private ArrayList<IdentifierDE> identifiers;
    private String displayName;
    private boolean systemManaged;

    public PegacornDirectoryEntry(){
        this.identifiers = new ArrayList<>();
        this.displayName = null;
    }

    public boolean isSystemManaged() {
        return systemManaged;
    }

    public void setSystemManaged(boolean systemManaged) {
        this.systemManaged = systemManaged;
    }

    public PegId getId() {
        return id;
    }

    public void setId(PegId id) {
        this.id = id;
    }

    public void generateId(){
        this.id = new PegId(UUID.randomUUID().toString());
    }

    public ArrayList<IdentifierDE> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(ArrayList<IdentifierDE> identifier) {
        this.identifiers = identifier;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public IdentifierDE getIdentifierWithUse(IdentifierDEUseEnum identifierUse){
        for(IdentifierDE identifier: this.identifiers){
            if(identifier.getUse().equals(identifierUse)){
                return(identifier);
            }
        }
        return(null);
    }

    public IdentifierDE getIdentifierWithType(String identifierType){
        for(IdentifierDE identifier: this.identifiers){
            if(identifier.getType().contentEquals(identifierType)){
                return(identifier);
            }
        }
        return(null);
    }

    public void addIdentifier(IdentifierDE newIdentifier){
        if(this.identifiers.contains(newIdentifier)){
            return;
        }
        this.identifiers.add(newIdentifier);
    }

    @Override
    public String toString() {
        return "PegacornDirectoryEntry{" +
                "id=" + id +
                ", identifiers=" + identifiers +
                ", displayName='" + displayName + '\'' +
                ", systemManaged=" + systemManaged +
                '}';
    }
}
