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
public class Användare {

    private int id, klass, handledar_id, senast_inloggad;
    private String namn, email, telefonnummer, google_id;
    private Behörighet behörighet;

    public Användare(String google_id, int id, int klass, int handledar_id, int senast_inloggad, int behörighet, String namn, String email, String telefonnummer) {
        this.google_id = google_id;
        this.id = id;
        this.klass = klass;
        this.handledar_id = handledar_id;
        this.senast_inloggad = senast_inloggad;
        this.behörighet =  Behörighet.values()[behörighet];
        this.namn = namn;
        this.email = email;
        this.telefonnummer = telefonnummer;
    }

    public int getProgram() {
        try {
            Connection conn = ConnectionFactory.getConnection();
            Statement stmt = (Statement) conn.createStatement();
            String sql = String.format("SELECT program_id FROM klass WHERE ID=%d", klass);
            ResultSet data = stmt.executeQuery(sql);
            data.next();
            int program_id = data.getInt("program_id");
            conn.close();
            return program_id;
        } catch (Exception e) {
            System.out.println("Användare:getProgram: "+e.getMessage());
            return -1;
        }

    }

    //getters & setters
    public void setGoogle_id(String google_id) {
        this.google_id = google_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setKlass(int klass) {
        this.klass = klass;
    }

    public void setHandledar_id(int handledar_id) {
        this.handledar_id = handledar_id;
    }

    public void setSenast_inloggad(int senast_inloggad) {
        this.senast_inloggad = senast_inloggad;
    }

    public void setBehörighet(int behörighet) {
        this.behörighet =  Behörighet.values()[behörighet];
    }

    public void setNamn(String namn) {
        this.namn = namn;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefonnummer(String telefonnummer) {
        this.telefonnummer = telefonnummer;
    }

    public String getGoogle_id() {
        return google_id;
    }

    public int getId() {
        return id;
    }

    public int getKlass() {
        return klass;
    }

    public int getHandledar_id() {
        return handledar_id;
    }

    public int getSenast_inloggad() {
        return senast_inloggad;
    }

    public Behörighet getBehörighet() {
        return behörighet;
    }

    public String getNamn() {
        return namn;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefonnummer() {
        return telefonnummer;
    }

}
