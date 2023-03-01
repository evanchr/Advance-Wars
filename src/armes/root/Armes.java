package armes.root;

import unites.root.Unites;

public abstract class Armes {
	protected double degatsInfliges;
	
	public Armes() {
	}

	public double getDegatsInfliges() {
		return degatsInfliges;
	}

	public abstract void setDegatsInfliges(float pvAttaquant, Unites cible) ;

}
