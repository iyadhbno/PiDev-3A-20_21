/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.pidev.services;

import edu.esprit.pidev.interfaces.IServiceEtudiant;
import edu.esprit.pidev.entities.Etudiant;
import java.sql.Connection;
import java.sql.SQLException;
import edu.esprit.pidev.utiles.Connexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ibeno
 */
public class ServiceEtudiant implements IServiceEtudiant{

    Connection cnx;

    public ServiceEtudiant() {
        cnx = Connexion.getInstance().getConnection();
    }

    @Override
    public void ajouterEtudiant(Etudiant e) {

        PreparedStatement stmt = null;
        try {
            String sql = "INSERT INTO user (nom,email,password,tel,photo,role,cv) VALUES(?,?,?,?,?,?,?)";
            stmt = cnx.prepareStatement(sql);
            stmt.setString(1, e.getNom());
            stmt.setString(2, e.getEmail());
            stmt.setString(3, e.getPassword());
            stmt.setString(4, e.getTel());
            stmt.setString(5, e.getPhoto());
            stmt.setString(6, e.getRole().getId().toString());
            stmt.setString(7, e.getCv());
            
           
            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ServiceAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void modifierEtudiant(Etudiant e) {
        PreparedStatement stmt;
        try {

            String sql = "UPDATE user SET nom=?, tel=? ,cv=? WHERE email=?";
            stmt = cnx.prepareStatement(sql);
            stmt.setString(1, e.getNom());
            stmt.setString(2, e.getTel());
            stmt.setString(3, e.getCv());
            stmt.setString(4, e.getEmail());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ServiceEntreprise.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
    @Override
        public void modifierCv(String mail , String cv) {
        PreparedStatement stmt;
        try {

            String sql = "UPDATE user SET cv=? WHERE email=?";
            stmt = cnx.prepareStatement(sql);
            stmt.setString(1, cv);
            stmt.setString(2, mail);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceEntreprise.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    
    @Override
    public Etudiant loadDataModify(String id) {
        Etudiant e = new Etudiant();
        PreparedStatement stmt;

        try {
            String sql = "SELECT * FROM user WHERE email=?";
            stmt = cnx.prepareStatement(sql);
            stmt.setString(1, id);

            ResultSet rst = stmt.executeQuery();
            while (rst.next()) {
                e.setNom(rst.getString("Nom"));
                e.setEmail(rst.getString("Email"));
                e.setTel(rst.getString("Tel"));
                e.setPhoto(rst.getString("Photo"));
                e.setCv(rst.getString("Cv"));
            }
        } catch (SQLException ex) {
            
        }
        return e;
    }
    

    
    
    @Override
    public List<Etudiant> afficherEtudiants() {
        PreparedStatement stmt = null;
        List<Etudiant> Etudiant = new ArrayList<>();
        try {
            String sql = "SELECT * FROM user WHERE role=?";
            stmt = cnx.prepareStatement(sql);
            stmt.setInt(1, 1);
            ResultSet rst = stmt.executeQuery();
            while (rst.next()) {
                Etudiant e = new Etudiant();
                e.setNom(rst.getString("Nom"));
                e.setEmail(rst.getString("Email"));
                e.setCv(rst.getString("Cv"));
                e.setTel(rst.getString("Tel"));
                e.setEtatCompte(rst.getBoolean("etatCompte"));
                e.setPhoto(rst.getString("Photo"));
                
                Etudiant.add(e);
                System.out.println(e.getEtatCompte());
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return Etudiant;

    }

}
