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
	 * Creates a new set of Hanoi towers. The amount of towers default to three.
	 */
	public TowerSet() {
		this(3);
	}
	
	/**
	 * Creates a new set of Hanoi towers.
	 * 
	 * @param towerAmount - the amount of towers in this set.
	 */
	public TowerSet(int towerAmount) {
		this.towers = new Tower[towerAmount];
		
		for (int i = 0; i < towers.length; i++) {
			this.towers[i] = new Tower();
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
