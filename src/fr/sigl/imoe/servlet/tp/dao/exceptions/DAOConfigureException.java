package fr.sigl.imoe.servlet.tp.dao.exceptions;

/**
 * Exception de configuration du DAO.
 *
 * @author Chris
 */
public class DAOConfigureException extends Exception {
    /**
     * UID de version pour la sérialisation.
     */
    public static final long serialVersionUID = 3413475441624576190L;

    /**
     * Constructeur par défaut.
     */
    public DAOConfigureException() {
        super();
    }

    /**
     * Constructeur à partir d'un message descriptif.
     *
     * @param msg           Message décrivant l'erreur.
     */
    public DAOConfigureException(String msg) {
        super(msg);
    }

    /**
     * Constructeur à partir d'une cause.
     *
     * @param cause         Cause à l'origine de l'erreur.
     */
    public DAOConfigureException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructeur à partir d'un message descriptif et d'une cause.
     *
     * @param msg           Message décrivant l'erreur.
     * @param cause         Cause à l'origine de l'erreur.
     */
    public DAOConfigureException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
