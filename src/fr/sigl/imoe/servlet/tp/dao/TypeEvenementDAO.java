package fr.sigl.imoe.servlet.tp.dao;

import java.util.List;

import fr.sigl.imoe.servlet.tp.bo.TypeEvenement;
import fr.sigl.imoe.servlet.tp.dao.exceptions.DAORequestException;

/**
 * Interface du DAO pour les types d'�v�nements.
 *
 * @author Chris
 */
public interface TypeEvenementDAO {
    /**
     * Retourne la liste des types existants.
     *
     * @return la liste des types.
     * @exception  DAORequestException      Exception g�n�rique lors de l'acc�s � la base.
     */
    List<TypeEvenement> getTypesEvenements() throws DAORequestException;


    /**
     * Retourne le type d'�v�nement associ�e � l'id sp�cifi� en param�tre.
     *
     * @param  id                           L'identifiant technique du type.
     * @return L'instance du type correspondant � l'identifiant technique.
     * @exception  DAORequestException      Exception g�n�rique lors de l'acc�s � la base.
     */
    TypeEvenement getTypeEvenement(String id) throws DAORequestException;
}
