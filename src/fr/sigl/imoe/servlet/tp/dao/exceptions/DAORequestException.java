package fr.sigl.imoe.servlet.tp.dao.exceptions;

/**
 * Exception lors de l'exécution d'une requête via le DAO.
 *
 * @author Chris
 */
public class DAORequestException extends Exception {
    /**
     * UID de version pour la sérialisation.
     */
    public static final long serialVersionUID = 3413475441624576191L;

    /**
     * Constructeur par défaut.
     */
    public DAORequestException() {
        super();
    }

    /**
     * Constructeur à partir d'un message descriptif.
     *
     * @param msg           Message décrivant l'erreur.
     */
    public DAORequestException(String msg) {
        super(msg);
    }

    /**
     * Constructeur à partir d'une cause.
     *
     * @param cause         Cause à l'origine de l'erreur.
     */
    public DAORequestException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructeur à partir d'un message descriptif et d'une cause.
     *
     * @param msg           Message décrivant l'erreur.
     * @param cause         Cause à l'origine de l'erreur.
     */
    public DAORequestException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
