/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.pidev.ui.inscription;

import edu.esprit.pidev.entities.Role;
import edu.esprit.pidev.services.ServiceRole;
import edu.esprit.pidev.entities.Entreprise;
import edu.esprit.pidev.services.ServiceEntreprise;
import static edu.esprit.pidev.utiles.Consts.IMG_PATH;
import edu.esprit.pidev.utiles.MailUtiles;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * FXML Controller class
 *
 * @author ibeno
 */
public class CreatAccountEntrepriseController implements Initializable {

    @FXML
    private TextField nomEnt;
    @FXML
    private TextField mailEnt;
    @FXML
    private TextField telEnt;
    @FXML
    private PasswordField passwordEnt;
    @FXML
    private PasswordField cpasswordEnt;
    @FXML
    private TextField offreEnt;
    @FXML
    private Button ajouterEnt;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Hyperlink back;
    @FXML
    private Button ajoutPhoto;
    @FXML
    private Label photoName;
    File filePhotoEntreprise = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void information_Box(String title, String message) {
        Alert dg = new Alert(Alert.AlertType.INFORMATION);
        dg.setTitle(title);
        dg.setContentText(message);
        dg.show();
    }

    public void alert_Box(String title, String message) {
        Alert dg = new Alert(Alert.AlertType.WARNING);
        dg.setTitle(title);
        dg.setContentText(message);
        dg.show();
    }

    private void backToLogin() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/esprit/pidev/ui/login/Login.fxml"));

        Parent root = loader.load();

        Stage window = (Stage) ajouterEnt.getScene().getWindow();
        window.setScene(new Scene(root, 800, 800));
    }

    @FXML
    private void ajouterEnt(ActionEvent event) throws IOException {
        ServiceEntreprise sEnt = new ServiceEntreprise();
        UserUtiles sUser = new UserUtiles();
        MailUtiles sMail = new MailUtiles();
        String nom = nomEnt.getText();
        String mail = mailEnt.getText();
        String password = passwordEnt.getText();
        String cpassword = cpasswordEnt.getText();
        String tel = telEnt.getText();
        File filePhoto = filePhotoEntreprise;
        String offre = offreEnt.getText();
        ServiceRole s = new ServiceRole();
        Role role = s.getRoleByName(Role.RoleEnum.Entreprise);

        //controle de saisie
        if (nom.isEmpty()) {
            alert_Box("Verifier votre nom", "Votre nom ne doit pas être vide");
        } else if (!sUser.testEmail(mail)) {
            alert_Box("Verifier votre mail", "veillez saisir une adresse mail valide");
        } else if (!sUser.testPassword(password)) {
            alert_Box("Verifier mot de passe", "Votre mot de passe doit doit contenir au moins une une majuscule et un chiffre ");
        } else if (!password.equals(cpassword)) {
            alert_Box("Verifier mot de passe", "Veillez verifier votre mot de passe ");
        } else if (!sUser.testTel(tel)) {
            alert_Box("Verifier votre numero telephone", "Veillez mettre un numero de telephone valide");

        } else if (filePhoto == null) {
            alert_Box("Verifier la photo", "inserer une photo");
        } else if (offre.isEmpty()) {
            alert_Box("Verifier le cv", "Ecrire quelque chose ici");
        } else {
            String photo = copyPhoto();
            Entreprise e = new Entreprise(nom, mail, sUser.crypterPassword(password), tel, photo, role, offre, true);
            sEnt.ajouterEntreprise(e);
            sMail.envoyerMail(mail, "Compte créer avec succès", "Bonjour M." + nom + " et bienvenua a votre entreprise sur notre platforme ");
            information_Box("Compte créer avec succès", "Vous venez de recevoir un e-mail de confirmation");
            backToLogin();

        }
    }

    private void backLogin(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/esprit/pidev/ui/login/Login.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        backToLogin();
    }

    @FXML
    private void ajoutPhoto(ActionEvent event) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Images files", "jpeg");
        chooser.setFileFilter(filter);
        chooser.showOpenDialog(null);
        filePhotoEntreprise = chooser.getSelectedFile();
        photoName.setText(filePhotoEntreprise.getName());
    }

    public String copyPhoto() throws IOException {
        UserUtiles sU = new UserUtiles();
        Date d = new Date();
        String strTimestamp = String.valueOf(d.getTime());
        String randomString = sU.randomString() + "_" + strTimestamp + ".jpeg";
        Path copied = Paths.get(IMG_PATH + randomString);
        Path originalPath = Paths.get(filePhotoEntreprise.getAbsolutePath());
        Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);
        return randomString;
    }
}
