package fr.sigl.imoe.servlet.tp.dao;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Logger;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

/**
 * Classe d'export de la base de donn�es.
 *
 * @author Chris
 */
public final class DatabaseExport {
    /**
     * Logger JUL.
     */
    public static final Logger LOGGER = Logger.getLogger(DatabaseExport.class.getName());

    /**
     * Constructeur priv�.
     */
    private DatabaseExport() {
        super();
    }

    /**
     * M�thode de lancement de l'export de la base de donn�es.
     *
     * @param args              Arguments du programme.
     * @throws Exception        Exception g�n�rique.
     */
    public static void main(String[] args) throws Exception {
        // Cr�ation de la connexion.
        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        Connection jdbcConnection = DriverManager.getConnection("jdbc:odbc:evenements", "", "");
        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);

        // Configuration de la connexion.
        connection.getConfig().setProperty("http://www.dbunit.org/properties/primaryKeyFilter",
                                           new MyPrimaryKeyFilter());

        // Export complet de la base.
        IDataSet fullDataSet = connection.createDataSet();
        FlatXmlDataSet.write(fullDataSet, new FileOutputStream("full-dataset.xml"));

    }
}
