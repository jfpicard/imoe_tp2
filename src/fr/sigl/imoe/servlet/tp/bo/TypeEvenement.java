package fr.sigl.imoe.servlet.tp.bo;

import java.io.Serializable;

/**
 * Objet m�tier repr�sentant un type d'�v�nement.
 *
 * @author Chris
 */
public class TypeEvenement implements Serializable {
    /**
	 * UID de version pour la s�rialisation.
	 */
	private static final long serialVersionUID = 7247737742203213161L;
    /**
     * Identifiant technique.
     */
    private String id;
    /**
     * Libell� de l'�v�nement.
     */
    private String libelle;


    /**
     * Constructeur par d�faut.
     */
    public TypeEvenement() {
        super();
    }
    
    /**
     * Constructeur.
     *
     * @param  newId               Identifiant de l'�v�nement
     * @param  newLibelle      	   Libell� de l'�v�nement
     */
    public TypeEvenement(String newId, String newLibelle) {
        super();
        this.id = newId;
        this.libelle = newLibelle;
    }


    /**
	 * @return the libelle
	 */
	public String getLibelle() {
		return libelle;
	}
	/**
	 * @param libelle the libelle to set
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	/**
     * @return Returns the id.
     */
    public String getId() {
        return id;
    }
    /**
     * @param newId The id to set.
     */
    public void setId(String newId) {
        this.id = newId;
    }
}
