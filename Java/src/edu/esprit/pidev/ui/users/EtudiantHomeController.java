/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.pidev.ui.users;

import edu.esprit.pidev.entities.Etudiant;
import edu.esprit.pidev.services.ServiceEtudiant;
import edu.esprit.pidev.services.ServiceUser;
import static edu.esprit.pidev.utiles.Consts.CV_PATH;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * FXML Controller class
 *
 * @author ibeno
 */
public class EtudiantHomeController implements Initializable {

    public Etudiant user2;

    @FXML
    private Button modifEtu;
    @FXML
    private Button modifPw;
    @FXML
    private TextField nomEtu;
    @FXML
    private TextField mailEtu;
    @FXML
    private TextField telEtu;
    @FXML
    private Label usernameLab;
    @FXML
    private Hyperlink logOut;
    @FXML
    private PasswordField actualPw;
    @FXML
    private PasswordField newPw;
    @FXML
    private PasswordField cnewPw;
    @FXML
    private Label cvLab;
    @FXML
    private Button modifCv;

    File fileCvEtuidiant = null;
    @FXML
    private Button nvCv;
    @FXML
    private ImageView photoEtu;
    @FXML
    private Button nvPhoto;
    @FXML
    private Button modifPhoto;
    @FXML
    private Label photoName;
    File filePhotoEtu = null;

    public EtudiantHomeController() {
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
    }

    public void iniializeFxml(Etudiant e) {
        System.out.println(e.getNom());
        System.out.println(e.getTel());

        Image image = new Image(IMG_PATH_LOAD + e.getPhoto());
        System.out.println(IMG_PATH_LOAD);
        ImageView iv1 = new ImageView();
        photoEtu.setImage(image);

    }

    public void showData(Etudiant e) {
        nomEtu.setText(e.getNom());
        mailEtu.setText(e.getEmail());
        telEtu.setText(e.getTel());
        cvLab.setText(e.getCv());
        usernameLab.setText("bienvenue " + e.getNom());
        mailEtu.setDisable(true);
    }

    @FXML
    private void modifPw(ActionEvent event) {
        ServiceUser sUser = new ServiceUser();
        UserUtiles uUser = new UserUtiles();
        String actualmdp = actualPw.getText();
        String newMdp = newPw.getText();
        String cnewMdp = cnewPw.getText();
        String mail = mailEtu.getText();

        if (!sUser.verifierPwBd(uUser.crypterPassword(actualmdp))) {
            uUser.alert_Box("verifier mot de passe", "veillez verifier le mot de passe introduit");
        } else if (!uUser.testPassword(newMdp)) {
            uUser.alert_Box("Verifier mot de passe", "Votre mot de passe doit doit contenir au moins une une majuscule et un chiffre ");
        } else if (!newMdp.equals(cnewMdp)) {
            uUser.alert_Box("Verifier mot de passe", "Veillez verifier votre mot de passe ");
        } else {
            sUser.modifierPassword(mail, uUser.crypterPassword(newMdp));
            uUser.information_Box("mot de passe", "mot de passe changer avec succes");
        }
    }

    @FXML

    private void logOut(ActionEvent event) throws IOException {
        Etudiant e = new Etudiant();
        this.user2 = e;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/esprit/pidev/ui/login/Login.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) logOut.getScene().getWindow();
        window.setScene(new Scene(root, 800, 800));
    }

    @FXML
    private void modifEtu(ActionEvent event) {
        String nom = nomEtu.getText();
        String tel = telEtu.getText();
        String email = mailEtu.getText();
        Etudiant e = new Etudiant(nom, email, tel);
        ServiceEtudiant sU = new ServiceEtudiant();
        UserUtiles uUtiles = new UserUtiles();
        if (nom.isEmpty()) {
            uUtiles.alert_Box("Verifier votre nom", "Votre nom ne doit pas être vide");

        } else if (!uUtiles.testNom(nom)) {
            uUtiles.alert_Box("Verifier votre nom", "Veuillez mettre un nom valide");

        } else if (!uUtiles.testTel(tel)) {
            uUtiles.alert_Box("Verifier votre numero telephone", "Veillez mettre un numero de telephone valide");
        } else {
            sU.modifierEtudiant(e);
            uUtiles.information_Box("modification ", "compte modifier avec succes");
            this.user2 = sU.loadDataModify(email);
            showData(this.user2);
        }
    }

    @FXML
    private void nvCv(ActionEvent event) {
        UserUtiles uUtiles = new UserUtiles();
        try {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("pdf files", "pdf");
            chooser.setFileFilter(filter);
            chooser.showOpenDialog(null);
            fileCvEtuidiant = chooser.getSelectedFile();
            cvLab.setText(fileCvEtuidiant.getName());
        } catch (Exception x) {
            uUtiles.alert_Box("Warning", "veillez choisir un cv");

        }

    }

    public String copyCV() throws IOException {

        UserUtiles sU = new UserUtiles();
        Date d = new Date();
        String strTimestamp = String.valueOf(d.getTime());
        String randomString = sU.randomString() + "_" + strTimestamp + ".pdf";
        Path copied = Paths.get(CV_PATH + randomString);
        Path originalPath = Paths.get(fileCvEtuidiant.getAbsolutePath());
        Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);

        return randomString;
    }

    @FXML
    private void modifCv(ActionEvent event) throws IOException {

        File fileCv = fileCvEtuidiant;
        String mail = mailEtu.getText();
        ServiceEtudiant sEtu = new ServiceEtudiant();
        UserUtiles uUtiles = new UserUtiles();

        if (fileCv == null) {
            uUtiles.alert_Box("Verifier le cv", "inserer cv");
        }
        String cv = copyCV();
        sEtu.modifierCv(mail, cv);

        uUtiles.information_Box("Changement", "CV modifier avec Succes");

    }

    @FXML
    private void modifPhoto(ActionEvent event) throws IOException {
        File filePhoto = filePhotoEtu;
        String mail = mailEtu.getText();
        ServiceUser sUser = new ServiceUser();
        UserUtiles uUtiles = new UserUtiles();

        if (filePhoto == null) {
            uUtiles.alert_Box("Verifier le cv", "inserer cv");
        }
        String photo = copyPhoto();
        sUser.modifierPhoto(mail, photo);

        uUtiles.information_Box("Changement", "photo modifier avec Succes");
    }

    @FXML
    private void nvPhoto(ActionEvent event) {
        UserUtiles uUtiles = new UserUtiles();
        try {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("image files", "jpeg");
            chooser.setFileFilter(filter);
            chooser.showOpenDialog(null);
            filePhotoEtu = chooser.getSelectedFile();
        } catch (Exception x) {
            uUtiles.alert_Box("Warning", "veillez choisir une photo");

        }
    }

    public String copyPhoto() throws IOException, IOException {
        UserUtiles sU = new UserUtiles();
        Date d = new Date();
        String strTimestamp = String.valueOf(d.getTime());
        String randomString = sU.randomString() + "_" + strTimestamp + ".jpeg";
        Path copied = Paths.get(IMG_PATH + randomString);
        Path originalPath = Paths.get(filePhotoEtu.getAbsolutePath());
        Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);
        return randomString;

    }
}
