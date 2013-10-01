package fr.sigl.imoe.servlet.tp.dao.exceptions;

/**
 * Exception lors de l'ex�cution d'une requ�te via le DAO.
 *
 * @author Chris
 */
public class DAORequestException extends Exception {
    /**
     * UID de version pour la s�rialisation.
     */
    public static final long serialVersionUID = 3413475441624576191L;

    /**
     * Constructeur par d�faut.
     */
    public DAORequestException() {
        super();
    }

    /**
     * Constructeur � partir d'un message descriptif.
     *
     * @param msg           Message d�crivant l'erreur.
     */
    public DAORequestException(String msg) {
        super(msg);
    }

    /**
     * Constructeur � partir d'une cause.
     *
     * @param cause         Cause � l'origine de l'erreur.
     */
    public DAORequestException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructeur � partir d'un message descriptif et d'une cause.
     *
     * @param msg           Message d�crivant l'erreur.
     * @param cause         Cause � l'origine de l'erreur.
     */
    public DAORequestException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
