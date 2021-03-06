package nu.t4.beans;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.LinkedList;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonValue;

/**
 *
 * @author maikwagner
 */
@Stateless
public class NarvaroManager {

    public JsonArray getNarvaro() {
        try {
            Connection conn = ConnectionFactory.getConnection();
            Statement stmt = (Statement) conn.createStatement();
            String sql = "SELECT * FROM närvaro";
            ResultSet data = stmt.executeQuery(sql);

            JsonArrayBuilder elever = Json.createArrayBuilder();

            while (data.next()) {
                elever.add(Json.createObjectBuilder()
                        .add("narvaro_id", data.getInt("närvaro_id"))
                        .add("anvandar_id", data.getInt("användar_id"))
                        .add("trafikljus", data.getInt("trafikljus"))
                        .add("godkant", data.getInt("godkänt"))
                        .add("datum", data.getString("datum"))
                        .build());
            }

            conn.close();
            return elever.build();

        } catch (Exception e) {
            System.out.println("NarvaroManagar - getNarvaro()");
            System.out.println(e.getMessage());
            return null;
        }

    }

    public boolean setNarvaro(JsonObject array, int id) {
        try {
            Connection conn = ConnectionFactory.getConnection();
            Statement stmt = (Statement) conn.createStatement();
            String sqlbase = "INSERT INTO närvaro VALUES(null, %d, %d, 0, '%s')";
            String sql = "";

            JsonObject item = array;
            int trafikljus = item.getInt("trafikljus");
            String datum = item.getString("datum");
            sql = String.format(sqlbase, id, trafikljus, datum);
            System.out.println(sql);
            stmt.executeUpdate(sql);
            conn.close();
            return true;

        } catch (Exception e) {
            System.out.println("elevhandledare - setElevHandledare()");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public JsonArray getGodkandNarvaro(int larare_id, int klass_id) {
        try {
            Connection conn = ConnectionFactory.getConnection();
            Statement stmt = (Statement) conn.createStatement();
            String sql = String.format("SELECT namn, id FROM skolans_användare "
                    + "WHERE behörighet = 0 AND klass = %d AND %d IN (SELECT id FROM klass "
                    + "WHERE program_id = (SELECT program_id FROM klass "
                    + "WHERE id = (SELECT klass FROM skolans_användare "
                    + "WHERE id = %d)))", klass_id, klass_id, larare_id);
            ResultSet data = stmt.executeQuery(sql);
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            while (data.next()) {
                int elev_id = data.getInt("id");
                String namn = data.getString("namn");
                arrayBuilder.add(Json.createObjectBuilder()
                        .add("elev_id", elev_id)
                        .add("namn", namn)
                        .build());
            }
            Iterator<JsonValue> iterator = arrayBuilder.build().iterator();
            while (iterator.hasNext()) {
                JsonObject obj = (JsonObject) iterator.next();
                JsonArrayBuilder arrayBuilder2 = Json.createArrayBuilder();
                int elev_id = obj.getInt("elev_id");
                String namn = obj.getString("namn");

                sql = String.format("SELECT UNIX_TIMESTAMP(datum) AS datum, trafikljus FROM närvaro "
                        + "WHERE användar_id = %d AND godkänt != 0 AND godkänt != 2 ORDER BY datum", elev_id);
                System.out.println(sql);
                ResultSet data2 = stmt.executeQuery(sql);
                while (data2.next()) {
                    arrayBuilder2.add(Json.createObjectBuilder()
                            .add("datum", data2.getInt("datum"))
                            .add("trafikljus", data2.getInt("trafikljus"))
                            .build());
                }
                arrayBuilder.add(Json.createObjectBuilder()
                        .add("elev_id", elev_id)
                        .add("namn", namn)
                        .add("narvaro", arrayBuilder2.build())
                        .build());
            }

            conn.close();
            return arrayBuilder.build();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
};
