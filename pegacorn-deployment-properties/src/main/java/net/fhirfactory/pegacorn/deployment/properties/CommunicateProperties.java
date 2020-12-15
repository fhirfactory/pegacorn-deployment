/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.fhirfactory.pegacorn.deployment.properties;

import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author Mark A. Hunter (ACT Health)
 */

@ApplicationScoped
public class CommunicateProperties {
    

    // Site Details
    private static String ROOMSERVER_IDENTITY_PREFIX = "CHS";

    public static String getRoomserverIdentityPrefix() {
        return ROOMSERVER_IDENTITY_PREFIX;
    }
}
