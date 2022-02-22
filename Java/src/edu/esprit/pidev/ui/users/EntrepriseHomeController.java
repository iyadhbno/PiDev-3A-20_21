/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.pidev.ui.users;

import edu.esprit.pidev.entities.Entreprise;
import edu.esprit.pidev.services.ServiceEntreprise;
import edu.esprit.pidev.services.ServiceUser;
import static edu.esprit.pidev.utiles.Consts.IMG_PATH;
import static edu.esprit.pidev.utiles.Consts.IMG_PATH_LOAD;
import edu.esprit.pidev.utiles.UserUtiles;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * FXML Controller class
 *
 * @author ibeno
 */
public class EntrepriseHomeController implements Initializable {

    public Entreprise user1;

    @FXML
    private Button modifEnt;
    @FXML
    private Button modifPw;
    @FXML
    private TextField nomEnt;
    @FXML
    private TextField mailEnt;
    @FXML
    private TextField telEnt;
    @FXML
    private TextField offreEnt;
    @FXML
    private Label usernameLab;
    @FXML
    private Hyperlink logOut;
    @FXML
    private TextField actualPw;
    @FXML
    private TextField newPw;
    @FXML
    private TextField cnewPw;
    @FXML
    private ImageView photoEnt;
    @FXML
    private Button nvPhoto;
    @FXML
    private Button modifPhoto;
    
    File filePhotoEnt = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void modifPw(ActionEvent event) {

        ServiceUser sUser = new ServiceUser();
        UserUtiles uUser = new UserUtiles();
        String actualmdp = actualPw.getText();
        String newMdp = newPw.getText();
        String cnewMdp = cnewPw.getText();
        String mail = mailEnt.getText();

        if (!sUser.verifierPwBd(uUser.crypterPassword(actualmdp))) {
            uUser.alert_Box("verifier mot de passe", "veillez verifier le mot de passe introduit");
        } else if (!uUser.testPassword(newMdp)) {
            uUser.alert_Box("Verifier mot de passe", "Votre mot de passe doit doit contenir au moins une une majuscule et un chiffre ");
        } else if (!newMdp.equals(cnewMdp)) {
            uUser.alert_Box("Verifier mot de passe", "Veillez verifier votre mot de passe ");
        } else {
            sUser.modifierPassword(mail, uUser.crypterPassword(newMdp));
            uUser.information_Box("mot de passe", "mot de passe changer avec succes");
            newPw.setText("");
            cnewPw.setText("");
            actualPw.setText("");
        }

    }

    public void iniializeFxml(Entreprise e) {
        System.out.println(e.getNom());
        System.out.println(e.getTel());

        Image image = new Image(IMG_PATH_LOAD + e.getPhoto());
        System.out.println(image);
        ImageView iv1 = new ImageView();
        photoEnt.setImage(image);
    }

    public void showData(Entreprise e) {
        nomEnt.setText(e.getNom());
        mailEnt.setText(e.getEmail());
        telEnt.setText(e.getTel());
        offreEnt.setText(e.getOffre());
        usernameLab.setText("bienvenue " + e.getNom());
        mailEnt.setDisable(true);
    }

    @FXML
    private void logOut(ActionEvent event) throws IOException {

        Entreprise e = new Entreprise();
        this.user1 = e;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/esprit/pidev/ui/login/Login.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) logOut.getScene().getWindow();
        window.setScene(new Scene(root, 800, 800));
    }

    @FXML
    private void modifEnt(ActionEvent event) {

        String nom = nomEnt.getText();
        String tel = telEnt.getText();
        String email = mailEnt.getText();
        String offre = offreEnt.getText();
        Entreprise e = new Entreprise(nom, email, tel, offre);
        ServiceEntreprise sEnt = new ServiceEntreprise();
        UserUtiles uUtiles = new UserUtiles();
        if (nom.isEmpty()) {
            uUtiles.alert_Box("Verifier votre nom", "Votre nom ne doit pas être vide");

        } else if (!uUtiles.testNom(nom)) {
            uUtiles.alert_Box("Verifier votre nom", "Veuillez mettre un nom valide");

        } else if (!uUtiles.testTel(tel)) {
            uUtiles.alert_Box("Verifier votre numero telephone", "Veillez mettre un numero de telephone valide");
        } else {
            sEnt.modifierEntreprise(e);
            uUtiles.information_Box("modification ", "compte modifier avec succes");
            this.user1 = sEnt.loadDataModify(email);
            showData(this.user1);
        }
    }

    @FXML
    private void nvPhoto(ActionEvent event) {
        UserUtiles uUtiles = new UserUtiles();
        try {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("image files", "jpeg");
            chooser.setFileFilter(filter);
            chooser.showOpenDialog(null);
            filePhotoEnt = chooser.getSelectedFile();
        } catch (Exception x) {
            uUtiles.alert_Box("Warning", "veillez choisir une photo");

        }
    }

    @FXML
    private void modifPhoto(ActionEvent event) throws IOException {
        File filePhoto = filePhotoEnt;
        String mail = mailEnt.getText();
        ServiceUser sUser = new ServiceUser();
        UserUtiles uUtiles = new UserUtiles();

        if (filePhoto == null) {
            uUtiles.alert_Box("Verifier la photo", "inserer une photo");
        }
        String photo = copyPhoto();
        sUser.modifierPhoto(mail, photo);

        uUtiles.information_Box("Changement", "photo modifier avec Succes");
    }

    public String copyPhoto() throws IOException, IOException {
        UserUtiles sU = new UserUtiles();
        Date d = new Date();
        String strTimestamp = String.valueOf(d.getTime());
        String randomString = sU.randomString() + "_" + strTimestamp + ".jpeg";
        Path copied = Paths.get(IMG_PATH + randomString);
        Path originalPath = Paths.get(filePhotoEnt.getAbsolutePath());
        Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);
        return randomString;
    }
}
