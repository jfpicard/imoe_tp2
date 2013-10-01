package fr.sigl.imoe.servlet.tp.dao;

import org.dbunit.dataset.Column;
import org.dbunit.dataset.filter.IColumnFilter;

/**
 * Filtre pour les clefs primaires. Pour Access aucune colonne
 * ne doit être considéré comme une clef primaire.
 *
 * @author Chris
 */
public class MyPrimaryKeyFilter implements IColumnFilter {
    /**
     * Filtre de colonne.
     *
     * @param tableName         Nom de la table.
     * @param column            Nom de la colonne.
     * @return false systématiquement.
     * @see org.dbunit.dataset.filter.IColumnFilter#accept(java.lang.String, org.dbunit.dataset.Column)
     */
    public boolean accept(String tableName, Column column) {
        return false;
    }
}
