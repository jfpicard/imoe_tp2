package initdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

/**
 * Application utilitaire permettant de générer une nouvelle
 * base fichier propre.
 *
 * @author Chris
 */
public class InitDBHelper {
    /**
     * Logger JUL.
     */
    public static final Logger LOGGER = Logger.getLogger(InitDBHelper.class.getName());

    /**
     * Handler de connexion avec la base.
     */
    private Connection connection = null;

    /**
     * Constructeur par défaut.
     * 
     * @throws Exception
     *             Erreur générique.
     */
    public InitDBHelper() throws Exception {
        super();
        connect();
    }

    /**
     * Connection é la base de données.
     * 
     * @throws Exception
     *             Erreur générique.
     */
    private void connect() throws Exception {
        Class.forName("org.hsqldb.jdbcDriver");
        connection = DriverManager.getConnection("jdbc:hsqldb:file:/tmp/dbtpj2ee/tpdb", "sa", "");
    }

    /**
     * Fermeture de la connexion et arrét de la base proprement.
     * 
     * @throws SQLException
     *             Erreur SQL standard.
     */
    public void shutdown() throws SQLException {
        Statement st = connection.createStatement();
        st.execute("SHUTDOWN");
        connection.close();
    }

    /**
     * Exécution d'une requéte de type SELECT et affichage du résultat.
     * 
     * @param expression
     *            Requéte é exécuter.
     * @throws SQLException
     *             Erreur SQL standard.
     */
    public synchronized void query(String expression) throws SQLException {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(expression);

        dump(rs);
        st.close();
    }

    /**
     * Exécution d'une requéte de type CREATE, DROP, INSERT ou UPDATE.
     * 
     * @param expression
     *            Requéte é exécuter.
     * @throws SQLException
     *             Erreur SQL standard.
     */
    public synchronized void update(String expression) throws SQLException {
        Statement st = connection.createStatement();
        int i = st.executeUpdate(expression);
        if (i == -1) {
            LOGGER.severe("db error : " + expression);
        }

        st.close();
    }

    /**
     * Affichage du résultat d'une requéte.
     * 
     * @param rs
     *            Le ResultSet é afficher.
     * @throws SQLException
     *             Erreur SQL standard.
     */
    public static void dump(ResultSet rs) throws SQLException {
        ResultSetMetaData meta = rs.getMetaData();
        int colmax = meta.getColumnCount();
        int i;
        Object o = null;
        StringBuffer buffer = new StringBuffer();

        for (; rs.next();) {
            for (i = 0; i < colmax; ++i) {
                o = rs.getObject(i + 1);

                // with 1 not 0
                buffer.append(o.toString() + " ");
            }
            buffer.append(" \n");
        }
        LOGGER.info(buffer.toString());
    }

    /**
     * Méthode de lancement de l'initialisation de la base de données.
     *
     * @param args              Arguments du programme.
     *
     * @throws Exception        Exception générique.
     */
    public static void main(String[] args) throws Exception {
        InitDBHelper db = new InitDBHelper();

        // Création des tables
        db.update("DROP TABLE TYPEEVENEMENT IF EXISTS");
        db.update("DROP TABLE EVENT IF EXISTS");
        db.update("CREATE TABLE TYPEEVENEMENT(ID VARCHAR(50),LABEL VARCHAR(200));");
        db.update("CREATE TABLE EVENT(ID VARCHAR(50),TITLE VARCHAR(50),START_DATE TIMESTAMP,END_DATE TIMESTAMP,TYPE VARCHAR(50),DESCRIPTION VARCHAR(2048));");

        // Insertion de données d'exemple
        db.update("INSERT INTO TYPEEVENEMENT VALUES('1','Rendez-vous');");
        db.update("INSERT INTO TYPEEVENEMENT VALUES('2','Anniversaire');");
        db.update("INSERT INTO TYPEEVENEMENT VALUES('3','R\u00e9union');");
        db.update("INSERT INTO TYPEEVENEMENT VALUES('4','Cong\u00e9s');");
        db.update("INSERT INTO TYPEEVENEMENT VALUES('5','Autres');");
        db.update("INSERT INTO EVENT VALUES('996416860873','IMOE - TP2','2013-09-30 19:00:00.000000000','2013-09-30 22:00:00.000000000','3','TP sur les Servlets et JSP');");
        db.update("INSERT INTO EVENT VALUES('1067960246800','Jour f\u00e9ri\u00e9','2013-11-01 00:00:00.000000000','2013-11-01 23:59:59.999999999','4','C''est la toussaint !');");
        db.update("INSERT INTO EVENT VALUES('1069879407179','Rendu conception','2013-10-31 23:00:00.000000000','2013-10-31 23:00:00.000000000','5','Rendu de conception MiniGRC');");

        // Arrét de la base pour la laisser dans un état cohérent
        db.shutdown();
    }
}
