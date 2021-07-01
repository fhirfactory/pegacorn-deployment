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
package net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments;

public class ApplicationDebugSegment {
    private String wildflyLogLevel;
    private String wildflyLogEnable;
    private String pegacornLogLevel;
    private String pegacornLogCategory;
    private String aetherLogLevel;
    private String aetherLogCategory;
    private String javaxNetDebug;

    public ApplicationDebugSegment(){
        super();
    }

    public String getWildflyLogLevel() {
        return wildflyLogLevel;
    }

    public void setWildflyLogLevel(String wildflyLogLevel) {
        this.wildflyLogLevel = wildflyLogLevel;
    }

    public String getWildflyLogEnable() {
        return wildflyLogEnable;
    }

    public void setWildflyLogEnable(String wildflyLogEnable) {
        this.wildflyLogEnable = wildflyLogEnable;
    }

    public String getPegacornLogLevel() {
        return pegacornLogLevel;
    }

    public void setPegacornLogLevel(String pegacornLogLevel) {
        this.pegacornLogLevel = pegacornLogLevel;
    }

    public String getPegacornLogCategory() {
        return pegacornLogCategory;
    }

    public void setPegacornLogCategory(String pegacornLogCategory) {
        this.pegacornLogCategory = pegacornLogCategory;
    }

    public String getAetherLogLevel() {
        return aetherLogLevel;
    }

    public void setAetherLogLevel(String aetherLogLevel) {
        this.aetherLogLevel = aetherLogLevel;
    }

    public String getAetherLogCategory() {
        return aetherLogCategory;
    }

    public void setAetherLogCategory(String aetherLogCategory) {
        this.aetherLogCategory = aetherLogCategory;
    }

    public String getJavaxNetDebug() {
        return javaxNetDebug;
    }

    public void setJavaxNetDebug(String javaxNetDebug) {
        this.javaxNetDebug = javaxNetDebug;
    }

    @Override
    public String toString() {
        return "ApplicationDebugSegment{" +
                "wildflyLogLevel='" + wildflyLogLevel + '\'' +
                ", wildflyLogEnable='" + wildflyLogEnable + '\'' +
                ", pegacornLogLevel='" + pegacornLogLevel + '\'' +
                ", pegacornLogCategory='" + pegacornLogCategory + '\'' +
                ", aetherLogLevel='" + aetherLogLevel + '\'' +
                ", aetherLogCategory='" + aetherLogCategory + '\'' +
                ", javaxNetDebug='" + javaxNetDebug + '\'' +
                '}';
    }
}
