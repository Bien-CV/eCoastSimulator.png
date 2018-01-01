package commun;

import java.util.UUID;

public class ServeurInfo {

	String ip="127.0.0.1";
	String port="1099";
	
	public String getAdresseServeur() {
		return "//"+ip+":"+port+"/hoteldesventes";
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
