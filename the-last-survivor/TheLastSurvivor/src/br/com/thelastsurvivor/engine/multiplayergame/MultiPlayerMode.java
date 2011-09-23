package br.com.thelastsurvivor.engine.multiplayergame;

import java.util.UUID;

public class MultiPlayerMode {

	protected static final String SERVICE = "LASTSURVIVOR";
	protected static final UUID MEU_UUID =  UUID.fromString("db12d1e9-caba-84ef-398b-18211984abcd");
	
	protected MultiplayerCommunication communication;

	public MultiplayerCommunication getCommunication() {
		return communication;
	}

	public void setCommunication(MultiplayerCommunication communication) {
		this.communication = communication;
	}
	
	
	
}
