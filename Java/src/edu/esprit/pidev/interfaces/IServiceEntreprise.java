/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.pidev.interfaces;

import edu.esprit.pidev.entities.Entreprise;
import java.util.List;

/**
 *
 * @author ibeno
 */
public interface IServiceEntreprise {
    
    public void ajouterEntreprise(Entreprise e);
    
    public void modifierEntreprise(Entreprise e);
    
    public Entreprise loadDataModify(String id);
    
    public List<Entreprise> afficherEntreprises();
    
}
