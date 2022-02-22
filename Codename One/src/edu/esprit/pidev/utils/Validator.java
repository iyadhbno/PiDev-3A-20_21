/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.pidev.utils;


public class Validator {

    public Validator() {
    }


    public boolean test_Email(String mail) {
        int test = 0;
        int position = 0;
        int test2 = 0;
        String[] tab = {"/", ";", ",", ":", "'", "&", "=", ">", "-", "_", "+", " ", "!"};

        for (int i = 0; i < mail.length(); i++) {
            if (mail.charAt(i) == "@".charAt(0)) {
                test++;
                position = i;
            }

        }
        for (int k = 0; k < mail.length(); k++) {

            for (String tab1 : tab) {
                if (mail.charAt(k) == tab1.charAt(0)) {
                    return false;
                }
            }
        }
        for (int i = 0; i < mail.length(); i++) {
            if ((test == 1) && (mail.charAt(i) == ".".charAt(0))) {

                if (((mail.length() > i + 2) && (i > position + 4))) {
                    for (int j = position; j < mail.length(); j++) {
                        if (mail.charAt(j) == ".".charAt(0)) {
                            test2++;

                        }
                    }
                    if (test2 > 1) {
                        return false;
                    }

                    return true;
                }

            }

        }
        return false;
    }

    public boolean test_Cin(String cin) {

        int i, length;
        length = cin.length();

        if (length != 8) {
            return false;
        }

        for (i = 0; i < length; i++) {

            if (!(cin.charAt(i) >= '0' && cin.charAt(i) <= '9')) {
                return false;
            }

        }
        return true;

    }

    public boolean test_num_telephonique(String tel) {
        int i;
        String[] tab = {"0", "1", "4", "6", "8"};
        for (i = 0; i < tab.length; i++) {
            if (tel.charAt(0) == tab[i].charAt(0)) {
                return false;
            }
        }

        return true;
    }

    public boolean test_Tel(String tel) {

        int i, length;
        length = tel.length();

        if (length != 8) {
            return false;
        }

        for (i = 0; i < length; i++) {

            if ((!(tel.charAt(i) >= '0' && tel.charAt(i) <= '9')) || (test_num_telephonique(tel) == false)) {
                return false;
            }

        }
        return true;

    }
    
    public boolean test_Password(String password) {

        int nombre_Maj = 0;
        int nombre_Entier = 0;
        int nombre_Min = 0;

        int ascii;

        for (int i = 0; i < password.length(); i++) {
            ascii = password.charAt(i);

            if ((ascii >= 65) && (ascii <= 90)) {
                nombre_Maj++;
            }
            if (password.charAt(i) >= '0' && password.charAt(i) <= '9') {
                nombre_Entier++;
            }
            if ((ascii >= 97) && (ascii <= 122)) {
                nombre_Min++;
            }

        }
        if ((nombre_Entier >= 1) && (nombre_Maj >= 1) && (nombre_Min >= 1) && (password.length() >= 8)) {
            return true;
        }
        return false;

    }


 
}
