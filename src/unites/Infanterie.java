package unites;

import arme.MitrailleuseLegere;
import ressources.Affichage;
import ressources.Chemins;
import unites.types.Pietons;

public class Infanterie extends Pietons{
	final private int DEPLACEMENT = 3;
	final public static int PRIX = 1500;
	final public static String IDENTIFIANT = "Infanterie";
	
	public Infanterie(int appartient_a) {
		super(appartient_a, IDENTIFIANT);
		armePrincipale = new MitrailleuseLegere();
	}

	public void affiche(int x, int y) {
		Affichage.dessineImageDansCase(x, y, Chemins.getCheminUnite(this.appartient_a,this.est_disponible,Chemins.FICHIER_INFANTERIE)); 
		super.affiche(x, y); //appelle la méthode mère pour afficher les pv
	}
	
	@Override
	public int getDEPLACEMENT() {
		return DEPLACEMENT;
	}
	
}

