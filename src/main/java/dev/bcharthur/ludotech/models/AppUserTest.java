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
//        Client user = new Client();
//        user.setId(1);
//        user.setFirstName("John");
//        user.setLastName("Doe");
//        user.setEmail("john@example.com");
//        user.setPhone("1234567890");
//        user.setAddress("123 Rue de Paris");
//        user.setPassword("password123");
//        user.setRole("client");
//        Date now = new Date();
//        user.setCreatedAT(now);
//
//        // Assert
//        assertNotNull(user, "L'utilisateur ne doit pas être null");
//        assertEquals(1, user.getId(), "L'ID doit être égal à 1");
//        assertEquals("John", user.getFirstName(), "Le prénom doit être 'John'");
//        assertEquals("Doe", user.getLastName(), "Le nom doit être 'Doe'");
//        assertEquals("john@example.com", user.getEmail(), "L'email doit être 'john@example.com'");
//        assertEquals("1234567890", user.getPhone(), "Le téléphone doit être '1234567890'");
//        assertEquals("123 Rue de Paris", user.getAddress(), "L'adresse doit être '123 Rue de Paris'");
//        assertEquals("password123", user.getPassword(), "Le mot de passe doit être 'password123'");
//        assertEquals("client", user.getRole(), "Le rôle doit être 'client'");
//        assertEquals(now, user.getCreatedAT(), "La date de création doit correspondre");
//    }
//
//    @Test
//    @DisplayName("Création d'un Client sans email")
//    public void testCreationAppUserWithoutEmail() {
//        // Arrange
//        Client user = new Client();
//        user.setId(2);
//        user.setFirstName("Alice");
//        user.setLastName("Smith");
//        // L'email n'est pas renseigné (null)
//        user.setPhone("0987654321");
//        user.setAddress("456 Rue de Lyon");
//        user.setPassword("Passw0rd!");
//        user.setRole("client");
//        Date now = new Date();
//        user.setCreatedAT(now);
//
//        // Assert
//        assertNotNull(user, "L'utilisateur ne doit pas être null");
//        assertNull(user.getEmail(), "L'email doit être null");
//    }
//}
