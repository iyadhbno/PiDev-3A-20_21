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
public class Role {
    public enum RoleEnum {
    Admin, SuperAdmin, Etudiant, Entreprise 
}
    private Short id;
    private RoleEnum name;

    public Role(Short id, RoleEnum name) {
        this.id = id;
        this.name = name ;
    }

    /**
     * @return the id
     */
    public Short getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Short id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public RoleEnum getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(RoleEnum name) {
        this.name = name;
    }
    
    
}
