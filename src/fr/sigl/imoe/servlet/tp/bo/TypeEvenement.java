package fr.sigl.imoe.servlet.tp.bo;

import java.io.Serializable;

/**
 * Objet métier représentant un type d'événement.
 *
 * @author Chris
 */
public class TypeEvenement implements Serializable {
    /**
	 * UID de version pour la sérialisation.
	 */
	private static final long serialVersionUID = 7247737742203213161L;
    /**
     * Identifiant technique.
     */
    private String id;
    /**
     * Libellé de l'événement.
     */
    private String libelle;


    /**
     * Constructeur par défaut.
     */
    public TypeEvenement() {
        super();
    }
    
    /**
     * Constructeur.
     *
     * @param  newId               Identifiant de l'événement
     * @param  newLibelle      	   Libellé de l'événement
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
