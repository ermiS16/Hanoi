package gui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.FillRule;
import logic.hanoi.Plate;
import logic.hanoi.Tower;

public class CanvasUtilitys {
		private static double MARGIN_BOTTOM = 200;
	/**
	 * Draws all Towers from a TowerSet with its containing Plates
	 * 
	 * @param gc       GraphicContext of the Canvas on which the Towers are drawn.
	 * @param towerSet Set of Towers, which will be drawn.
	 */
	public static void drawTower(GraphicsContext gc, Tower[] towerSet, boolean showTowerValue) {

		int setLength = towerSet.length;
		int representationTextIndex = 2;
		int valueTextIndex = 3;
		
		// Scalingfactor for x-axis.
		final double scalingFactorX = setLength + 1;

		// Distance between Towers
		final double groundOffsetX = gc.getCanvas().getWidth() * Math.pow(scalingFactorX, -1);

		// Gap between Tower and Text
		double textGap = 30;

		// To Center the Text
		double textCenterOffset = towerSet[0].getPhysicalWidth() / 2;

		// Start for the drawing of the Towers
//		double newY = towerSet[0].getPhysicalHeight();
		double newY = 0;
		newY = gc.getCanvas().getHeight() - MARGIN_BOTTOM;
//		double newY = gc.getCanvas().getHeight()/SCALING_FACTOR_TOWER_Y;
//		double newY = windowHeight/SCALING_FACTOR_TOWER_Y;
		double newX = groundOffsetX;

		int towerIndex = 1;

		// Draw all Towers
		for (Tower t : towerSet) {
			gc.setFill(Color.BROWN);

			
			// Save Position for Tower
			t.getPosition().setPosition(newX, newY);

			// Draw new Tower
			if (t.getHitbox().isHit()) gc.setFill(Color.YELLOW);
			gc.setLineWidth(t.getPhysicalWidth());
//			gc.fillRect(newX, newY, t.getPhysicalWidth(), t.getPhysicalHeight());
			gc.fillRect(newX, newY-t.getPhysicalHeight(), t.getPhysicalWidth(), t.getPhysicalHeight());
			
			// Hitbox for ClickEvents
			double hitBoxStartX = newX;
			double hitBoxEndX = newX + t.getPhysicalWidth();
			double hitBoxStartY = newY - t.getPhysicalHeight();
			double hitBoxEndY = newY;
			t.getHitbox().setHitbox(hitBoxStartX, hitBoxEndX, hitBoxStartY, hitBoxEndY);
//			System.out.println("Tower: " + towerIndex + "\nMin X: " + t.getHitbox().getMinX()
//					+ ", Max X: " + t.getHitbox().getMaxX() + "\nMin Y: " + t.getHitbox().getMinY()
//					+ ", Max Y: "+ t.getHitbox().getMaxY());
				
			
			// Name of Tower
			gc.setLineWidth(0.5);
			double towerTexX = newX - textCenterOffset;
			double towerTextY = newY + textGap;
			gc.strokeText("Tower " + towerIndex,towerTexX, towerTextY);

			// Represantation of Tower in Bits
			gc.strokeText(t.getRepresentation(), newX - textCenterOffset, newY  + (textGap * representationTextIndex));

			// Represantation of Tower as Value
			if(showTowerValue) {
				double valueTextX = newX - textCenterOffset;
				double valueTextY = newY + (textGap * valueTextIndex);
				gc.strokeText(t.getValue() + "", valueTextX , valueTextY);
			}

			

			// New Position for the next Tower
			newX += groundOffsetX;
//			newY = t.getPhysicalHeight();

			towerIndex++;
		}
	}

	
	/**
	 * Draws the Plates from a specific Tower on the Canvas
	 * 
	 * @param gc    The GraphicContext of the Canvas
	 * @param tower Tower wich Plates shall be Drawn
	 */
	public static void drawPlates(GraphicsContext gc, Tower[] towerSet, boolean showPlateValue) {
		List<Plate> plateList = null;
		
		double valueTextX = 0;
		double valueTextY = 0;
		int valueTextOffsetY = 10;
		double hitBoxStartX = 0;
		double hitboxEndX = 0;
		double hitboxStartY = 0;
		double hitboxEndY = 0;
		
		for(Tower tower : towerSet) {
			plateList = tower.getPlates();
			// Plates must exist
			if (!plateList.isEmpty()) {
				// Plate Attributes
				double plateGap = 2;

				// Initializing Startposition
				double newX = 0;
//				double newY = tower.getPosition().getY() + tower.getPhysicalHeight();
				double newY = tower.getPosition().getY();
				int plateIndex = 1;
				
				for (Plate p : plateList) {
					if (p.getHitbox().isHit()) gc.setFill(Color.YELLOW);
					else gc.setFill(Color.RED);

					// First, get the middle of the Tower on the X-Axis, then subtract the half of the physical width of the plate.
					newX = tower.getPosition().getX() + (tower.getPhysicalWidth() / 2) - (p.getPhysicalWidth() / 2);
					
					// Draw the Plate
					gc.setLineWidth(p.getPhysicalHeight());
					newY -= p.getPhysicalHeight();
					gc.fillRect(newX, newY, p.getPhysicalWidth(), p.getPhysicalHeight());

					// Set the Hitbox for the Plate
					hitBoxStartX = newX;
					hitboxEndX =  newX + p.getPhysicalWidth();
					hitboxStartY = newY;
					hitboxEndY = newY + p.getPhysicalHeight();
					p.getHitbox().setHitbox(hitBoxStartX, hitboxEndX, hitboxStartY, hitboxEndY);
					p.getHitbox().setPointMatrix();
					
//					System.out.println("Plate: " + plateIndex + "\nMin X: " + p.getHitbox().getMinX()
//							+ ", Max X: " + p.getHitbox().getMaxX() + "\nMin Y: " + p.getHitbox().getMinY()
//							+ ", Max Y: "+ p.getHitbox().getMaxY());
					
					//Show Value "on" Plate
					if(showPlateValue) {
						gc.setLineWidth(0.5);
						gc.setFill(Color.BLACK);
						valueTextX = newX + (p.getPhysicalWidth()/2) ;
						valueTextY = newY+valueTextOffsetY;
						gc.strokeText(""+p.getValue(), valueTextX, valueTextY);
					}
				
					// Setting the new Startposition
					newY -= + plateGap;
					plateIndex++;
				}
			}
		}
	}
	
	public static void drawCountdown(GraphicsContext gc, String time){
		gc.clearRect(0, 0, gc.getCanvas().getWidth(), 60);
		gc.strokeLine(550, 60, 700, 60);
		gc.strokeLine(550, 0, 550, 60);
		gc.strokeLine(700, 0, 700, 60);
		gc.strokeText(time, 600, 40);
	}
	
	public static void drawMouseSelect(GraphicsContext gc, double xStart, double xEnd, double yStart, double yEnd) {
		gc.setFill(Color.BLACK);
		gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
		gc.strokeLine(xStart, yStart, xEnd, yStart);
		gc.strokeLine(xEnd, yStart, xEnd, yEnd);
		gc.strokeLine(xStart, yStart, xStart, yEnd);
		gc.strokeLine(xStart, yEnd, xEnd, yEnd);
		
		gc.setLineWidth(1);
	}
}
