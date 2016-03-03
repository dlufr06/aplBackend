/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.t4.services.elev;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import nu.t4.beans.APLManager;
import nu.t4.beans.elev.ElevManager;
import nu.t4.tools.Användare;
import nu.t4.tools.Behörighet;
import nu.t4.tools.checkAuthorization;

/**
 *
 * @author Daniel Lundberg
 */
@Path("api/elev")
public class ElevService {

    @EJB
    APLManager manager;

    @EJB
    ElevManager elevManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOwn(@Context HttpHeaders headers) {

        String idTokenString = headers.getHeaderString("Authorization");
        GoogleIdToken.Payload payload = manager.googleAuth(idTokenString);
        if (payload == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        Användare anv = checkAuthorization.getUser(payload.getSubject());
        if (anv == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        Behörighet behörighet = anv.getBehörighet();

        if (behörighet == Behörighet.lärare) {
            return Response.ok(elevManager.getElevFranKlass(anv.getKlass())).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@Context HttpHeaders headers) {

        String idTokenString = headers.getHeaderString("Authorization");
        GoogleIdToken.Payload payload = manager.googleAuth(idTokenString);
        if (payload == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        Användare anv = checkAuthorization.getUser(payload.getSubject());
        if (anv == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        Behörighet behörighet = anv.getBehörighet();

        if (behörighet == Behörighet.lärare) {
            return Response.ok(elevManager.getElevAll()).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

    }

}
