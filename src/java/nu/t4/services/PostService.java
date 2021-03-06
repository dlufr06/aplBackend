/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.t4.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import java.io.StringReader;
import nu.t4.beans.ElevHandledare;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import nu.t4.beans.APLManager;

/**
 *
 * @author maikwagner
 */
@Path("post")
public class PostService {

    @EJB
    ElevHandledare elevHandledare;
    @EJB
    APLManager manager;

    @POST
    @Path("/elevhandledare")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setElevHandledare(@Context HttpHeaders headers, String body) {
        //Kollar att inloggningen är ok
        String idTokenString = headers.getHeaderString("Authorization");
        if (manager.googleAuth(idTokenString) == null) {

            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        //Skapa ett json objekt av indatan
        JsonReader jsonReader = Json.createReader(new StringReader(body));
        JsonArray array = jsonReader.readArray();
        jsonReader.close();

        if (elevHandledare.setElevHandledare(array)) {
            return Response.ok().build();
        } else {
            return Response.serverError().build();
        }
    }

    @POST
    @Path("/logg")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postLogg(@Context HttpHeaders headers, String body) {
        //Kollar att inloggningen är ok
        String idTokenString = headers.getHeaderString("Authorization");
        GoogleIdToken.Payload payload = manager.googleAuth(idTokenString);

        if (payload == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        JsonObject elev = manager.getGoogleUser(payload.getSubject());
        if (elev == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        //Skapa ett json objekt av indatan
        JsonReader jsonReader = Json.createReader(new StringReader(body));
        JsonObject logg = jsonReader.readObject();
        jsonReader.close();
        
        
        int id = elev.getInt("id");
        int ljus = logg.getInt("ljus");
        String datum = logg.getString("datum");
        String innehall = logg.getString("innehall");
        JsonValue bildValue = logg.get("imgUrl");
        String bild = null;
        if(bildValue != JsonValue.NULL)
        {
            bild = bildValue.toString();
        }

        if (manager.postLogg(id, innehall, datum, ljus, bild)) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.serverError().build();
        }
    }
}
