package main;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.IllegalFormatException;
import java.util.Properties;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import logic.hanoi.Plate;
import logic.hanoi.Tower;
import logic.hanoi.TowerSet;

/**
 * This Class handles file operation, such as save, open and export
 * @author Eric
 * @version 1.0
 */

public class MenuFile {
	
	private static String TOWER_AMOUNT_KEY = "towerAmount";
	private static String BITLENGTH_KEY = "bitlength";
	private static String TOWER_KEY = "tower";
	private static String PLATE_VALUE_KEY = "plateValue";
	private static String PLATE_PHYSICAL_WIDTH_KEY = "platePhysicalWidth";
	private static String PLATE_PHYSICAL_HEIGHT_KEY = "platePhysicalHeight";
	private static String AMOUNT_PLATES_ON_TOWER = "platesOnTower";
	
	/**
	 * Saves and Store the necessary Data of the Session in a Properties file,  and returns the state true or false
	 * if it's succeeded or failed.
	 * 
	 * @param towerSet TowerSet that has to be saved
	 * @param amountTower Amount of Towers that has to be saved
	 * @param bitlength The bitlength that has to be saved
	 * @param path The Location, where Data is stored in a file.
	 * @return true if save was successful, false otherwise
	 */
	public static boolean save(TowerSet towerSet, int amountTower, 
			int bitlength, String path) {
		
		Properties props = new Properties();
		
		//Saving of amount of Towers and bitlength
		props.setProperty(TOWER_AMOUNT_KEY, ""+amountTower);
		props.setProperty(BITLENGTH_KEY, ""+bitlength);
		
		int i = 0;
		int k = 0;
		int amountPlates = 0;
		
		//Save Amount of Plates, and their Specifications, for every Tower existing in the TowerSet
		for(Tower t : towerSet.getTowers()) {
			i++;
			amountPlates = t.getPlates().size();
			props.setProperty(TOWER_KEY+i, t.toString());
			for(Plate p : t.getPlates()) {
				k++;
				props.setProperty(PLATE_VALUE_KEY+k, ""+p.getValue());
				props.setProperty(PLATE_PHYSICAL_WIDTH_KEY+k, ""+p.getPhysicalWidth());
				props.setProperty(PLATE_PHYSICAL_HEIGHT_KEY+k, ""+p.getPhysicalHeight());
			}
			props.setProperty(AMOUNT_PLATES_ON_TOWER+i, ""+amountPlates);
			k=1;
		}
		
		//Storing the Serializted Data a file
		try {
			if(!path.endsWith(".properties")) return false;
			props.store(new FileOutputStream(path), "Tower props");
			return true;
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return false;
		} catch (IOException e1) {
			e1.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Opens and Restore Data, needed to run a session, from a Properties file.
	 * Returns the state true or false if it's succeeded or failed.
	 * 
	 * @param file That shall be opened
	 * @param application to restore the Data in
	 * @return the "new" application with the loaded data
	 */
	public static App open(File file, App application) {
		Properties props = new Properties();
		String path = file.getAbsolutePath();
		if(!path.endsWith(".properties")) return null;
		
		//Initialiting, if restoring the data failes, the application will still run.
		int amountTower = application.getAmountTowers();
		int bitlength = application.getTowerHeight();
		TowerSet towerSet = application.getTowerSet();
		
		double plateWidth = 0;
		double plateHeight = 0;
		int plateValue = 0;
		int amountPlates = 0;

		//Loading the Properties
		try {
			props.load(new FileInputStream(path));
			//Restoring the amount of towers and the bitlength
			try {
				amountTower = Integer.parseInt((String) props.get(TOWER_AMOUNT_KEY));
				bitlength = Integer.parseInt((String) props.get(BITLENGTH_KEY));
				
				//For every Tower, their Plates are restored, with it's specific size and values
				Tower[] towers = new Tower[amountTower];
				int keyOffset = 1;
				for(int i=0; i<amountTower; i++) {
					amountPlates = Integer.parseInt((String) props.getProperty(AMOUNT_PLATES_ON_TOWER+keyOffset));
					towers[i] = new Tower(bitlength, false, keyOffset);
					
					for(int k=1; k<=amountPlates; k++) {

						plateWidth = Double.parseDouble((String) 
								props.get(PLATE_PHYSICAL_WIDTH_KEY+k));
								
						plateHeight = Double.parseDouble((String) 
								props.get(PLATE_PHYSICAL_HEIGHT_KEY+k));
						
						plateValue = Integer.parseInt((String) props.get(PLATE_VALUE_KEY+k));
						
						Plate plate = new Plate(plateValue);
						plate.setPhysicalParameters(plateWidth, plateHeight);
						towers[i].getPlates().add(plate);
					}
					//The "new" Tower may be need to recalculated
					towers[i].recalculate();
					keyOffset++;
				}
				//At least, the new TowerSet is created
				towerSet = new TowerSet(towers);
				
			}catch(IllegalFormatException e) {
				e.printStackTrace();
			}	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new App(amountTower, bitlength, towerSet);		
	}	

	
	/**
	 * Exports a snapshot of the Canvas as an PNG Image.
	 * 
	 * @param file The File, in which the Image is stored.
	 * @param can The current canvas, from which a snapshot is taken
	 * @return ture if the export was successful, false otherwise
	 */
	public static boolean export(File file, Canvas can) {
			int width = (int) can.getWidth();
			int height = (int) can.getHeight();
			String formatName = null;
			String fileEnding = file.getName();
			//Checks if the right formatName is passed
			if(fileEnding.endsWith(".png")) formatName = "png";
		
		//The Snapshot is written in a writable image and then rendered, at least written to a .png file.
		try {
			WritableImage writableImage = new WritableImage(width, height);
			can.snapshot(null, writableImage);
			RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
			ImageIO.write(renderedImage, formatName ,file);
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}catch(IllegalArgumentException iae) {
			iae.printStackTrace();
		}
		return true;
	}
	
}
