package fr.sigl.imoe.servlet.tp.dao;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.sigl.imoe.servlet.tp.dao.hibernate.TestHibernateDAOFactory;
import fr.sigl.imoe.servlet.tp.dao.jdbc.TestJdbcDAOFactory;

/**
 * Suite de tests pour le DAO.
 *
 * @author Chris
 */
public class AllTests extends TestSuite {
    /**
     * Suite de tests � ex�cuter.
     *
     * @return Test � effectuer.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite("Test for fr.sigl.imoe.servlet.tp.dao");
        // --- Tests pour le JdbcDAOFactory ---
        // Ajout d'acc�s au DAO.
        suite.addTest(new TestJdbcDAOFactory("testCreateDAOFactory"));
        suite.addTest(new TestJdbcDAOFactory("testCreateEvenementDAO"));
        suite.addTest(new TestJdbcDAOFactory("testCreateTypeEvenementDAO"));
        // Tests des �v�nements
        suite.addTest(new TestJdbcDAOFactory("testGetEvenement"));
        suite.addTest(new TestJdbcDAOFactory("testGetEvenements"));
        suite.addTest(new TestJdbcDAOFactory("testInsertEvenement"));
        suite.addTest(new TestJdbcDAOFactory("testUpdateEvenement"));
        suite.addTest(new TestJdbcDAOFactory("testDeleteEvenement"));
        // Tests des types
        suite.addTest(new TestJdbcDAOFactory("testGetTypesEvenements"));
        suite.addTest(new TestJdbcDAOFactory("testGetTypeEvenement"));


        // --- Tests pour le HibernateDAOFactory ---
        // Ajout d'acc�s au DAO.
        suite.addTest(new TestHibernateDAOFactory("testCreateDAOFactory"));
        suite.addTest(new TestHibernateDAOFactory("testCreateEvenementDAO"));
        suite.addTest(new TestHibernateDAOFactory("testCreateTypeEvenementDAO"));
        // Tests des �v�nements
        suite.addTest(new TestHibernateDAOFactory("testGetEvenement"));
        suite.addTest(new TestHibernateDAOFactory("testGetEvenements"));
        suite.addTest(new TestHibernateDAOFactory("testInsertEvenement"));
        suite.addTest(new TestHibernateDAOFactory("testUpdateEvenement"));
        suite.addTest(new TestHibernateDAOFactory("testDeleteEvenement"));
        // Tests des types
        suite.addTest(new TestHibernateDAOFactory("testGetTypesEvenements"));
        suite.addTest(new TestHibernateDAOFactory("testGetTypeEvenement"));
        return suite;
    }

}
