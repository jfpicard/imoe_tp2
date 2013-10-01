package fr.sigl.imoe.servlet.tp.dao.hibernate;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import fr.sigl.imoe.servlet.tp.dao.exceptions.DAOConfigureException;

/**
 * Classe utilitaire pour la gestion des sessions hibernate.
 * 
 * @author Chris
 */
public final class HibernateUtils {
    /**
     * Logger JUL.
     */
    public static final Logger LOGGER = Logger.getLogger(HibernateDAOFactory.class.getName());
    /**
     * Factory pour les sessions hibernate.
     */
    private static SessionFactory sessionFactory = null;
    /**
     * Thread local pour stocker la session courante.
     */
    public static final ThreadLocal<Session> SESSION = new ThreadLocal<Session>();

    /**
     * Constructeur privé car classe utilitaire.
     */
    private HibernateUtils() {
        super();
    }

    /**
     * Configuration d'hibernate.
     *
     * @param configFile                Fichier de configuration à utiliser.
     * @throws DAOConfigureException    Exception en cas d'échec de configuration.
     */
    public static void configure(String configFile) throws DAOConfigureException {
        // Trace de début de configuration la DAOFactory.
        if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.log(Level.INFO, "Configuration de l'implémentation Hibernate de la DAOFactory.");
        }

        Configuration cfg = new Configuration();
        cfg.configure(configFile);
        sessionFactory = cfg.buildSessionFactory();

        // Vérifie que la connexion est valide.
        checkConnection();

        // Si on est arrivé ici c'est que la configuration doit étre valide.
        LOGGER.log(Level.INFO, "Implémentation Hibernate de la DAOFactory correctement configurée.");
    }

    /**
     * Vérifie que la connexion hibernate spécifiée est valide et correctement
     * configurée.
     *
     * @throws DAOConfigureException        Exception lors de la configuration du DAO.
     */
    private static void checkConnection() throws DAOConfigureException {
        try {
            // Vérification que la session est connectée.
            if (!currentSession().isConnected()) {
                throw new DAOConfigureException("Connexion é la base de données impossible.");
            }

            // Fermeture de la connexion.
            closeSession();
        } catch (Exception e) {
            throw new DAOConfigureException("Connexion é la base de données impossible.", e);
        }
    }    

    /**
     * Retourne la session courante ou en créé une nouvelle si nécessaire.
     *
     * @return la session courante.
     */
    public static Session currentSession() {
        Session s = SESSION.get();
        // Open a new Session, if this Thread has none yet
        if (s == null) {
            s = sessionFactory.openSession();
            SESSION.set(s);
        }
        return s;
    }

    /**
     * Ferme la session courante.
     */
    public static void closeSession() {
        Session s = SESSION.get();
        SESSION.set(null);
        if (s != null) {
            s.close();
        }
    }
}
