package br.com.thelastsurvivor.engine.util;

import br.com.thelastsurvivor.engine.game.spacecraft.Spacecraft;
import br.com.thelastsurvivor.engine.multiplayergame.protocol.ProtocolCommunication;

public interface IClient extends ICommunication{

	public Spacecraft getSpacecraft(); 
	
	public ProtocolCommunication getProtocol();
	public void drawGame(String[] message);
}
