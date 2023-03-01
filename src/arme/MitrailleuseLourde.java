package arme;

import armes.root.Armes;
import unites.Bazooka;
import unites.Convoi;
import unites.Dca;
import unites.Helicoptere;
import unites.Bombardier;
import unites.Infanterie;
import unites.Tank;
import unites.root.Unites;

public class MitrailleuseLourde  extends Armes{

	public MitrailleuseLourde() {
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
			degatsInfliges = pvAttaquant * 0.80;
			break;
		case Tank.IDENTIFIANT:
			degatsInfliges = pvAttaquant * 0.30;
			break;
		case Dca.IDENTIFIANT:
			degatsInfliges = pvAttaquant * 0.30;
			break;
		case Helicoptere.IDENTIFIANT:
			degatsInfliges = pvAttaquant * 1.1;
			break;
		case Bombardier.IDENTIFIANT:
			degatsInfliges = pvAttaquant * 0.70;
			break;
		case Convoi.IDENTIFIANT:
			degatsInfliges = pvAttaquant * 0.50;
			break;
		default:
			degatsInfliges = 0;
		}
	}
}
