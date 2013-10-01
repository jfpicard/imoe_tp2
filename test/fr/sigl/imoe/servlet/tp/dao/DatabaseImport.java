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
 * Classe d'import de la base de donn�es.
 *
 * @author Chris
 */
public final class DatabaseImport {
    /**
     * Logger JUL.
     */
    public static final Logger LOGGER = Logger.getLogger(DatabaseImport.class.getName());

    /**
     * Constructeur priv�.
     */
    private DatabaseImport() {
        super();
    }

    /**
     * M�thode de lancement de l'import de la base de donn�es.
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

        // Initialisation du dataset � partir du fichier xml.
        IDataSet dataSet = new FlatXmlDataSet(new File("full-dataset.xml"));

        // Insertion en base apr�s suppression de l'existant.
        try {
            DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
        } finally {
            connection.close();
        }
    }

}
