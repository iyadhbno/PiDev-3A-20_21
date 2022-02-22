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
public class Etudiant extends User {
    
    private String cv;
    
    public Etudiant( String nom, String email, String password, String tel, String photo, Role role, String cv, Boolean etatCompte) {
        super( nom, email, password, tel, photo, role, etatCompte);
        this.cv = cv;
    }

    public Etudiant(String nom, String email, String tel) {
        super(nom, email, tel);
    }

    public Etudiant() {
    }



    /**
     * @return the cv
     */
    public String getCv() {
        return cv;
    }

    /**
     * @param cv the cv to set
     */
    public void setCv(String cv) {
        this.cv = cv;
    }
    
}
