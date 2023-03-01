package unites;

import arme.MissilesAirSol;
import arme.MitrailleuseLourde;
import ressources.Affichage;
import ressources.Chemins;
import unites.types.Aeriens;

public class Helicoptere extends Aeriens{
	final private int DEPLACEMENT = 6;
	final public static int PRIX = 12000;
	final public static String IDENTIFIANT = "Helicoptere";
	
	public Helicoptere(int appartient_a) {
		super(appartient_a, IDENTIFIANT);
		armePrincipale = new MissilesAirSol();
		armeSecondaire = new MitrailleuseLourde();
	}

	public void affiche(int x, int y) {
		Affichage.dessineImageDansCase(x, y, Chemins.getCheminUnite(this.appartient_a,this.est_disponible,Chemins.FICHIER_HELICOPTERE));
		super.affiche(x, y); //appelle la méthode mère pour afficher les pv
	}
	
	@Override
	public int getDEPLACEMENT() {
		return DEPLACEMENT;
	}
}
