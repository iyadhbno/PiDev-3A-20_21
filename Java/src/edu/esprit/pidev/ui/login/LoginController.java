/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.pidev.ui.login;

import edu.esprit.pidev.entities.Admin;
import edu.esprit.pidev.entities.Entreprise;
import edu.esprit.pidev.entities.Etudiant;
import edu.esprit.pidev.entities.MyAddress;
import edu.esprit.pidev.services.MyLocation;
import edu.esprit.pidev.services.ServiceAdmin;
import edu.esprit.pidev.services.ServiceEntreprise;
import edu.esprit.pidev.services.ServiceEtudiant;
import edu.esprit.pidev.services.ServiceUser;
import edu.esprit.pidev.ui.users.AdminHomeController;
import edu.esprit.pidev.ui.users.EntrepriseHomeController;
import edu.esprit.pidev.ui.users.EtudiantHomeController;
import edu.esprit.pidev.utiles.UserUtiles;
import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author ibeno
 */

public class LoginController implements Initializable {

    @FXML
    private TextField mailLog;
    @FXML
    private TextField pwdLog;
    @FXML
    private Button inscriEtu;
    @FXML
    private Button inscriEnt;
    @FXML
    private Button connectLog;
    @FXML
    private Button pwLog;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label label_adresse;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO;
        label_adresse.setText(MyAdress());
        
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
    private void loadInscriEtu(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/esprit/pidev/ui/inscription/CreatAccountEtudiant.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    private void loadInscriEnt(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/esprit/pidev/ui/inscription/CreatAccountEntreprise.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    public void goToHomeAdmin(String id) throws Exception {
        Admin a = new Admin();
        ServiceAdmin sa = new ServiceAdmin();        
        a = sa.loadDataModify(id);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/esprit/pidev/ui/users/AdminHome.fxml"));
        Parent root = loader.load();
        AdminHomeController HomeScene = loader.getController();
        HomeScene.user = a;
        HomeScene.id=id;
        HomeScene.iniializeFxml(a);
        HomeScene.showData(a);
        Stage window = (Stage) connectLog.getScene().getWindow();
        window.setScene(new Scene(root, 800, 800));

    }

    public void goToHomeEtudiant (String id) throws Exception {
        Etudiant etu = new Etudiant();
        ServiceEtudiant sEtu = new ServiceEtudiant();
        etu = sEtu.loadDataModify(id);
        System.out.println(etu.getEmail());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/esprit/pidev/ui/users/EtudiantHome.fxml"));
        Parent root = loader.load();
        EtudiantHomeController HomeScene = loader.getController();
        HomeScene.user2 = etu;
        HomeScene.iniializeFxml(etu);
        HomeScene.showData(etu);
        Stage window = (Stage) connectLog.getScene().getWindow();
        window.setScene(new Scene(root, 800, 800));

    }

    public void goToHomeEntreprise(String id) throws Exception {
        Entreprise e = new Entreprise();
        ServiceEntreprise se = new ServiceEntreprise();
        e = se.loadDataModify(id);
        System.out.println(e.getEmail());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/esprit/pidev/ui/users/EntrepriseHome.fxml"));

        Parent root = loader.load();

        EntrepriseHomeController HomeScene = loader.getController();

        HomeScene.user1 = e;
        HomeScene.iniializeFxml(e);

        HomeScene.showData(e);
        Stage window = (Stage) connectLog.getScene().getWindow();
        window.setScene(new Scene(root, 800, 800));

    }

    @FXML
    private void connectLog(ActionEvent event) throws IOException, Exception {
        UserUtiles uUtiles = new UserUtiles();
        ServiceUser sU = new ServiceUser();
        String username = mailLog.getText();
        String password = uUtiles.crypterPassword(pwdLog.getText());

        int role = sU.verifierData(username, password);
        Boolean etat = sU.verifEtatCompte(username);
        if (role == -1){
            uUtiles.alert_Box("Verification", "Veillez verifier vos cordonée");
        } else if (etat == false) {
            uUtiles.alert_Box("etat compte", "Votre compte est désactivé, Veillez contacter l'administrateur");
        } else if (role == 0 || role == 3 ) { 
            goToHomeAdmin(username);

        } else if (role == 1) {
            goToHomeEtudiant(username);

        } else if (role == 2) {
            goToHomeEntreprise(username);

        

    }
    }

    @FXML
    private void pwLog(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/esprit/pidev/ui/login/ResetPassword.fxml"));
        rootPane.getChildren().setAll(pane);
        
    }
    
    
    private String MyAdress() {

        MyLocation location = new MyLocation();
        MyAddress adresse = new MyAddress();
        try {
            String MyIp = location.MyIpAdress();
            adresse = location.CurrentLocation(MyIp);

        } catch (SocketException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return adresse.getCity();
    }

}
