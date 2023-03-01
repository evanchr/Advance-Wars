package cases.terrains;

import cases.root.Cases;
import coordonnees.Coordonnees;
import ressources.Affichage;
import ressources.Chemins;
import unites.root.Unites;
import unites.types.Chenilles;
import unites.types.Pietons;

public class Montagne extends Cases{
	final public static String IDENTIFIANT = "Montagne";
	
	public Montagne(Coordonnees coordonnees,Unites occupe_par) {
		super(coordonnees, occupe_par, IDENTIFIANT);
	}
	
	/**
	 * Affichage du terrain correspondant à la case
	 * @param x ordonnée de la case 
	 * @param y abscisse de la case
	 */
	public void affiche(int x, int y) {
		Affichage.dessineImageDansCase(x, y, Chemins.getCheminTerrain(Chemins.FICHIER_MONTAGNE)); 
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
		if (occupe_par!=null && occupe_par.getAppartient_a() != joueurActif || unite instanceof Chenilles) { //les Chenilles ne peuvent pas passer par la montagne
			cout=Integer.MAX_VALUE; //cout = +infini
		} else if (unite instanceof Pietons){
			cout = 2; //cout de 2 pour les Piétons
		} else {
			cout = 1; //cout de 1 pour Aériens
		}
		return cout;
	}
}
