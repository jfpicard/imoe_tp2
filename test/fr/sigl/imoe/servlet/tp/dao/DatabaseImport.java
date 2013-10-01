package fr.sigl.imoe.servlet.tp.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Logger;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;

/**
 * Classe d'import de la base de données.
 *
 * @author Chris
 */
public final class DatabaseImport {
    /**
     * Logger JUL.
     */
    public static final Logger LOGGER = Logger.getLogger(DatabaseImport.class.getName());

    /**
     * Constructeur privé.
     */
    private DatabaseImport() {
        super();
    }

    /**
     * Méthode de lancement de l'import de la base de données.
     *
     * @param args              Arguments du programme.
     * @throws Exception        Exception générique.
     */
    public static void main(String[] args) throws Exception {
        // Création de la connexion.
        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        Connection jdbcConnection = DriverManager.getConnection("jdbc:odbc:evenements", "", "");
        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);

        // Configuration de la connexion.
        connection.getConfig().setProperty("http://www.dbunit.org/properties/primaryKeyFilter",
                                           new MyPrimaryKeyFilter());

        // Initialisation du dataset à partir du fichier xml.
        IDataSet dataSet = new FlatXmlDataSet(new File("full-dataset.xml"));

        // Insertion en base après suppression de l'existant.
        try {
            DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
        } finally {
            connection.close();
        }
    }

}
