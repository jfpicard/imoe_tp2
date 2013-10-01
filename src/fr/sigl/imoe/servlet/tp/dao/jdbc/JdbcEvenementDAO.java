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
 * Impl�mentation JDBC de l'EvenementDAO.
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
     * Position de la date de d�but dans le resultSet.
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
     * Constructeur � partir d'une factory.
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
     * @exception  DAORequestException      Exception g�n�rique lors de l'acc�s � la base.
     * @see fr.sigl.imoe.servlet.tp.dao.EvenementDAO#getEvenements()
     */
    public List getEvenements() throws DAORequestException {
        Connection connection = null;
        try {
            List evenements = new ArrayList();

            // R�cup�ration de la connexion.
            connection = factory.getConnection();
            Statement stmt = connection.createStatement();

            // Pr�paration de la requ�te
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT ID,TITLE,START_DATE,END_DATE,TYPE,DESCRIPTION FROM EVENT");

            // Ex�cution de la requ�te.
            ResultSet rs = stmt.executeQuery(sql.toString());

            // Cr�ation des �v�nements � partir du r�sultat.
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
            String errMsg = "Erreur lors de la r�cup�ration de la liste des evenements.";
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
     * Retourne le evenement associ� � l'identifiant pass� en param�tre.
     *
     * @param  id                           L'identifiant technique du evenement.
     * @return L'instance du evenement correspondant � l'identifiant technique.
     * @exception  DAORequestException      Exception g�n�rique lors de l'acc�s � la base.
     * @see fr.sigl.imoe.servlet.tp.dao.EvenementDAO#getEvenement(java.lang.String)
     */
    public Evenement getEvenement(String id) throws DAORequestException {
        // V�rification pr�alable
        if (id == null) {
            throw new DAORequestException("L'identifiant du evenement ne doit pas �tre null pour une recherche.");
        }

        Connection connection = null;
        try {
            Evenement evenement = null;

            // R�cup�ration de la connexion.
            connection = factory.getConnection();
            Statement stmt = connection.createStatement();

            // Pr�paration de la requ�te
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT ID,TITLE,START_DATE,END_DATE,TYPE,DESCRIPTION FROM EVENT WHERE ID='");
            sql.append(id);
            sql.append("'");

            // Ex�cution de la requ�te
            ResultSet rs = stmt.executeQuery(sql.toString());

            // Cr�ation de l'�v�nement � partir du r�sultat.
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
            String errMsg = "Erreur lors de la r�cup�ration du evenement ayant pour identifiant '" + id + "'";
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
     * Met �� jour le evenement.
     *
     * @param  evenement                      Une instance de evenement
     * @exception  DAORequestException      Exception g�n�rique lors de l'acc�s � la base.
     * @see fr.sigl.imoe.servlet.tp.dao.EvenementDAO#updateEvenement(fr.sigl.imoe.servlet.tp.bo.Evenement)
     */
    public void updateEvenement(Evenement evenement) throws DAORequestException {
        // V�rification pr�alable
        if (evenement == null) {
            throw new DAORequestException("Le evenement ne doit pas �tre null pour une mise � jour.");
        }

        Connection connection = null;
        try {
            // R�cup�ration de la connexion.
            connection = factory.getConnection();

            // Pr�paration de la requ�te
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

            // Ex�cution de la requ�te
            stmt.executeUpdate();
        } catch (Exception e) {
            String errMsg = "Erreur lors de la mise � jour de l'evenement.";
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
     * Ajoute un nouveau evenement � la base.
     *
     * @param  evenement                      Une instance de evenement
     * @exception  DAORequestException      Exception g�n�rique lors de l'acc�s� la base.
     * @see fr.sigl.imoe.servlet.tp.dao.EvenementDAO#insertEvenement(fr.sigl.imoe.servlet.tp.bo.Evenement)
     */
    public void insertEvenement(Evenement evenement) throws DAORequestException {
        // V�rification pr�alable
        if (evenement == null) {
            throw new DAORequestException("Le evenement ne doit pas �tre null pour une insertion.");
        }

        Connection connection = null;
        try {
            // Cr�ation de l'identifiant technique pour le evenement
            evenement.setId(String.valueOf(System.currentTimeMillis()));

            // R�cup�ration de la connexion.
            connection = factory.getConnection();

            // Pr�paration de la requ�te
            StringBuffer sql = new StringBuffer();
            sql.append("INSERT INTO EVENT (ID,TITLE,START_DATE,END_DATE,TYPE,DESCRIPTION) VALUES (?,?,?,?,?,?)");
            PreparedStatement stmt = connection.prepareStatement(sql.toString());

            stmt.setString(ID_POS, evenement.getId());
            stmt.setString(TITLE_POS, evenement.getTitre());
            stmt.setTimestamp(START_DATE_POS, evenement.getDateDebut());
            stmt.setTimestamp(END_DATEL_POS, evenement.getDateFin());
            stmt.setString(TYPE_ID_POS, evenement.getType().getId());
            stmt.setString(DESCRIPTION_POS, evenement.getDescription());

            // Ex�cution de la requ�te
            stmt.executeUpdate();
        } catch (Exception e) {
            String errMsg = "Erreur lors de la cr�ation de l'evenement.";
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
     * @exception  DAORequestException      Exception g�n�rique lors de l'acc�s � la base.
     * @see fr.sigl.imoe.servlet.tp.dao.EvenementDAO#deleteEvenement(fr.sigl.imoe.servlet.tp.bo.Evenement)
     */
    public void deleteEvenement(Evenement evenement) throws DAORequestException {
        // V�rification pr�alable
        if (evenement == null) {
            throw new DAORequestException("L'evenement ne doit pas �tre null pour une suppression.");
        }

        Connection connection = null;
        try {
            // R�cup�ration de la connexion.
            connection = factory.getConnection();

            // Pr�paration de la requ�te
            StringBuffer sql = new StringBuffer();
            sql.append("DELETE FROM EVENT WHERE ID=?");
            PreparedStatement stmt = connection.prepareStatement(sql.toString());
            stmt.setString(ID_POS, evenement.getId());

            // Ex�cution de la requ�te.
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
