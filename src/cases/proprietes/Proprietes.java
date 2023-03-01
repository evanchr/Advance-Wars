package cases.proprietes;

import java.awt.Color;
import java.awt.Font;

import cases.root.Cases;
import coordonnees.Coordonnees;
import ressources.Affichage;
import unites.root.Unites;

public abstract class Proprietes extends Cases{
	protected int appartient;
	protected float pv;
	public String IDENTIFIANT;
	
	public Proprietes(Coordonnees coordonnees, int appartient, Unites occupe_par, String id) {
		super(coordonnees, occupe_par, id);
		this.appartient = appartient;
		pv = 20;
	}
	
	public Proprietes(Coordonnees coordonnees, int appartient, String id) {
		super(coordonnees, id);
		this.appartient = appartient;
		pv = 20;
	}

	public void affiche(int x, int y) {
		super.affiche(x, y); //affichage de l'unité qui occupe la case, en blanc en bas à droite de la case
		if(pv < 20) { //affichage des pv de la propriété lorsqu'elle subit des dégats, en vert en haut à gauche de la case
			Affichage.afficheTexteDansCase(x, y, String.valueOf(Math.round(pv)), new Color(6,73,0), 0.15, 0.7, new Font("Times", Font.BOLD, 16));
		}
	}

	public void capture(Unites unite) {
		while (occupe_par == unite && pv>0){
			pv -= unite.getPv();
		}
	}
	
	public int getAppartient() {
		return appartient;
	}

	public void setAppartient(int appartient) {
		this.appartient = appartient;
	}
	
	/**
	 * @param joueurActif : le joueur qui souhaite franchir la case
	 * @param unite : l'unité qui souhaite franchir la case
	 * @return le cout de franchissement de la case en fonction de l'unité et du joueur actif
	 */
	@Override
	public int getCout(Unites unite, int joueurActif) {
		//un joueur ne peut pas franchir une case qui est occupée par une unité adverse
		if (occupe_par!=null && occupe_par.getAppartient_a() != joueurActif) {
			cout = Integer.MAX_VALUE; //cout = +infini
		} else {
			cout = 1; //cout de 1 pour toutes les unités
		}
		return cout;
	}

	public void setCout(int cout) {
		this.cout = cout;
	}

	public float getPv() {
		return this.pv;
	}

	public void setPv(float pv) {
		this.pv = pv;
	}
}
