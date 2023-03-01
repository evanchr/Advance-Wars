package unites;

import ressources.Affichage;
import ressources.Chemins;
import unites.types.Chenilles;

//La classe Convoi est implémentée mais n'existe pas dans cette version du jeu
public class Convoi extends Chenilles{
	final private int DEPLACEMENT = 6;
	final public static int PRIX = 5000;
	final public static String IDENTIFIANT = "Convoi";
	
	public Convoi(int appartient_a) {
		super(appartient_a, IDENTIFIANT);
	}

	public void affiche(int x, int y) {
		Affichage.dessineImageDansCase(x, y, Chemins.getCheminUnite(this.appartient_a,this.est_disponible,Chemins.FICHIER_CONVOI)); 
		super.affiche(x, y); //appelle la méthode mère pour afficher les pv
	}
	
	@Override
	public int getDEPLACEMENT() {
		return DEPLACEMENT;
	}
}
