package logic.hanoi;

/**
 * Represents a set of towers where plates can be moved from one to another.
 * 
 * @author Jonathan
 * @version 1.0
 */
public class TowerSet {
	//saves the towers of this tower set in order
	private Tower[] towers;
	
	/**
	 * Creates a new set of Hanoi towers. The amount of towers default to 3, the tower height to 3 and the
	 * number system to binary (2).
	 */
	public TowerSet() {
		this(3, 3, 2);
	}
	
	/**
	 * Creates a new set of Hanoi towers.
	 * 
	 * @param towerAmount - the amount of towers in this set.
	 * @param towerHeight - the initial height of the tower that is initialized with plates.
	 * @param numberSystem - the number system of these towers. There will be numberSystem - 1 plates of each size in
	 * the system.
	 * 
	 * Mind that there is a total of (numberSystem - 1) * towerHeight plates (plus potential ghost plates). The first
	 * tower will have all plates, all other towers none.
	 */
	public TowerSet(int towerAmount, int towerHeight, int numberSystem) {
		this.towers = new Tower[towerAmount];
		
		for (int i = 0; i < towers.length; i++) {
			this.towers[i] = new Tower(towerHeight, numberSystem, i == 0);
		}
	}
	
	
	/**
	 * Gets the towers of this tower set in order.
	 * 
	 * @return  the towers of this tower set in order.
	 */
	public Tower[] getTowers() {
		return towers;
	}
	
	
	/**
	 * Moves top plates from one tower to another. This will update the towers (including adding ghosts, sorting and
	 * recalculating value and representation).
	 * 
	 * @param from - the tower the plate to move come from. It is assumed the plates intended to move are on the top of
	 * specified tower.
	 * @param to - the tower the plates are moved to.
	 * @param amount - the amount of plates moved.
	 * 
	 * @return true if the move was successful, false if there was a problem (not enough plates on the from-tower,
	 * smaller bottom plate on the to-tower, etc).
	 */
	public boolean movePlates(Tower from, Tower to, int amount) {
		
		
		return false;
	}
}
