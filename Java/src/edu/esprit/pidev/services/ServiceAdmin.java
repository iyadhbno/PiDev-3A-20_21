/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.pidev.services;

import edu.esprit.pidev.interfaces.IServiceAdmin;
import edu.esprit.pidev.entities.Admin;
import java.sql.Connection;
import java.sql.SQLException;
import edu.esprit.pidev.utiles.Connexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ibeno
 */
public class ServiceAdmin implements IServiceAdmin{

    
    
    Connection cnx;

    public ServiceAdmin() {
        cnx = Connexion.getInstance().getConnection();
    }

    @Override
    public void ajouterAdmin(Admin a) {

        PreparedStatement stmt;
        try {
            String sql = "INSERT INTO user (nom,email,password,tel,photo,role,etatCompte) VALUES( ?,? ,? ,? , ?,?,?)";
            stmt = cnx.prepareStatement(sql);
            stmt.setString(1, a.getNom());
            stmt.setString(2, a.getEmail());
            stmt.setString(3, a.getPassword());
            stmt.setString(4, a.getTel());
            stmt.setString(5, a.getPhoto());
            stmt.setString(6, a.getRole().getId().toString());
            stmt.setBoolean(7, a.getEtatCompte());

            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ServiceAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void modifierAdmin(Admin a) {

        PreparedStatement stmt;
        try {

            String sql = "UPDATE user SET nom=? ,tel=? WHERE email=?";
            stmt = cnx.prepareStatement(sql);
            stmt.setString(1, a.getNom());
            stmt.setString(2, a.getTel());
            stmt.setString(3, a.getEmail());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ServiceAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void modifierCv(String mail, String photo) {
        PreparedStatement stmt;
        try {

            String sql = "UPDATE user SET photo=? WHERE email=?";
            stmt = cnx.prepareStatement(sql);
            stmt.setString(1, photo);
            stmt.setString(2, mail);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceEntreprise.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public Admin loadDataModify(String id) {
        Admin a = new Admin();
        PreparedStatement stmt;

        try {
            String sql = "SELECT * FROM user WHERE email=?";
            stmt = cnx.prepareStatement(sql);
            stmt.setString(1, id);

            ResultSet rst = stmt.executeQuery();
            while (rst.next()) {
                a.setNom(rst.getString("Nom"));
                a.setEmail(rst.getString("Email"));
                a.setTel(rst.getString("Tel"));
                a.setPhoto(rst.getString("Photo"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return a;
    }

    @Override
    public List<Admin> afficherAdmins() {
        PreparedStatement stmt = null;
        List<Admin> Admin = new ArrayList<>();

        try {

            String sql = "SELECT * FROM user WHERE role=? ";
            stmt = cnx.prepareStatement(sql);
            stmt.setInt(1, 0);

            ResultSet rst = stmt.executeQuery();

            while (rst.next()) {
                Admin a = new Admin();
                a.setNom(rst.getString("Nom"));
                a.setEmail(rst.getString("Email"));
                a.setTel(rst.getString("Tel"));
                a.setPhoto(rst.getString("Photo"));
                a.setEtatCompte(rst.getBoolean("etatCompte"));
                Admin.add(a);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        return Admin;

    }

    @Override
    public boolean verifSuperAdmin(String mail) {

        Statement stm;
        String role = "";

        try {
            stm = cnx.createStatement();

            String query = "SELECT * FROM user WHERE email='" + mail + "'";
            ResultSet rst = stm.executeQuery(query);

            while (rst.next()) {
                role = rst.getString("role");
                if ("3".equals(role)) {

                    return true;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ServiceAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
