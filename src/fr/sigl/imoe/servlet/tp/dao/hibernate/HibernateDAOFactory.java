package fr.sigl.imoe.servlet.tp.dao.hibernate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import org.hibernate.Session;

import fr.sigl.imoe.servlet.tp.dao.EvenementDAO;
import fr.sigl.imoe.servlet.tp.dao.DAOFactory;
import fr.sigl.imoe.servlet.tp.dao.TypeEvenementDAO;
import fr.sigl.imoe.servlet.tp.dao.exceptions.DAOConfigureException;
import fr.sigl.imoe.servlet.tp.dao.exceptions.DAORequestException;

/**
 * Implémentation Hibernate de la DAOFactory.
 *
 * @author Chris
 */
public class HibernateDAOFactory extends DAOFactory {
    /**
     * Logger JUL.
     */
    public static final Logger LOGGER = Logger.getLogger(HibernateDAOFactory.class.getName());
    /**
     * Fichier de configuration de l'implémentation Hibernate de la DAOFactory.
     */
    public static final String HIBERNATE_CONFIG_FILENAME = "fr/sigl/imoe/servlet/tp/dao/hibernate/hibernate.cfg.xml";

    /**
     * Méthode de configuration de la factory.
     *
     * @throws DAOConfigureException        Exception lors de la configuration du DAO.
     * @see fr.sigl.imoe.servlet.tp.dao.DAOFactory#configure()
     */
    protected void configure() throws DAOConfigureException {
        HibernateUtils.configure(HIBERNATE_CONFIG_FILENAME);
    }

    /**
     * Retourne une session hibernate ou null si la factory
     * n'est pas correctement initialisée.
     *
     * @return                      Une instance de Session
     */
    protected Session getSession() {
        return HibernateUtils.currentSession();
    }

    /**
     * Fermeture de la session courante.
     */
    public void closeSession() {
        HibernateUtils.closeSession();
    }

    /**
     * Retourne l'implémentation associée à la factory du EvenementDAO.
     *
     * @return Une instance de JdbcEvenementDAO.
     * @see fr.sigl.imoe.servlet.tp.dao.DAOFactory#getEvenementDAO()
     */
    public EvenementDAO getEvenementDAO() {
        return new HibernateEvenementDAO(this);
    }

    /**
     * Retourne l'implémentation associée à la factory du TypeEvenementDAO.
     *
     * @return Une instance de JdbcTypeEvenementDAO.
     * @see fr.sigl.imoe.servlet.tp.dao.DAOFactory#getTypeEvenementDAO()
     */
    public TypeEvenementDAO getTypeEvenementDAO() {
        return new HibernateTypeEvenementDAO(this);
    }

    /**
     * Fermeture de la session courante.
     *
     * @throws DAORequestException          Exception lors de la fermeture de la connexion.
     */
    public void close() throws DAORequestException {
        getSession().flush();
        HibernateUtils.closeSession();

        // Extension de la base pour l'écriture sur le fichier.
        try {
            Connection jdbcConnection = DriverManager.getConnection("jdbc:hsqldb:file:/tmp/dbtpj2ee/tpdb", "sa", "");
            jdbcConnection.createStatement().execute("SHUTDOWN");
            jdbcConnection.close();
        } catch (SQLException e) {
            throw new DAORequestException("Echec de fermeture de la connexion.", e);
        }
    }
}
