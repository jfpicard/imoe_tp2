package fr.sigl.imoe.servlet.tp.dao;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.sigl.imoe.servlet.tp.dao.exceptions.DAOConfigureException;
import fr.sigl.imoe.servlet.tp.dao.exceptions.DAORequestException;

/**
 * Classe abstraite dont doivent hériter toutes les DAOFactory
 * concrétes.
 *
 * @author Chris
 */
public abstract class DAOFactory {
    /**
     * Logger JUL.
     */
    public static final Logger LOGGER = Logger.getLogger(DAOFactory.class.getName());
    /**
     * Fichier de propriétés du DAOFactory.
     */
    public static final String DAO_FACTORY_PROPERTIES_FILENAME = "config/dao.properties";
    /**
     * Instance unique de DAO à utiliser.
     */
    private static DAOFactory factoryInstance = null;
    /**
     * Nom du fichier de propriétés utilisé pour configurer la factory.
     */
    private static String factoryPropertiesFilenameUse = DAO_FACTORY_PROPERTIES_FILENAME;

    /**
     * Retourne une instance de la DAOFactory à utiliser à partir de la configuration
     * actuelle ou de celle par défaut si elle n'a pas encore été précisée.
     *
     * @return une instance de DAOFactory.
     * @throws DAOConfigureException        Exception lors de la configuration du DAO.
     */
    public static DAOFactory getDAOFactory() throws DAOConfigureException {
        return getDAOFactory(factoryPropertiesFilenameUse);
    }

    /**
     * Retourne une instance de la DAOFactory à utiliser en se basant sur
     * la configuration fournie. Si celle-ci est null alors la configuration par défaut
     * est utilisée.
     *
     * @param propertiesFilename            Le nom du fichier de propriétés.
     * @return une instance de DAOFactory.
     * @throws DAOConfigureException        Exception lors de la configuration du DAO.
     */
    public static DAOFactory getDAOFactory(String propertiesFilename) throws DAOConfigureException {
        // Si la configuration est nulle alors celle par défaut est utilisée.
        if (propertiesFilename == null) {
            propertiesFilename = DAO_FACTORY_PROPERTIES_FILENAME;
        }

        // Si l'instance n'existe pas encore ou qu'elle n'utilise pas la mème configuration alors elle est créée.
        if (factoryInstance == null || !factoryPropertiesFilenameUse.equals(propertiesFilename)) {
            factoryInstance = createFactory(propertiesFilename);
            factoryPropertiesFilenameUse = propertiesFilename;
        }
        return factoryInstance;
    }

    /**
     * Retourne l'implémentation associée à la factory du EvenementDAO.
     *
     * @return Une instance de EvenementDAO.
     */
    public abstract EvenementDAO getEvenementDAO();

    /**
     * Retourne l'implémentation associée à la factory du TypeEvenementDAO.
     *
     * @return Une instance de TypeEvenementDAO.
     */
    public abstract TypeEvenementDAO getTypeEvenementDAO();

    /**
     * Créé une instance de DAOFactory. Le choix de la factory concrète
     * se base sur le fichier de propriétés dont le chemin est passé en paramétre.
     *
     * @param propertiesFilename            Le nom du fichier de propriétés.
     * @return Une instance de DAOFactory.
     * @throws DAOConfigureException        Exception lors de la configuration du DAO.
     */
    @SuppressWarnings("rawtypes")
	private static DAOFactory createFactory(String propertiesFilename) throws DAOConfigureException {
        // Lecture des paramétres du fichier dao.properties
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesFilename);
        Properties properties = new Properties();
        try {
            properties.load(is);
        } catch (Exception e) {
            String errMsg = "Echec de chargement des paramétres pour la configuration du DAOFactory.";
            LOGGER.log(Level.SEVERE, errMsg);
            throw new DAOConfigureException(errMsg);
        }

        // Récupération des paramétres de connexion dans la variable properties
        String factoryClassName = (String) properties.get("dao.factory");

        // Instanciation de la factory
        DAOFactory factory = null;
        try {
            Class factoryClass = Class.forName(factoryClassName);
            Object obj = factoryClass.newInstance();
            if (DAOFactory.class.isInstance(obj)) {
                factory = (DAOFactory) obj;
                factory.configure();
            } else {
                String errMsg = "La classe '" + factoryClassName + "' doit hériter de DAOFactory";
                LOGGER.log(Level.SEVERE, errMsg);
                throw new DAOConfigureException(errMsg);
            }
        } catch (ClassNotFoundException e) {
            String errMsg = "Echec de paramétrage de la DAOFactory.";
            LOGGER.log(Level.SEVERE, errMsg, e);
            throw new DAOConfigureException(errMsg, e);
        } catch (InstantiationException e) {
            String errMsg = "Echec de paramétrage de la DAOFactory.";
            LOGGER.log(Level.SEVERE, errMsg, e);
            throw new DAOConfigureException(errMsg, e);
        } catch (IllegalAccessException e) {
            String errMsg = "Echec de paramétrage de la DAOFactory.";
            LOGGER.log(Level.SEVERE, errMsg, e);
            throw new DAOConfigureException(errMsg, e);
        } catch (DAOConfigureException e) {
            throw e;
        }

        return factory;
    }

    /**
     * Méthode abstraite de configuration de la factory.
     *
     * @throws DAOConfigureException        Exception lors de la configuration du DAO.
     */
    protected abstract void configure() throws DAOConfigureException;

    /**
     * Fermeture de la session courante.
     *
     * @throws DAORequestException          Exception lors de la fermeture de la connexion.
     */
    public abstract void close() throws DAORequestException;
}
