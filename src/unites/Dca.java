package unites;

import arme.MitrailleuseLourde;
import ressources.Affichage;
import ressources.Chemins;
import unites.types.Chenilles;

public class Dca extends Chenilles{
	final private int DEPLACEMENT = 6;
	final public static int PRIX = 6000;
	final public static String IDENTIFIANT = "Dca";
	
	public Dca(int appartient_a) {
		super(appartient_a, IDENTIFIANT);
		armePrincipale = new MitrailleuseLourde();
	}

	public void affiche(int x, int y) {
		Affichage.dessineImageDansCase(x, y, Chemins.getCheminUnite(this.appartient_a,this.est_disponible,Chemins.FICHIER_DCA)); 
		super.affiche(x, y); //appelle la méthode mère pour afficher les pv
	}
	
	@Override
	public int getDEPLACEMENT() {
		return DEPLACEMENT;
	}
}
