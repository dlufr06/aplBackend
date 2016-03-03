/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.t4.beans.program;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.ResultSet;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import nu.t4.beans.ConnectionFactory;
import nu.t4.tools.Användare;

/**
 *
 * @author Daniel Lundberg
 */
@Stateless
public class HandledareManager { 
    public JsonArray getAll(){
        String sql = String.format("SELECT * FROM handledare");
        return  getHandledare(sql);
    }
    
    public JsonArray getMinaHandledare(Användare anv){
        String sql = String.format("SELECT * FROM handledare WHERE program_id=%d",anv.getProgram());
        return  getHandledare(sql);
    }
    
    private JsonArray getHandledare(String sql) {
        try {
            Connection conn = ConnectionFactory.getConnection();
            Statement stmt = (Statement) conn.createStatement();
            ResultSet data = stmt.executeQuery(sql);

            JsonArrayBuilder programs = Json.createArrayBuilder();
            while (data.next()) {
                programs.add(Json.createObjectBuilder()
                        .add("id", data.getInt("ID"))                      
                        .add("namn", data.getString("namn"))
                        .add("email", data.getString("email"))
                        .add("telefonnummer", data.getString("telefonnummer"))
                        .add("program_id", data.getInt("program_id"))
                        .add("foretag", data.getString("företag"))
                        .build());
            }
            conn.close();
            return programs.build();

        } catch (Exception e){
            System.out.println("HandledareManager:getHandledare: "+e.getMessage());
            return null;
        }
    }
}
