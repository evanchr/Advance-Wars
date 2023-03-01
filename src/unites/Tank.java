package unites;

import arme.Canon;
import arme.MitrailleuseLegere;
import ressources.Affichage;
import ressources.Chemins;
import unites.types.Chenilles;

public class Tank extends Chenilles{
	final private int DEPLACEMENT = 6;
	final public static int PRIX = 7000;
	final public static String IDENTIFIANT = "Tank";
	
	public Tank(int appartient_a) {
		super(appartient_a, IDENTIFIANT);
		armePrincipale = new Canon();
		armeSecondaire = new MitrailleuseLegere();
	}

	public void affiche(int x, int y) {
		Affichage.dessineImageDansCase(x, y, Chemins.getCheminUnite(this.appartient_a,this.est_disponible,Chemins.FICHIER_TANK)); 
		super.affiche(x, y); //appelle la méthode mère pour afficher les pv
	}
	
	@Override
	public int getDEPLACEMENT() {
		return DEPLACEMENT;
	}
}
