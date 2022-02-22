/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.pidev.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import java.io.IOException;


/**
 *
 * @author ibeno
 */
public class Login extends Form {

    Form current;

    public Login() {

        current = this; //Récupération de l'interface(Form) en cours
        setTitle("Home");
        setLayout(BoxLayout.y());

        add(new Label("Choose an option"));
        Button btnAddTask = new Button("Add Task");
        Button btnListTasks = new Button("List Tasks");
        
        String username ="moderna@gmail.com";
        btnAddTask.addActionListener(e -> {
            try {
                new ProfilEnt(username).show();
            } catch (IOException ex) {
               
            }
        });
        addAll(btnAddTask, btnListTasks);

    }

}


