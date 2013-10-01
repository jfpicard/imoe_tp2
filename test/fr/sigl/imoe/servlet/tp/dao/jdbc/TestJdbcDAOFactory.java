package fr.sigl.imoe.servlet.tp.dao.jdbc;

import fr.sigl.imoe.servlet.tp.dao.TestDAOFactory;

/**
 * Tests unitaires pour la JdbcDAOFactory.
 *
 * @author Chris
 */
public class TestJdbcDAOFactory extends TestDAOFactory {
    /**
     * Constructeur par d�faut.
     */
    public TestJdbcDAOFactory() {
        super();
    }

    /**
     * Constructeur prenant en param�tre un nom.
     *
     * @param name      Nom pour le cas de tests.
     */
    public TestJdbcDAOFactory(String name) {
        super(name);
    }

    /**
     * Retourne la classe d'impl�mentation r�elle du DAO.
     * Cette m�thode est � surcharger pour le test d'une impl�mentation sp�cifique.
     *
     * @return La classe de la DAOFactory test�e.
     * @see fr.sigl.imoe.servlet.tp.dao.TestDAOFactory#getDAOFactoryClass()
     */
    public Class getDAOFactoryClass() {
        return JdbcDAOFactory.class;
    }

    /**
     * Retourne le nom du fichier de propri�t�s. Si la valeur est null
     * alors c'est la valeur par d�faut qui sera utilis�e.
     *
     * @return Le nom du fichier de propri�t� � utiliser.
     * @see fr.sigl.imoe.servlet.tp.dao.TestDAOFactory#getDAOPropertiesFilename()
     */
    public String getDAOPropertiesFilename() {
        return "fr/sigl/imoe/servlet/tp/dao/jdbc/dao-jdbc.properties";
    }

    /**
     * Retourne la classe d'impl�mentation du EvenementDAO.
     * Cette m�thode est � surcharger pour le test d'une impl�mentation sp�cifique.
     *
     * @return La classe du EvenementDAO.
     * @see fr.sigl.imoe.servlet.tp.dao.TestDAOFactory#getEvenementDAOClass()
     */
    public Class getEvenementDAOClass() {
        return JdbcEvenementDAO.class;
    }

    /**
     * Retourne la classe d'impl�mentation du TypeEvenementDAO.
     * Cette m�thode est � surcharger pour le test d'une impl�mentation sp�cifique.
     *
     * @return La classe du TypeEvenementDAO.
     * @see fr.sigl.imoe.servlet.tp.dao.TestDAOFactory#getTypeEvenementDAOClass()
     */
    public Class getTypeEvenementDAOClass() {
        return JdbcTypeEvenementDAO.class;
    }
}
