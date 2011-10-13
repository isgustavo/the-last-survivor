package br.com.thelastsurvivor.engine.util;

import br.com.thelastsurvivor.engine.game.spacecraft.Spacecraft;
import br.com.thelastsurvivor.engine.multiplayergame.protocol.ProtocolCommunication;

public interface IServer extends ICommunication{

	
	public Spacecraft getSpacecraft(); 
	
	public ProtocolCommunication getProtocol();
	
	public void setSpacecraftClientToUpdate(String[] values);
	
}
