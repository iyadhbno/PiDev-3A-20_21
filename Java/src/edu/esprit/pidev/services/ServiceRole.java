/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.pidev.services;
import edu.esprit.pidev.interfaces.IServiceRole;
import edu.esprit.pidev.entities.Role;
import edu.esprit.pidev.entities.Role.RoleEnum;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import edu.esprit.pidev.utiles.Connexion;

/**
 *
 * @author ibeno
 */
public class ServiceRole implements IServiceRole{

    Connection cnx;

    public ServiceRole() {
        cnx = Connexion.getInstance().getConnection();
    }

    //recuperation du role id par le nom
    @Override
    public Role getRoleByName(RoleEnum input) {

        Statement stm;
        try {
            stm = cnx.createStatement();

            String query = "SELECT id from role Where name='" +input+"'";
            ResultSet rst = stm.executeQuery(query);

            while (rst.next()) {
                Short id = rst.getShort("id");
                return new Role(id, input);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceEtudiant.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

}
