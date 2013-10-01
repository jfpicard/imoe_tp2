package fr.sigl.imoe.servlet.tp.dao;

import java.util.List;

import fr.sigl.imoe.servlet.tp.bo.Evenement;
import fr.sigl.imoe.servlet.tp.dao.exceptions.DAORequestException;

/**
 * Interface du DAO pour les �v�nements.
 *
 * @author Chris
 */
public interface EvenementDAO {
    /**
     * Retourne la liste des �v�nements existants.
     *
     * @return la liste des �v�nements.
     * @exception  DAORequestException      Exception g�n�rique lors de l'acc�s � la base.
     */
    List<Evenement> getEvenements() throws DAORequestException;

    /**
     * Retourne l'�v�nement associ� � l'identifiant pass� en param�tre.
     *
     * @param  id                           L'identifiant technique de l'�v�nement.
     * @return L'instance de l'�v�nement correspondant � l'identifiant technique.
     * @exception  DAORequestException      Exception g�n�rique lors de l'acc�s � la base.
     */
    Evenement getEvenement(String id) throws DAORequestException;

    /**
     * Met � jour l'�v�nement.
     *
     * @param  evenement                    Une instance d'�v�nement.
     * @exception  DAORequestException      Exception g�n�rique lors de l'acc�s � la base.
     */
    void updateEvenement(Evenement evenement) throws DAORequestException;

    /**
     * Ajoute un nouvel �v�nement � la base.
     *
     * @param  evenement                    Une instance d'�v�nement.
     * @exception  DAORequestException      Exception g�n�rique lors de l'acc�s � la base.
     */
    void insertEvenement(Evenement evenement) throws DAORequestException;

    /**
     * Efface un �v�nement.
     *
     * @param  evenement                    Une instance d'�v�nement.
     * @exception  DAORequestException      Exception g�n�rique lors de l'acc�s � la base.
     */
    void deleteEvenement(Evenement evenement) throws DAORequestException;
}
