/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LivreTest {

    @Test
    void testCreationLivre() {
        Livre livre = new Livre(1, "Le Petit Prince", "Antoine de Saint-Exupéry", "978-0156012195");

        assertNotNull(livre);
        assertEquals(1, livre.getId());
        assertEquals("Le Petit Prince", livre.getTitre());
        assertEquals("Antoine de Saint-Exupéry", livre.getAuteur());
        assertEquals("978-0156012195", livre.getIsbn());
    }

    @Test
    void testModificationLivre() {
        Livre livre = new Livre(2, "1984", "George Orwell", "978-0451524935");

        livre.setTitre("Animal Farm");
        livre.setAuteur("Orwell");
        livre.setIsbn("978-0141036137");

        assertEquals("Animal Farm", livre.getTitre());
        assertEquals("Orwell", livre.getAuteur());
        assertEquals("978-0141036137", livre.getIsbn());
    }

    @Test
    void testToString() {
        Livre livre = new Livre(3, "Les Misérables", "Victor Hugo", "978-2070409209");

        String expected = "Livre{id=3, titre='Les Misérables', auteur='Victor Hugo', ISBN='978-2070409209'}";
        assertEquals(expected, livre.toString());
    }
}

