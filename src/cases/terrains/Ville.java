package cases.terrains;

import cases.proprietes.Proprietes;
import coordonnees.Coordonnees;
import ressources.Affichage;
import ressources.Chemins;
import unites.root.Unites;

public class Ville extends Proprietes{
	final public static String IDENTIFIANT = "Ville";
	
	public Ville(Coordonnees coordonnees, int appartient,  Unites occupe_par) {
		super(coordonnees, appartient, occupe_par, IDENTIFIANT);
	}
	
	public Ville(Coordonnees coordonnees, int appartient) {
		super(coordonnees, appartient, IDENTIFIANT);
	} 
	
	/**
	 * Affichage du terrain correspondant à la case
	 * @param x ordonnée de la case 
	 * @param y abscisse de la case
	 */
	public void affiche(int x, int y) {
		Affichage.dessineImageDansCase(x, y, Chemins.getCheminPropriete(Chemins.FICHIER_VILLE, appartient)); 
		super.affiche(x, y); //affichage de l'unité qui occupe la case, et des pv de la propriété
	}
}
