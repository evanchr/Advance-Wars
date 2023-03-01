/** package principal */
package main;

import java.util.ArrayList;
import java.util.List;

import cases.proprietes.Proprietes;
import cases.root.Cases;
import cases.terrains.*;
import coordonnees.Coordonnees;
import librairies.AssociationTouches;
import librairies.StdDraw;
import ressources.Config;
import ressources.ParseurCartes;
import ressources.Affichage;
import unites.*;
import unites.root.Unites;
import unites.types.Pietons;

public class Jeu {
	private int indexJoueurActif, cagnotteJ1, cagnotteJ2, cagnotteActive, deplacement, d=0; //l'indice du joueur actif:  1 = rouge, 2 = bleu ; 'd' correspond à l'action associée à la touche d : unites dispo
	private Cases[][] carteInterne;
	private Unites unite;
	private Coordonnees curseur;
	private List<Cases> chemin;
	private List<Cases> cibles;
	private List<Proprietes> enCapture;
	private boolean flecheActive, finDeTourAutoJ1, finDeTourAutoJ2, finPartie;
	
	// l'indice 0 est reserve au neutre, qui ne joue pas mais peut posseder des proprietes
	public Jeu(String fileName) throws Exception {
		//appel au parseur, qui renvoie un tableau de String 
		String[][] carteString = ParseurCartes.parseCarte(fileName);
		
		// initialise la configuration avec la longueur de la carte
		Config.setDimension(carteString[0].length, carteString.length);
	    
		//initalise la taille de carteInterne en fonction de la taille de la carte
		carteInterne = new Cases[Config.longueurCarteYCases][Config.longueurCarteXCases];
		
		//On parcours carteString et on sépare les terrains et unités et on créé une nouvelle carte remplie avec des cases
		for (int i=0; i<carteString.length; i++) {
			for (int j=0; j < carteString[0].length; j++){
				String [] terrain = carteString[i][j].split(";"); 
				String [] unites = new String[2];
				if (terrain.length>1) {
					unites = terrain[1].split(":");
					switch (unites[0]) {
					case "Bazooka":
						unite = new Bazooka(Integer.valueOf(unites[1]));
						break;
					case "Bombardier":
						unite = new Bombardier(Integer.valueOf(unites[1]));
						break;
					case "DCA":
						unite = new Dca(Integer.valueOf(unites[1]));
						break;
					case "Helico":
						unite = new Helicoptere(Integer.valueOf(unites[1]));
						break;
					case "Infanterie":
						unite = new Infanterie(Integer.valueOf(unites[1]));
						break;
					case "Tank":
						unite = new Tank(Integer.valueOf(unites[1]));
						break;
					default:
						unite = null;
					}
				} 
				
				//on créé les unités et on les places sur les cases correspondantes 
				terrain = terrain[0].split(":");	
				switch (terrain[0]) {
				case "Plaine": 
					carteInterne[i][j] = new Plaine(new Coordonnees(i,j), unite);
					unite = null;
					break;
				case "Foret": 
					carteInterne[i][j] = new Foret(new Coordonnees(i,j), unite);
					unite = null;
					break;
				case "Eau": 
					carteInterne[i][j] = new Eau(new Coordonnees(i,j), unite);
					unite = null;
					break;
				case "Montagne": 
					carteInterne[i][j] = new Montagne(new Coordonnees(i,j), unite);
					unite = null;
					break;
				case "Ville": 
					carteInterne[i][j] = new Ville(new Coordonnees(i,j), Integer.valueOf(terrain[1]));
					break;
				case "QG": 
					carteInterne[i][j] = new QG(new Coordonnees(i,j), Integer.valueOf(terrain[1]));
					break;
				case "Usine": 
					carteInterne[i][j] = new Usine(new Coordonnees(i,j), Integer.valueOf(terrain[1]));
					break;
				default:
					carteInterne[i][j] = null;
				}
			}
		}	
		
		//convertit les coordonnées des cases du tableau
		for (int i=0; i<carteInterne.length; i++) {
			for (int j=0; j<carteInterne[0].length; j++){
				carteInterne[i][j].setCoordonnees(j,Config.longueurCarteYCases-i-1);;
			}
		}
		
		finPartie = false; //initialisation de finPartie à false. Lorsque finPartie = true, la partie est terminée
		curseur = new Coordonnees(0,0); //initialise la position du curseur
		indexJoueurActif = 1; // rouge commence
		
		//initialise les cagnottes des joueurs
		cagnotteJ1 = 0;
		cagnotteActive = cagnotteJ1;
		ajouteCagnotte();
		cagnotteJ2 = 0;
		
		//initialise le nombre restant de déplacement à l'inifni. On ne choisi pas 0 car 0 signifie qu'il n'y a plus de déplacement restant
		deplacement = Integer.MAX_VALUE; 

		chemin = new ArrayList<Cases>(); //initialise la liste des cases pour le traçage de chemins	
		enCapture = new ArrayList<Proprietes>(); //initialise la liste des proprietes en train d'être capturée
	}

	/**
	 * @return false tant que la partie n'est pas finie
	 */
	public boolean isOver() {
		while (!finPartie){
			return false;
		}
		return true;
	}

	/**
	 * Affichage des informations utiles au dessus du plateau de jeu
	 */
	public void afficheStatutJeu() {
		Affichage.videZoneTexte();
		Affichage.afficheTexteDescriptif("Joueur 1 : "+cagnotteJ1+" crédits.",1);
		Affichage.afficheTexteDescriptif("Joueur 2 : "+cagnotteJ2+" crédits.",2);
		Affichage.afficheTexteDescriptif("A : Activer fin de tour auto",4);
		Affichage.afficheTexteDescriptif("T : Finir le tour actuel",5);
		Affichage.afficheTexteDescriptif("D : Placer le curseur sur unité dispo",6);
		}


	public void display() {
		StdDraw.clear();
		
		//on change cagnotteActive en fonction du joueur actif 
		if(indexJoueurActif == 1) {
			cagnotteJ1 = cagnotteActive;
		} else {
			cagnotteJ2 = cagnotteActive;
		}
		afficheStatutJeu();
		
		//affiche la carte en parcourant carteInterne et en réalisant la conversion nécessaire pour orienter la carte dans le bon sens
		for (int i=0; i<carteInterne.length; i++) {
			for (int j=0; j<carteInterne[0].length; j++){
				carteInterne[i][j].affiche(carteInterne[i][j].getCoordonnees().getX(),carteInterne[i][j].getCoordonnees().getY());
			}
		}
		
		drawGameCursor(); //affichage du curseur
		
		/* si une unité qui était en train de réaliser une capture quitte la case ou est détruite, on annule la capture
		 * on remet les pv de la propriété à 20 et on l'enlève de la liste des propriétés en train d'être capturée
		 */
		for (int i=0; i<enCapture.size(); i++) {
			if (enCapture.get(i).getOccupe_par()==null) {
				enCapture.get(i).setPv(20);
				enCapture.remove(i);
			}
		}
		
		//met fin au tour du joueur si la fonction de fin de tour auto est activée pour celui ci
		if(indexJoueurActif == 1 && finDeTourAutoJ1 && unitesDispo().size()==0) {
			finDeTour();
		} else if (indexJoueurActif == 2 && finDeTourAutoJ2 && unitesDispo().size()==0) {
			finDeTour();
		}
		
		StdDraw.show(); //montre à l'ecran les changement demandes
		
	}

	public void initialDisplay() {
		StdDraw.enableDoubleBuffering(); // rend l'affichage plus fluide : tout draw est mis en buffer et ne s'affiche qu'au prochain StdDraw.show();
		display();
	}

	public void drawGameCursor() {
		Affichage.dessineCurseur(curseur.getX(), curseur.getY()); //affiche le curseur en (x,y)
	}

	public void update() {
		AssociationTouches toucheSuivante = AssociationTouches.trouveProchaineEntree(); //cette fonction boucle jusqu'a la prochaine entree de l'utilisateur
		if (toucheSuivante.isHaut()) { 
			if (curseur.getY()<Config.longueurCarteYCases-1 ){
				if (flecheActive && !ajouteCase(Coordonnees.getCase(carteInterne, new Coordonnees(curseur.getX(),curseur.getY()+1)))) {
					
				} else {
					curseur.setY(curseur.getY()+1);
				}
			}
			display();
			}
		if (toucheSuivante.isBas()){ 
			if (curseur.getY()>0){
				if (flecheActive && !ajouteCase(Coordonnees.getCase(carteInterne, new Coordonnees(curseur.getX(),curseur.getY()-1)))) {
					
				} else {
					curseur.setY(curseur.getY()-1);
				}
			}
			display();
		}
		if (toucheSuivante.isGauche()) { 
			if (curseur.getX()>0){
				if (flecheActive && !ajouteCase(Coordonnees.getCase(carteInterne, new Coordonnees(curseur.getX()-1,curseur.getY())))) {
					
				} else {
					curseur.setX(curseur.getX()-1);
				}
			}
			display();
		}
		if 	(toucheSuivante.isDroite()) { 
			if (curseur.getX()<Config.longueurCarteXCases-1){
				if (flecheActive && !ajouteCase(Coordonnees.getCase(carteInterne, new Coordonnees(curseur.getX()+1,curseur.getY())))) {
					
				} else {
					curseur.setX(curseur.getX()+1);
				}
			}
			display();
		}
		if 	(toucheSuivante.isEntree()) { 
			//si la case est occupée par une unité et que le joueur appuie sur entrée, le jeu passe en mode traçage de chemin
			if(!flecheActive) {
				chemin.add(Coordonnees.getCase(carteInterne,curseur));
				unite = chemin.get(0).getOccupe_par();
				if(unite!=null && unite.getEst_disponible() && unite.getAppartient_a()==indexJoueurActif) {
					List<String> options1 = new ArrayList<String>();
					if(enCapture.indexOf(chemin.get(0))!=-1) {
						options1.add("Continuer la capture");
					}
					options1.add("Déplacer");
					String[] options = new String [options1.size()]; 
					for (int i=0;i<options1.size();i++) {
						options[i] = options1.get(i);
					}
					int resultat1 = Affichage.popup("Que voulez vous faire avec cette unité ?", options, true, 0);
					if (resultat1 == options1.indexOf("Déplacer")) {
						//l'utilisateur choisi "deplacer"
						deplacement = unite.getDEPLACEMENT();
						flecheActive = true;
					} else if (resultat1 == options1.indexOf("Continuer la capture")) {
						unite.setEst_disponible(false);
						videChemin();
					} else {
						videChemin();
					}
				} else if (chemin.get(0) instanceof Usine && unite==null && ((Usine) chemin.get(0)).getAppartient()==indexJoueurActif){
					//l'utilisateur a selectionné une usine pour produire une unité
					String[] options = {"Infanterie (1500 crédits)", "Bazooka (3500 crédits)", "Tank (7000 crédits)", "DCA (6000 crédits)", "Hélicoptère (12000 crédits)", "Bombardier (20000 crédits)"};
					switch (Affichage.popup("Quelle unité voulez-vous produire ?", options, true, 0)) {
					case 0: //l'utilisateur choisi Infanterie
						if(Infanterie.PRIX<=cagnotteActive) {
							chemin.get(0).setOccupe_par(new Infanterie(indexJoueurActif));
							chemin.get(0).getOccupe_par().setEst_disponible(false);
							cagnotteActive -= Infanterie.PRIX;
							display();
						} else {
							System.out.println("Vous n'avez pas assez de crédits");
							videChemin();
						}
						break;
					case 1:  //l'utilisateur choisi Bazooka
						if(Bazooka.PRIX<=cagnotteActive) {
							chemin.get(0).setOccupe_par(new Bazooka(indexJoueurActif));
							chemin.get(0).getOccupe_par().setEst_disponible(false);
							cagnotteActive -= Bazooka.PRIX;
							display();
						} else {
							System.out.println("Vous n'avez pas assez de crédits");
							videChemin();
						}
						break;
					case 2:  //l'utilisateur choisi Tank
						if(Tank.PRIX<=cagnotteActive) {
							chemin.get(0).setOccupe_par(new Tank(indexJoueurActif));
							chemin.get(0).getOccupe_par().setEst_disponible(false);
							cagnotteActive -= Tank.PRIX;
							display();
						} else {
							System.out.println("Vous n'avez pas assez de crédits");
							videChemin();
						}
						break;
					case 3:  //l'utilisateur choisi Dca
						if(Dca.PRIX<=cagnotteActive) {
							chemin.get(0).setOccupe_par(new Dca(indexJoueurActif));
							chemin.get(0).getOccupe_par().setEst_disponible(false);
							cagnotteActive -= Dca.PRIX;
							display();
						} else {
							System.out.println("Vous n'avez pas assez de crédits");
							videChemin();
						}
						break;
					case 4:  //l'utilisateur choisi Helicoptere
						if(Helicoptere.PRIX<=cagnotteActive) {
							chemin.get(0).setOccupe_par(new Helicoptere(indexJoueurActif));
							chemin.get(0).getOccupe_par().setEst_disponible(false);
							cagnotteActive -= Helicoptere.PRIX;
							display();
						} else {
							System.out.println("Vous n'avez pas assez de crédits");
							videChemin();
						}
						break;
					case 5:  //l'utilisateur choisi Bombardier
						if(Bombardier.PRIX<=cagnotteActive) {
							chemin.get(0).setOccupe_par(new Bombardier(indexJoueurActif));
							chemin.get(0).getOccupe_par().setEst_disponible(false);
							cagnotteActive -= Bombardier.PRIX;
							display();
						} else {
							System.out.println("Vous n'avez pas assez de crédits");
							videChemin();
						}
						break;
					default :
						videChemin();
					}
				} else {
					videChemin();
				}
				display();
			} else if (flecheActive){ //fleche active correspond au déplacement d'une unité
				chemin.add(Coordonnees.getCase(carteInterne,curseur));
				if(chemin.get(chemin.size()-1).getOccupe_par()==null) {
					List<String> listeActions = new ArrayList<String>();
					String[] options = {"Oui", "Non"};
					if (Affichage.popup("Voulez-vous déplacer votre "+unite.IDENTIFIANT+" ici ?", options, true, 0) == 0) {
						//l'utilisateur à repondu oui
						if(chemin.get(chemin.size()-1) instanceof Proprietes && unite instanceof Pietons) {
							if (((Proprietes) chemin.get(chemin.size()-1)).getAppartient()!=indexJoueurActif) {
								listeActions.add("Capturer");
							}
						}
						List<Cases> cibles = cibles(chemin.get(chemin.size()-1));
						for (int i=0;i<cibles.size();i++) {
							if(unite.degatsInf(cibles.get(i).getOccupe_par())==0) {
								cibles.remove(i);
							}
						}
						if(cibles.size()>0){
							listeActions.add("Attaquer");
						}
						display();
						listeActions.add("Attendre");
						String[] options2 = new String [listeActions.size()]; 
						for (int i=0;i<listeActions.size();i++) {
							options2[i] = listeActions.get(i);
						}
						int resultat = Affichage.popup("Que voulez vous faire sur cette case ?", options2, true, 0);
						if (resultat != -1) {
							//l'utilisateur a répondu
							d=0;
							chemin.get(chemin.size()-1).setOccupe_par(unite);
							chemin.get(0).setOccupe_par(null);
							if (resultat == listeActions.indexOf("Capturer")) {
								//l'utilisateur a choisi Capturer
								enCapture.add((Proprietes) chemin.get(chemin.size()-1));
								videChemin();
							}
							if (resultat == listeActions.indexOf("Attaquer")) {
								//comme expliqué dans le sujet, l'attaque n'est possible qu'à la fin d'un déplacement
								//l'utilisateur choisi attaquer
								display();
								String[] options3 = new String [cibles.size()];
								for (int i=0;i<cibles.size();i++) {
									options3[i] = cibles.get(i).getOccupe_par().IDENTIFIANT;
								}
								int resultatCible = Affichage.popup("Quelle cible voulez-vous attaquer ?", options3, true, 0);
								if (resultatCible != -1) {
									//l'attaquant a choisi sa cible
									display();
									float degatsAttaque = (float) unite.degatsInf(cibles.get(resultatCible).getOccupe_par(),cibles.get(resultatCible));
									cibles.get(resultatCible).getOccupe_par().setPv(cibles.get(resultatCible).getOccupe_par().getPv()-degatsAttaque);
									System.out.println("Vous avez infligé "+Math.floor(degatsAttaque)+" points de dégats à votre adversaire.");
									if(cibles.get(resultatCible).getOccupe_par().getPv()>0) {
										float degatsRiposte = (float) cibles.get(resultatCible).getOccupe_par().degatsInf(unite,chemin.get(chemin.size()-1));
										unite.setPv(unite.getPv()-degatsRiposte);
										System.out.println("Attention ! L'adversaire riposte, il vous a infligé "+Math.floor(degatsRiposte)+" points de dégats.");
									}
								display();
								} 
							}
							unite.setEst_disponible(false);
							unite = null;
							videChemin();
						} 
					}
					display();
				}
			}
		}
		if 	(toucheSuivante.isEchap()) {
			if(flecheActive) {
				videChemin();
			}
		}

		// La touche 't' permet de finir le tour actuel
		if (toucheSuivante.isCaractere('t')) {
			String[] options = {"Oui", "Non"};
			if (Affichage.popup("Finir le tour de joueur "+indexJoueurActif+"?", options, true, 1) == 0) {
				//le choix 0, "Oui", a été selectionné
				finDeTour();
			}
			display();
		}
		
		// La touche 'a' permet d'activer ou désactiver la fin de tour automatique
		if (toucheSuivante.isCaractere('a')) {
			String[] options = {"Oui", "Non (si déjà actif, sélectionner pour désactiver)"};
			if (Affichage.popup("Activer la fin de tour automatique pour joueur "+indexJoueurActif+" ?", options, true, 0) == 0) {
				//le choix 0, "Oui", a été selectionné
				if (indexJoueurActif == 1) {
					finDeTourAutoJ1 = true;
					System.out.println("La fin de tour automatique est activée pour le joueur 1");
				} else {
					finDeTourAutoJ2 = true;
					System.out.println("La fin de tour automatique est activée pour le joueur 2");				}
			} else {
				//le choix 1, "Non", a été selectionné (sert à désactiver la fin de tour auto)
				if (indexJoueurActif == 1) {
					finDeTourAutoJ1 = false;
					System.out.println("La fin de tour automatique est désactivée pour le joueur 1");
					} else {
					finDeTourAutoJ2 = false;
					System.out.println("La fin de tour automatique est désactivée pour le joueur 2");
					}
			}
			display();
		}
		
		//La touche 'd' permet au joueur de placer le curseur sur une de ses troupes encore disponible. 
		//S'il appuie plusieurs fois, le curseur passe d'une unité à l'autre.
		//Si aucune troupe n'est disponibles, le curseur ne bouge pas et un message informe le joueur.
		if (toucheSuivante.isCaractere('d') && !flecheActive) {
			if (unitesDispo().size()>0) {
				curseur.setX(unitesDispo().get(d).getCoordonnees().getX());
				curseur.setY(unitesDispo().get(d).getCoordonnees().getY());
				if(d<unitesDispo().size()-1) {
					d++;
				} else {
					d=0;
				}
			} else {
				System.out.println("Vous n'avez plus d'unités disponibles.");
			}
			display();
		}
	}
	
	/**
	 * Ajoute caseSuivante à la liste des cases composant le chemin si cela est possible
	 * @param caseSuivante est la case dont on veut savoir si elle est franchissable ou non
	 * @return true si caseSuivante peut être franchie par l'unité, false si le déplacement est impossible
	 */
	private boolean ajouteCase(Cases caseSuivante) {
		int cout = caseSuivante.getCout(unite, indexJoueurActif);
		if(chemin.contains(caseSuivante)) { //si case suivante a déjà été parcourue, on supprime toutes les cases qu'on a parcourues depuis
			int i=chemin.indexOf(caseSuivante)+1;
			while (chemin.get(chemin.size()-1) != caseSuivante){
				deplacement += chemin.get(i).getCout(unite, indexJoueurActif);
				chemin.remove(i);
			}
			return true;
		} else if (deplacement == 0){
			System.out.println("Limite de déplacement atteinte.");
			return false;
		} else if (deplacement>=cout) {
			chemin.add(caseSuivante);
			deplacement -= cout;
			return true;
		} else {
			System.out.println("Case infranchissable !");
			return false;
		}
	}

	/**
	 * Vide la liste de cases correspondant au chemin en cours de traçage
	 */
	public void videChemin() {
		flecheActive = false;
		chemin.clear();
		deplacement = Integer.MAX_VALUE;
		display();
	}
	
	/**
	 * Cette méthode est appelée au début de chaque tour pour mettre à jour la cagnotte du joueur actif
	 * Un joueur gagne 1000 pièces par propriétés lui appartenant.
	 */
	public void ajouteCagnotte() {
		for (int i=0; i<carteInterne.length; i++) {
			for (int j=0; j<carteInterne[0].length; j++) {
				if(carteInterne[i][j] instanceof Proprietes) {
					if(((Proprietes) carteInterne[i][j]).getAppartient()==indexJoueurActif) {
						cagnotteActive+=1000;
					}
				}
			}
		}
	}	 
	
	/**
	 * @return La liste des unités encore disponibles pour le joueur actif
	 */
	public List<Cases> unitesDispo() {
		List<Cases> dispos = new ArrayList<Cases>();
		for (int i=0; i<carteInterne.length; i++) {
			for (int j=0; j<carteInterne[0].length; j++) {
				Unites unite = carteInterne[i][j].getOccupe_par();
				if(unite !=null && unite.getAppartient_a()==indexJoueurActif && unite.getEst_disponible()) {
					dispos.add(carteInterne[i][j]);
				}
			}
		}
		return dispos;
	}
	
	/**
	 * @param caseActuelle case occupée par l'unité qui attaque
	 * @return la liste des cibles atteignables par l'unité qui se situe sur caseActuelle
	 */
	public List<Cases> cibles(Cases caseActuelle) {
		cibles = new ArrayList<Cases>();
		Cases caseGauche = null;
		Cases caseHaut = null;
		Cases caseDroite = null;
		Cases caseBas = null;
		if(caseActuelle.getXCase()>0) {
			caseGauche = Coordonnees.getCase(carteInterne, new Coordonnees(caseActuelle.getXCase()-1,caseActuelle.getYCase()));
		}
		if(caseActuelle.getYCase()<Config.longueurCarteYCases-1) {
			caseHaut = Coordonnees.getCase(carteInterne, new Coordonnees(caseActuelle.getXCase(),caseActuelle.getYCase()+1));
		}
		if(caseActuelle.getXCase()<Config.longueurCarteXCases-1) {
			caseDroite = Coordonnees.getCase(carteInterne, new Coordonnees(caseActuelle.getXCase()+1,caseActuelle.getYCase()));
		}
		if(caseActuelle.getYCase()>0) {
			caseBas = Coordonnees.getCase(carteInterne, new Coordonnees(caseActuelle.getXCase(),caseActuelle.getYCase()-1));
		}
		if(caseGauche!=null && caseGauche.getOccupe_par()!=null && caseGauche.getOccupe_par().getAppartient_a()!=indexJoueurActif) {
			cibles.add(caseGauche);
		}
		if(caseHaut!=null && caseHaut.getOccupe_par()!=null && caseHaut.getOccupe_par().getAppartient_a()!=indexJoueurActif) {
			cibles.add(caseHaut);
		}
		if(caseDroite!=null && caseDroite.getOccupe_par()!=null && caseDroite.getOccupe_par().getAppartient_a()!=indexJoueurActif) {
			cibles.add(caseDroite);
		}
		if(caseBas!=null && caseBas.getOccupe_par()!=null && caseBas.getOccupe_par().getAppartient_a()!=indexJoueurActif) {
			cibles.add(caseBas);
		}
		return cibles;
	}
	
	/**
	 * enlève les points de vie de chaque propriete qui est en train d'être capturée 
	 * si la propriété n'a plus de pv alors elle appartient au joueur qui la capture et retrouve l'entierté de ses pv
	 */
	public void baissePvProprietes() {
		for (int i=0; i<enCapture.size(); i++) {
			if (enCapture.get(i).getAppartient()!=indexJoueurActif && indexJoueurActif==enCapture.get(i).getOccupe_par().getAppartient_a()) {
				enCapture.get(i).setPv(enCapture.get(i).getPv()-enCapture.get(i).getOccupe_par().getPv());
			}
			if (enCapture.get(i).getPv()<=0) {
				if (enCapture.get(i) instanceof QG) {
					finPartie = true;
					display();
					String [] fin = {"OK"};
					Affichage.popup("Félicitations ! Le joueur "+indexJoueurActif+" remporte la partie !", fin, true, 0);
				}
				enCapture.get(i).setAppartient(enCapture.get(i).getOccupe_par().getAppartient_a());
				enCapture.get(i).setPv(20);
				enCapture.remove(i);
			}
		}
	}
	
	/**
	 * Cette méthode met fin au tour du joueur actif et commence le tour du joueur suivant
	 */
	public void finDeTour() {
		//on diminue les pv de toutes les propriétés en cours de capture
		baissePvProprietes();
		
		if (indexJoueurActif == 1) {
			indexJoueurActif = 2;
			cagnotteActive = cagnotteJ2;
			ajouteCagnotte();
		} else {
			indexJoueurActif = 1;
			cagnotteActive = cagnotteJ1;
			ajouteCagnotte();
		}

		//Si l'utilisateur décide d'arreter son tour pendant qu'il trace un deplacement, on annule le deplacement
		if(flecheActive) {
			videChemin();
		}
		
		//A la fin du tour, toutes les troupes redeviennent disponibles
		for (int i=0; i<carteInterne.length; i++) {
			for (int j=0; j<carteInterne[0].length; j++) {
				if(carteInterne[i][j].getOccupe_par()!=null) {
					carteInterne[i][j].getOccupe_par().setEst_disponible(true);
				}
			}		
		}
		System.out.println("FIN DE TOUR");
	}
}