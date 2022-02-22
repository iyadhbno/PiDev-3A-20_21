/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.pidev.entities;

/**
 *
 * @author ibeno
 */
public class Admin extends User {

    public Admin(String nom, String email, String password, String tel, String photo, Role role, Boolean etatCompte) {
        super(nom, email, password, tel, photo, role, etatCompte);
    }

    public Admin(String nom, String email, String tel) {
        super(nom, email, tel);    }

    
    

    public Admin() {
    }

}
