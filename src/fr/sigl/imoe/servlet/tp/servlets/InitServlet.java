package fr.sigl.imoe.servlet.tp.servlets;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import fr.sigl.imoe.servlet.tp.dao.DAOFactory;
import fr.sigl.imoe.servlet.tp.dao.exceptions.DAOConfigureException;

/**
 * Servlet d'initialisation de l'application permettant
 * de s'assurer que le DAO est correctement configur� et
 * fermant le DAO lors du undeploy de l'application.
 */
public class InitServlet extends HttpServlet {
    /**
	 * Generated Serial Version UID.
	 */
	private static final long serialVersionUID = -7400635874579165711L;

	/**
     * Logger JUL.
     */
    public static final Logger LOGGER = Logger.getLogger(MVC2Servlet.class.getName());

	/**
	 * M�thode d'initialisation pour v�rifier que le DAO est correctement configur�.
	 *
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException {
		super.init();
		try {
			LOGGER.log(Level.INFO, "Initialisaation de la connexion du DAO...");
			DAOFactory.getDAOFactory();
		} catch (DAOConfigureException e) {
            LOGGER.log(Level.SEVERE, "Echec d'initialisation du DAO.", e);
            throw new ServletException(e);
		}
	}

	/**
	 * M�thode permettant de fermer le DAO au undeploy.
	 *
	 * @see javax.servlet.GenericServlet#destroy()
	 */
	@Override
	public void destroy() {
		super.destroy();
		try {
			LOGGER.log(Level.INFO, "Fermeture de la connexion du DAO...");
			DAOFactory.getDAOFactory().close();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Echec de fermeture de la connexion du DAO...", e);
		}
	}
}
