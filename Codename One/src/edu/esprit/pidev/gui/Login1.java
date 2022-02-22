/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.pidev.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import edu.esprit.pidev.entities.Entreprise;
import edu.esprit.pidev.entities.Etudiant;
import edu.esprit.pidev.service.ServiceEnt;
import edu.esprit.pidev.service.ServiceEtu;
import edu.esprit.pidev.service.ServiceUser;
import java.io.IOException;
import static java.lang.String.valueOf;
import java.util.Random;

/**
 *
 * @author ibeno
 */
public class Login1 extends Form {

    Form current;
    public static String username = "";

    public Login1() {
        current = this; //Récupération de l'interface(Form) en cours
        setTitle("Acceuil");
        setLayout(BoxLayout.y());

        
        Button InscritEtu = new Button("Inscription etudiant");
        Button InscritEnt = new Button("Inscription entreprise");
        FontImage.setMaterialIcon(InscritEtu, FontImage.MATERIAL_CREATE);
        FontImage.setMaterialIcon(InscritEnt, FontImage.MATERIAL_CREATE);

        Button seConnecter = new Button("Se connecter");
        FontImage.setMaterialIcon(seConnecter, FontImage.MATERIAL_LOGIN);

        TextField email = new TextField("", "Adresse Email");
        TextField password = new TextField("", "Mot de passe");
        
        Button forgetPassword = new Button("mot de passe oublié");


        password.setConstraint(TextField.PASSWORD);

        seConnecter.addActionListener((e) -> {
            String result = ServiceUser.getInstance().loginCheck(email.getText(), password.getText());

            if ("1".equals(result)) {
                Etudiant etu = new Etudiant();
                this.username = email.getText();
                etu = ServiceEtu.getInstance().getUser(this.username);

                Dialog d = new Dialog("Bienvenue");
                
                TextArea popupBody = new TextArea("Salut " + etu.getNom().toUpperCase() + " L'equipe Aura est heureuse de te souhaiter la bienvenue.", 3, 20);
                popupBody.setUIID("PopupBody");
                popupBody.setEditable(false);
                d.setLayout(new BorderLayout());
                d.add(BorderLayout.CENTER, popupBody);
                try {
                    new ProfilEtu(this.username).show();
                } catch (IOException ex) {
                }
            }else if ("2".equals(result)) {
                Entreprise ent = new Entreprise();
                this.username = email.getText();
                ent = ServiceEnt.getInstance().getEnt(this.username);

                Dialog d = new Dialog("Bienvenue");
                
                TextArea popupBody = new TextArea("Salut " + ent.getNom().toUpperCase() + " L'equipe FreeEtudiant est heureuse de te souhaiter la bienvenue.", 3, 20);
                popupBody.setUIID("PopupBody");
                popupBody.setEditable(false);
                d.setLayout(new BorderLayout());
                d.add(BorderLayout.CENTER, popupBody);
                try {
                    new ProfilEnt(this.username).show();
                } catch (IOException ex) {
                }
            } else {
                Dialog.show("Attention", "Veuillez verifier vos données ", new Command("OK"));

            }

        }
        );
        
        forgetPassword.addActionListener((e) -> {

            new ForgetPasswordCheck(current).show();

        });
        
        
        
          InscritEtu.addActionListener((e) -> {

            new InscriEtu(current).show();

        });
          
           InscritEnt.addActionListener((e) -> {

            new InscriEnt(current).show();

        });
        
        
        
        addAll(email, password,seConnecter,forgetPassword,InscritEtu,InscritEnt);

    }
    
}
