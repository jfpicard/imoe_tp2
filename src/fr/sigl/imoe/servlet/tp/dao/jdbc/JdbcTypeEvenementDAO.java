package fr.sigl.imoe.servlet.tp.dao.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.sigl.imoe.servlet.tp.bo.TypeEvenement;
import fr.sigl.imoe.servlet.tp.dao.TypeEvenementDAO;
import fr.sigl.imoe.servlet.tp.dao.exceptions.DAORequestException;

/**
 * Implémentation JDBC de TypeEvenementDAO.
 *
 * @author Chris
 */
public class JdbcTypeEvenementDAO implements TypeEvenementDAO {
    /**
     * Logger JUL.
     */
    public static final Logger LOGGER = Logger.getLogger(JdbcTypeEvenementDAO.class.getName());
    /**
     * Instance de la JdbcDAOFactory.
     */
    private JdbcDAOFactory factory;

    /**
     * Constructeur à partir d'une factory.
     *
     * @param newFactory           Instance de JdbcDAOFactory
     */
    public JdbcTypeEvenementDAO(JdbcDAOFactory newFactory) {
        super();
        this.factory = newFactory;
    }

    /**
     * Retourne le type associée à l'id spécifié en paramètre.
     *
     * @param  id                           L'identifiant technique du type.
     * @return L'instance du type correspondant à l'identifiant technique.
     * @exception  DAORequestException      Exception générique lors de l'accès à la base.
     * @see fr.sigl.imoe.servlet.tp.dao.TypeEvenementDAO#getTypeEvenement(java.lang.String)
     */
    public TypeEvenement getTypeEvenement(String id) throws DAORequestException {
        // Vérification préalable
        if (id == null) {
            throw new DAORequestException("L'identifiant du type ne doit pas étre null pour une recherche.");
        }

        Connection connection = null;
        try {
            TypeEvenement type = null;

            // Récupération de la connexion.
            connection = factory.getConnection();
            Statement stmt = connection.createStatement();

            // Préparation de la requête SQL.
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT ID,LABEL FROM TYPEEVENEMENT WHERE ID='");
            sql.append(id);
            sql.append("'");

            // Exécution de la requête.
            ResultSet rs = stmt.executeQuery(sql.toString());

            // Création du type à partir du résultat.
            if (rs.next()) {
            	type = new TypeEvenement(rs.getString(1), rs.getString(2));
            }

            return type;
        } catch (Exception e) {
            String errMsg = "Erreur lors de la récupération du type ayant pour identifiant '" + id + "'";
            LOGGER.log(Level.SEVERE, errMsg, e);
            throw new DAORequestException(errMsg, e);
        } finally {
            //Ferme la connexion dans tous les cas
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Echec de fermeture de la connexion.", e);
            }
        }
    }

    /**
     * Retourne la liste des types existants.
     *
     * @return la liste des types.
     * @exception  DAORequestException      Exception générique lors de l'accés à la base.
     * @see fr.sigl.imoe.servlet.tp.dao.TypeEvenementDAO#getTypesEvenements()
     */
    public List getTypesEvenements() throws DAORequestException {
        Connection connection = null;
        try {
            List typesEvenements = new ArrayList();

            // Récupération de la connexion.
            connection = factory.getConnection();
            Statement stmt = connection.createStatement();

            // Préparation de la requête SQL.
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT ID,LABEL FROM TYPEEVENEMENT");

            // Exécution de la requête.
            ResultSet rs = stmt.executeQuery(sql.toString());

            // Création des types à partir du résultat.
            while (rs.next()) {
                TypeEvenement type = new TypeEvenement(rs.getString(1), rs.getString(2));
                typesEvenements.add(type);
            }

            return typesEvenements;
        } catch (Exception e) {
            String errMsg = "Erreur lors de la récupération de la liste des types d'événements.";
            LOGGER.log(Level.SEVERE, errMsg, e);
            throw new DAORequestException(errMsg, e);
        } finally {
            //Ferme la connexion dans tous les cas
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Echec de fermeture de la connexion.", e);
            }
        }
    }
}
