package arme;

import armes.root.Armes;
import unites.Convoi;
import unites.Dca;
import unites.Tank;
import unites.root.Unites;

public class MissilesAirSol  extends Armes{

	public MissilesAirSol() {
	}
	
	/**
	 * Calcul des dégats infligés par l'arme en fonction des pv de l'unité qui attaque et de la cible
	 */
	@Override
	public void setDegatsInfliges(float pvAttaquant, Unites cible) {
		switch(cible.IDENTIFIANT) {
		case Tank.IDENTIFIANT:
			degatsInfliges = pvAttaquant * 0.7;
			break;
		case Dca.IDENTIFIANT:
			degatsInfliges = pvAttaquant * 0.4;
			break;
		case Convoi.IDENTIFIANT:
			degatsInfliges = pvAttaquant * 0.7;
			break;
		default:
			degatsInfliges = 0;
		}
	}
}
