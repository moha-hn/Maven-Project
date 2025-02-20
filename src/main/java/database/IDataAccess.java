/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package database;

/**
 *
 * @author 2417033
 */

import model.Emprunt;
import model.Livre;
import model.Membre;

import java.util.List;

public interface IDataAccess {
    
    void ajouterLivre(Livre livre);
    List<Livre> listerLivres();
    Livre trouverLivreParId(int id);
    void mettreAJourLivre(Livre livre);
    void supprimerLivre(int id);

    
    void ajouterMembre(Membre membre);
    List<Membre> listerMembres();
    Membre trouverMembreParId(int id);
    void mettreAJourMembre(Membre membre);
    void supprimerMembre(int id);

    
    void ajouterEmprunt(Emprunt emprunt);
    List<Emprunt> listerEmprunts();
    Emprunt trouverEmpruntParId(int id);
    void mettreAJourEmprunt(Emprunt emprunt);
    void supprimerEmprunt(int id);
}

