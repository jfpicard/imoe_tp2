package fr.sigl.imoe.servlet.tp.dao;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.sigl.imoe.servlet.tp.dao.exceptions.DAOConfigureException;
import fr.sigl.imoe.servlet.tp.dao.exceptions.DAORequestException;

/**
 * Classe abstraite dont doivent h�riter toutes les DAOFactory
 * concr�tes.
 *
 * @author Chris
 */
public abstract class DAOFactory {
    /**
     * Logger JUL.
     */
    public static final Logger LOGGER = Logger.getLogger(DAOFactory.class.getName());
    /**
     * Fichier de propri�t�s du DAOFactory.
     */
    public static final String DAO_FACTORY_PROPERTIES_FILENAME = "config/dao.properties";
    /**
     * Instance unique de DAO � utiliser.
     */
    private static DAOFactory factoryInstance = null;
    /**
     * Nom du fichier de propri�t�s utilis� pour configurer la factory.
     */
    private static String factoryPropertiesFilenameUse = DAO_FACTORY_PROPERTIES_FILENAME;

    /**
     * Retourne une instance de la DAOFactory � utiliser � partir de la configuration
     * actuelle ou de celle par d�faut si elle n'a pas encore �t� pr�cis�e.
     *
     * @return une instance de DAOFactory.
     * @throws DAOConfigureException        Exception lors de la configuration du DAO.
     */
    public static DAOFactory getDAOFactory() throws DAOConfigureException {
        return getDAOFactory(factoryPropertiesFilenameUse);
    }

    /**
     * Retourne une instance de la DAOFactory � utiliser en se basant sur
     * la configuration fournie. Si celle-ci est null alors la configuration par d�faut
     * est utilis�e.
     *
     * @param propertiesFilename            Le nom du fichier de propri�t�s.
     * @return une instance de DAOFactory.
     * @throws DAOConfigureException        Exception lors de la configuration du DAO.
     */
    public static DAOFactory getDAOFactory(String propertiesFilename) throws DAOConfigureException {
        // Si la configuration est nulle alors celle par d�faut est utilis�e.
        if (propertiesFilename == null) {
            propertiesFilename = DAO_FACTORY_PROPERTIES_FILENAME;
        }

        // Si l'instance n'existe pas encore ou qu'elle n'utilise pas la m�me configuration alors elle est cr��e.
        if (factoryInstance == null || !factoryPropertiesFilenameUse.equals(propertiesFilename)) {
            factoryInstance = createFactory(propertiesFilename);
            factoryPropertiesFilenameUse = propertiesFilename;
        }
        return factoryInstance;
    }

    /**
     * Retourne l'impl�mentation associ�e � la factory du EvenementDAO.
     *
     * @return Une instance de EvenementDAO.
     */
    public abstract EvenementDAO getEvenementDAO();

    /**
     * Retourne l'impl�mentation associ�e � la factory du TypeEvenementDAO.
     *
     * @return Une instance de TypeEvenementDAO.
     */
    public abstract TypeEvenementDAO getTypeEvenementDAO();

    /**
     * Cr�� une instance de DAOFactory. Le choix de la factory concr�te
     * se base sur le fichier de propri�t�s dont le chemin est pass� en param�tre.
     *
     * @param propertiesFilename            Le nom du fichier de propri�t�s.
     * @return Une instance de DAOFactory.
     * @throws DAOConfigureException        Exception lors de la configuration du DAO.
     */
    @SuppressWarnings("rawtypes")
	private static DAOFactory createFactory(String propertiesFilename) throws DAOConfigureException {
        // Lecture des param�tres du fichier dao.properties
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesFilename);
        Properties properties = new Properties();
        try {
            properties.load(is);
        } catch (Exception e) {
            String errMsg = "Echec de chargement des param�tres pour la configuration du DAOFactory.";
            LOGGER.log(Level.SEVERE, errMsg);
            throw new DAOConfigureException(errMsg);
        }

        // R�cup�ration des param�tres de connexion dans la variable properties
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
                String errMsg = "La classe '" + factoryClassName + "' doit h�riter de DAOFactory";
                LOGGER.log(Level.SEVERE, errMsg);
                throw new DAOConfigureException(errMsg);
            }
        } catch (ClassNotFoundException e) {
            String errMsg = "Echec de param�trage de la DAOFactory.";
            LOGGER.log(Level.SEVERE, errMsg, e);
            throw new DAOConfigureException(errMsg, e);
        } catch (InstantiationException e) {
            String errMsg = "Echec de param�trage de la DAOFactory.";
            LOGGER.log(Level.SEVERE, errMsg, e);
            throw new DAOConfigureException(errMsg, e);
        } catch (IllegalAccessException e) {
            String errMsg = "Echec de param�trage de la DAOFactory.";
            LOGGER.log(Level.SEVERE, errMsg, e);
            throw new DAOConfigureException(errMsg, e);
        } catch (DAOConfigureException e) {
            throw e;
        }

        return factory;
    }

    /**
     * M�thode abstraite de configuration de la factory.
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
