package cases.terrains;

import cases.root.Cases;
import coordonnees.Coordonnees;
import ressources.Affichage;
import ressources.Chemins;
import unites.root.Unites;

public class Plaine extends Cases{
	final public static String IDENTIFIANT = "Plaine";
	
	public Plaine(Coordonnees coordonnees, Unites occupe_par) {
		super(coordonnees, occupe_par, IDENTIFIANT);
	}

	/**
	 * Affichage du terrain correspondant à la case
	 * @param x ordonnée de la case 
	 * @param y abscisse de la case
	 */
	public void affiche(int x, int y) {
		Affichage.dessineImageDansCase(x, y, Chemins.getCheminTerrain(Chemins.FICHIER_PLAINE)); 
		super.affiche(x, y); //affichage de l'unité qui occupe la case, en blanc en bas à droite de la case
	}
	
	/**
	 * @param joueurActif : le joueur qui souhaite franchir la case
	 * @param unite : l'unité qui souhaite franchir la case
	 * @return le cout de franchissement de la case en fonction de l'unité et du joueur actif
	 */
	@Override
	public int getCout(Unites unite, int joueurActif) {
		//un joueur ne peut pas franchir une case qui est occupée par une unité adverse
		if (occupe_par!=null && occupe_par.getAppartient_a() != joueurActif) { 
			cout = Integer.MAX_VALUE; //cout = +infini
		} else {
			cout = 1; //cout de 1 pour toutes les unités
		}
		return cout;
	}
}
