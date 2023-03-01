package unites;

import arme.Bombes;
import ressources.Affichage;
import ressources.Chemins;
import unites.types.Aeriens;

public class Bombardier extends Aeriens{
	final private int DEPLACEMENT = 7;
	final public static int PRIX = 20000;
	final public static String IDENTIFIANT = "Bombardier";
	
	public Bombardier(int appartient_a) {
		super(appartient_a, IDENTIFIANT);
		armePrincipale = new Bombes();
	}

	public void affiche(int x, int y) {
		Affichage.dessineImageDansCase(x, y, Chemins.getCheminUnite(this.appartient_a,this.est_disponible,Chemins.FICHIER_BOMBARDIER)); 
		super.affiche(x, y); //appelle la méthode mère pour afficher les pv
	}
	
	@Override
	public int getDEPLACEMENT() {
		return DEPLACEMENT;
	}
}
