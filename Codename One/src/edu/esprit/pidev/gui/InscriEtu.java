/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.pidev.gui;

import com.codename1.capture.Capture;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import edu.esprit.pidev.entities.Etudiant;
import edu.esprit.pidev.service.ServiceEtu;
import edu.esprit.pidev.service.ServiceUser;

/**
 *
 * @author ibeno
 */
public class InscriEtu extends Form {

    String photo = "default.jpg";

    public InscriEtu(Form previous) {
        /*
        Le paramètre previous définit l'interface(Form) précédente.
        Quelque soit l'interface faisant appel à AddTask, on peut y revenir
        en utilisant le bouton back
         */
        setTitle("Ajouter un nouveau etudiant");
        setLayout(BoxLayout.y());

        TextField nomEtu = new TextField("", "nom");
        TextField mail = new TextField("", "mail");
        TextField password = new TextField("", "password");
        TextField confirmer_password = new TextField("", "Confirmer mot de passe");
        TextField tel = new TextField("", "tel");
        //Button btnAjoutPhoto = new Button("ajouter une photo");

        Button btnValider = new Button("s'inscrire");

        password.setConstraint(TextField.PASSWORD);
        confirmer_password.setConstraint(TextField.PASSWORD);

        /*btnAjoutPhoto.addActionListener((ActionEvent e) -> {
            try {
                String imgPath = Capture.capturePhoto();
                String link = imgPath.toString();
                int pod = link.indexOf("/", 4);
                String news = link.substring(pod + 36, link.length());
                this.photo = news;

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });*/

        btnValider.addActionListener((ko) -> {

            Etudiant etu = new Etudiant(nomEtu.getText(), mail.getText(), password.getText(), tel.getText(), "default.jpeg");
            String result = ServiceUser.getInstance().checkUserUnique(etu);
            ServiceEtu.getInstance().addEtudiant(etu);
            Dialog.show("Success", "Entreprise ajouter avec succees", new Command("OK"));

        });

        addAll(nomEtu, mail, password, confirmer_password, tel, btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK,
        ko -> previous.showBack()); // Revenir vers l'interface précédente

    }
}
