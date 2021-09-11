package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.repository.ProductRepository;
import com.ecommerce.microcommerce.utils.ResourceNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Indique à Spring que ce controleur est un controleur REST
* C'est une combinaison de @Controller qui permet de désigner une classe comme controleur
* qui lui confère la capacité de traiter les requêtes de type GET, POST, etc.
* et de @ResponseBody aux méthodes qui devront répondre directement sans passer par cette vue
*/

/** @RestController est la combinaison des deux annotations précédentes. Une fois ajouté,
* il indique que cette classe va pouvoir traiter les requêtes que nous allons définir. Il indique
* aussi que chaque méthode va renvoyer directement la réponse JSON à l'utilisateur, donc pas
* de vue dans le circuit
*/

/**
 * Personnalisation de la documentation SWAGGER grace à @API
 */
@Api( description = "API pour les opérations CRUD sur les produits.")
@RestController
public class ProductController {

    /**
     * L'annotation @Autowired demande à Spring de fabriquer une instance à partir de la variable
     * crée après elle.
     * ProductDao a désormais accès à toutes les méthodes que nous avons définies.
     */
    @Autowired
    private ProductRepository productRepository;

    /**
     * @RequestMapping permet de faire le lien entre l'URL "/Produits" invoquée et
     * la méthode listeProduits.
     * value: l'URL à laquelle la méthode doit répondre
     * method: à quel type de requête la méthode doit répondre
     * produces: dans certains cas, il y aura lieu de préciser que la méthode est capable de répondre
     * en XML et en JSON
     */

    /**
     * @GetMapping permet de faire la même chose mais seulement, comme son nom l'indique, on GET
     */

    @GetMapping("/produit")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    /**            @RequestMapping(value="/Produits", method= RequestMethod.GET)
    public MappingJacksonValue listeProduits() {
        Iterable<Product> produits = productDao.findAll();

        return listeProduits();
    }
    */

    @GetMapping("/mesProduits")
    public List<Product> maRequeteSql() {
        return productRepository.findMyProduct();
    }

    @ApiOperation(value = "Récupère un produit grâce à son ID à condition que celui-ci soit en stock !")
    @GetMapping("/produit/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable(value = "id") int productId)
        throws ResourceNotFoundException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + productId));
        return ResponseEntity.ok().body(product);
    }

    /** @RequestMapping(value="/Produits/{id}", method= RequestMethod.GET)
    public Product afficherUnProduit(@PathVariable int id) {
        return productDao.findById(id);
    }
    */

    /**
     * methode specifique
     */
    @GetMapping("/test/produit/{prixLimit}")
    public List<Product> testeDeRequetes(@PathVariable double prixLimit) {
        return productRepository.findByPrixGreaterThan(400);
    }

    /**
     * @PostMapping : l'URI indiquée (/Produits) est exactement la même que dans la méthode listeProduits.
     * Alors, comment Spring sait-il laquelle appeler ? Grâce aux annotations @PostMapping et @GetMapping !
     * Celles-ci indiquent à quel type de requête HTTP  la méthode est associée, POST  ou GET.
     * @RequestBody : Spring Boot a configuré Jackson pour convertir les objets Java renvoyés en réponse
     * (Product dans notre cas) en JSON.
     * Ici, nous avons besoin du contraire. Nous allons recevoir une requête POST avec les informations
     * d'un nouveau produit au format JSON. Il nous faut donc constituer un objet Product à partir de ce JSON.
     * C'est là que @RequestBody vient à la rescousse. Cette annotation demande à Spring que le JSON contenu
     * dans la partie body de la requête HTTP soit converti en objet Java.
     * Comment ?  Spring, qui a déjà tout auto-configuré au début, ira simplement chercher la librairie
     * capable de faire cela et l'utiliser. Dans notre cas c'est Jackson, mais cela pourrait tout à fait être Gson.
     * La requête JSON est ainsi convertie, dans notre cas, en objet Product puis passée en paramètre à ajouterProduit.
     * Il ne nous reste plus qu'à appeler la méthode save que nous avons déjà créée et le nouveau produit est ajouté
     */

    /** ResponseEntity est une classe qui hérite de HttpEntity,  qui permet de définir le code HTTP
     * à retourner. L'interêt de ResponseEntity est de nous donner la main pour personnaliser le code facilement.
     */

    /** Dans un premier temps, nous faisons appel à la DAO pour ajouter le produit.
     * Dans le cas où le produit ajouté est vide ou n'existe pas, nous retournons le code 204 No Content.
     * Pour cela, la méthode noContent() est utilisée.  Cette méthode est chainée avec la méthode build() qui construit
     * le header et y ajoute le code choisi.
     * Dans le cas où tout s'est bien passé et que productAdded n'est donc pas null, nous avons besoin, en plus du code 201,
     * d'ajouter l'URI vers cette nouvelle ressource créée afin d'être conforme avec le protocole HTTP.
     * Nous déclarons donc une instance de la classe URI afin de la passer ensuite comme argument de ResponseEntity.
     * Nous instancions cette URI à partir de l'URL de la requête reçue.
     * Le fait de ne pas coder l'URL en dur vous offre la liberté de modifier l'emplacement de votre Microservice à volonté.
     * Nous ajoutons ensuite l'id du produit à l'URI à l'aide de la méthode buildAndExpand.
     * Nous retrouvons l'id dans l'instance de Product que nous avons reçu : productAdded.getId().
     * Enfin, nous invoquons la méthode created de ResponseEntity, qui accepte comme argument l'URI de la ressource
     * nouvellement créée et renvoie le code de statut 201.
     */

    @PostMapping(value = "/produit")
    public Product createProduct(@Valid @RequestBody Product product) {
        return productRepository.save(product);
    }

    @PutMapping("/produit/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") int productId,
        @Valid @RequestBody Product productDetails) throws ResourceNotFoundException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + productId));

        product.setId(productDetails.getId());
        product.setNom(productDetails.getNom());
        product.setPrix(productDetails.getPrix());
        product.setPrixAchat(productDetails.getPrixAchat());

        final Product updatedProduct = productRepository.save(product);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/produit/{id}")
    public Map <String, Boolean> deleteProduct(@PathVariable(value = "id") int productId)
        throws ResourceNotFoundException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + productId));

        productRepository.delete(product);
        Map <String, Boolean> response = new HashMap <> ();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
