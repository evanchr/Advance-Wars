package coordonnees;

import cases.root.Cases;

public class Coordonnees {
	protected int x, y;

	public Coordonnees(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * @param carte : la carte interne du jeu
	 * @param xy : les coordonnées de la case recherchée
	 * @return la case qui à pour coordonnées xy dans la carte du jeu
	 */
	public static Cases getCase(Cases[][] carte, Coordonnees xy) {
		return carte[carte.length-xy.getY()-1][xy.getX()]; //on réalise la conversion entre indices i,j de la carte et x,y des coordonnées
	} 
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
