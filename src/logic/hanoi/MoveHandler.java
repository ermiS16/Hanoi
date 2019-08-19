package logic.hanoi;

import java.util.ArrayList;
import java.util.List;

import gui.Hitbox;

public class MoveHandler {
	
	private boolean plateSelected;
	private List<Plate> platesToMove;
	private int amountPlatesHit;
	private Tower platesFrom;
	
	public MoveHandler() {
		this.plateSelected = false;
		this.platesToMove = new ArrayList<>();
		this.amountPlatesHit = 0;
		this.platesFrom = null;
	}
	
	public void setPlateSelected(boolean select) {
		this.plateSelected = select;
	}
	public boolean plateSelected() {
		return this.plateSelected;
	}
	
	public void addPlateToMove(Plate p) {
		this.platesToMove.add(p);
	}

	public List<Plate> getPlateToMove() {
		return this.platesToMove;
	}

	public void removePlateToMove(Plate p) {
		if (this.platesToMove.contains(p)) this.platesToMove.remove(p);
	}

	public void removePlateToMoveAll(List<Plate> plates) {
		this.platesToMove.removeAll(plates);
	}

	public int getAmountPlatesHit() {
		return this.amountPlatesHit;
	}

	public void increaseAmountPlatesHit() {
		this.amountPlatesHit++;
	}

	public void decreaseAmountPlatesHit() {
		if (this.amountPlatesHit > 0) this.amountPlatesHit--;
	}

	public void resetAmountPlatesHit() {
		this.amountPlatesHit = 0;
	}

	public void platesFrom(Tower t) {
		this.platesFrom = t;
	}
	
	public Tower getPlatesFrom() {
		return this.platesFrom;
	}
	
	public void resetPlatesFrom() {
		this.platesFrom = null;
	}
	
	/**
	 * Move Plates from one Tower to another one
	 * Return true if it was successful, false if not.
	 * 
	 * @param from The Tower from where Plated must to be moved
	 * @param to The Tower where Plates must be moved
	 * @param plateMoveList List of Plates that shall be moved
	 * @return true if plates are moved successful, false otherwise
	 */
	private boolean movePlates(Tower from, Tower to, List<Plate> plateMoveList) {
		boolean moved = false;
		int platesAmount = platesToMove.size();
		if (to == from) {
			return false;
		}

		int lsba = from.getLSBAValue();

		// if the smallest plate on the receiving tower is smaller than this lsba, this
		// can't be done
		if (lsba > to.getLSBAValue()) {
			return false;
		}

		List<Plate> moveList = new ArrayList<>();
		//Checks if List of Plates are Movable as a whole
		if (movable(from, getPlateToMove())) {
			
			//Every Plates is removed from the tower that owns them
			for (Plate p : getPlateToMove()) {
				from.removeOfValue(platesAmount, from.getLSBAValue(), false);
				p.getHitbox().setHit(false);
				moveList.add(p);
			}

			// add plates to receiving tower
			to.addPlates(lsba, platesAmount, moveList);

			// Remove all Plates that has moved
			removePlateToMoveAll(getPlateToMove());

			//Recalculate both towers
			from.recalculate();
			to.recalculate();

			moved = true;
		
		} else {
			moved = false;
		}
		return moved;
	}
	
	
	
	/**
	 * Checks if a List of Plates is Moable as a whole from a certain Tower
	 * 
	 * @param tower     whose Plates shall be checked
	 * @param plateList List of Plates, that shall be moved.
	 * @return true if Plates can be moved as a whole, false otherwise.
	 */
	private static boolean movable(Tower tower, List<Plate> plateList) {
		PlateComperator sortingOrder = new PlateComperator();
		plateList.sort(sortingOrder.PLATE_SORTING_ORDER);

		// Value of Plate on the Top.
		int lsba = tower.getLSBAValue();
		boolean movable = true;

		// Special Cases
		if (tower.getPlates().size() < plateList.size())
			return false;
		else if (tower.getPlates().size() < 1)
			return false;
		else if (tower.getPlates().size() == 1)
			return true;

		// Iterates from the Plate on the Top down, to check if following plates
		// are Contained in the List of Plates, that has to move.
		else {
			for (int i = 0, k = tower.getPlates().size() - 1; i < plateList.size(); i++, k--) {
				if (plateList.get(i) != tower.getPlates().get(k)) {
					movable = false;
				}
			}
		}
		return movable;
	}
	
	
	public void clickLogicEasy(Tower[] towerSet, double clickX, double clickY) {
		
	}
	
	/**
	 * Simple Logic for movement of Plates. More than one Plate is movable.
	 * @param towerSet
	 * @param clickX
	 * @param clickY
	 */
	public void clickLogicMiddle(Tower[] towerSet, double clickX, double clickY) {
		boolean moved = false;
		Tower from = null;
		
		for (Tower t : towerSet) {
			if (!t.getPlates().isEmpty()) {
				
				//Plates can only be "clicked" if their are on a Tower, where another one is already clicked or
				//no Tower is selected at that moment.
				if (t == getPlatesFrom() || getPlatesFrom() == null) {
					for (Plate p : t.getPlates()) {
						// Checks if Hitbox of a Plate is hit
						if (hitMatch(p.getHitbox(), clickX, clickY)) {
							// Checks if Plates already Hit
							if (!p.getHitbox().isHit()) {
								// Saves Tower, from which Plate shall be moved.
								platesFrom(t);
								// Set Hit for Hitbox on true
								p.getHitbox().setHit(true);
								increaseAmountPlatesHit();
								addPlateToMove(p);
							} else {
								p.getHitbox().setHit(false);
								decreaseAmountPlatesHit();
								removePlateToMove(p);
								if (getPlateToMove().isEmpty()) platesFrom(null);
							} // else
						} // if
					} // fpr
				} // if
			} // if
			
			
			//If a clicked Tower wasn't hit before, it will be the destination for the Plates, when
			//Plates are selected.
			if (hitMatch(t.getHitbox(), clickX, clickY)) {
				if (!t.getHitbox().isHit() && getAmountPlatesHit() != 0) {
					t.getHitbox().setHit(true);
					from = getPlatesFrom();
					
					//Plates are moved from a Tower to another (t)
					try {
						moved = movePlates(from, t, getPlateToMove());
						if (moved) {
							
							//Reset the hitbox of the both involved towers
							//and reset the Plates that must be moved to zero.
							resetAmountPlatesHit();
							t.getHitbox().setHit(false);
							from.getHitbox().setHit(false);
							resetPlatesFrom();
						} else {
							
							//When the Plates are not moved, then t is no longer a available destination.
							t.getHitbox().setHit(false);
						}
					} catch (NullPointerException exception) {
						exception.printStackTrace();
					}
				//Tower is not a destination, when no plates are selected, or the tower is already hit,
				//because it would be the source of plates then.
				} else	t.getHitbox().setHit(false);
			}
		}
	}

	
	/**
	 * Hard Logic for the Movement of Plate. Only the Plate on the Top is movable.
	 * @param towerSet Towerset with all Towers of the Session
	 * @param clickX Point on x-Axis
	 * @param clickY Point on y-Axis
	 */
	public void clickLogicHard(Tower[] towerSet, double clickX, double clickY) {
		boolean moved = false;
		Tower from = null;
		
		for(Tower t : towerSet) {
			if(!t.getPlates().isEmpty() && !plateSelected()) {
				for(Plate p : t.getPlates()) {
					if(hitMatch(p.getHitbox(), clickX, clickY)) {
						if(!p.getHitbox().isHit()) {
							platesFrom(t);
							p.getHitbox().setHit(true);
							setPlateSelected(true);
							addPlateToMove(p);
						}else {
							p.getHitbox().setHit(false);
							removePlateToMove(p);
							setPlateSelected(false);
							if(getPlateToMove().isEmpty()) from=null;
						} // else
					} // if
				} // for
			} //if
			
			if(hitMatch(t.getHitbox(), clickX, clickY)) {
				if(!t.getHitbox().isHit() && plateSelected()) {
					t.getHitbox().setHit(true);
					from = getPlatesFrom();
					try {
						moved = movePlates(from,t, getPlateToMove());
						if(moved) {
							setPlateSelected(false);
							t.getHitbox().setHit(false);
							from.getHitbox().setHit(false);
							resetPlatesFrom();
						}else {
							t.getHitbox().setHit(false);
							setPlateSelected(false);
						}
					}catch(NullPointerException npe) {
						npe.printStackTrace();
					}
				}else {
					t.getHitbox().setHit(false);
				}
			}
		} // for
	}
	
	/**
	 * Checks if a Hitbox is Hit by the Cursor.
	 * 
	 * @param hitbox The Hitbox to be checked
	 * @param x      Coordinate on X-Axis
	 * @param y      Coordinate on y-Axis
	 * @return true if Hit, false otherwise
	 */
	private boolean hitMatch(Hitbox hitbox, double x, double y) {
		if (hitbox.contains(x, y))
			return true;
		else
			return false;
	}
	
}
