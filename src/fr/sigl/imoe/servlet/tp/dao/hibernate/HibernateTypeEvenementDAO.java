package fr.sigl.imoe.servlet.tp.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;

import fr.sigl.imoe.servlet.tp.bo.TypeEvenement;
import fr.sigl.imoe.servlet.tp.dao.TypeEvenementDAO;
import fr.sigl.imoe.servlet.tp.dao.exceptions.DAORequestException;

/**
 * Impl�mentation Hibernate de TypeEvenementDAO.
 *
 * @author Chris
 */
public class HibernateTypeEvenementDAO implements TypeEvenementDAO {
    /**
     * Logger JUL.
     */
    public static final Logger LOGGER = Logger.getLogger(HibernateTypeEvenementDAO.class.getName());
    /**
     * Instance de la HibernateDAOFactory.
     */
    private HibernateDAOFactory factory;

    /**
     * Constructeur � partir d'une factory.
     *
     * @param newFactory           Instance de HibernateDAOFactory
     */
    public HibernateTypeEvenementDAO(HibernateDAOFactory newFactory) {
        super();
        this.factory = newFactory;
    }

    /**
     * Retourne la liste des types d'�v�nements.
     *
     * @return La liste des types d'�v�nements existantes.
     * @exception  DAORequestException      Exception g�n�rique lors de l'acc�s � la base.
     * @see fr.sigl.imoe.servlet.tp.dao.TypeEvenementDAO#getTypesEvenements()
     */
    @SuppressWarnings("unchecked")
	public List<TypeEvenement> getTypesEvenements() throws DAORequestException {
        List<TypeEvenement> lstTypesEvenements = new ArrayList<TypeEvenement>();
        Session session = null;
        try {
            // Ex�cution de la requ�te.
            session = factory.getSession();
            lstTypesEvenements = session.getNamedQuery("typeEvenement.list").list();
        } catch (Exception e) {
            String errMsg = "Erreur lors de la r�cup�ration de la liste des types.";
            LOGGER.log(Level.SEVERE, errMsg, e);
            throw new DAORequestException(errMsg, e);
        }
        return lstTypesEvenements;
    }

    /**
     * Retourne le type d'�v�nement associ�e � l'id sp�cifi� en param�tre.
     *
     * @param  id                           L'identifiant technique du type.
     * @return L'instance du type d'�v�nement correspondant � l'identifiant technique.
     * @exception  DAORequestException      Exception g�n�rique lors de l'acc�s � la base.
     * @see fr.sigl.imoe.servlet.tp.dao.TypeEvenementDAO#getTypeEvenement(java.lang.String)
     */
    public TypeEvenement getTypeEvenement(String id) throws DAORequestException {
        // V�rification pr�alable
        if (id == null) {
            throw new DAORequestException("L'identifiant du type ne doit pas �tre null pour une recherche.");
        }

        TypeEvenement typeEvenement = null;
        Session session = null;
        try {
            // Ex�cution de la requ�te.
            session = factory.getSession();
            typeEvenement = (TypeEvenement) session.get(TypeEvenement.class, id);
        } catch (Exception e) {
            String errMsg = "Erreur lors de la r�cup�ration du type ayant pour identifiant '" + id + "'";
            LOGGER.log(Level.SEVERE, errMsg, e);
            throw new DAORequestException(errMsg, e);
        }
        return typeEvenement;
    }
}
