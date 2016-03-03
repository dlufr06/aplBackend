/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.t4.beans.elev;

import com.mysql.jdbc.Connection;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonValue;
import nu.t4.beans.ConnectionFactory;

/**
 *
 * @author Daniel Lundberg
 */
@Stateless
public class ElevManager {
    
    public JsonArray getElevFranKlass(int klass_id){
            String sql = String.format("SELECT * FROM aplapp.skolans_användare WHERE klass = %d AND behörighet = 0;", klass_id);
            return getElever(sql);
    }
    public JsonArray getElevAll(){
            String sql = String.format("SELECT * FROM aplapp.skolans_användare WHERE behörighet = 0;");
            return getElever(sql);
    }
    
        
    //Privata metoder
    private JsonArray getElever(String sql){
            try {
            Connection conn = ConnectionFactory.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet data = stmt.executeQuery(sql);
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            while (data.next()) {
                arrayBuilder.add(
                        Json.createObjectBuilder()
                        .add("id",data.getInt("ID"))
                        .add("namn",data.getString("namn"))
                        .add("handledare_ID",data.getInt("handledare_ID"))
                        .build()
                );
                
            }
            conn.close();
            return arrayBuilder.build();
        } catch (Exception e) {
            System.out.println("Error from ElevManager:getElevFranKlass: "+e.getMessage());
            return null;
        }
    }

}
