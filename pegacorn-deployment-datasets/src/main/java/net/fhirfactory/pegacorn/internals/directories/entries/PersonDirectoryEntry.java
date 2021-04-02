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
package net.fhirfactory.pegacorn.internals.directories.entries;

import net.fhirfactory.pegacorn.internals.directories.entries.common.PegacornDirectoryEntry;
import net.fhirfactory.pegacorn.internals.directories.entries.datatypes.*;

import java.util.ArrayList;

public class PersonDirectoryEntry extends PegacornDirectoryEntry {
    private HumanNameDE officialName;
    private ArrayList<HumanNameDE> otherNames;
    private ArrayList<ContactPointDE> contactPoints;

    public PersonDirectoryEntry(){
        super();
        this.contactPoints = new ArrayList<>();
        this.otherNames = new ArrayList<>();
    }

    public HumanNameDE getOfficialName() {
        return officialName;
    }

    public void setOfficialName(HumanNameDE officialName) {
        this.officialName = officialName;
    }

    public ArrayList<HumanNameDE> getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(ArrayList<HumanNameDE> otherNames) {
        this.otherNames = otherNames;
    }

    public ArrayList<ContactPointDE> getContactPoints() {
        return contactPoints;
    }

    public void setContactPoints(ArrayList<ContactPointDE> contactPoints) {
        this.contactPoints = contactPoints;
    }

    public HumanNameDE getHumanNameWithUse(HumanNameDEUseEnum nameUse){
        for(HumanNameDE currentName: getOtherNames() ){
            if(currentName.getNameUse().equals(nameUse)){
                return(currentName);
            }
        }
        return(null);
    }
}
