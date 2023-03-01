package arme;

import armes.root.Armes;
import unites.Convoi;
import unites.Dca;
import unites.Tank;
import unites.root.Unites;

public class Canon extends Armes{

	public Canon() {
	}

	/**
	 * Calcul des dégats infligés par l'arme en fonction des pv de l'unité qui attaque et de la cible
	 */
	@Override
	public void setDegatsInfliges(float pvAttaquant, Unites cible) {
		switch(cible.IDENTIFIANT) {
		case Tank.IDENTIFIANT:
			degatsInfliges = pvAttaquant * 0.55;
			break;
		case Dca.IDENTIFIANT:
			degatsInfliges = pvAttaquant * 0.60;
			break;
		case Convoi.IDENTIFIANT:
			degatsInfliges = pvAttaquant * 0.70;
			break;
		default:
			degatsInfliges = 0;
		}
	}
}
