package com.ecommerce.microcommerce.repository;

import com.ecommerce.microcommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.Entity;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    /**
     * Ajout de requete spécifique
     * Toute la logique est fournie par le nom de la méthode !
     *
     *     findBy : indique que l'opération à exécuter est un SELECT ;
     *
     *     Prix : fournit le nom de la propriété sur laquelle le SELECT s'applique
     *
     *     GreaterThan : définit une condition "plus grand que"
     *
     *     Cette méthode génère une requête équivalent au pseudo-code SQL suivant :
     *
     * select * from product  where prix > [un chiffre ici]
     */
    List<Product> findByPrixGreaterThan(double prixLimit);

    @Query(value = "SELECT * FROM produits", nativeQuery = true)
    List<Product> findMyProduct();

}
