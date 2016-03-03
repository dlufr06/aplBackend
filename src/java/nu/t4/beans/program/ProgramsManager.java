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

/**
 *
 * @author Daniel Lundberg
 */
@Stateless
public class ProgramsManager { 
      public JsonArray getPrograms() {
        try {
            Connection conn = ConnectionFactory.getConnection();
            Statement stmt = (Statement) conn.createStatement();
            String sql = "SELECT * FROM program";
            ResultSet data = stmt.executeQuery(sql);

            JsonArrayBuilder programs = Json.createArrayBuilder();
            while (data.next()) {
                programs.add(Json.createObjectBuilder()
                        .add("id", data.getInt("id"))
                        .add("namn", data.getString("namn"))
                        .build());
            }
            conn.close();
            return programs.build();

        } catch (Exception e){
            System.out.println("ProgramBean:getPrograms: "+e.getMessage());
            return null;
        }
    }
}
