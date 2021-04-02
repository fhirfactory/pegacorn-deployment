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
import net.fhirfactory.pegacorn.internals.directories.entries.datatypes.ContactPointDE;
import net.fhirfactory.pegacorn.internals.directories.entries.datatypes.IdentifierDE;
import net.fhirfactory.pegacorn.internals.directories.entries.datatypes.IdentifierDEUseEnum;

import java.util.ArrayList;

public class PractitionerRoleDirectoryEntry extends PegacornDirectoryEntry {
    private IdentifierDE primaryOrganizationID;
    private IdentifierDE primaryLocationID;
    private String primaryRoleCategory;
    private String primaryRoleID;
    private String practitionerRoleADGroup;
    private ArrayList<ContactPointDE> contactPoints;
    private ArrayList<IdentifierDE> activePractitionerSet;

    public PractitionerRoleDirectoryEntry(){
        this.contactPoints = new ArrayList<>();
        this.activePractitionerSet = new ArrayList<>();
    }

    public IdentifierDE getPrimaryOrganizationID() {
        return primaryOrganizationID;
    }

    public void setPrimaryOrganizationID(IdentifierDE primaryOrganizationID) {
        this.primaryOrganizationID = primaryOrganizationID;
    }

    public IdentifierDE getPrimaryLocationID() {
        return primaryLocationID;
    }

    public void setPrimaryLocationID(IdentifierDE primaryLocationID) {
        this.primaryLocationID = primaryLocationID;
    }

    public String getPrimaryRoleCategory() {
        return primaryRoleCategory;
    }

    public void setPrimaryRoleCategory(String primaryRoleCategory) {
        this.primaryRoleCategory = primaryRoleCategory;
    }

    public String getPrimaryRoleID() {
        return primaryRoleID;
    }

    public void setPrimaryRoleID(String primaryRoleID) {
        this.primaryRoleID = primaryRoleID;
    }

    public String getPractitionerRoleADGroup() {
        return practitionerRoleADGroup;
    }

    public void setPractitionerRoleADGroup(String practitionerRoleADGroup) {
        this.practitionerRoleADGroup = practitionerRoleADGroup;
    }

    public ArrayList<IdentifierDE> getActivePractitionerSet() {
        return activePractitionerSet;
    }

    public void setActivePractitionerSet(ArrayList<IdentifierDE> activePractitionerSet) {
        this.activePractitionerSet = activePractitionerSet;
    }

    public ArrayList<ContactPointDE> getContactPoints() {
        return contactPoints;
    }

    public void setContactPoints(ArrayList<ContactPointDE> contactPoints) {
        this.contactPoints = contactPoints;
    }
}
