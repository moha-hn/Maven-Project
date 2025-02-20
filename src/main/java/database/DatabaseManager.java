/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

/**
 *
 * @author 2417033
 */
import model.Emprunt;
import model.Livre;
import model.Membre;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager implements IDataAccess {
    private static DatabaseManager instance;
    private Connection connection;

    private DatabaseManager() {
        try {
            // Connexion à la base SQLite
            connection = DriverManager.getConnection("jdbc:sqlite:bibliotheque.db");
            initialiserTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    private void initialiserTables() {
        try (Statement stmt = connection.createStatement()) {
            // Création des tables
            stmt.execute("CREATE TABLE IF NOT EXISTS livres (id INTEGER PRIMARY KEY AUTOINCREMENT, titre TEXT, auteur TEXT, isbn TEXT)");
            stmt.execute("CREATE TABLE IF NOT EXISTS membres (id INTEGER PRIMARY KEY AUTOINCREMENT, nom TEXT, email TEXT)");
            stmt.execute("CREATE TABLE IF NOT EXISTS emprunts (id INTEGER PRIMARY KEY AUTOINCREMENT, livre_id INTEGER, membre_id INTEGER, dateEmprunt TEXT, dateRetour TEXT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ajouterLivre(Livre livre) {
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO livres (titre, auteur, isbn) VALUES (?, ?, ?)")) {
            stmt.setString(1, livre.getTitre());
            stmt.setString(2, livre.getAuteur());
            stmt.setString(3, livre.getIsbn());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Livre> listerLivres() {
        List<Livre> livres = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM livres")) {
            while (rs.next()) {
                livres.add(new Livre(rs.getInt("id"), rs.getString("titre"), rs.getString("auteur"), rs.getString("isbn")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livres;
    }

    @Override
    public Livre trouverLivreParId(int id) {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM livres WHERE id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Livre(rs.getInt("id"), rs.getString("titre"), rs.getString("auteur"), rs.getString("isbn"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void mettreAJourLivre(Livre livre) {
        try (PreparedStatement stmt = connection.prepareStatement("UPDATE livres SET titre = ?, auteur = ?, isbn = ? WHERE id = ?")) {
            stmt.setString(1, livre.getTitre());
            stmt.setString(2, livre.getAuteur());
            stmt.setString(3, livre.getIsbn());
            stmt.setInt(4, livre.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimerLivre(int id) {
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM livres WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 📌 CRUD pour Membre
    @Override
    public void ajouterMembre(Membre membre) {
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO membres (nom, email) VALUES (?, ?)")) {
            stmt.setString(1, membre.getNom());
            stmt.setString(2, membre.getEmail());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Membre> listerMembres() {
        List<Membre> membres = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM membres")) {
            while (rs.next()) {
                membres.add(new Membre(rs.getInt("id"), rs.getString("nom"), rs.getString("email")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return membres;
    }

    @Override
    public Membre trouverMembreParId(int id) {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM membres WHERE id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Membre(rs.getInt("id"), rs.getString("nom"), rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void supprimerMembre(int id) {
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM membres WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 📌 CRUD pour Emprunt
    @Override
    public void ajouterEmprunt(Emprunt emprunt) {
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO emprunts (livre_id, membre_id, dateEmprunt, dateRetour) VALUES (?, ?, ?, ?)")) {
            stmt.setInt(1, emprunt.getLivre().getId());
            stmt.setInt(2, emprunt.getMembre().getId());
            stmt.setString(3, emprunt.getDateEmprunt().toString());
            stmt.setString(4, emprunt.getDateRetour().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Emprunt> listerEmprunts() {
        List<Emprunt> emprunts = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM emprunts")) {
            while (rs.next()) {
                emprunts.add(new Emprunt(
                        rs.getInt("id"),
                        trouverLivreParId(rs.getInt("livre_id")),
                        trouverMembreParId(rs.getInt("membre_id")),
                        LocalDate.parse(rs.getString("dateEmprunt")),
                        LocalDate.parse(rs.getString("dateRetour"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emprunts;
    }

    @Override
    public void supprimerEmprunt(int id) {
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM emprunts WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mettreAJourMembre(Membre membre) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Emprunt trouverEmpruntParId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mettreAJourEmprunt(Emprunt emprunt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

