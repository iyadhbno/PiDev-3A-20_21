/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.pidev.ui.users;

import com.itextpdf.text.DocumentException;
import edu.esprit.pidev.entities.Admin;
import edu.esprit.pidev.entities.Entreprise;
import edu.esprit.pidev.entities.Etudiant;
import edu.esprit.pidev.entities.Role;
import edu.esprit.pidev.services.ServiceAdmin;
import edu.esprit.pidev.services.ServiceEntreprise;
import edu.esprit.pidev.services.ServiceEtudiant;
import edu.esprit.pidev.services.ServiceNotification;
import edu.esprit.pidev.services.ServicePdf;
import edu.esprit.pidev.services.ServiceRole;
import edu.esprit.pidev.services.ServiceUser;
import static edu.esprit.pidev.utiles.Consts.CV_PATH;
import static edu.esprit.pidev.utiles.Consts.IMG_PATH;
import static edu.esprit.pidev.utiles.Consts.IMG_PATH_LOAD;
import edu.esprit.pidev.utiles.MailUtiles;
import java.io.IOException;
import java.net.URL;
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
import javafx.stage.Stage;
import edu.esprit.pidev.utiles.UserUtiles;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * FXML Controller class
 *
 * @author ibeno
 */
public class AdminHomeController implements Initializable {

    public Admin user = new Admin();
    public String id;

    //Profile Admin 
    @FXML
    private Button modifPw;
    @FXML
    private TextField nomAdm;
    @FXML
    private TextField mailAdm;
    @FXML
    private Label usernameLab;
    @FXML
    private Hyperlink logOut;
    @FXML
    private Button modifAdm;
    @FXML
    private PasswordField actualPw;
    @FXML
    private PasswordField newPw;
    @FXML
    private PasswordField cnewPw;
    @FXML
    private ImageView photoAdmin;
    @FXML
    private Button nvPhoto;
    @FXML
    private Button modifPhoto;

    //Tab etudiant
    @FXML
    private TableView<Etudiant> tabEtudiants;
    @FXML
    private TableColumn<Etudiant, String> nomEtu;
    @FXML
    private TableColumn<Etudiant, String> emailEtu;
    @FXML
    private TableColumn<Etudiant, Hyperlink> cvEtu;
    @FXML
    private TableColumn<Etudiant, String> telEtu;
    @FXML
    private TableColumn<Etudiant, Boolean> etatCompteEtu;
    @FXML
    private TableColumn<Etudiant, String> photoEtu;

    //Tab Entreprise
    @FXML
    private TableView<Entreprise> tabEntreprises;
    @FXML
    private TableColumn<Entreprise, String> nomEnt;
    @FXML
    private TableColumn<Entreprise, String> emailEnt;
    @FXML
    private TableColumn<Entreprise, String> offreEnt;
    @FXML
    private TableColumn<Entreprise, String> telEnt;
    @FXML
    private TableColumn<Entreprise, Boolean> etatCompteEnt;
    @FXML
    private TableColumn<Entreprise, String> photoEnt;

    //tab Admins
    @FXML
    private TableView<Admin> tabAdmins;
    @FXML
    private TableColumn<Admin, String> nomAdm1;
    @FXML
    private TableColumn<Admin, String> emailAdm1;
    @FXML
    private TableColumn<Admin, String> telAdm1;
    @FXML
    private TableColumn<Admin, Boolean> etatCompteAdm;
    @FXML
    private TableColumn<Admin, String> photoAdm;
    @FXML
    private Tab listAdminTab;

    //tab Ajouter Admin
    @FXML
    private Tab ajouterAdminTab;

    @FXML
    private Button ajouterAdm;
    @FXML
    private TextField nomAdm2;
    @FXML
    private TextField mailAdm2;
    @FXML
    private PasswordField passwordAdm2;
    @FXML
    private PasswordField cpasswordAdm2;
    @FXML
    private TextField telAdm2;
    @FXML
    private Button ajoutPhoto;
    @FXML
    private Label photoName;

    File filePhotoAdmin = null;
    @FXML
    private TextField telAdmin;
    @FXML
    private Button afficherCv;

    File fileCvEtudiant;
    @FXML
    private Button etatCompteEtudiant;
    @FXML
    private Button afficherPhotoEtudiant;
    @FXML
    private Button modifEtatEnt;
    @FXML
    private Button modifEtatAdm;
    @FXML
    private Button afficherPhotoEnt;
    @FXML
    private Button afficherPhotoAdm;
    @FXML
    private PieChart stats_users;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //  tab etu
        List<String> listCritereEt;
        listCritereEt = Arrays.asList("nom", "email", "cv", "tel", "etatCompte", "photo");
        ObservableList<String> listCritereA1;
        listCritereA1 = FXCollections.observableArrayList(listCritereEt);
        affichageTabEtudiant();

        //  tab entreprise
        List<String> listCritereEn;
        listCritereEn = Arrays.asList("nom", "email", "tel", "offre", "photo");
        ObservableList<String> listCritereEnt;
        listCritereEnt = FXCollections.observableArrayList(listCritereEn);
        affichageTabEntreprise();

        // tab admins
        List<String> listCritereA;
        listCritereA = Arrays.asList("nom", "email", "tel", "photo");
        ObservableList<String> listCritereAdm;
        listCritereAdm = FXCollections.observableArrayList(listCritereA);
        loadData();
        // TODO
    }
    public void loadData()
    {
    
            affichageTabAdmin();
            stats_users();

    }

    public void affichageTabEtudiant() {
        ServiceEtudiant sa = new ServiceEtudiant();
        ObservableList<Etudiant> list = FXCollections.observableArrayList(sa.afficherEtudiants());
        nomEtu.setCellValueFactory(new PropertyValueFactory<>("nom"));
        emailEtu.setCellValueFactory(new PropertyValueFactory<>("email"));
        cvEtu.setCellValueFactory(new PropertyValueFactory<>("cv"));
        telEtu.setCellValueFactory(new PropertyValueFactory<>("tel"));
        etatCompteEtu.setCellValueFactory(new PropertyValueFactory<>("etatCompte"));
        photoEtu.setCellValueFactory(new PropertyValueFactory<>("photo"));
        tabEtudiants.setItems(list);

    }

    public void affichageTabEntreprise() {
        ServiceEntreprise sEnt = new ServiceEntreprise();
        ObservableList<Entreprise> list = FXCollections.observableArrayList(sEnt.afficherEntreprises());
        nomEnt.setCellValueFactory(new PropertyValueFactory<>("nom"));
        emailEnt.setCellValueFactory(new PropertyValueFactory<>("email"));
        telEnt.setCellValueFactory(new PropertyValueFactory<>("tel"));
        offreEnt.setCellValueFactory(new PropertyValueFactory<>("offre"));
        etatCompteEnt.setCellValueFactory(new PropertyValueFactory<>("etatCompte"));
        photoEnt.setCellValueFactory(new PropertyValueFactory<>("photo"));
        tabEntreprises.setItems(list);

    }

    public void affichageTabAdmin() {
        ServiceAdmin sAdm = new ServiceAdmin();
        ObservableList<Admin> list = FXCollections.observableArrayList(sAdm.afficherAdmins());
        nomAdm1.setCellValueFactory(new PropertyValueFactory<>("nom"));
        emailAdm1.setCellValueFactory(new PropertyValueFactory<>("email"));
        telAdm1.setCellValueFactory(new PropertyValueFactory<>("tel"));
        etatCompteAdm.setCellValueFactory(new PropertyValueFactory<>("etatCompte"));
        photoEnt.setCellValueFactory(new PropertyValueFactory<>("photo"));
        tabAdmins.setItems(list);

    }

    //modifier la photo du compte
    @FXML
    private void nvPhoto(ActionEvent event) throws IOException {
        UserUtiles uUtiles = new UserUtiles();
        try {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("image files", "jpeg");
            chooser.setFileFilter(filter);
            chooser.showOpenDialog(null);
            filePhotoAdmin = chooser.getSelectedFile();
        } catch (Exception x) {
            uUtiles.alert_Box("Warning", "veillez choisir une photo");

        }
    }

    @FXML
    private void modifPhoto(ActionEvent event) throws IOException {
        File filePhoto = filePhotoAdmin;
        String mail = mailAdm.getText();
        ServiceUser sUser = new ServiceUser();
        UserUtiles uUtiles = new UserUtiles();

        if (filePhoto == null) {
            uUtiles.alert_Box("Verifier le cv", "inserer cv");
        }
        String photo = copyPhoto();
        sUser.modifierPhoto(mail, photo);

        uUtiles.information_Box("Changement", "photo modifier avec Succes");
    }
//fin modif photo

    @FXML
    private void modifPw(ActionEvent event) {
        ServiceUser sUser = new ServiceUser();
        UserUtiles uUser = new UserUtiles();
        String actualmdp = actualPw.getText();
        String newMdp = newPw.getText();
        String cnewMdp = cnewPw.getText();
        String mail = mailAdm.getText();

        if (!sUser.verifierPwBd(uUser.crypterPassword(actualmdp))) {
            uUser.alert_Box("verifier mot de passe", "veillez verifier le mot de passe introduit");
        } else if (!uUser.testPassword(newMdp)) {
            uUser.alert_Box("Verifier mot de passe", "Votre mot de passe doit doit contenir au moins une une majuscule et un chiffre ");
        } else if (!newMdp.equals(cnewMdp)) {
            uUser.alert_Box("Verifier mot de passe", "Veillez verifier votre mot de passe ");
        } else {
            sUser.modifierPassword(mail, uUser.crypterPassword(newMdp));
            uUser.information_Box("mot de passe", "mot de passe changer avec succes");
            actualPw.setText("");
            cnewPw.setText("");
            newPw.setText("");
        }
    }

    public void iniializeFxml(Admin a) {

        ServiceAdmin sA = new ServiceAdmin();
        if (!sA.verifSuperAdmin(a.getEmail())) {
            listAdminTab.setDisable(true);
            ajouterAdminTab.setDisable(true);

        }
        Image image = new Image(IMG_PATH_LOAD + a.getPhoto());
        System.out.println(IMG_PATH_LOAD);
        ImageView iv1 = new ImageView();
        photoAdmin.setImage(image);

    }

    public void showData(Admin a) {
        nomAdm.setText(a.getNom());
        mailAdm.setText(a.getEmail());
        telAdmin.setText(a.getTel());
        usernameLab.setText("bienvenue " + a.getNom());
        mailAdm.setDisable(true);

    }

    @FXML
    private void logOut(ActionEvent event) throws IOException {

        Admin a = new Admin();
        this.user = a;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/esprit/pidev/ui/login/Login.fxml"));
        Parent root = loader.load();

        Stage window = (Stage) logOut.getScene().getWindow();
        window.setScene(new Scene(root, 800, 800));
    }

    @FXML
    private void modifAdm(ActionEvent event) {

        String nom = nomAdm.getText();
        String tel = telAdmin.getText();
        String email = mailAdm.getText();
        Admin a = new Admin(nom, email, tel);
        ServiceAdmin sU = new ServiceAdmin();
        UserUtiles uUtiles = new UserUtiles();

        if (nom.isEmpty()) {
            alert_Box("Verifier votre nom", "Votre nom ne doit pas être vide");

        } else if (!uUtiles.testNom(nom)) {
            alert_Box("Verifier votre nom", "Veuillez mettre un nom valide");

        } else if (!uUtiles.testTel(tel)) {
            alert_Box("Verifier votre numero telephone", "Veillez mettre un numero de telephone valide");
        } else {
            sU.modifierAdmin(a);
            uUtiles.information_Box("modification ", "compte modifier avec succes");
            this.user = sU.loadDataModify(email);
            showData(this.user);

        }
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

    @FXML
    private void ajouterAdm(ActionEvent event) throws IOException {

        ServiceAdmin sAdm = new ServiceAdmin();
        ServiceUser sU = new ServiceUser();
        UserUtiles sUser = new UserUtiles();
        MailUtiles sMail = new MailUtiles();
        String nom = nomAdm2.getText();
        String mail = mailAdm2.getText();
        String password = passwordAdm2.getText();
        String cpassword = cpasswordAdm2.getText();
        String tel = telAdm2.getText();
        File filePhoto = filePhotoAdmin;
        ServiceRole s = new ServiceRole();
        Role role = s.getRoleByName(Role.RoleEnum.Admin);

        //controle de saisie
        if (nom.isEmpty()) {
            alert_Box("Verifier votre nom", "Votre nom ne doit pas être vide");
        } else if (!sUser.testEmail(mail)) {
            alert_Box("Verifier votre mail", "veillez saisir une adresse mail valide");
        } else if (sU.verifierEmailBd(mail)) {
            alert_Box("Verifier votre mail", "cet email existe deja");
        } else if (!sUser.testPassword(password)) {
            alert_Box("Verifier mot de passe", "Votre mot de passe doit doit contenir au moins une une majuscule et un chiffre ");
        } else if (!password.equals(cpassword)) {
            alert_Box("Verifier mot de passe", "Veillez verifier votre mot de passe ");
        } else if (!sUser.testTel(tel)) {
            alert_Box("Verifier votre numero telephone", "Veillez mettre un numero de telephone valide");
        } else if (filePhoto == null) {
            alert_Box("Verifier la photo", "inserer une photo");
        } else {
            String photo = copyPhoto();
            Admin a = new Admin(nom, mail, sUser.crypterPassword(password), tel, photo, role, true);
            sAdm.ajouterAdmin(a);
            sMail.envoyerMail(mail, "Compte créer avec succès", "Bonjour M." + nom + " vous etes inscrit en tant qu'administrateur sur notre platforme ");
            information_Box("Compte créer avec succès", "Vous venez de recevoir un e-mail de confirmation");
            nomAdm2.setText("");
            mailAdm2.setText("");
            passwordAdm2.setText("");
            cpasswordAdm2.setText("");
            telAdm2.setText("");
            filePhotoAdmin = null;
            loadData();

        }

    }
//ajout d'une photo lors de l'inscription

    @FXML
    private void ajoutPhoto(ActionEvent event) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Images files", "jpeg");
        chooser.setFileFilter(filter);
        chooser.showOpenDialog(null);
        filePhotoAdmin = chooser.getSelectedFile();
        photoName.setText(filePhotoAdmin.getName());
    }

    public String copyPhoto() throws IOException, IOException {
        UserUtiles sU = new UserUtiles();
        Date d = new Date();
        String strTimestamp = String.valueOf(d.getTime());
        String randomString = sU.randomString() + "_" + strTimestamp + ".jpeg";
        Path copied = Paths.get(IMG_PATH + randomString);
        Path originalPath = Paths.get(filePhotoAdmin.getAbsolutePath());
        Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);
        return randomString;
    }
//fin de l'ajout d'une pphoto

    @FXML
    private void afficherCv(ActionEvent event) throws IOException {
        TableViewSelectionModel<Etudiant> selectionModel = tabEtudiants.getSelectionModel();
        String cv = tabEtudiants.getSelectionModel().getSelectedItem().getCv();
        File file = new File(CV_PATH + cv);
        Desktop desktop = Desktop.getDesktop();
        if (file.exists()) {
            desktop.open(file);
        }
    }

    @FXML
    private void etatCompteEtu(ActionEvent event) {
        UserUtiles uUtiles = new UserUtiles();
        ServiceUser sUser = new ServiceUser();
        String mailetudiant = tabEtudiants.getSelectionModel().getSelectedItem().getEmail();
        Boolean selectionModel = tabEtudiants.getSelectionModel().getSelectedItem().getEtatCompte();
        if (selectionModel == true) {
            sUser.modifierEtatCompte(mailetudiant, false);

            uUtiles.information_Box("etat compte", "etat de compte a été changé");
            affichageTabEtudiant();
        } else {
            sUser.modifierEtatCompte(mailetudiant, true);

            uUtiles.information_Box("etat compte", "etat de compte a été changé");
            affichageTabEtudiant();

        }
    }

    @FXML
    private void afficherPhotoEtudiant(ActionEvent event) throws IOException {
        TableViewSelectionModel<Etudiant> selectionModel = tabEtudiants.getSelectionModel();
        String photo = tabEtudiants.getSelectionModel().getSelectedItem().getPhoto();
        File file = new File(IMG_PATH + photo);
        Desktop desktop = Desktop.getDesktop();
        if (file.exists()) {
            desktop.open(file);
        }
    }

    @FXML
    private void modifEtatEnt(ActionEvent event) {
        UserUtiles uUtiles = new UserUtiles();
        ServiceUser sUser = new ServiceUser();
        String mailentreprise = tabEntreprises.getSelectionModel().getSelectedItem().getEmail();
        Boolean selectionModel = tabEntreprises.getSelectionModel().getSelectedItem().getEtatCompte();
        if (selectionModel == true) {
            sUser.modifierEtatCompte(mailentreprise, false);

            uUtiles.information_Box("etat compte", "etat de compte a été changé");
            affichageTabEntreprise();
        } else {
            sUser.modifierEtatCompte(mailentreprise, true);

            uUtiles.information_Box("etat compte", "etat de compte a été changé");
            affichageTabEntreprise();

        }
    }

    @FXML
    private void modifEtatAdm(ActionEvent event) {
        UserUtiles uUtiles = new UserUtiles();
        ServiceUser sUser = new ServiceUser();
        String mailAdm = tabAdmins.getSelectionModel().getSelectedItem().getEmail();
        Boolean selectionModel = tabAdmins.getSelectionModel().getSelectedItem().getEtatCompte();
        if (selectionModel == true) {
            sUser.modifierEtatCompte(mailAdm, false);

            uUtiles.information_Box("etat compte", "etat de compte a été changé");
            affichageTabAdmin();
        } else {
            sUser.modifierEtatCompte(mailAdm, true);

            uUtiles.information_Box("etat compte", "etat de compte a été changé");
            affichageTabAdmin();

        }
    }

    @FXML
    private void afficherPhotoEnt(ActionEvent event) throws IOException {
        TableViewSelectionModel<Entreprise> selectionModel = tabEntreprises.getSelectionModel();
        String photo = tabEntreprises.getSelectionModel().getSelectedItem().getPhoto();
        File file = new File(IMG_PATH + photo);
        Desktop desktop = Desktop.getDesktop();
        if (file.exists()) {
            desktop.open(file);
        }
    }

    @FXML
    private void afficherPhotoAdm(ActionEvent event) throws IOException {
        TableViewSelectionModel<Admin> selectionModel = tabAdmins.getSelectionModel();
        String photo = tabAdmins.getSelectionModel().getSelectedItem().getPhoto();
        File file = new File(IMG_PATH + photo);
        Desktop desktop = Desktop.getDesktop();
        if (file.exists()) {
            desktop.open(file);
        }
    }

    @FXML
    private void generatePdfEtudiant(ActionEvent event) throws FileNotFoundException, DocumentException {

        ServiceNotification Notification = new ServiceNotification();
        UserUtiles uUtiles = new UserUtiles();
        boolean test = uUtiles.check_Box("", "Vous etes sur le point de generer un fichier pdf\n Vous voulez continuez ?");
        if (test) {
            ServicePdf scpdf = new ServicePdf();
            Admin admin;
            ServiceAdmin sa = new ServiceAdmin();
            admin = sa.loadDataModify(this.id);           

            scpdf.listeEtudiant(admin);
            Notification.Notification("Pdf Generé ", "Verifier votre repertoire");
        }
    }

    @FXML
    private void generatePdfEntreprise(ActionEvent event) throws FileNotFoundException, DocumentException {
        
         ServiceNotification Notification = new ServiceNotification();
        UserUtiles uUtiles = new UserUtiles();
        boolean test = uUtiles.check_Box("", "Vous etes sur le point de generer un fichier pdf\n Vous voulez continuez ?");
        if (test) {
            ServicePdf scpdf = new ServicePdf();
            Entreprise entreprise;
            ServiceEntreprise sa = new ServiceEntreprise();
            entreprise = sa.loadDataModify(this.id);           

            scpdf.listeEntreprise(entreprise);
            Notification.Notification("Pdf Generé ", "Verifier votre repertoire");
    }}

    @FXML
    private void generatePdfAdmin(ActionEvent event)  throws FileNotFoundException, DocumentException{
        
        
         ServiceNotification Notification = new ServiceNotification();
        UserUtiles uUtiles = new UserUtiles();
        boolean test = uUtiles.check_Box("", "Vous etes sur le point de generer un fichier pdf\n Vous voulez continuez ?");
        if (test) {
            ServicePdf scpdf = new ServicePdf();
            Admin admin;
            ServiceAdmin sa = new ServiceAdmin();
            admin = sa.loadDataModify(this.id);           

            scpdf.liste_admins(admin);
            Notification.Notification("Pdf Generé ", "Verifier votre repertoire");
    }}
    
    
     private void stats_users() {
        ServiceUser su = new ServiceUser();
        int admin = su.nbAdmins();
        int entreprises = su.nbEntreprises();
        int etudiants = su.nbEudiants();
        int all = entreprises + etudiants + admin;
        System.out.println("number"+admin);
        ObservableList<PieChart.Data> list_stat = FXCollections.observableArrayList(
                new PieChart.Data("Admins: " + (admin * 100) / all + "%", admin),
                new PieChart.Data("Entreprises:" + (entreprises * 100) / all + "%", entreprises),
                new PieChart.Data("Etudiants:" + (etudiants * 100) / all + "%", etudiants)
        );
        stats_users.setData(list_stat);

    }
    }

   

