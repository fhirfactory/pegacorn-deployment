/*
 * The MIT License
 *
 * Copyright 2022 Mark A. Hunter.
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
package net.fhirfactory.dricats.configuration.defaults.dricats.subsystems.mitaf.mllp;

/**
 *
 * @author mhunter
 */
public class MLLPConfigurationDefaults {
    public static final Integer MAXIMUM_CONCURRENT_CONSUMERS = 20;
    public static final Integer ACCEPT_TIMEOUT = 30000;
    public static final Integer BIND_TIMEOUT = 30000;
    public static final Boolean CONVERT_TO_STRING_PAYLOAD = true;
    public static final Boolean VALIDATE_PAYLOAD = false;
    public static final Boolean KEEP_ALIVE = true;
    public static final Integer CONNECT_TIMEOUT = 30000;
    public static final Boolean FORWARD_ZDE_SEGMENT = false;
    public static final Boolean GENERATE_HL7_HEADERS = true;
}
