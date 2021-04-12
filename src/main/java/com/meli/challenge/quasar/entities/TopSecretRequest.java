package com.meli.challenge.quasar.entities;

import java.util.ArrayList;
import java.util.List;

public class TopSecretRequest {

	List<TopSecretSatelliteRequest> satellites;
	public TopSecretRequest(){
		this.satellites = new ArrayList<>();

		/*TopSecretSatelliteRequest uno = new TopSecretSatelliteRequest("kenobi", 100, new String[]{"este", "", "", "mensaje", ""});
		TopSecretSatelliteRequest dos = new TopSecretSatelliteRequest("skywalker", 115.5f, new String[]{"", "es", "", "", "secreto"});
		TopSecretSatelliteRequest tres = new TopSecretSatelliteRequest("sato", 142.7f, new String[]{"este", "", "un", "", ""});

		this.satellites.add(uno);
		this.satellites.add(dos);
		this.satellites.add(tres);*/
	}

	public List<TopSecretSatelliteRequest> getSatellites() {
		return this.satellites;
	}

	public void setSatellites( List<TopSecretSatelliteRequest> satellites){
		this.satellites = satellites;
	}
}


