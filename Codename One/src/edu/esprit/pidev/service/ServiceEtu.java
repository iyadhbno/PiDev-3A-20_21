/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.pidev.service;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import edu.esprit.pidev.entities.Etudiant;
import edu.esprit.pidev.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ibeno
 */
public class ServiceEtu {

    public ArrayList<Etudiant> etudiants;

    public static ServiceEtu instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceEtu() {
        req = new ConnectionRequest();
    }

    public static ServiceEtu getInstance() {
        if (instance == null) {
            instance = new ServiceEtu();
        }
        return instance;
    }

    public boolean modifierEtudiant(Etudiant etu) {
        String url = Statics.BASE_URL + "/modifyEtu?email=" + etu.getEmail() + "&nom=" + etu.getNom() + "&tel=" + etu.getTel() + "&photo=" + etu.getPhoto();
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public boolean addEtudiant(Etudiant e) {
        String url = Statics.BASE_URL + "/newEtudiantM?nom=" + e.getNom() + "&email=" + e.getEmail() + "&password=" + e.getPassword() + "&tel=" + e.getTel() + "&cv=" + e.getCv() + "&photo=" + e.getPhoto(); //création de l'URL
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion  //req.setPost(true);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); //Supprimer cet actionListener
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;

    }

    public ArrayList<Etudiant> parseEtudiants(String jsonText) {
        try {
            etudiants = new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json            
            Map<String, Object> etudiandsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) etudiandsListJson.get("root");
            //Parcourir la liste des tâches Json
            for (Map<String, Object> obj : list) {
                //Création des tâches et récupération de leurs données
                Etudiant e = new Etudiant();
                //int idd = Integer.parseInt(obj.get("id").toString());
                e.setNom(obj.get("nom").toString());
                e.setEmail(obj.get("email").toString());
                e.setPassword(obj.get("password").toString());
                e.setTel(obj.get("tel").toString());
                e.setPhoto(obj.get("photo").toString());
                //Ajouter la tâche extraite de la réponse Json à la liste
                etudiants.add(e);
            }
        } catch (IOException ex) {

        }

        return etudiants;
    }

    
    
    

    public Etudiant getUser(String username) {
        String url = Statics.BASE_URL + "/getUser?email=" + username;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                etudiants = parseEtudiants(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return etudiants.get(0);
    }

    public ArrayList<Etudiant> getAllEtudiants() {
        String url = Statics.BASE_URL + "/Etudiant/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                etudiants = parseEtudiants(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return etudiants;
    }

}
