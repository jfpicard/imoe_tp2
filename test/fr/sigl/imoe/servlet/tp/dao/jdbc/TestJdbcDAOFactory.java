package fr.sigl.imoe.servlet.tp.dao.jdbc;

import fr.sigl.imoe.servlet.tp.dao.TestDAOFactory;

/**
 * Tests unitaires pour la JdbcDAOFactory.
 *
 * @author Chris
 */
public class TestJdbcDAOFactory extends TestDAOFactory {
    /**
     * Constructeur par défaut.
     */
    public TestJdbcDAOFactory() {
        super();
    }

    /**
     * Constructeur prenant en paramètre un nom.
     *
     * @param name      Nom pour le cas de tests.
     */
    public TestJdbcDAOFactory(String name) {
        super(name);
    }

    /**
     * Retourne la classe d'implémentation réelle du DAO.
     * Cette méthode est à surcharger pour le test d'une implémentation spécifique.
     *
     * @return La classe de la DAOFactory testée.
     * @see fr.sigl.imoe.servlet.tp.dao.TestDAOFactory#getDAOFactoryClass()
     */
    public Class getDAOFactoryClass() {
        return JdbcDAOFactory.class;
    }

    /**
     * Retourne le nom du fichier de propriétés. Si la valeur est null
     * alors c'est la valeur par défaut qui sera utilisée.
     *
     * @return Le nom du fichier de propriété à utiliser.
     * @see fr.sigl.imoe.servlet.tp.dao.TestDAOFactory#getDAOPropertiesFilename()
     */
    public String getDAOPropertiesFilename() {
        return "fr/sigl/imoe/servlet/tp/dao/jdbc/dao-jdbc.properties";
    }

    /**
     * Retourne la classe d'implémentation du EvenementDAO.
     * Cette méthode est à surcharger pour le test d'une implémentation spécifique.
     *
     * @return La classe du EvenementDAO.
     * @see fr.sigl.imoe.servlet.tp.dao.TestDAOFactory#getEvenementDAOClass()
     */
    public Class getEvenementDAOClass() {
        return JdbcEvenementDAO.class;
    }

    /**
     * Retourne la classe d'implémentation du TypeEvenementDAO.
     * Cette méthode est à surcharger pour le test d'une implémentation spécifique.
     *
     * @return La classe du TypeEvenementDAO.
     * @see fr.sigl.imoe.servlet.tp.dao.TestDAOFactory#getTypeEvenementDAOClass()
     */
    public Class getTypeEvenementDAOClass() {
        return JdbcTypeEvenementDAO.class;
    }
}
