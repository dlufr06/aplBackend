/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.t4.services.program;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import nu.t4.beans.APLManager;
import nu.t4.beans.program.ProgramsManager;
import nu.t4.tools.Användare;
import nu.t4.tools.checkAuthorization;

/**
 *
 * @author DanLun2
 */
@Path("api/program")
public class ProgramService {

    @EJB
    APLManager manager;
    
    @EJB
    ProgramsManager programsManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPrograms(@Context HttpHeaders headers) {

        String idTokenString = headers.getHeaderString("Authorization");
        GoogleIdToken.Payload payload = manager.googleAuth(idTokenString);
        if (payload == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        Användare user = checkAuthorization.getUser(payload.getSubject());
        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        return Response.ok(programsManager.getPrograms()).build();
    }
}
