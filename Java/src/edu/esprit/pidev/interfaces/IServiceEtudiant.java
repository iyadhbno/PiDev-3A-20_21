/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.pidev.interfaces;

import edu.esprit.pidev.entities.Etudiant;
import java.util.List;

/**
 *
 * @author ibeno
 */
public interface IServiceEtudiant {
    
      public void ajouterEtudiant(Etudiant e);
      
      public void modifierEtudiant(Etudiant e);
      
      public void modifierCv(String mail , String cv);
      
      public Etudiant loadDataModify(String id);
      
      public List<Etudiant> afficherEtudiants();
      
      
      
      
}
