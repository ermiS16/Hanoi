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
	 * Creates a new set of Hanoi towers. The amount of towers default to three, the tower height to three and the
	 * dimension to one.
	 */
	public TowerSet() {
		this(3, 3, 1);
	}
	
	/**
	 * Creates a new set of Hanoi towers.
	 * 
	 * @param towerAmount - the amount of towers in this set.
	 * @param towerHeight - the initial height of the tower that is initialized with plates.
	 * @param numberSystem - the number system of these towers. There will be numberSystem - 1 plates of each size in
	 * the system.
	 * 
	 * Mind that there is a totl of towerHeight * dimension plates (plus potential ghost plates). The first tower will
	 * have all plates, all other towers none.
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
}
