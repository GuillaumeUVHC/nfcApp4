package nfcApp4.entites;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Processnfc {
	
	//static ProcessBuilder builder = new ProcessBuilder("C:\\Users\\croyg\\Desktop\\test.bat");
	//static ProcessBuilder builder = new ProcessBuilder("/home/pi/Documents/sw3693/_build/NfcrdlibEx1_BasicDiscoveryLoop/NfcrdlibEx1_BasicDiscoveryLoop");
	static ProcessBuilder builder = new ProcessBuilder("C:\\Users\\croyg\\Desktop\\test.bat");
	static Process process = null;
	static BufferedReader reader = null;
	
	/*public Processnfc() {
		builder = new ProcessBuilder("C:\\Users\\croyg\\Desktop\\test.bat");
		builder.redirectErrorStream(true);
	}*/
	
	static public int start() {
		
		if (process == null) {
		
			builder.redirectErrorStream(true);
			try {
				process = builder.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			InputStream is = process.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is));
			return 1;
		}
		return 0;
	}
	
	static public String read() {
		String id;
		String line = null;
		
		try {
			while ((line = reader.readLine()) != null) {
				if(line.length() > 2) { //ligne pas vide
					if(line.contains("UID") && !line.contains("echo")) { //2eme conditions pour test
						id = line.substring(8, line.length()).trim();
						return id;
					}
				}	
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "ERROR";
		}
		
		return "ERROR";
		
	}
	
	static public int stop() {
		if (process != null) {
			process.destroy();
			process = null;
			reader = null;
			return 1;
		}
		return 0;
	}
}
