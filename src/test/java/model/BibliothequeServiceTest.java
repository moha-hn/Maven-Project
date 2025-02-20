/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package model;

import controller.BibliothequeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Assure un ordre d'exécution des tests
class BibliothequeServiceTest {
    private static BibliothequeService service;

    @BeforeAll
    static void setup() {
        service = new BibliothequeService();
    }

    @Test
    @Order(1)
    void testAjouterLivre() {
        // Ajouter un livre
        service.ajouterLivre(5,"Test Livre", "Auteur Test", "1234567890");

        // Vérifier si le livre a été ajouté en listant tous les livres
        List<Livre> livres = service.listerLivres();
        boolean livreAjoute = livres.stream().anyMatch(l -> l.getTitre().equals("Test Livre"));

        assertTrue(livreAjoute, "Le livre doit être ajouté à la base de données.");
    }

    @Test
    @Order(2)
    void testListerLivres() {
        // Vérifier que la liste contient au moins 1 livre
        List<Livre> livres = service.listerLivres();

        assertNotNull(livres, "La liste des livres ne doit pas être null.");
        assertFalse(livres.isEmpty(), "Il doit y avoir au moins un livre dans la base de données.");
    }
}

