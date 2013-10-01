package fr.sigl.imoe.servlet.tp.bo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Objet m�tier repr�sentant un �v�nement.
 *
 * @author Chris
 */
public class Evenement implements Serializable {
    /**
     * UID de version pour la s�rialisation.
     */
    public static final long serialVersionUID = 3413475441624576180L;
    /**
     * Identifiant technique.
     */
    private String id;
    /**
     * Titre de l'�v�nement
     */
    private String titre;
    /**
     * Date de d�but de l'�v�nement.
     */
    private Timestamp dateDebut;
    /**
     * Date de fin de l'�v�nement.
     */
    private Timestamp dateFin;
    /**
     * Type d'�v�nement
     */
    private TypeEvenement type;
    /**
     * Description de l'�v�nement
     */
    private String description;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the titre
	 */
	public String getTitre() {
		return titre;
	}
	/**
	 * @param titre the titre to set
	 */
	public void setTitre(String titre) {
		this.titre = titre;
	}
	/**
	 * @return the dateDebut
	 */
	public Timestamp getDateDebut() {
		return dateDebut;
	}
	/**
	 * @param dateDebut the dateDebut to set
	 */
	public void setDateDebut(Timestamp dateDebut) {
		this.dateDebut = dateDebut;
	}
	/**
	 * @return the dateFin
	 */
	public Timestamp getDateFin() {
		return dateFin;
	}
	/**
	 * @param dateFin the dateFin to set
	 */
	public void setDateFin(Timestamp dateFin) {
		this.dateFin = dateFin;
	}
	/**
	 * @return the type
	 */
	public TypeEvenement getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(TypeEvenement type) {
		this.type = type;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
