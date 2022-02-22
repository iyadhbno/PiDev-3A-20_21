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
import com.codename1.ui.TextField;
import edu.esprit.pidev.entities.User;
import edu.esprit.pidev.service.SendMail;
import edu.esprit.pidev.service.ServiceUser;
import edu.esprit.pidev.utils.Validator;
import static java.lang.String.valueOf;
import java.util.Random;

/**
 *
 * @author ibeno
 */
public class ForgetPasswordCheck extends Form {

    Form current;
    public static String username;
    public static String verificationCode;

    public ForgetPasswordCheck() {
    }

    public ForgetPasswordCheck(Form previous) {
        

        // n.setAlertSound("/notification_sound_bells.mp3"); //file name must begin with notification_sound
//        Message m = new Message("Body of message");
//        Display.getInstance().sendMessage(new String[] {"seifeddine.bensalah@gmail.com"}, "Subject of message", m);
        current = this;
        setTitle("Mot de passe oublié");
        Validator v = new Validator();
        Random random = new Random();
        

        TextField email = new TextField("", "Adresse Email");

        //cnt.add(show).add(showPopup);
        Button verifier = new Button("verifier");

        verifier.addActionListener((e) -> {

            if ( email.getText().length() == 0 || "false".equals(ServiceUser.getInstance().forgetPasswordCheck(email.getText()))) {
                Dialog.show("Attention", "Veuillez verifier vos parametres ", new Command("OK"));
            } else {
                String verificationCode = valueOf(random.nextInt()).substring(1, 6);
                this.verificationCode=verificationCode;
                this.username=email.getText();

                SendMail sendMail = new SendMail();
                try {
                    sendMail.send(email.getText(), "Code de verification", "Voice votre code de verification "+verificationCode);
                } catch (Exception ex) {
                    ex.printStackTrace();

                }
                Dialog.show("Code de Verification", "Veuillez verifier votre courrier", new Command("OK"));

                new VerificationCodeForgetPassword(current).show();
            }

        });

        addAll( email, verifier);

        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());

    }
}
