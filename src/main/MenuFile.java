package main;

import com.sun.imageio.plugins.jpeg.JPEGImageWriter;
import com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations.SecretKeyResolver;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.CodeSource;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import logic.hanoi.Plate;
import logic.hanoi.Tower;
import logic.hanoi.TowerSet;

public class MenuFile {
	
	private static String TOWER_AMOUNT_KEY = "towerAmount";
	private static String BITLENGTH_KEY = "bitlength";
	private static String TOWERSET_KEY = "towerSet";
	private static String TOWER_KEY = "tower";
	private static String PLATE_VALUE_KEY = "plateValue";
	private static String PLATE_KEY = "plate";
	private static String PLATE_PHYSICAL_WIDTH_KEY = "platePhysicalWidth";
	private static String PLATE_PHYSICAL_HEIGHT_KEY = "platePhysicalHeight";
	private static String AMOUNT_PLATES_ON_TOWER = "platesOnTower";
	
	public static boolean save(TowerSet towerSet, int amountTower, 
			int bitlength, String path) {
		
		Properties props = new Properties();
		props.setProperty(TOWER_AMOUNT_KEY, ""+amountTower);
		props.setProperty(BITLENGTH_KEY, ""+bitlength);
		int i = 0;
		int k = 0;
		int amountPlates = 0;
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
//		props.setProperty(TOWERSET_KEY, towerSet.toString());
		try {
//			String path = getCurrentJarPath()+"props.properties";
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
	
	private String getCurrentJarPath() {
		String path = getJarPath();
		if(path.endsWith(".jar")) {
			return path.substring(0, path.lastIndexOf("/"));
		}
		return path;
	}
	
	private String getJarPath() {
		final CodeSource source = this.getClass().getProtectionDomain().getCodeSource();
		if(source != null) {
			return source.getLocation().getPath().replaceAll("%20", " ");
		}
		return null;
	}
	
	public static App open(File file, App application) {
		Properties props = new Properties();
		String path = file.getAbsolutePath();
		if(!path.endsWith(".properties")) return null;
		
		int amountTower = application.getAmountTowers();
		int bitlength = application.getTowerHeight();
		TowerSet towerSet = application.getTowerSet();
		Tower[] towers = new Tower[amountTower];
		
		double plateWidth = 0;
		double plateHeight = 0;
		int plateValue = 0;
		int amountPlates = 0;
		String pw;
		String ph;
		String pv;
		String ap;
		try {
			props.load(new FileInputStream(path));
			try {
				amountTower = Integer.parseInt((String) props.get(TOWER_AMOUNT_KEY));
				bitlength = Integer.parseInt((String) props.get(BITLENGTH_KEY));
				
				int keyOffset = 1;
				for(int i=0; i<amountTower; i++) {
					ap = props.getProperty((String) AMOUNT_PLATES_ON_TOWER+keyOffset);
					amountPlates = Integer.parseInt((String) props.getProperty(AMOUNT_PLATES_ON_TOWER+keyOffset));
					towers[i] = new Tower(bitlength, false, keyOffset);
					
					for(int k=1; k<=amountPlates; k++) {

						pw = props.getProperty(PLATE_PHYSICAL_WIDTH_KEY+k);
						plateWidth = Double.parseDouble((String) 
								props.get(PLATE_PHYSICAL_WIDTH_KEY+k));
								
						ph = props.getProperty(PLATE_PHYSICAL_HEIGHT_KEY+k);
						plateHeight = Double.parseDouble((String) 
								props.get(PLATE_PHYSICAL_HEIGHT_KEY+k));
						
						pv = props.getProperty(PLATE_VALUE_KEY+k);
						plateValue = Integer.parseInt((String) props.get(PLATE_VALUE_KEY+k));
						
						Plate plate = new Plate(plateValue);
						plate.setPhysicalParameters(plateWidth, plateHeight);
						towers[i].getPlates().add(plate);
					}
					towers[i].recalculate();
					keyOffset++;
				}
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

	public static boolean export(File file, Canvas can) {
			int width = (int) can.getWidth();
			int height = (int) can.getHeight();
			String formatName = null;
			String fileEnding = file.getName();
			if(fileEnding.endsWith(".png")) formatName = "png";
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
