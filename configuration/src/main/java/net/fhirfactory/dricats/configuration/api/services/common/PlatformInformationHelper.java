/*
 * The MIT License
 *
 * Copyright 2022 Mark A. Hunter (ACT Health).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.fhirfactory.dricats.configuration.api.services.common;

import net.fhirfactory.dricats.util.SystemPropertyHelper;

/**
 *
 * @author mhunter
 */
public abstract class PlatformInformationHelper {
    
    private SystemPropertyHelper propertyHelper;

    //
    // Constructor
    //

    public PlatformInformationHelper(SystemPropertyHelper propertyHelper){
        this.propertyHelper = propertyHelper;
    }

    //
    // Getters and Setters
    //
    
    protected SystemPropertyHelper getPropertyHelper(){
        return(propertyHelper);
    }
        
    //
    // Business Methods
    //
    
    protected String getActualHostIP(){
        String actualHostIP = getPropertyHelper().getProperty("MY_HOST_IP", "Unknown");
        return(actualHostIP);
    }

    protected String getActualPodIP(){
        String actualHostIP = getPropertyHelper().getProperty("MY_POD_IP", "Unknown");
        return(actualHostIP);
    }
    
}
