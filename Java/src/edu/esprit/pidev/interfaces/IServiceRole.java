/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.pidev.interfaces;

import edu.esprit.pidev.entities.Role;
import edu.esprit.pidev.entities.Role.RoleEnum;

/**
 *
 * @author ibeno
 */
public interface IServiceRole {
    
    
    public Role getRoleByName(RoleEnum input);
}
