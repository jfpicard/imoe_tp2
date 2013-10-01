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
 * Impl�mentation JDBC de TypeEvenementDAO.
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
     * Constructeur � partir d'une factory.
     *
     * @param newFactory           Instance de JdbcDAOFactory
     */
    public JdbcTypeEvenementDAO(JdbcDAOFactory newFactory) {
        super();
        this.factory = newFactory;
    }

    /**
     * Retourne le type associ�e � l'id sp�cifi� en param�tre.
     *
     * @param  id                           L'identifiant technique du type.
     * @return L'instance du type correspondant � l'identifiant technique.
     * @exception  DAORequestException      Exception g�n�rique lors de l'acc�s � la base.
     * @see fr.sigl.imoe.servlet.tp.dao.TypeEvenementDAO#getTypeEvenement(java.lang.String)
     */
    public TypeEvenement getTypeEvenement(String id) throws DAORequestException {
        // V�rification pr�alable
        if (id == null) {
            throw new DAORequestException("L'identifiant du type ne doit pas �tre null pour une recherche.");
        }

        Connection connection = null;
        try {
            TypeEvenement type = null;

            // R�cup�ration de la connexion.
            connection = factory.getConnection();
            Statement stmt = connection.createStatement();

            // Pr�paration de la requ�te SQL.
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT ID,LABEL FROM TYPEEVENEMENT WHERE ID='");
            sql.append(id);
            sql.append("'");

            // Ex�cution de la requ�te.
            ResultSet rs = stmt.executeQuery(sql.toString());

            // Cr�ation du type � partir du r�sultat.
            if (rs.next()) {
            	type = new TypeEvenement(rs.getString(1), rs.getString(2));
            }

            return type;
        } catch (Exception e) {
            String errMsg = "Erreur lors de la r�cup�ration du type ayant pour identifiant '" + id + "'";
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
     * @exception  DAORequestException      Exception g�n�rique lors de l'acc�s � la base.
     * @see fr.sigl.imoe.servlet.tp.dao.TypeEvenementDAO#getTypesEvenements()
     */
    public List getTypesEvenements() throws DAORequestException {
        Connection connection = null;
        try {
            List typesEvenements = new ArrayList();

            // R�cup�ration de la connexion.
            connection = factory.getConnection();
            Statement stmt = connection.createStatement();

            // Pr�paration de la requ�te SQL.
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT ID,LABEL FROM TYPEEVENEMENT");

            // Ex�cution de la requ�te.
            ResultSet rs = stmt.executeQuery(sql.toString());

            // Cr�ation des types � partir du r�sultat.
            while (rs.next()) {
                TypeEvenement type = new TypeEvenement(rs.getString(1), rs.getString(2));
                typesEvenements.add(type);
            }

            return typesEvenements;
        } catch (Exception e) {
            String errMsg = "Erreur lors de la r�cup�ration de la liste des types d'�v�nements.";
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
