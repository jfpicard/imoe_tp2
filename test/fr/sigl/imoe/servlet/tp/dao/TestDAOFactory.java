package fr.sigl.imoe.servlet.tp.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;

import fr.sigl.imoe.servlet.tp.bo.Evenement;
import fr.sigl.imoe.servlet.tp.bo.TypeEvenement;

/**
 * Template g�n�rique pour tester une impl�mentation de DAOFactory.
 *
 * @author Chris
 */
public abstract class TestDAOFactory extends TestCase {
    /**
     * Connexion JDBC vers la base.
     */
    private Connection jdbcConnection = null;

    /**
     * Constructeur par d�faut.
     */
    public TestDAOFactory() {
        super();
    }

    /**
     * Constructeur prenant en param�tre un nom.
     *
     * @param name      Nom pour le cas de tests.
     */
    public TestDAOFactory(String name) {
        super(name);
    }

    /**
     * @throws Exception        Exception g�n�rique.
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        
        // Initialisation de la base de donn�es.
        setUpDatabase();
    }

    /**
     * @throws Exception        Exception g�n�rique.
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        DAOFactory.getDAOFactory().close();
        super.tearDown();
    }


    /**
     * Initialisation de la base de donn�es.
     *
     * @throws Exception        Exception g�n�rique.
     */
    private void setUpDatabase() throws Exception {
        // Cr�ation de la connexion
        try {
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (Exception e) {
            System.out.println("ERROR: failed to load HSQLDB JDBC driver.");
            e.printStackTrace();
            return;
        }

        jdbcConnection = DriverManager.getConnection("jdbc:hsqldb:file:/tmp/dbtpj2ee/tpdb", "sa", "");
        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);

        // Configuration de la connexion.
        connection.getConfig().setProperty("http://www.dbunit.org/properties/primaryKeyFilter",
                                           new MyPrimaryKeyFilter());

        // Initialisation du dataset � partir du fichier xml.
        IDataSet dataSet = new FlatXmlDataSet(new File("db/full-dataset.xml"));

        // Insertion en base apr�s suppression de l'existant.
        try {
            DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
        } finally {
            connection.close();
        }
    }

    /**
     * Retourne la classe d'impl�mentation r�elle du DAO.
     * Cette m�thode est � surcharger pour le test d'une impl�mentation sp�cifique.
     *
     * @return La classe de la DAOFactory test�e.
     */
    public abstract Class getDAOFactoryClass();

    /**
     * Retourne le nom du fichier de propri�t�s. Si la valeur est null
     * alors c'est la valeur par d�faut qui sera utilis�e.
     *
     * @return Le nom du fichier de propri�t� � utiliser.
     */
    public abstract String getDAOPropertiesFilename();

    /**
     * Retourne la classe d'impl�mentation du EvenementDAO.
     * Cette m�thode est � surcharger pour le test d'une impl�mentation sp�cifique.
     *
     * @return La classe du EvenementDAO.
     */
    public abstract Class getEvenementDAOClass();

    /**
     * Retourne la classe d'impl�mentation du TypeEvenementDAO.
     * Cette m�thode est � surcharger pour le test d'une impl�mentation sp�cifique.
     *
     * @return La classe de l'TypeEvenementDAO.
     */
    public abstract Class getTypeEvenementDAOClass();

    /**
     * Test de la cr�ation de la DAOFactory.
     *
     * @throws Exception        Exception g�n�rique.
     */
    public void testCreateDAOFactory() throws Exception {
        DAOFactory factory = DAOFactory.getDAOFactory(getDAOPropertiesFilename());
        assertNotNull(factory);
        assertEquals(getDAOFactoryClass(), factory.getClass());
    }

    /**
     * Test de la cr�ation du EvenementDAO.
     *
     * @throws Exception        Exception g�n�rique.
     */
    public void testCreateEvenementDAO() throws Exception {
        EvenementDAO dao = DAOFactory.getDAOFactory(getDAOPropertiesFilename()).getEvenementDAO();
        assertNotNull(dao);
        assertEquals(getEvenementDAOClass(), dao.getClass());
    }

    /**
     * Test de la cr�ation du TypeEvenementDAO.
     *
     * @throws Exception        Exception g�n�rique.
     */
    public void testCreateTypeEvenementDAO() throws Exception {
        TypeEvenementDAO dao = DAOFactory.getDAOFactory(getDAOPropertiesFilename()).getTypeEvenementDAO();
        assertNotNull(dao);
        assertEquals(getTypeEvenementDAOClass(), dao.getClass());
    }

    /**
     * Test unitaire pour la r�cup�ration de la liste des �v�nements.
     *
     * @throws Exception        Exception g�n�rique.
     */
    public void testGetEvenements() throws Exception {
        EvenementDAO dao = DAOFactory.getDAOFactory(getDAOPropertiesFilename()).getEvenementDAO();
        List lstEvenements = dao.getEvenements();
        assertNotNull(lstEvenements);
        assertEquals(3, lstEvenements.size());
    }

    /**
     * Test unitaire pour la r�cup�ration d'un �v�nement.
     *
     * @throws Exception        Exception g�n�rique.
     */
    public void testGetEvenement() throws Exception {
        String id = "996416860873";

        EvenementDAO dao = DAOFactory.getDAOFactory(getDAOPropertiesFilename()).getEvenementDAO();
        Evenement evenement = dao.getEvenement(id);
        assertNotNull(evenement);
        assertEquals("996416860873", evenement.getId());
        assertEquals("IMOE - TP2", evenement.getTitre());
        assertEquals("2013-09-30 19:00:00.0", evenement.getDateDebut().toString());
        assertEquals("2013-09-30 22:00:00.0", evenement.getDateFin().toString());
        TypeEvenement type = evenement.getType();
        assertNotNull(type);
        assertEquals("3", type.getId());
        assertEquals("R�union", type.getLibelle());
        assertEquals("TP sur les Servlets et JSP", evenement.getDescription());
    }

    /**
     * Test unitaire pour la mise � jour d'un evenement.
     *
     * @throws Exception        Exception g�n�rique.
     */
    public void testUpdateEvenement() throws Exception {
        String id = "1067960246800";

        EvenementDAO dao = DAOFactory.getDAOFactory(getDAOPropertiesFilename()).getEvenementDAO();
        Evenement evenement = dao.getEvenement(id);

        // Lecture pr�alable.
        assertNotNull(evenement);
        assertEquals("1067960246800", evenement.getId());
        assertEquals("Jour f�ri�", evenement.getTitre());
        assertEquals("C'est la toussaint !", evenement.getDescription());

        // Mise � jour du evenement.
        evenement.setTitre("Toussaint");
        evenement.setDescription("C'est f�ri� !");

        // Sauvegarde de la mise � jour.
        dao.updateEvenement(evenement);

        // Relecture pour v�rification.
        evenement = dao.getEvenement(id);
        assertNotNull(evenement);
        assertEquals("1067960246800", evenement.getId());
        assertEquals("Toussaint", evenement.getTitre());
        assertEquals("C'est f�ri� !", evenement.getDescription());
    }

    /**
     * Test unitaire pour l'insertion d'un evenement.
     *
     * @throws Exception        Exception g�n�rique.
     */
    public void testInsertEvenement() throws Exception {
        // R�cup�ration des dao.
        EvenementDAO evenementDAO = DAOFactory.getDAOFactory(getDAOPropertiesFilename()).getEvenementDAO();
        TypeEvenementDAO typeDAO = DAOFactory.getDAOFactory(getDAOPropertiesFilename()).getTypeEvenementDAO();
        TypeEvenement type = typeDAO.getTypeEvenement("5");

        // Cr�ation d'un nouveau evenement.
        Timestamp dateDebut = new Timestamp(new Date().getTime());
        Evenement newEvenement = new Evenement();
        newEvenement.setTitre("Exemple");
        newEvenement.setDateDebut(dateDebut);
        newEvenement.setDateFin(null);
        newEvenement.setDescription("Un exemple de description");
        newEvenement.setType(type);

        // Insertion en base
        evenementDAO.insertEvenement(newEvenement);

        // Relecture du nouveau evenement en parcourant tous les �l�ments.
        List lstEvenements = evenementDAO.getEvenements();
        newEvenement = null;
        for (Iterator it = lstEvenements.iterator(); it.hasNext();) {
            Evenement evenement = (Evenement) it.next();
            if ("Exemple".equals(evenement.getTitre())) {
                newEvenement = evenement;
            }
        }

        // V�rification des donn�es ins�r�es.
        assertNotNull(newEvenement);
        assertEquals("Exemple", newEvenement.getTitre());
        assertEquals(dateDebut, newEvenement.getDateDebut());
        assertNull(newEvenement.getDateFin());
        type = newEvenement.getType();
        assertNotNull(type);
        assertEquals("5", type.getId());
        assertEquals("Un exemple de description", newEvenement.getDescription());
    }

    /**
     * Test unitaire pour la suppression d'un evenement.
     *
     * @throws Exception        Exception g�n�rique.
     */
    public void testDeleteEvenement() throws Exception {
        String id = "1069879407179";

        // Lecture de toutes les evenements.
        EvenementDAO dao = DAOFactory.getDAOFactory(getDAOPropertiesFilename()).getEvenementDAO();
        List<Evenement> lstEvenements = dao.getEvenements();
        assertNotNull(lstEvenements);
        assertEquals(3, lstEvenements.size());

        // Lecture de l'evenement pour suppression.
        Evenement evenement = dao.getEvenement(id);
        assertNotNull(evenement);
        assertEquals("1069879407179", evenement.getId());

        // Suppression du evenement
        dao.deleteEvenement(evenement);

        // Lecture de toutes les evenements.
        lstEvenements = dao.getEvenements();
        assertNotNull(lstEvenements);
        assertEquals(2, lstEvenements.size());
    }

    /**
     * Test unitaire pour la r�cup�ration de la liste des types d'�v�nements.
     *
     * @throws Exception        Exception g�n�rique.
     */
    public void testGetTypesEvenements() throws Exception {
        TypeEvenementDAO dao = DAOFactory.getDAOFactory(getDAOPropertiesFilename()).getTypeEvenementDAO();
        List<TypeEvenement> lstTypesEvenements = dao.getTypesEvenements();
        assertNotNull(lstTypesEvenements);
        assertEquals(5, lstTypesEvenements.size());
    }

    /**
     * Test unitaire pour la r�cup�ration d'un type d'�v�nement.
     *
     * @throws Exception        Exception g�n�rique.
     */
    public void testGetTypeEvenement() throws Exception {
        String id = "3";

        TypeEvenementDAO dao = DAOFactory.getDAOFactory(getDAOPropertiesFilename()).getTypeEvenementDAO();
        TypeEvenement type = dao.getTypeEvenement(id);
        assertNotNull(type);
        assertEquals("3", type.getId());
        assertEquals("R�union", type.getLibelle());
    }
}
