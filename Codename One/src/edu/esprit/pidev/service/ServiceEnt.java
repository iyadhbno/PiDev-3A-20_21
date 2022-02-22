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
import edu.esprit.pidev.entities.Entreprise;
import edu.esprit.pidev.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ibeno
 */
public class ServiceEnt {

    public ArrayList<Entreprise> entreprises;

    public static ServiceEnt instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    ServiceEnt() {
        req = new ConnectionRequest();
    }

    public static ServiceEnt getInstance() {
        if (instance == null) {
            instance = new ServiceEnt();
        }
        return instance;
    }
    

    public boolean addEntreprise(Entreprise e) {
        String url = Statics.BASE_URL + "/newEntrepriseM?nom=" + e.getNom() + "&email=" + e.getEmail() + "&password=" + e.getPassword() + "&tel=" + e.getTel() + "&cv=" + e.getOffre() + "&photo=" + e.getPhoto(); //création de l'URL
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion        
        //req.setPost(true);
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

    public boolean modifEnt(Entreprise ent) {
        return false;
    }

    
    
    public ArrayList<Entreprise> parseEntreprises(String jsonText) {
        try {
            entreprises = new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json            
            Map<String, Object> entreprisesListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) entreprisesListJson.get("root");
            //Parcourir la liste des tâches Json
            for (Map<String, Object> obj : list) {
                //Création des tâches et récupération de leurs données
                Entreprise e = new Entreprise();
                //int idd = Integer.parseInt(obj.get("id").toString());
                e.setNom(obj.get("nom").toString());
                e.setEmail(obj.get("email").toString());
                e.setPassword(obj.get("password").toString());
                e.setTel(obj.get("tel").toString());
//                e.setOffre(obj.get("offre").toString());
                e.setPhoto(obj.get("photo").toString());
                //Ajouter la tâche extraite de la réponse Json à la list
                entreprises.add(e);
            }
        } catch (IOException ex) {

        }
        return entreprises;
    }
    
    public boolean modifierUserMotDePasse(Entreprise ent) {

        String url = Statics.BASE_URL + "/modifyPasswordEtu?mail=" + ent.getEmail() + "&password=" + ent.getPassword();
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
    
    

    public boolean modifierEntreprise(Entreprise ent) {
        String url = Statics.BASE_URL + "/modifyEnt?email=" + ent.getEmail() + "&nom=" + ent.getNom() + "&tel=" + ent.getTel();
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
    
    

    public Entreprise getEntreprise(String username) {
        String url = Statics.BASE_URL + "/getUser?email=" + username;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                entreprises = parseEntreprises(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return entreprises.get(0);
    }

    
    
    public Entreprise getEnt(String username) {
        String url = Statics.BASE_URL + "/getUser?email=" + username;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                entreprises = parseEntreprises(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return entreprises.get(0);
    }

}
