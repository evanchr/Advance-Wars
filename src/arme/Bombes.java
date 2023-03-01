package arme;

import armes.root.Armes;
import unites.Bazooka;
import unites.Convoi;
import unites.Dca;
import unites.Infanterie;
import unites.Tank;
import unites.root.Unites;

public class Bombes extends Armes{

	public Bombes() {
	}

	/**
	 * Calcul des dégats infligés par l'arme en fonction des pv de l'unité qui attaque et de la cible
	 */
	@Override
	public void setDegatsInfliges(float pvAttaquant, Unites cible) {
		switch(cible.IDENTIFIANT) {
		case Infanterie.IDENTIFIANT:
			degatsInfliges = pvAttaquant * 1;
			break;
		case Bazooka.IDENTIFIANT:
			degatsInfliges = pvAttaquant * 1;
			break;
		case Tank.IDENTIFIANT:
			degatsInfliges = pvAttaquant * 1;
			break;
		case Dca.IDENTIFIANT:
			degatsInfliges = pvAttaquant * 0.7;
			break;
		case Convoi.IDENTIFIANT:
			degatsInfliges = pvAttaquant * 1;
			break;
		default:
			degatsInfliges = 0;
		}
	}
}
