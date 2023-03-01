package unites.root;

import java.awt.Color;
import java.awt.Font;

import armes.root.Armes;
import cases.root.Cases;
import ressources.Affichage;

public abstract class Unites {
	protected int appartient_a;
	protected float pv;
	protected boolean est_disponible;
	protected Armes armePrincipale, armeSecondaire; //une unité peut avoir plusieurs armes dans cette version du jeu
	public String IDENTIFIANT;

	public Unites(int appartient_a, String id) {
		this.appartient_a = appartient_a;
		pv = 10; //une unité a initialement 10 pv
		est_disponible = true;
		IDENTIFIANT = id;
	}

	public void affiche(int x, int y) {
		//si une unité est touchée, on affiche ses pv restants en blanc en bas à droite de la case
		if(pv < 10) {
			Affichage.afficheTexteDansCase(x, y, String.valueOf(Math.round(pv)), new Color(255,255,255), 0.7, 0.15, new Font("Times", Font.BOLD, 16));
		}
	}
	
	/**
	 * Calcule les dégats infligés à une cible avec l'arme principale et l'arme secondaire si elle existe
	 * @param cible : la cible à attaquer
	 * @return les dégats infligés par l'arme faisant le plus de degats
	 */
	public double degatsInf(Unites cible) {
		armePrincipale.setDegatsInfliges(pv,cible); //dégats infligés par l'arme en fonction de la cible et des pv de l'attaquant
		double degats = armePrincipale.getDegatsInfliges();
		if (armeSecondaire!=null) {
			armeSecondaire.setDegatsInfliges(pv,cible); //dégats infligés par l'arme en fonction de la cible et des pv de l'attaquant
			degats = Math.max(armePrincipale.getDegatsInfliges(), armeSecondaire.getDegatsInfliges()) ;
		}
		return degats;
	}
	
	public abstract float degatsInf(Unites occupe_par, Cases cases); //applique la couverture de terrain aux unités concernées (Piétons et Chenilles)
	public abstract int getDEPLACEMENT(); //renvoie le nombre de déplacement que l'unité peut réaliser

	public int getAppartient_a() {
		return appartient_a; //renvoie le joueur auquel l'unité appartient
	}

	public void setAppartient_a(int appartient_a) {
		this.appartient_a = appartient_a; //permet d'affecter une unité à un joueur
	}

	public float getPv() {
		return pv; //renvoie le nombre de pv de l'unité
	}

	public void setPv(double f) {
		this.pv = (float) Math.ceil(f); //change le nombre de pv de l'unité arrondi à l'entier supérieur
	}

	public boolean getEst_disponible() {
		return est_disponible; //renvoir si l'unité est dispo
	}

	public void setEst_disponible(boolean est_disponible) {
		this.est_disponible = est_disponible; //change la disponibilité de l'unité
	}

	
}
