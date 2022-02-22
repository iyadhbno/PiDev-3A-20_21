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
public class Entreprise extends User {
    
    private String offre ;
    
    public Entreprise(String nom, String email, String password, String tel, String photo, Role role, String offre, Boolean etatCompte) {
        super(nom, email, password, tel, photo, role, etatCompte);
        this.offre = offre ;
      
       
    }

    public Entreprise(String nom, String email, String tel, String offre) {
        super(nom, email, tel);
        this.offre = offre;
    }

    public Entreprise(String password) {
        super(password);
    }
    
    
    public Entreprise() {
    }

    /**
     * @return the offre
     */
    public String getOffre() {
        return offre;
    }

    /**
     * @param offre the offre to set
     */
    public void setOffre(String offre) {
        this.offre = offre;
    }
    
}
