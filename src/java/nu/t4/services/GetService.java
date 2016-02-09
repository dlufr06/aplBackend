/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.t4.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import nu.t4.beans.ElevHandledare;
import javax.ejb.EJB;
import javax.json.JsonArray;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import nu.t4.beans.APLManager;
import nu.t4.beans.AktivitetManager;

/**
 *
 * @author maikwagner
 */
@Path("get")
public class GetService {

    @EJB
    ElevHandledare elevHandledare;
    @EJB
    APLManager manager;
    @EJB
    AktivitetManager aktivitetManager;

    @GET
    @Path("/elever")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getElever(@Context HttpHeaders headers) {
        //Kollar att inloggningen är ok
        String idTokenString = headers.getHeaderString("Authorization");
        if (manager.googleAuth(idTokenString) == null) {

            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        JsonArray elever = elevHandledare.getElev();
        if (elever != null) {
            return Response.ok(elever).build();
        } else {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("handledare")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHandledare(@Context HttpHeaders headers) {
        //Kollar att inloggningen är ok
        String idTokenString = headers.getHeaderString("Authorization");
        if (manager.googleAuth(idTokenString) == null) {

            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        JsonArray handledare = elevHandledare.getHandledare();
        if (handledare != null) {
            return Response.ok(handledare).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("aktiviteter")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAktiviteter(@Context HttpHeaders headers) {
        String basic_auth = headers.getHeaderString("Authorization");

        if (!manager.handledarAuth(basic_auth)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        
        int id = manager.getHandledarId(basic_auth);

        JsonArray aktiviteter = aktivitetManager.getAktiviteter(id);
        if (aktiviteter != null) {
            return Response.ok(aktiviteter).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
