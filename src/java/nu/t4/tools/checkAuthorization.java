/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.t4.tools;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.ResultSet;
import nu.t4.beans.ConnectionFactory;

/**
 *
 * @author DanLun2
 */
public class checkAuthorization {

    public static Användare getUser(String google_id) {
        try {
            Connection conn = ConnectionFactory.getConnection();
            Statement stmt = (Statement) conn.createStatement();
            String sql = String.format("SELECT * FROM skolans_användare WHERE google_id='%s'", google_id);
            ResultSet data = stmt.executeQuery(sql);
            data.next();
            Användare user; 
            if (data.getInt("behörighet") == 0) {
                user = new Användare(google_id, data.getInt("ID"), data.getInt("klass"), data.getInt("handledare_ID"), data.getInt("senast_inloggad"), 0, data.getString("namn"), data.getString("email"), data.getString("telefonnummer"));
            }else{
                user = new Användare(google_id, data.getInt("ID"), data.getInt("klass"), -1, data.getInt("senast_inloggad"), data.getInt("behörighet"), data.getString("namn"), data.getString("email"), data.getString("telefonnummer"));
            }
            conn.close();
            return user;
        } catch (Exception e) {
            System.out.println("CheckAuth:"+e.getMessage());
            return null;
        }

    }
}
