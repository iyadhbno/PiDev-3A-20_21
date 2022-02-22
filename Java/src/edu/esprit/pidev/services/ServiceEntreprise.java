/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.pidev.services;

import edu.esprit.pidev.interfaces.IServiceEntreprise;
import edu.esprit.pidev.entities.Entreprise;
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
public class ServiceEntreprise implements IServiceEntreprise{

    Connection cnx;

    public ServiceEntreprise() {
        cnx = Connexion.getInstance().getConnection();
    }

    @Override
    public void ajouterEntreprise(Entreprise e) {

        PreparedStatement stmt;
        try {
            String sql = "INSERT INTO user (nom,email,password,tel,photo,role,offre) VALUES(?,?,?,?,?,?,?)";
            stmt = cnx.prepareStatement(sql);
            stmt.setString(1, e.getNom());
            stmt.setString(2, e.getEmail());
            stmt.setString(3, e.getPassword());
            stmt.setString(4, e.getTel());
            stmt.setString(5, e.getPhoto());
            stmt.setString(6, e.getRole().getId().toString());
            stmt.setString(7, e.getOffre());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ServiceAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void modifierEntreprise(Entreprise e) {
        PreparedStatement stmt;
        try {
            String sql = "UPDATE user SET nom=? ,tel=? ,offre=? WHERE email=?";
            stmt = cnx.prepareStatement(sql);
            stmt.setString(1, e.getNom());
            stmt.setString(2, e.getTel());
            stmt.setString(3, e.getOffre());
            stmt.setString(4, e.getEmail());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceEntreprise.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Entreprise loadDataModify(String id) {
        Entreprise e = new Entreprise();
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
                e.setOffre(rst.getString("Offre"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return e;
    }

    
    @Override
    public List<Entreprise> afficherEntreprises() {
        PreparedStatement stmt = null;
        List<Entreprise> Entreprises = new ArrayList<>();

        try {

            String sql = "SELECT * FROM user WHERE role=? ";
            stmt = cnx.prepareStatement(sql);
            stmt.setInt(1, 2);

            ResultSet rst = stmt.executeQuery();

            while (rst.next()) {
                Entreprise e = new Entreprise();
                e.setNom(rst.getString("Nom"));
                e.setEmail(rst.getString("Email"));
                e.setTel(rst.getString("Tel"));
                e.setOffre(rst.getString("Offre"));
                
                e.setEtatCompte(rst.getBoolean("etatCompte")); 
                e.setPhoto(rst.getString("Photo"));
                Entreprises.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        return Entreprises;

    }

}
