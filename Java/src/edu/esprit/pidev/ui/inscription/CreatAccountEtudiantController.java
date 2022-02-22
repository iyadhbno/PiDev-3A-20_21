
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.pidev.ui.inscription;

import com.github.sarxos.webcam.Webcam;
import edu.esprit.pidev.entities.Etudiant;
import edu.esprit.pidev.entities.Role;
import edu.esprit.pidev.services.ServiceEtudiant;
import edu.esprit.pidev.services.ServiceNotification;
import edu.esprit.pidev.services.ServiceRole;
import edu.esprit.pidev.services.ServiceUser;
import static edu.esprit.pidev.utiles.Consts.CV_PATH;
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
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * FXML Controller class
 *
 * @author ibeno
 */
public class CreatAccountEtudiantController implements Initializable {

    @FXML
    private TextField nomEtu;
    @FXML
    private TextField mailEtu;
    @FXML
    private PasswordField passwordEtu;
    @FXML
    private PasswordField cpasswordEtu;
    @FXML
    private TextField telEtu;
    @FXML
    private Button ajouterEtu;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button cv;

    @FXML
    private Label cvName;
    @FXML
    private Hyperlink back;
    @FXML
    private Label photoName;
    File fileCvEtuidiant = null;
    String filePhotoEtudiant = null;
    File filePhotoEtudiantFile = null;

    @FXML
    private Button photo;
    private String link_picture_client = "";


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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

    private void BackToLogin() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/esprit/pidev/ui/login/Login.fxml"));

        Parent root = loader.load();

        Stage window = (Stage) ajouterEtu.getScene().getWindow();
        window.setScene(new Scene(root, 800, 800));
    }

    @FXML
    private void ajouterEtu(ActionEvent event) throws IOException {
        ServiceEtudiant sEtu = new ServiceEtudiant();
        ServiceUser sUser = new ServiceUser();
        UserUtiles uUtiles = new UserUtiles();
        MailUtiles sMail = new MailUtiles();
        String nom = nomEtu.getText();
        String mail = mailEtu.getText();
        String password = passwordEtu.getText();
        String cpassword = cpasswordEtu.getText();
        String tel = telEtu.getText();
        File fileCv = fileCvEtuidiant;
        String filePhoto = filePhotoEtudiant;
        ServiceRole s = new ServiceRole();
        Role role = s.getRoleByName(Role.RoleEnum.Etudiant);

        //controle de saisie
        if (nom.isEmpty()) {
            alert_Box("Verifier votre nom", "Votre nom ne doit pas être vide");
        } else if (!uUtiles.testEmail(mail)) {
            alert_Box("Verifier votre mail", "veillez saisir une adresse mail valide");
        } else if (sUser.verifierEmailBd(mail)) {
            alert_Box("Verifier votre mail", "veillez saisir une adresse non existant");
        } else if (!uUtiles.testPassword(password)) {
            alert_Box("Verifier mot de passe", "Votre mot de passe doit doit contenir au moins une une majuscule et un chiffre ");
        } else if (!password.equals(cpassword)) {
            alert_Box("Verifier mot de passe", "Veillez verifier votre mot de passe ");
        } else if (!uUtiles.testTel(tel)) {
            alert_Box("Verifier votre numero telephone", "Veillez mettre un numero de telephone valide");
        } else if (fileCv == null) {
            alert_Box("Verifier le cv", "inserer cv");
        } else if (filePhoto == null) {
            alert_Box("Verifier la photo", "inserer une photo");

        } else {
            String cv = copyCV();
            String photo = copyPhoto();
            Etudiant e = new Etudiant(nom, mail, uUtiles.crypterPassword(password), tel, photo, role, cv, true);
            sEtu.ajouterEtudiant(e);
            sMail.envoyerMail(mail, "Compte créer avec succès", "Bonjour M." + nom + " et bienvenu sur notre platforme ");
            information_Box("Compte créer avec succès", "Vous venez de recevoir un e-mail de confirmation");
            BackToLogin();
        }

    }

    private void backLogin(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/esprit/pidev/ui/login/Login.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    public void ajoutCv(ActionEvent event) throws IOException {

        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("pdf files", "pdf");
        chooser.setFileFilter(filter);
        chooser.showOpenDialog(null);
        fileCvEtuidiant = chooser.getSelectedFile();
        cvName.setText(fileCvEtuidiant.getName());

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
        public void ajoutPhoto(ActionEvent event) throws IOException {

        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Images files", "jpeg");
        chooser.setFileFilter(filter);
        chooser.showOpenDialog(null);
        filePhotoEtudiant = chooser.getSelectedFile().getName();
        filePhotoEtudiantFile=chooser.getSelectedFile();
        photoName.setText(filePhotoEtudiant);

    }

    public String copyPhoto() throws IOException {
        UserUtiles sU = new UserUtiles();
        Date d = new Date();
        String strTimestamp = String.valueOf(d.getTime());
        String randomString = sU.randomString() + "_" + strTimestamp + ".jpeg";
        Path copied = Paths.get(IMG_PATH + randomString);
        Path originalPath = Paths.get(filePhotoEtudiantFile.getAbsolutePath());
        Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);
        return randomString;
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        BackToLogin();
    }
    
     private String code_random() {

        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString 
        StringBuilder sb = new StringBuilder(4);

        for (int i = 0; i < 4; i++) {

            // generate a random number between 
            // 0 to AlphaNumericString variable length 
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb 
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    @FXML
    private void take_picture(ActionEvent event) throws IOException {
    
    ServiceNotification sn = new ServiceNotification();

        String code_random = code_random();

        Webcam webcam = Webcam.getDefault();
        webcam.open();
        String filename = "";
        filename = code_random + "_" + telEtu.getText() + ".jpg";
        ImageIO.write(webcam.getImage(), "JPG", new File("Images/" + filename));
        filePhotoEtudiant = filename;
        webcam.close();       

        sn.Notification("Felicitation", "Photo Prise");
    
    
    
    }

}
