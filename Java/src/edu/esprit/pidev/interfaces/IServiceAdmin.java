/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.pidev.interfaces;

import edu.esprit.pidev.entities.Admin;
import java.util.List;

/**
 *
 * @author ibeno
 */
public interface IServiceAdmin {
    
     void ajouterAdmin(Admin a);
    
     void modifierAdmin(Admin a);
    
     void modifierCv(String mail, String photo);
    
     Admin loadDataModify(String id);
    
     List<Admin> afficherAdmins();
    
     boolean verifSuperAdmin(String mail);
     
    
}
