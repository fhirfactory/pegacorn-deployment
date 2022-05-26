/*
 * Copyright (c) 2022 Mark A. Hunter (ACT Health)
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

import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.datatypes.PublishListEntryType;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.datatypes.SubscriptionListEntryType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PublishAndSubscriptSegment implements Serializable {
    private List<PublishListEntryType> publishedDataList;
    private List<SubscriptionListEntryType> subscribedDataList;

    //
    // Constructor(s)
    //

    public PublishAndSubscriptSegment(){
        this.publishedDataList = new ArrayList<>();
        this.subscribedDataList = new ArrayList<>();
    }

    //
    // Getters and Setters
    //

    public List<PublishListEntryType> getPublishedDataList() {
        return publishedDataList;
    }

    public void setPublishedDataList(List<PublishListEntryType> publishedDataList) {
        this.publishedDataList = publishedDataList;
    }

    public List<SubscriptionListEntryType> getSubscribedDataList() {
        return subscribedDataList;
    }

    public void setSubscribedDataList(List<SubscriptionListEntryType> subscribedDataList) {
        this.subscribedDataList = subscribedDataList;
    }

    //
    // toString()
    //

    @Override
    public String toString() {
        return "PublishAndSubscriptSegment{" +
                "publishedDataList=" + publishedDataList +
                ", subscribedDataList=" + subscribedDataList +
                '}';
    }
}
