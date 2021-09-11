package com.ecommerce.microcommerce.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;

/** Ajouter l'annotation @JsonIgnore ou @JsonIgnoreProperties
* (Exemple: @JsonIgnoreProperties(value = {"prixAchat", "id"})
* au-dessus des propriétés que vous souhaitez cacher
* ou juste @JsonIgnore au dessus de la déclaration de variable
*/

/** Pour un filtrage dynamique, utiliser @JsonFilter
*/

/** @Entity Pour transformer la classe en persistence
* @Table to provide the details of the table that this entity is mapped to
*/
@Entity
@Table(name = "produits")
public class Product {

    /** Vous annotez l'attribut id avec @Id et @GeneratedValue afin qu'il soit
    * identifié en tant que clé unique auto-générée.
    */
    @Id
    @GeneratedValue
    private int id;

    @Length(min=3, max=20)
    private String nom;
    @Min(value=1)
    private double prix;
    private double prixAchat;

    // constructeur par defaut
    public Product() {
    }

    // constructeur pour les tests
    public Product(int id, String nom, double prix, double prixAchat) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.prixAchat = prixAchat;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    @Column(name = "nom", nullable = false)
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    @Column(name = "prix", nullable = false)
    public double getPrix() {
        return prix;
    }
    public void setPrix(double prix) {
        this.prix = prix;
    }

    @Column(name = "prixAchat", nullable = true)
    public double getPrixAchat() { return prixAchat; }
    public void setPrixAchat(double prixAchat) {
        this.prixAchat = prixAchat;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" +
                ", nom='" + nom + '\'' +
                ", prix=" + prix + '}';
    }

}
