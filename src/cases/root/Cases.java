package cases.root;

import coordonnees.Coordonnees;
import unites.root.Unites;

public abstract class Cases {
	protected Coordonnees coordonnees;
	protected Unites occupe_par;
	protected int cout;
	public String IDENTIFIANT;
	
	public Cases(Coordonnees coordonnees,Unites occupe_par, String id) {
		this.coordonnees = coordonnees;
		this.occupe_par = occupe_par;
		IDENTIFIANT = id;
	}

	public Cases(Coordonnees coordonnees, String id) {
		this.coordonnees = coordonnees;
		IDENTIFIANT = id;
	}

	/**
	 * Affichage de l'unité qui occupe la case si c'est le cas
	 * @param x ordonnée de la case 
	 * @param y abscisse de la case
	 */
	public void affiche(int x, int y) {
		if (this.occupe_par!=null) {
			if(this.occupe_par.getPv()>0) {
				this.occupe_par.affiche(x, y);
			} else {
				this.occupe_par = null;
			}
		}
	}

	public int getXCase() {
		return coordonnees.getX(); //renvoie l'ordonnée de la case
	}
	
	public void setXCase(int x) {
		coordonnees.setX(x); //change l'ordonnée de la case
	}
	
	public int getYCase() {
		return coordonnees.getY(); //renvoie l'abscisse de la case
	}
	
	public void setYCase(int y) {
		coordonnees.setX(y); //change l'abscisse de la case
	}

	public Coordonnees getCoordonnees() {
		return coordonnees; //renvoie les coordonnées de la case
	}
	
	public void setCoordonnees(int x, int y) { //change les coordonnées de la case
		coordonnees.setX(x);
		coordonnees.setY(y);
	}

	public Unites getOccupe_par() {
		return occupe_par; //renvoie l'unité qui occupe la case
	}

	public void setOccupe_par(Unites occupe_par) {
		this.occupe_par = occupe_par; //change l'unité qui occupe la case
	}

	public abstract int getCout(Unites unite, int joueurActif); //renvoie le coût de la case

	public void setCout(int cout) {
		this.cout = cout; //change le coût de la case
	}

}