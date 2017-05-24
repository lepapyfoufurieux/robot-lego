package be;

public interface RobotInterface {
	/**
	 * Le robot doit avancer d'une (et d'une seule) case en marche avant.
	 * Il est conseill� que le robot se recale automatiquement avec la bande blanche pendant le d�placement.
	 */
	public void avanceUneCase();

	/**
	 * Le robot doit tourner d'un quart de tour vers la gauche.
	 */
	public void tourneAGauche();
	
	/**
	 * Le robot doit tourner d'un quart de tour vers la droite.
	 */
	public void tourneADroite();
	
	/**
	 * Le robot doit faire un demi-tour sur lui-m�me.
	 */
	public void tourneDemiTour();
	
	/**
	 * Le robot doit regarder s'il y a un mur devant lui.
	 * @return true s'il y a un mur, false sinon
	 */
	public boolean regardeToutDroit();
	
	/**
	 * Le robot doit regarder s'il y a un mur � sa gauche.
	 * � la fin de cette m�thode, le robot doit �tre au m�me endroit (et m�me orientation) qu'� son d�but.
	 * @return true s'il y a un mur, false sinon
	 */
	public boolean regardeAGauche();
	
	/**
	 * Le robot doit regarder s'il y a un mur � sa droite.
	 * � la fin de cette m�thode, le robot doit �tre au m�me endroit (et m�me orientation) qu'� son d�but.
	 * @return true s'il y a un mur, false sinon
	 */
	public boolean regardeADroite();
}
