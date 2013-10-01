package fr.sigl.imoe.servlet.tp.dao.hibernate;

import fr.sigl.imoe.servlet.tp.dao.TestDAOFactory;

/**
 * Tests unitaires pour la JdbcDAOFactory.
 *
 * @author Chris
 */
public class TestHibernateDAOFactory extends TestDAOFactory {
    /**
     * Constructeur par d�faut.
     */
    public TestHibernateDAOFactory() {
        super();
    }

    /**
     * Constructeur prenant en param�tre un nom.
     *
     * @param name      Nom pour le cas de tests.
     */
    public TestHibernateDAOFactory(String name) {
        super(name);
    }

    /**
     * @throws Exception        Exception g�n�rique.
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
    	super.tearDown();
    }

    /**
     * Retourne la classe d'impl�mentation r�elle du DAO.
     * Cette m�thode est � surcharger pour le test d'une impl�mentation sp�cifique.
     *
     * @return La classe de la DAOFactory test�e.
     * @see fr.sigl.imoe.servlet.tp.dao.TestDAOFactory#getDAOFactoryClass()
     */
    public Class getDAOFactoryClass() {
        return HibernateDAOFactory.class;
    }

    /**
     * Retourne le nom du fichier de propri�t�s. Si la valeur est null
     * alors c'est la valeur par d�faut qui sera utilis�e.
     *
     * @return Le nom du fichier de propri�t� � utiliser.
     * @see fr.sigl.imoe.servlet.tp.dao.TestDAOFactory#getDAOPropertiesFilename()
     */
    public String getDAOPropertiesFilename() {
        return "fr/sigl/imoe/servlet/tp/dao/hibernate/dao-hibernate.properties";
    }

    /**
     * Retourne la classe d'impl�mentation du EvenementDAO.
     * Cette m�thode est � surcharger pour le test d'une impl�mentation sp�cifique.
     *
     * @return La classe du EvenementDAO.
     * @see fr.sigl.imoe.servlet.tp.dao.TestDAOFactory#getEvenementDAOClass()
     */
    public Class getEvenementDAOClass() {
        return HibernateEvenementDAO.class;
    }

    /**
     * Retourne la classe d'impl�mentation du TypeEvenementDAO.
     * Cette m�thode est � surcharger pour le test d'une impl�mentation sp�cifique.
     *
     * @return La classe du TypeEvenementDAO.
     * @see fr.sigl.imoe.servlet.tp.dao.TestDAOFactory#getTypeEvenementDAOClass()
     */
    public Class getTypeEvenementDAOClass() {
        return HibernateTypeEvenementDAO.class;
    }
}
