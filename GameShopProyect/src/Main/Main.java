package Main;

import javax.swing.SwingUtilities;

import Presentacion.Controller.Controller;
import Presentacion.View.InfoDB;

public class Main {
	
	public static final String applicationName = "GameShop";
	public static String database;
	public static String user;
	public static String password;

	public static void main(String[] args) {
		
		//InfoDB db = new InfoDB();
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new InfoDB();
				//while(!db.getState()) {}
				//Controller.getInstance();
			}
		});
	}

}
