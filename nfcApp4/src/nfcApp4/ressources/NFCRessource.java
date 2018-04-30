package nfcApp4.ressources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import nfcApp4.entites.Processnfc;

@Path("/nfc")
public class NFCRessource {

	
	@GET
	@Path("/getnfc")
	public String getnfc() {
		
		ProcessBuilder builder = new ProcessBuilder("C:\\Users\\croyg\\Desktop\\test.bat");
		builder.redirectErrorStream(true);
		Process process = null;
		String id;
		//Demarrage Process
		try {
			process = builder.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		InputStream is = process.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		
		//Lecture retour
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				if(line.length() > 2) { //ligne pas vide
					if(line.contains("UID")) {
						id = line.substring(8, line.length());
						process.destroy();
						return id;
					}
				}	
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "ERROR";
		}
		
		return "ERROR";
	}
	
	@GET
	@Path("/read")
	public String getnfc2() {
		String id;
		id = Processnfc.read();
		return id;
	}
	
	@GET
	@Path("/start")
	public Response start() {
		ResponseBuilder builder = Response.status(Response.Status.NOT_MODIFIED);
		
		if (Processnfc.start() == 1) {
			builder = Response.status(Response.Status.OK);
		}
		
		return builder.build();
	}
	
	
	
	@GET
	@Path("/stop")
	public Response stop() {
		ResponseBuilder builder = Response.status(Response.Status.NOT_MODIFIED);
		
		if (Processnfc.stop() == 1) {
			builder = Response.status(Response.Status.OK);
		}
		return builder.build();
	}
	
}
