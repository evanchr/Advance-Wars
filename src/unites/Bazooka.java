package unites;

import arme.Canon;
import arme.MitrailleuseLegere;
import ressources.Affichage;
import ressources.Chemins;
import unites.types.Pietons;

public class Bazooka extends Pietons{
	final private int DEPLACEMENT = 2;
	final public static int PRIX = 3500;
	final public static String IDENTIFIANT = "Bazooka";
	
	public Bazooka(int appartient_a) {
		super(appartient_a, IDENTIFIANT);
		armePrincipale = new Canon();
		armeSecondaire = new MitrailleuseLegere();
	}

	public void affiche(int x, int y) {
		Affichage.dessineImageDansCase(x, y, Chemins.getCheminUnite(this.appartient_a,this.est_disponible,Chemins.FICHIER_BAZOOKA)); 
		super.affiche(x, y); //appelle la méthode mère pour afficher les pv
	}
	
	@Override
	public int getDEPLACEMENT() {
		return DEPLACEMENT;
	}
}
