package fr.sigl.imoe.servlet.tp.dao;

import java.util.List;

import fr.sigl.imoe.servlet.tp.bo.Evenement;
import fr.sigl.imoe.servlet.tp.dao.exceptions.DAORequestException;

/**
 * Interface du DAO pour les événements.
 *
 * @author Chris
 */
public interface EvenementDAO {
    /**
     * Retourne la liste des événements existants.
     *
     * @return la liste des événements.
     * @exception  DAORequestException      Exception générique lors de l'accés à la base.
     */
    List<Evenement> getEvenements() throws DAORequestException;

    /**
     * Retourne l'événement associé à l'identifiant passé en paramétre.
     *
     * @param  id                           L'identifiant technique de l'événement.
     * @return L'instance de l'événement correspondant à l'identifiant technique.
     * @exception  DAORequestException      Exception générique lors de l'accés à la base.
     */
    Evenement getEvenement(String id) throws DAORequestException;

    /**
     * Met à jour l'événement.
     *
     * @param  evenement                    Une instance d'événement.
     * @exception  DAORequestException      Exception générique lors de l'accés à la base.
     */
    void updateEvenement(Evenement evenement) throws DAORequestException;

    /**
     * Ajoute un nouvel événement à la base.
     *
     * @param  evenement                    Une instance d'événement.
     * @exception  DAORequestException      Exception générique lors de l'accés à la base.
     */
    void insertEvenement(Evenement evenement) throws DAORequestException;

    /**
     * Efface un événement.
     *
     * @param  evenement                    Une instance d'événement.
     * @exception  DAORequestException      Exception générique lors de l'accés à la base.
     */
    void deleteEvenement(Evenement evenement) throws DAORequestException;
}
