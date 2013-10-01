package fr.sigl.imoe.servlet.tp.dao.hibernate;

import fr.sigl.imoe.servlet.tp.dao.TestDAOFactory;

/**
 * Tests unitaires pour la JdbcDAOFactory.
 *
 * @author Chris
 */
public class TestHibernateDAOFactory extends TestDAOFactory {
    /**
     * Constructeur par défaut.
     */
    public TestHibernateDAOFactory() {
        super();
    }

    /**
     * Constructeur prenant en paramètre un nom.
     *
     * @param name      Nom pour le cas de tests.
     */
    public TestHibernateDAOFactory(String name) {
        super(name);
    }

    /**
     * @throws Exception        Exception générique.
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
    	super.tearDown();
    }

    /**
     * Retourne la classe d'implémentation réelle du DAO.
     * Cette méthode est à surcharger pour le test d'une implémentation spécifique.
     *
     * @return La classe de la DAOFactory testée.
     * @see fr.sigl.imoe.servlet.tp.dao.TestDAOFactory#getDAOFactoryClass()
     */
    public Class getDAOFactoryClass() {
        return HibernateDAOFactory.class;
    }

    /**
     * Retourne le nom du fichier de propriétés. Si la valeur est null
     * alors c'est la valeur par défaut qui sera utilisée.
     *
     * @return Le nom du fichier de propriété à utiliser.
     * @see fr.sigl.imoe.servlet.tp.dao.TestDAOFactory#getDAOPropertiesFilename()
     */
    public String getDAOPropertiesFilename() {
        return "fr/sigl/imoe/servlet/tp/dao/hibernate/dao-hibernate.properties";
    }

    /**
     * Retourne la classe d'implémentation du EvenementDAO.
     * Cette méthode est à surcharger pour le test d'une implémentation spécifique.
     *
     * @return La classe du EvenementDAO.
     * @see fr.sigl.imoe.servlet.tp.dao.TestDAOFactory#getEvenementDAOClass()
     */
    public Class getEvenementDAOClass() {
        return HibernateEvenementDAO.class;
    }

    /**
     * Retourne la classe d'implémentation du TypeEvenementDAO.
     * Cette méthode est à surcharger pour le test d'une implémentation spécifique.
     *
     * @return La classe du TypeEvenementDAO.
     * @see fr.sigl.imoe.servlet.tp.dao.TestDAOFactory#getTypeEvenementDAOClass()
     */
    public Class getTypeEvenementDAOClass() {
        return HibernateTypeEvenementDAO.class;
    }
}
