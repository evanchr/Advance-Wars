package arme;

import armes.root.Armes;
import unites.Bazooka;
import unites.Convoi;
import unites.Dca;
import unites.Helicoptere;
import unites.Infanterie;
import unites.Tank;
import unites.root.Unites;

public class MitrailleuseLegere  extends Armes{

	public MitrailleuseLegere() {
	}

	/**
	 * Calcul des dégats infligés par l'arme en fonction des pv de l'unité qui attaque et de la cible
	 */
	@Override
	public void setDegatsInfliges(float pvAttaquant, Unites cible) {
		switch(cible.IDENTIFIANT) {
		case Infanterie.IDENTIFIANT:
			degatsInfliges = pvAttaquant * 0.60;
			break;
		case Bazooka.IDENTIFIANT:
			degatsInfliges = pvAttaquant * 0.55;
			break;
		case Tank.IDENTIFIANT:
			degatsInfliges = pvAttaquant * 0.15;
			break;
		case Dca.IDENTIFIANT:
			degatsInfliges = pvAttaquant * 0.10;
			break;
		case Helicoptere.IDENTIFIANT:
			degatsInfliges = pvAttaquant * 0.30;
			break;
		case Convoi.IDENTIFIANT:
			degatsInfliges = pvAttaquant * 0.40;
			break;
		default:
			degatsInfliges = 0;
		}
	}
}
