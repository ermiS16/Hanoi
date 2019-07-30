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
	
	
	//Default Values for Construction of a Tower Set
	private final static int DEFAULT_TOWER_AMOUNT = 3;
	private final static int DEFAULT_TOWER_LOCIGAL_HEIGHT = 3;
	private final static int DEFAULT_NUMBER_SYSTEM = 2;
	
	/**
	 * Creates a new set of Hanoi towers. The amount of towers default to 3, the tower height to 3 and the
	 * number system to binary (2).
	 */
	public TowerSet() {
		this(DEFAULT_TOWER_AMOUNT, DEFAULT_TOWER_LOCIGAL_HEIGHT, DEFAULT_NUMBER_SYSTEM);
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
	public TowerSet(int towerAmount, int towerLogicalHeight, int numberSystem) {
		this.towers = new Tower[towerAmount];
		
		for (int i = 0; i < towers.length; i++) {
			this.towers[i] = new Tower(towerLogicalHeight, numberSystem, i == 0);
		}
	}
	
	public static int getDefaultNumberSystem() {
		return DEFAULT_NUMBER_SYSTEM;
	}
	public static int getDefaultHeight() {
		return DEFAULT_TOWER_LOCIGAL_HEIGHT;
	}
	public static int getDefaultAmount() {
		return DEFAULT_TOWER_AMOUNT;
	}
	public int getTowerSetLength() {
		return towers.length;
	}
	
	/**
	 * Gets the towers of this tower set in order.
	 * 
	 * @return  the towers of this tower set in order.
	 */
	public Tower[] getTowers() {
		return towers;
	}
	
	public Tower getTower(int index) {
		return this.towers[index];
	}
}
