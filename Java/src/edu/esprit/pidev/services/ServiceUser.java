/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.pidev.services;
import edu.esprit.pidev.entities.Admin;
import edu.esprit.pidev.interfaces.IServiceUser;
import edu.esprit.pidev.utiles.Connexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ibeno
 */
public class ServiceUser implements IServiceUser{

    Connection cnx;

    public ServiceUser() {
        cnx = Connexion.getInstance().getConnection();
    }

    @Override
    public int verifierData(String id, String password) { //Login user 

        PreparedStatement stmt = null;
        ResultSet rst = null;
        int resultat = -1;
        try {
            String sql = "SELECT * FROM user WHERE (tel=? OR email=?) AND password=?";
            stmt = cnx.prepareStatement(sql);
            stmt.setString(1, id);
            stmt.setString(2, id);
            stmt.setString(3, password);
            rst = stmt.executeQuery();
            if (rst.next()) {
                resultat = rst.getShort("role");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return resultat;
    }
    
    
    @Override
    public boolean verifEtatCompte(String mail) { //verifie l'etat de compte

        PreparedStatement stmt = null;
        ResultSet rst = null;
        boolean resultat = false;
        try {
            String sql = "SELECT * FROM user WHERE email=?";
            stmt = cnx.prepareStatement(sql);
            stmt.setString(1, mail);
            rst = stmt.executeQuery();
            if (rst.next()) {
                resultat = rst.getBoolean("etatCompte");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return resultat;
    }

    @Override
    public boolean verifierEmailBd(String email) { //Controle De Saisie si mail existe
        PreparedStatement stmt = null;
        ResultSet rst = null;
        try {
            String sql = "SELECT * FROM user WHERE email=?";
            stmt = cnx.prepareStatement(sql);
            stmt.setString(1, email);
            rst = stmt.executeQuery();
            if (rst.next()) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
    
    
    @Override
        public boolean verifierPwBd(String password) { //Controle De Saisie si password correct
        PreparedStatement stmt = null;
        ResultSet rst = null;
        try {
            String sql = "SELECT password FROM user WHERE password=?";
            stmt = cnx.prepareStatement(sql);
            stmt.setString(1, password);
            rst = stmt.executeQuery();
            if (rst.next()) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
    

    
    @Override
    public void modifierPassword(String mail, String password) {
        PreparedStatement stmt;
        try {

            String sql = "UPDATE user SET password=? WHERE email=?";
            stmt = cnx.prepareStatement(sql);
            stmt.setString(1, password);
            stmt.setString(2, mail);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ServiceEntreprise.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
    @Override
    public void modifierEtatCompte(String mail, Boolean etatCompte) {
        PreparedStatement stmt;
        try {

            String sql = "UPDATE user SET etatCompte=? WHERE email=?";
            stmt = cnx.prepareStatement(sql);
            stmt.setBoolean(1, etatCompte);
            stmt.setString(2, mail);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ServiceEntreprise.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    @Override
     public void modifierPhoto(String mail , String photo) {
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
     
         public int nbAdmins() {
        Statement stm = null;
        List<Admin> Coachs = new ArrayList<>();

        try {
            stm = cnx.createStatement();
            String CoachQ = "SELECT email FROM `user` WHERE role='3'";
            ResultSet rst = stm.executeQuery(CoachQ);

            while (rst.next()) {
                Admin c = new Admin();
                c.setEmail(rst.getString("email"));
                Coachs.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        return Coachs.size();

    }
         
          public int nbEntreprises() {
        Statement stm = null;
        List<Admin> Coachs = new ArrayList<>();

        try {
            stm = cnx.createStatement();
            String CoachQ = "SELECT email FROM `user` WHERE role='2'";
            ResultSet rst = stm.executeQuery(CoachQ);

            while (rst.next()) {
                Admin c = new Admin();
                c.setEmail(rst.getString("email"));
                Coachs.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        return Coachs.size();

    }
          
           public int nbEudiants() {
        Statement stm = null;
        List<Admin> Coachs = new ArrayList<>();

        try {
            stm = cnx.createStatement();
            String CoachQ = "SELECT email FROM `user` WHERE role='1'";
            ResultSet rst = stm.executeQuery(CoachQ);

            while (rst.next()) {
                Admin c = new Admin();
                c.setEmail(rst.getString("email"));
                Coachs.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        return Coachs.size();

    }

}
