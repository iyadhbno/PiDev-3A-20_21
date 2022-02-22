/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.pidev.interfaces;

/**
 *
 * @author ibeno
 */
public interface IServiceUser {
    
    public int verifierData(String id, String password);
    
    public boolean verifEtatCompte(String mail);
    
    public boolean verifierEmailBd(String email);
    
    public boolean verifierPwBd(String password);
    
    public void modifierPassword(String mail, String password);
    
    public void modifierEtatCompte(String mail, Boolean etatCompte);
    
    public void modifierPhoto(String mail , String photo);
    
    
}
