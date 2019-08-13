package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.CodeSource;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Properties;

import logic.hanoi.TowerSet;

public class MenuFile {
	
	private static String TOWER_AMOUNT_KEY = "towerAmount";
	private static String BITLENGTH_KEY = "bitlength";
	private static String TOWERSET_KEY = "towerSet";
	
	public static boolean save(int amountTower, int bitlength,
			TowerSet towerSet, String path) {
		
		Properties props = new Properties();
		props.setProperty(TOWER_AMOUNT_KEY, ""+amountTower);
		props.setProperty(BITLENGTH_KEY, ""+bitlength);
		props.setProperty(TOWERSET_KEY, ""+towerSet.toString());
		try {
//			String path = getCurrentJarPath()+"props.properties";
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
	

	public boolean saveAs(int amountTower, int bitlength) {
		return true;
	}
	
	public static App open(File file, App application) {
		Properties props = new Properties();
		String path = file.getAbsolutePath();
		if(!path.endsWith(".properties")) return null;
		
		int amountTower = application.getAmountTowers();
		int bitlength = application.getTowerHeight();
//		TowerSet towerSet = application.getTowerSet();
		
		try {
			props.load(new FileInputStream(path));
			try {
				amountTower = Integer.parseInt((String) props.get(TOWER_AMOUNT_KEY));
				bitlength = Integer.parseInt((String) props.get(BITLENGTH_KEY));					
//				towerSet = (TowerSet) props.get(TOWERSET_KEY);
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
	
	public App open(App application) {
		Properties props = new Properties();
		String path = getCurrentJarPath()+"props.properties";
		int amountTower = application.getAmountTowers();
		int bitlength = application.getTowerHeight();
		TowerSet towerSet = application.getTowerSet();
		try {
			props.load(new FileInputStream(path));
			try {
				amountTower = (int) props.get(TOWER_AMOUNT_KEY);
				bitlength = (int) props.get(BITLENGTH_KEY);
				towerSet = (TowerSet) props.get(TOWERSET_KEY);
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
	
}