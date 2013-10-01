package fr.sigl.imoe.servlet.tp.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.sigl.imoe.servlet.tp.bo.Evenement;
import fr.sigl.imoe.servlet.tp.dao.EvenementDAO;
import fr.sigl.imoe.servlet.tp.dao.TypeEvenementDAO;
import fr.sigl.imoe.servlet.tp.dao.exceptions.DAORequestException;

/**
 * Implémentation JDBC de l'EvenementDAO.
 *
 * @author Chris
 */
public class JdbcEvenementDAO implements EvenementDAO {
    /**
     * Logger JUL.
     */
    public static final Logger LOGGER = Logger.getLogger(JdbcEvenementDAO.class.getName());
    /**
     * Position de l'identifiant dans le resultSet.
     */
    private static final int ID_POS = 1;
    /**
     * Position du titre dans le resultSet.
     */
    private static final int TITLE_POS = 2;
    /**
     * Position de la date de début dans le resultSet.
     */
    private static final int START_DATE_POS = 3;
    /**
     * Position de la date de fin dans le resultSet.
     */
    private static final int END_DATEL_POS = 4;
    /**
     * Position de l'identifiant du type dans le resultSet.
     */
    private static final int TYPE_ID_POS = 5;
    /**
     * Position de la description dans le resultSet.
     */
    private static final int DESCRIPTION_POS = 6;

    /**
     * Instance de la JdbcDAOFactory.
     */
    private JdbcDAOFactory factory;

    /**
     * Constructeur à partir d'une factory.
     *
     * @param newFactory           Instance de JdbcDAOFactory
     */
    public JdbcEvenementDAO(JdbcDAOFactory newFactory) {
        super();
        this.factory = newFactory;
    }

    /**
     * Retourne la liste des evenements existants.
     *
     * @return la liste des evenements.
     * @exception  DAORequestException      Exception générique lors de l'accés à la base.
     * @see fr.sigl.imoe.servlet.tp.dao.EvenementDAO#getEvenements()
     */
    public List getEvenements() throws DAORequestException {
        Connection connection = null;
        try {
            List evenements = new ArrayList();

            // Récupération de la connexion.
            connection = factory.getConnection();
            Statement stmt = connection.createStatement();

            // Préparation de la requéte
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT ID,TITLE,START_DATE,END_DATE,TYPE,DESCRIPTION FROM EVENT");

            // Exécution de la requête.
            ResultSet rs = stmt.executeQuery(sql.toString());

            // Création des événements à partir du résultat.
            while (rs.next()) {
                Evenement evenement = new Evenement();
                evenement.setId(rs.getString(ID_POS));
                evenement.setTitre(rs.getString(TITLE_POS));
                evenement.setDateDebut(rs.getTimestamp(START_DATE_POS));
                evenement.setDateFin(rs.getTimestamp(END_DATEL_POS));
                TypeEvenementDAO typeDAO = factory.getTypeEvenementDAO();
                evenement.setType(typeDAO.getTypeEvenement(rs.getString(TYPE_ID_POS)));
                evenement.setDescription(rs.getString(DESCRIPTION_POS));
                evenements.add(evenement);
            }

            return evenements;
        } catch (Exception e) {
            String errMsg = "Erreur lors de la récupération de la liste des evenements.";
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
     * Retourne le evenement associé à l'identifiant passé en paramétre.
     *
     * @param  id                           L'identifiant technique du evenement.
     * @return L'instance du evenement correspondant à l'identifiant technique.
     * @exception  DAORequestException      Exception générique lors de l'accés à la base.
     * @see fr.sigl.imoe.servlet.tp.dao.EvenementDAO#getEvenement(java.lang.String)
     */
    public Evenement getEvenement(String id) throws DAORequestException {
        // Vérification préalable
        if (id == null) {
            throw new DAORequestException("L'identifiant du evenement ne doit pas étre null pour une recherche.");
        }

        Connection connection = null;
        try {
            Evenement evenement = null;

            // Récupération de la connexion.
            connection = factory.getConnection();
            Statement stmt = connection.createStatement();

            // Préparation de la requête
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT ID,TITLE,START_DATE,END_DATE,TYPE,DESCRIPTION FROM EVENT WHERE ID='");
            sql.append(id);
            sql.append("'");

            // Exécution de la requête
            ResultSet rs = stmt.executeQuery(sql.toString());

            // Création de l'événement à partir du résultat.
            if (rs.next()) {
                evenement = new Evenement();
                evenement.setId(rs.getString(ID_POS));
                evenement.setTitre(rs.getString(TITLE_POS));
                evenement.setDateDebut(rs.getTimestamp(START_DATE_POS));
                evenement.setDateFin(rs.getTimestamp(END_DATEL_POS));
                TypeEvenementDAO typeDAO = factory.getTypeEvenementDAO();
                evenement.setType(typeDAO.getTypeEvenement(rs.getString(TYPE_ID_POS)));
                evenement.setDescription(rs.getString(DESCRIPTION_POS));
            }

            return evenement;
        } catch (Exception e) {
            String errMsg = "Erreur lors de la récupération du evenement ayant pour identifiant '" + id + "'";
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
     * Met éà jour le evenement.
     *
     * @param  evenement                      Une instance de evenement
     * @exception  DAORequestException      Exception générique lors de l'accés à la base.
     * @see fr.sigl.imoe.servlet.tp.dao.EvenementDAO#updateEvenement(fr.sigl.imoe.servlet.tp.bo.Evenement)
     */
    public void updateEvenement(Evenement evenement) throws DAORequestException {
        // Vérification préalable
        if (evenement == null) {
            throw new DAORequestException("Le evenement ne doit pas étre null pour une mise à jour.");
        }

        Connection connection = null;
        try {
            // Récupération de la connexion.
            connection = factory.getConnection();

            // Préparation de la requête
            StringBuffer sql = new StringBuffer();
            sql.append("UPDATE EVENT SET TITLE=?, START_DATE=?,");
            sql.append("END_DATE=?, TYPE=?,");
            sql.append("DESCRIPTION=? WHERE ID=?");
            PreparedStatement stmt = connection.prepareStatement(sql.toString());

            stmt.setString(1, evenement.getTitre());
            stmt.setTimestamp(2, evenement.getDateDebut());
            stmt.setTimestamp(3, evenement.getDateFin());
            stmt.setString(4, evenement.getType().getId());
            stmt.setString(5, evenement.getDescription());
            stmt.setString(6, evenement.getId());

            // Exécution de la requête
            stmt.executeUpdate();
        } catch (Exception e) {
            String errMsg = "Erreur lors de la mise à jour de l'evenement.";
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
     * Ajoute un nouveau evenement à la base.
     *
     * @param  evenement                      Une instance de evenement
     * @exception  DAORequestException      Exception générique lors de l'accésà la base.
     * @see fr.sigl.imoe.servlet.tp.dao.EvenementDAO#insertEvenement(fr.sigl.imoe.servlet.tp.bo.Evenement)
     */
    public void insertEvenement(Evenement evenement) throws DAORequestException {
        // Vérification préalable
        if (evenement == null) {
            throw new DAORequestException("Le evenement ne doit pas étre null pour une insertion.");
        }

        Connection connection = null;
        try {
            // Création de l'identifiant technique pour le evenement
            evenement.setId(String.valueOf(System.currentTimeMillis()));

            // Récupération de la connexion.
            connection = factory.getConnection();

            // Préparation de la requéte
            StringBuffer sql = new StringBuffer();
            sql.append("INSERT INTO EVENT (ID,TITLE,START_DATE,END_DATE,TYPE,DESCRIPTION) VALUES (?,?,?,?,?,?)");
            PreparedStatement stmt = connection.prepareStatement(sql.toString());

            stmt.setString(ID_POS, evenement.getId());
            stmt.setString(TITLE_POS, evenement.getTitre());
            stmt.setTimestamp(START_DATE_POS, evenement.getDateDebut());
            stmt.setTimestamp(END_DATEL_POS, evenement.getDateFin());
            stmt.setString(TYPE_ID_POS, evenement.getType().getId());
            stmt.setString(DESCRIPTION_POS, evenement.getDescription());

            // Exécution de la requête
            stmt.executeUpdate();
        } catch (Exception e) {
            String errMsg = "Erreur lors de la création de l'evenement.";
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
     * Efface un evenement.
     *
     * @param  evenement                    Une instance d'evenement
     * @exception  DAORequestException      Exception générique lors de l'accés à la base.
     * @see fr.sigl.imoe.servlet.tp.dao.EvenementDAO#deleteEvenement(fr.sigl.imoe.servlet.tp.bo.Evenement)
     */
    public void deleteEvenement(Evenement evenement) throws DAORequestException {
        // Vérification préalable
        if (evenement == null) {
            throw new DAORequestException("L'evenement ne doit pas étre null pour une suppression.");
        }

        Connection connection = null;
        try {
            // Récupération de la connexion.
            connection = factory.getConnection();

            // Préparation de la requête
            StringBuffer sql = new StringBuffer();
            sql.append("DELETE FROM EVENT WHERE ID=?");
            PreparedStatement stmt = connection.prepareStatement(sql.toString());
            stmt.setString(ID_POS, evenement.getId());

            // Exécution de la requête.
            stmt.executeUpdate();
        } catch (Exception e) {
            String errMsg = "Erreur lors de la suppression de l'evenement ayant pour identifiant '" + evenement.getId() + "'";
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
