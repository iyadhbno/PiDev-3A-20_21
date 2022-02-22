package edu.esprit.pidev.entities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ibeno
 */
public abstract class User {

    private Long id;
    private String nom;
    private String email;
    private String password;
    private String tel;
    private String photo;
    private Role role;
    private Boolean etatCompte;

    //on a enlever l'id car il est generer automatiquement par la BD (pas besoin de l'alimenter manuellement)
    public User(String nom, String email, String password, String tel, String photo, Role role, Boolean etatCompte) {
        this.nom = nom;
        this.email = email;
        this.password = password;
        this.tel = tel;
        this.photo = photo;
        this.role = role;
        this.etatCompte = etatCompte;

    }

    public User() {
    }

    public User(String password) {
        this.password = password;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;

    }

    public User(String nom, String email, String tel) {
        this.email = email;

        this.nom = nom;
        this.tel = tel;
    }

    public User(String nom, String email, String password, String tel) {
        this.nom = nom;
        this.email = email;
        this.password = password;

        this.tel = tel;
    }

    public User(String nom, String email, String password, String tel, String photo) {
        this.nom = nom;
        this.email = email;
        this.password = password;
        this.tel = tel;
        this.photo = photo;
    }

    //on aura peut etre besoin de le recuperer mais on ne sera jamais ammener a le modifier
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * @param tel the tel to set
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * @return the role
     */
    public Role getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * @return the etatCompte
     */
    public Boolean getEtatCompte() {
        return etatCompte;
    }

    /**
     * @param etatCompte the etatCompte to set
     */
    public void setEtatCompte(Boolean etatCompte) {
        this.etatCompte = etatCompte;
    }

    /**
     * @return the photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * @param photo the photo to set
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
