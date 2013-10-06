package fr.sigl.imoe.servlet.tp.servlets;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.sigl.imoe.servlet.tp.bo.Evenement;
import fr.sigl.imoe.servlet.tp.bo.TypeEvenement;
import fr.sigl.imoe.servlet.tp.dao.EvenementDAO;
import fr.sigl.imoe.servlet.tp.dao.TypeEvenementDAO;
import fr.sigl.imoe.servlet.tp.dao.hibernate.HibernateDAOFactory;

/**
 * Servlet MVC2
 */
@WebServlet(
		name = "MCV2Servlet",
		urlPatterns = {"/mvc2/*"}
		)
public class MVC2Servlet extends HttpServlet {
	/**
	 * Generated Serial Version UID.
	 */
	private static final long serialVersionUID = 8814373079661691995L;

	/**
	 * Logger JUL.
	 */
	public static final Logger LOGGER = Logger.getLogger(MVC2Servlet.class.getName());

	/**
	 * Surcharge de la m�thode doGet qui effectue les traitements pour le type de requ�te GET.
	 *
	 * @param request               La requ�te HTTP.
	 * @param response              La r�ponse HTTP.
	 * @throws ServletException     Exception g�n�rique pour le traitement de la servlet.
	 * @throws IOException          Exception g�n�rique d'entr�e / sortie.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String urlEnd = request.getRequestURI().substring(request.getContextPath().length());

		/**
		 * Accueil
		 */
		if (urlEnd.equals("/mvc2")) 
		{
			try
			{
				EvenementDAO evtDAO = HibernateDAOFactory.getDAOFactory().getEvenementDAO();
				request.setAttribute("evts", evtDAO.getEvenements());
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			getServletContext().getRequestDispatcher("/accueil.jsp").forward(request, response);
		}
		/**
		 * Ajouter un événement
		 */
		else if (urlEnd.equals("/mvc2/add"))
		{
			try 
			{
				TypeEvenementDAO typeEvtDAO = HibernateDAOFactory.getDAOFactory().getTypeEvenementDAO();
				request.setAttribute("types", typeEvtDAO.getTypesEvenements());
				getServletContext().getRequestDispatcher("/add.jsp").forward(request, response);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				response.sendRedirect(getServletContext().getContextPath() + "/mvc2");
			}			
		}
		/**
		 * Editer un événement
		 */
		else if (urlEnd.contains("/mvc2/edit"))
		{
			String id = request.getParameter("id");

			try 
			{
				EvenementDAO evtDAO = HibernateDAOFactory.getDAOFactory().getEvenementDAO();
				request.setAttribute("event", evtDAO.getEvenement(id));
				TypeEvenementDAO typeEvtDAO = HibernateDAOFactory.getDAOFactory().getTypeEvenementDAO();
				request.setAttribute("types", typeEvtDAO.getTypesEvenements());
				getServletContext().getRequestDispatcher("/edit.jsp").forward(request, response);	
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				response.sendRedirect(getServletContext().getContextPath() + "/mvc2");
			}
		}
		/**
		 * Supprimer un événement
		 */
		else if (urlEnd.contains("/mvc2/delete"))
		{
			try 
			{
				EvenementDAO evtDAO = HibernateDAOFactory.getDAOFactory().getEvenementDAO();
				evtDAO.deleteEvenement(evtDAO.getEvenement(request.getParameter("id")));
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			response.sendRedirect(getServletContext().getContextPath() + "/mvc2");
		}
		/**
		 * Détail d'un événement
		 */
		else if (urlEnd.contains("/mvc2/detail"))
		{
			try 
			{
				EvenementDAO evtDAO = HibernateDAOFactory.getDAOFactory().getEvenementDAO();
				request.setAttribute("event", evtDAO.getEvenement(request.getParameter("id")));
				getServletContext().getRequestDispatcher("/detail.jsp").forward(request, response);	
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				response.sendRedirect(getServletContext().getContextPath() + "/mvc2");
			}
		}
		/**
		 * Rediriger vers l'accueil pour une URL non traitée par l'application
		 */
		else
		{
			try
			{
				EvenementDAO evtDAO = HibernateDAOFactory.getDAOFactory().getEvenementDAO();
				request.setAttribute("evts", evtDAO.getEvenements());
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			getServletContext().getRequestDispatcher("/accueil.jsp").forward(request, response);
		}
	}

	/**
	 * Surcharge de la m�thode doGet qui effectue les traitements pour le type de requ�te POST.
	 *
	 * @param request               La requ�te HTTP.
	 * @param response              La r�ponse HTTP.
	 * @throws ServletException     Exception g�n�rique pour le traitement de la servlet.
	 * @throws IOException          Exception g�n�rique d'entr�e / sortie.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String urlEnd = request.getRequestURI().substring(request.getContextPath().length());

		/**
		 * Ajouter un événement
		 */
		if (urlEnd.equals("/mvc2/add"))
		{
			try 
			{
				// Errors
				boolean titleError = false;
				boolean typeError = false;
				boolean beginError = false;
				boolean endError = false;
				
				// Titre
				String title = request.getParameter("title");
				titleError = title.isEmpty();
	
				// Type
				String type = request.getParameter("type");
				typeError = type.isEmpty();
				TypeEvenementDAO typeEvtDAO = HibernateDAOFactory.getDAOFactory().getTypeEvenementDAO();
				TypeEvenement typeEvt = typeEvtDAO.getTypeEvenement(type);
				typeError = (typeEvt == null);
				
				// Début
				String begin = null;
				Timestamp beginTimestamp = null;
				try
				{
					begin = request.getParameter("begin");
					beginTimestamp = Timestamp.valueOf(begin);	
				}
				catch (Exception e) 
				{
					beginError = true;
				}
				
				// Fin
				String end = null;
				Timestamp endTimestamp = null;
				try
				{
					end = request.getParameter("end");
					endTimestamp = Timestamp.valueOf(end);
				}
				catch (Exception e) 
				{
					endError = true;
				}		
				
				// Description
				String description = request.getParameter("description");
				
				if (titleError || typeError || beginError || endError)
				{
					
					request.setAttribute("types", typeEvtDAO.getTypesEvenements());
					// titre
					if (titleError)
					{
						request.setAttribute("titleError", "Le titre ne peut pas être vide.");
					}
					else
					{
						request.setAttribute("title", title);
					}
					
					// type
					if (typeError)
					{
						request.setAttribute("typeError", "Le type ne peut pas être vide");
					}
					else
					{
						request.setAttribute("type", type);
					}
					
					// Début
					if (beginError)
					{
						request.setAttribute("beginError", "La date doit être au format : 'AAAA-MM-DD hh:mm:ss'.");
					}
					else
					{
						request.setAttribute("begin", begin);
					}
					
					// Fin
					if (endError)
					{
						request.setAttribute("endError", "La date doit être au format : 'AAAA-MM-DD hh:mm:ss'.");
					}
					else
					{
						request.setAttribute("end", end);
					}
					
					// Description
					request.setAttribute("description", description);
					
					getServletContext().getRequestDispatcher("/add.jsp").forward(request, response);
				}
				else
				{
					// Créer événement
					Evenement evt = new Evenement();
					evt.setTitre(title);
					evt.setType(typeEvt);
					evt.setDateDebut(endTimestamp);
					evt.setDateFin(beginTimestamp);
					evt.setDescription(description);
	
					// Ajouter événement
					EvenementDAO evtDAO = HibernateDAOFactory.getDAOFactory().getEvenementDAO();
					evtDAO.insertEvenement(evt);
					
					response.sendRedirect(getServletContext().getContextPath() + "/mvc2");
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				response.sendRedirect(getServletContext().getContextPath() + "/mvc2");
			}
		}
		/**
		 * Editer un événement
		 */
		else if (urlEnd.contains("/mvc2/edit"))
		{
			try 
			{
				// Errors
				boolean titleError = false;
				boolean typeError = false;
				boolean beginError = false;
				boolean endError = false;
				
				// Evénement id
				String eventId = request.getParameter("eventId");
				
				// Titre
				String title = request.getParameter("title");
				titleError = title.isEmpty();
	
				// Type
				String type = request.getParameter("type");
				typeError = type.isEmpty();
				TypeEvenementDAO typeEvtDAO = HibernateDAOFactory.getDAOFactory().getTypeEvenementDAO();
				TypeEvenement typeEvt = typeEvtDAO.getTypeEvenement(type);
				typeError = (typeEvt == null);
				
				// Début
				String begin = null;
				Timestamp beginTimestamp = null;
				try
				{
					begin = request.getParameter("begin");
					beginTimestamp = Timestamp.valueOf(begin);	
				}
				catch (Exception e) 
				{
					beginError = true;
				}
				
				// Fin
				String end = null;
				Timestamp endTimestamp = null;
				try
				{
					end = request.getParameter("end");
					endTimestamp = Timestamp.valueOf(end);
				}
				catch (Exception e) 
				{
					endError = true;
				}		
				
				// Description
				String description = request.getParameter("description");
				
				if (titleError || typeError || beginError || endError)
				{
					EvenementDAO evtDAO = HibernateDAOFactory.getDAOFactory().getEvenementDAO();
					request.setAttribute("event", evtDAO.getEvenement(eventId));
					
					request.setAttribute("types", typeEvtDAO.getTypesEvenements());
					
					// titre
					if (titleError)
					{
						request.setAttribute("titleError", "Le titre ne peut pas être vide.");
					}
					else
					{
						request.setAttribute("title", title);
					}
					
					// type
					if (typeError)
					{
						request.setAttribute("typeError", "Le type ne peut pas être vide");
					}
					else
					{
						request.setAttribute("type", type);
					}
					
					// Début
					if (beginError)
					{
						request.setAttribute("beginError", "La date doit être au format : 'AAAA-MM-DD hh:mm:ss'.");
					}
					else
					{
						request.setAttribute("begin", begin);
					}
					
					// Fin
					if (endError)
					{
						request.setAttribute("endError", "La date doit être au format : 'AAAA-MM-DD hh:mm:ss'.");
					}
					else
					{
						request.setAttribute("end", end);
					}
					
					// Description
					request.setAttribute("description", description);
					
					getServletContext().getRequestDispatcher("/edit.jsp").forward(request, response);
				}
				else
				{
					// Modifier événement
					EvenementDAO evtDAO = HibernateDAOFactory.getDAOFactory().getEvenementDAO();
					Evenement evt = evtDAO.getEvenement(eventId);
					
					evt.setTitre(title);
					evt.setType(typeEvt);
					evt.setDateDebut(endTimestamp);
					evt.setDateFin(beginTimestamp);
					evt.setDescription(description);
					
					evtDAO.updateEvenement(evt);

					response.sendRedirect(getServletContext().getContextPath() + "/mvc2");
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		/**
		 * Rediriger vers l'accueil pour une URL non traitée par l'application
		 */
		else
		{
			try
			{
				EvenementDAO evtDAO = HibernateDAOFactory.getDAOFactory().getEvenementDAO();
				request.setAttribute("evts", evtDAO.getEvenements());
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			getServletContext().getRequestDispatcher("/accueil.jsp").forward(request, response);
		}
	}
}
