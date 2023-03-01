package unites.types;

import cases.root.Cases;
import unites.root.Unites;

public abstract class Aeriens extends Unites{

	/**
	 * Constructeur Aériens
	 * @param appartient_a : le joueur auquel appartient l'unité
	 * @param id : l'identifiant des Aériens
	 */
	public Aeriens(int appartient_a, String id) {
		super(appartient_a, id);
	}

	public void affiche(int x, int y) {
		super.affiche(x, y);
	}

	public float degatsInf(Unites cible, Cases couverture) { //Les aériens ne sont pas concernés par les couvertures de terrain
		return (float) super.degatsInf(cible);
	}
	
	public abstract int getDEPLACEMENT();
}
