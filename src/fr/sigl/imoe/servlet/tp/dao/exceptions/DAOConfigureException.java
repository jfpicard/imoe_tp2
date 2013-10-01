package fr.sigl.imoe.servlet.tp.dao.exceptions;

/**
 * Exception de configuration du DAO.
 *
 * @author Chris
 */
public class DAOConfigureException extends Exception {
    /**
     * UID de version pour la s�rialisation.
     */
    public static final long serialVersionUID = 3413475441624576190L;

    /**
     * Constructeur par d�faut.
     */
    public DAOConfigureException() {
        super();
    }

    /**
     * Constructeur � partir d'un message descriptif.
     *
     * @param msg           Message d�crivant l'erreur.
     */
    public DAOConfigureException(String msg) {
        super(msg);
    }

    /**
     * Constructeur � partir d'une cause.
     *
     * @param cause         Cause � l'origine de l'erreur.
     */
    public DAOConfigureException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructeur � partir d'un message descriptif et d'une cause.
     *
     * @param msg           Message d�crivant l'erreur.
     * @param cause         Cause � l'origine de l'erreur.
     */
    public DAOConfigureException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
