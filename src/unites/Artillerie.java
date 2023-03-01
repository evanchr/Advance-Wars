package unites;

import ressources.Affichage;
import ressources.Chemins;
import unites.types.Pietons;

// La classe Artillerie est implémentée mais n'existe pas dans cette version du jeu
public class Artillerie extends Pietons{
	final private int DEPLACEMENT = 5;
	final public static int PRIX = 6000;
	final public static String IDENTIFIANT = "Artillerie";
	
	public Artillerie(int appartient_a) {
		super(appartient_a, IDENTIFIANT);
	}

	public void affiche(int x, int y) {
		Affichage.dessineImageDansCase(x, y, Chemins.getCheminUnite(this.appartient_a,this.est_disponible,Chemins.FICHIER_ARTILLERIE)); 
		super.affiche(x, y); //appelle la méthode mère pour afficher les pv
	}
	
	@Override
	public int getDEPLACEMENT() {
		return DEPLACEMENT;
	}
}
