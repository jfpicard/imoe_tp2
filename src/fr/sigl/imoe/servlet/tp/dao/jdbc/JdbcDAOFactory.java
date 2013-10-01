package fr.sigl.imoe.servlet.tp.dao.jdbc;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.sigl.imoe.servlet.tp.dao.EvenementDAO;
import fr.sigl.imoe.servlet.tp.dao.DAOFactory;
import fr.sigl.imoe.servlet.tp.dao.TypeEvenementDAO;
import fr.sigl.imoe.servlet.tp.dao.exceptions.DAOConfigureException;
import fr.sigl.imoe.servlet.tp.dao.exceptions.DAORequestException;

/**
 * Impl�mentation JDBC de la DAOFactory.
 *
 * @author Chris
 */
public class JdbcDAOFactory extends DAOFactory {
    /**
     * Logger JUL.
     */
    public static final Logger LOGGER = Logger.getLogger(JdbcDAOFactory.class.getName());
    /**
     * Fichier de propri�t�s de l'impl�mentation JDBC de la DAOFactory.
     */
    public static final String JDBC_PROPERTIES_FILENAME = "config/jdbc.properties";
    /**
     * Driver pour l'acc�s � la base de donn�es.
     */
    private String driver;
    /**
     * URL de la base de donn�es.
     */
    private String url;
    /**
     * Utilisateur pour la base de donn�es.
     */
    private String user;
    /**
     * Mot de passe pour la base de donn�es.
     */
    private String password;

    /**
     * M�thode de configuration de la factory.
     *
     * @throws DAOConfigureException        Exception lors de la configuration du DAO.
     * @see fr.sigl.imoe.servlet.tp.dao.DAOFactory#configure()
     */
    protected void configure() throws DAOConfigureException {
        // Trace de d�but de configuration la DAOFactory.
        if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.log(Level.INFO, "Configuration de l'impl�mentation JDBC de la DAOFactory.");
        }

        // Lecture des param�tres du fichier jdbc.properties
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(JDBC_PROPERTIES_FILENAME);
        Properties properties = new Properties();
        try {
            properties.load(is);
        } catch (Exception e) {
            String errMsg = "Echec de chargement des param�tres pour l'impl�mentation JDBC de la DAOFactory.";
            LOGGER.log(Level.SEVERE, errMsg);
            throw new DAOConfigureException(errMsg);
        }

        // R�cup�ration des param�tres de connexion dans la variable properties
        url = (String) properties.get("jdbc.url");
        driver = (String) properties.get("jdbc.driver");
        user = (String) properties.get("jdbc.user");
        password = (String) properties.get("jdbc.password");

        // Trace de d�bug pour v�rifier les �l�ments lus.
        if (LOGGER.isLoggable(Level.FINER)) {
            StringBuffer sb = new StringBuffer();
            sb.append("Param�tres de connexion JDBC : \n");
            sb.append("\t Driver   = '" + driver + "'\n");
            sb.append("\t URL      = '" + url + "'\n");
            sb.append("\t User     = '" + user + "'\n");
            sb.append("\t Password = '" + password + "'\n");
            LOGGER.log(Level.FINER, sb.toString());
        }

        // V�rifie que la connexion est valide.
        checkConnection();

        // Si on est arriv� ici c'est que la configuration doit �tre valide.
        LOGGER.log(Level.INFO, "Impl�mentation JDBC de la DAOFactory correctement configur�e.");
    }

    /**
     * V�rifie que la connexion jdbc sp�cifi�e est valide.
     *
     * @throws DAOConfigureException        Exception lors de la configuration du DAO.
     */
    private void checkConnection() throws DAOConfigureException {
        // Chargement du driver.
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new DAOConfigureException("Driver JDBC non trouv�.", e);
        }

        // Test de la connexion.
        try {
            Connection connection = getConnection();
            connection.close();
        } catch (SQLException e) {
            throw new DAOConfigureException("Connexion � la base de donn�es impossible.", e);
        }
    }

    /**
     * Retourne une instance de Connection.
     *
     * @return                      Une instance de Connection
     * @exception SQLException      Exception g�n�rique SQL.
     */
    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * Retourne l'impl�mentation associ�e � la factory du EvenementDAO.
     *
     * @return Une instance de JdbcEvenementDAO.
     * @see fr.sigl.imoe.servlet.tp.dao.DAOFactory#getEvenementDAO()
     */
    public EvenementDAO getEvenementDAO() {
        return new JdbcEvenementDAO(this);
    }

    /**
     * Retourne l'impl�mentation associ�e � la factory du TypeEvenementDAO.
     *
     * @return Une instance de JdbcTypeEvenementDAO.
     * @see fr.sigl.imoe.servlet.tp.dao.DAOFactory#getTypeEvenementDAO()
     */
    public TypeEvenementDAO getTypeEvenementDAO() {
        return new JdbcTypeEvenementDAO(this);
    }

    /**
     * Fermeture de la session courante.
     *
     * @throws DAORequestException          Exception lors de la fermeture de la connexion.
     */
    public void close() throws DAORequestException {
        try {
            Connection jdbcConnection = DriverManager.getConnection("jdbc:hsqldb:file:/tmp/dbtpj2ee/tpdb", "sa", "");
            jdbcConnection.createStatement().execute("SHUTDOWN");
            jdbcConnection.close();
        } catch (SQLException e) {
            throw new DAORequestException("Echec de fermeture de la connexion.", e);
        }
    }
}
