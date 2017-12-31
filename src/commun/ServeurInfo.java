package commun;

import java.util.UUID;

public class ServeurInfo {

	String ip="";
	String port="";
	
	public String getAdresseServeur() {
		return "rmi://"+ip+":"+port+"/hoteldesventes";
	}

	public ServeurInfo(String ipServeur,String portServeur) {
		ip=ipServeur;
		port=portServeur;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

}
