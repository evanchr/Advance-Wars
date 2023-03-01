package unites.types;

import cases.root.Cases;
import cases.terrains.Foret;
import cases.terrains.Montagne;
import cases.terrains.QG;
import cases.terrains.Usine;
import cases.terrains.Ville;
import unites.root.Unites;

public abstract class Pietons extends Unites{

	/**
	 * Constructeur Piétons
	 * @param appartient_a : le joueur auquel appartient l'unité
	 * @param id : l'identifiant des Piétons
	 */
	public Pietons(int appartient_a, String id) {
		super(appartient_a, id);
	}

	public void affiche(int x, int y) {
		super.affiche(x, y);
	}

	/**
	 * Cette méthode sert à appliquer une couverture aux unités sur les dégats qu'elles 
	 * subissent en fonction du terrain sur lequel elles se trouvent.
	 */
	public float degatsInf(Unites cible, Cases couverture) {
		float degats = (float) super.degatsInf(cible);
		switch(couverture.IDENTIFIANT) {
		case Foret.IDENTIFIANT:
		case Usine.IDENTIFIANT:
			degats -= degats*0.2;
			break;
		case Ville.IDENTIFIANT:
			degats -= degats*0.3;
			break;
		case Montagne.IDENTIFIANT:
		case QG.IDENTIFIANT:
			degats -= degats*0.4;
			break;
		default:
			break;
		}
		return degats;
	}
	
	public abstract int getDEPLACEMENT();
}
