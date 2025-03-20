//package dev.ananas.mystore.models;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.util.Date;
//
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//public class AppUserTest {
//
//    @BeforeAll
//    public static void initBeforeAll() {
//        System.out.println("Début des tests Client");
//    }
//
//    @BeforeEach
//    public void initBeforeEach() {
//        System.out.println("Avant chaque test");
//    }
//
//    @AfterEach
//    public void afterEach() {
//        System.out.println("Après chaque test");
//    }
//
//    @AfterAll
//    public static void afterAll() {
//        System.out.println("Fin des tests Client");
//    }
//
//    @Test
//    @DisplayName("Création d'un Client - cas positif")
//    public void testCreationAppUser() {
//        // Arrange
//        Client client = new Client();
//        client.setId(1);
//        client.setFirstName("John");
//        client.setLastName("Doe");
//        client.setEmail("john@example.com");
//        client.setPhone("1234567890");
//        client.setAddress("123 Rue de Paris");
//        client.setPassword("password123");
//        client.setRole("client");
//        Date now = new Date();
//        client.setCreatedAT(now);
//
//        // Assert
//        assertNotNull(client, "L'utilisateur ne doit pas être null");
//        assertEquals(1, client.getId(), "L'ID doit être égal à 1");
//        assertEquals("John", client.getFirstName(), "Le prénom doit être 'John'");
//        assertEquals("Doe", client.getLastName(), "Le nom doit être 'Doe'");
//        assertEquals("john@example.com", client.getEmail(), "L'email doit être 'john@example.com'");
//        assertEquals("1234567890", client.getPhone(), "Le téléphone doit être '1234567890'");
//        assertEquals("123 Rue de Paris", client.getAddress(), "L'adresse doit être '123 Rue de Paris'");
//        assertEquals("password123", client.getPassword(), "Le mot de passe doit être 'password123'");
//        assertEquals("client", client.getRole(), "Le rôle doit être 'client'");
//        assertEquals(now, client.getCreatedAT(), "La date de création doit correspondre");
//    }
//
//    @Test
//    @DisplayName("Création d'un Client sans email")
//    public void testCreationAppUserWithoutEmail() {
//        // Arrange
//        Client client = new Client();
//        client.setId(2);
//        client.setFirstName("Alice");
//        client.setLastName("Smith");
//        // L'email n'est pas renseigné (null)
//        client.setPhone("0987654321");
//        client.setAddress("456 Rue de Lyon");
//        client.setPassword("Passw0rd!");
//        client.setRole("client");
//        Date now = new Date();
//        client.setCreatedAT(now);
//
//        // Assert
//        assertNotNull(client, "L'utilisateur ne doit pas être null");
//        assertNull(client.getEmail(), "L'email doit être null");
//    }
//}
