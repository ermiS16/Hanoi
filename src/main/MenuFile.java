package main;

import com.sun.imageio.plugins.jpeg.JPEGImageWriter;
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
import logic.hanoi.TowerSet;

public class MenuFile {
	
	private static String TOWER_AMOUNT_KEY = "towerAmount";
	private static String BITLENGTH_KEY = "bitlength";
	
	public static boolean save(int amountTower, int bitlength, String path) {
		
		Properties props = new Properties();
		props.setProperty(TOWER_AMOUNT_KEY, ""+amountTower);
		props.setProperty(BITLENGTH_KEY, ""+bitlength);
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
		
		try {
			props.load(new FileInputStream(path));
			try {
				amountTower = Integer.parseInt((String) props.get(TOWER_AMOUNT_KEY));
				bitlength = Integer.parseInt((String) props.get(BITLENGTH_KEY));					
			}catch(IllegalFormatException e) {
				e.printStackTrace();
			}	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new App(amountTower, bitlength);		
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
