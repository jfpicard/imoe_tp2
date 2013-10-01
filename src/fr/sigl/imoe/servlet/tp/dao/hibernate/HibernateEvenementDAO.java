package fr.sigl.imoe.servlet.tp.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.Transaction;

import fr.sigl.imoe.servlet.tp.bo.Evenement;
import fr.sigl.imoe.servlet.tp.dao.EvenementDAO;
import fr.sigl.imoe.servlet.tp.dao.exceptions.DAORequestException;

/**
 * Implémentation Hibernate du EvenementDAO.
 *
 * @author Chris
 */
public class HibernateEvenementDAO implements EvenementDAO {
    /**
     * Logger JUL.
     */
    public static final Logger LOGGER = Logger.getLogger(HibernateEvenementDAO.class.getName());
    /**
     * Instance de la HibernateDAOFactory.
     */
    private HibernateDAOFactory factory;

    /**
     * Constructeur à partir d'une factory.
     *
     * @param newFactory           Instance de HibernateDAOFactory
     */
    public HibernateEvenementDAO(HibernateDAOFactory newFactory) {
        super();
        this.factory = newFactory;
    }

    /**
     * Retourne la liste des evenements existants.
     *
     * @return la liste des evenements.
     * @exception  DAORequestException      Exception générique lors de l'accés à la base.
     * @see fr.sigl.imoe.servlet.tp.dao.EvenementDAO#getEvenements()
     */
    @SuppressWarnings("unchecked")
	public List<Evenement> getEvenements() throws DAORequestException {
        List<Evenement> lstEvenements = new ArrayList<Evenement>();
        Session session = null;
        try {
            // Exécution de la requête.
            session = factory.getSession();
            lstEvenements = session.getNamedQuery("evenements.list").list();
        } catch (Exception e) {
            String errMsg = "Erreur lors de la récupération de la liste des evenements.";
            LOGGER.log(Level.SEVERE, errMsg, e);
            throw new DAORequestException(errMsg, e);
        }
        return lstEvenements;
    }

    /**
     * Retourne l'evenement associé à l'identifiant passé en paramétre.
     *
     * @param  id                           L'identifiant technique du evenement.
     * @return L'instance du evenement correspondant à l'identifiant technique.
     * @exception  DAORequestException      Exception générique lors de l'accés à la base.
     * @see fr.sigl.imoe.servlet.tp.dao.EvenementDAO#getEvenement(java.lang.String)
     */
    public Evenement getEvenement(String id) throws DAORequestException {
        // Vérification préalable
        if (id == null) {
            throw new DAORequestException("L'identifiant de l'evenement ne doit pas étre null pour une recherche.");
        }

        Evenement evenement = null;
        Session session = null;
        try {
            // Exécution de la requête.
            session = factory.getSession();
            evenement = (Evenement) session.get(Evenement.class, id);
        } catch (Exception e) {
            String errMsg = "Erreur lors de la récupération de l'evenement ayant pour identifiant '" + id + "'";
            LOGGER.log(Level.SEVERE, errMsg, e);
            throw new DAORequestException(errMsg, e);
        }
        return evenement;
    }

    /**
     * Met à jour le evenement.
     *
     * @param  evenement                      Une instance de evenement
     * @exception  DAORequestException      Exception générique lors de l'accés à la base.
     * @see fr.sigl.imoe.servlet.tp.dao.EvenementDAO#updateEvenement(fr.sigl.imoe.servlet.tp.bo.Evenement)
     */
    public void updateEvenement(Evenement evenement) throws DAORequestException {
        // Vérification préalable
        if (evenement == null) {
            throw new DAORequestException("L'evenement ne doit pas étre null pour une mise à jour.");
        }

        Transaction tx = null;
        Session session = null;
        try {
            // Exécution de la mise à jour.
            session = factory.getSession();
            tx = session.beginTransaction();
            tx.begin();
            session.update(evenement);
            tx.commit();
        } catch (Exception e) {
            // en cas d'erreur rollback de la transaction.
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }

            String errMsg = "Erreur lors de la mise à jour de l'evenement.";
            LOGGER.log(Level.SEVERE, errMsg, e);
            throw new DAORequestException(errMsg, e);
        }
    }

    /**
     * Ajoute un nouveau evenement à la base.
     *
     * @param  evenement                      Une instance de evenement
     * @exception  DAORequestException      Exception générique lors de l'accés à la base.
     * @see fr.sigl.imoe.servlet.tp.dao.EvenementDAO#insertEvenement(fr.sigl.imoe.servlet.tp.bo.Evenement)
     */
    public void insertEvenement(Evenement evenement) throws DAORequestException {
        // Vérification préalable
        if (evenement == null) {
            throw new DAORequestException("L'evenement ne doit pas étre null pour une insertion.");
        }

        Transaction tx = null;
        Session session = null;
        try {
            // Création de l'identifiant technique pour le evenement
            evenement.setId(String.valueOf(System.currentTimeMillis()));

            // Exécution de la mise à jour.
            session = factory.getSession();
            tx = session.beginTransaction();
            tx.begin();
            session.save(evenement);
            tx.commit();
        } catch (Exception e) {
            // en cas d'erreur rollback de la transaction.
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            String errMsg = "Erreur lors de la création de l'evenement.";
            LOGGER.log(Level.SEVERE, errMsg, e);
            throw new DAORequestException(errMsg, e);
        }
    }

    /**
     * Efface un evenement.
     *
     * @param  evenement                      Une instance de evenement
     * @exception  DAORequestException      Exception générique lors de l'accés à la base.
     * @see fr.sigl.imoe.servlet.tp.dao.EvenementDAO#deleteEvenement(fr.sigl.imoe.servlet.tp.bo.Evenement)
     */
    public void deleteEvenement(Evenement evenement) throws DAORequestException {
        // Vérification préalable
        if (evenement == null) {
            throw new DAORequestException("L'evenement ne doit pas étre null pour une suppression.");
        }

        Transaction tx = null;
        Session session = null;
        try {
            // Exécution de la mise à jour.
            session = factory.getSession();
            tx = session.beginTransaction();
            tx.begin();
            session.delete(evenement);
            tx.commit();
        } catch (Exception e) {
            // en cas d'erreur rollback de la transaction.
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            String errMsg = "Erreur lors de la suppression de l'evenement ayant pour identifiant '" + evenement.getId() + "'";
            LOGGER.log(Level.SEVERE, errMsg, e);
            throw new DAORequestException(errMsg, e);
        }
    }
}
