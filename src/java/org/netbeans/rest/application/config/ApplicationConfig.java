/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.rest.application.config;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Daniel Nilsson
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(nu.t4.filters.CORSFilter.class);
        resources.add(nu.t4.services.APLService.class);
        resources.add(nu.t4.services.AnvInfoService.class);
        resources.add(nu.t4.services.ElevKontaktService.class);
        resources.add(nu.t4.services.ElevService.class);
        resources.add(nu.t4.services.GetLoggElevService.class);
        resources.add(nu.t4.services.GetLoggLärareService.class);
        resources.add(nu.t4.services.GetService.class);
        resources.add(nu.t4.services.HLKontaktService.class);
        resources.add(nu.t4.services.HandledareService.class);
        resources.add(nu.t4.services.KommentarService.class);
        resources.add(nu.t4.services.LarareEleverService.class);
        resources.add(nu.t4.services.LarareOmdomeService.class);
        resources.add(nu.t4.services.LärareRedigeraAnvService.class);
        resources.add(nu.t4.services.MomentService.class);
        resources.add(nu.t4.services.NarvaroService.class);
        resources.add(nu.t4.services.PostService.class);
        resources.add(nu.t4.services.ProgramService.class);
        resources.add(nu.t4.services.elev.ElevLoggService.class);
        resources.add(nu.t4.services.elev.ElevService.class);
        resources.add(nu.t4.services.handledare.HandledareService.class);
        resources.add(nu.t4.services.lärareKontaktService.class);
        resources.add(nu.t4.services.program.ProgramService.class);
    }
    
}
