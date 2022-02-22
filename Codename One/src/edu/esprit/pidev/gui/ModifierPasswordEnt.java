/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.pidev.gui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import edu.esprit.pidev.entities.Entreprise;
import edu.esprit.pidev.entities.Etudiant;
import edu.esprit.pidev.entities.User;
import edu.esprit.pidev.service.ServiceEnt;
import edu.esprit.pidev.service.ServiceEtu;
import edu.esprit.pidev.service.ServiceUser;
import edu.esprit.pidev.utils.Validator;

/**
 *
 * @author ibeno
 */
public class ModifierPasswordEnt extends Form {

    Form current;
    public String username;
    public User user;

    public ModifierPasswordEnt() {
    }

    public ModifierPasswordEnt(Form previous, String username) {
        this.username = username;
        this.user = ServiceEtu.getInstance().getUser(this.username);

        current = this; //Récupération de l'interface(Form) en cours
        setTitle("Modifer vos parametres");
        Validator v = new Validator();

        TextField new_password = new TextField("", "Nouveau mot de passe");
        TextField confirmer_password = new TextField("", "Confirmer mot de passe");
        Button btnModifier = new Button("Modifier votre mot de passe");

        new_password.setConstraint(TextField.PASSWORD);
        confirmer_password.setConstraint(TextField.PASSWORD);

        btnModifier.addActionListener((e) -> {

            if (!v.test_Password(new_password.getText())) {
                Dialog.show("Attention", "Votre passe doit contenir majuscule un chiffre et une miniscule au minimum", new Command("OK"));
            } else if (!new_password.getText().equals(confirmer_password.getText())) {
                Dialog.show("Attention", "Les deux mot de passes ne correspondent pas", new Command("OK"));
            } else {

                //User u = new User(Integer.parseInt(tfStatus.getText()), tfName.getText());

                if (ServiceUser.getInstance().modifierUserMotDePasse(this.user.getEmail(),new_password.getText())) {
                    Dialog.show("Success", "Modification effectuée", new Command("OK"));
                } else {
                    Dialog.show("ERROR", "Server error", new Command("OK"));
                }
            }
        });

        addAll(new_password, confirmer_password, btnModifier);

        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());

    }

}
