package br.com.thelastsurvivor.engine.multiplayergame.protocol;


import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import br.com.thelastsurvivor.engine.effect.EffectShoot;
import br.com.thelastsurvivor.engine.game.spacecraft.Spacecraft;
import br.com.thelastsurvivor.engine.game.weapon.SimpleShoot;
import br.com.thelastsurvivor.engine.multiplayergame.client.EngineGameClient;
import br.com.thelastsurvivor.engine.simpleplayergame.message.MessageGame;
import br.com.thelastsurvivor.engine.util.IDraw;
import br.com.thelastsurvivor.engine.util.IDrawBehavior;
import br.com.thelastsurvivor.engine.util.IEffect;
import br.com.thelastsurvivor.util.Vector2D;

public class ProtocolCommunication {


	public String protocolSendsInitSpacecraftClient(String name, Integer client){

		String message = "";
		
		switch (client) {
		case 0:
			//Log.d("ENGINE_INIT_CLITN", "."+name);
			//spacecrafts.add(new Spacecraft(context, new Vector2D(200, 50), new Double(180.0),name));
			message +=  name+"/200/50/180";
		case 1:
			//Log.d("ENGINE_INIT_CLITN", "."+name);
			//spacecrafts.add(new Spacecraft(context, new Vector2D(350, 100), new Double(270.0),name));
			message += name+"/350/100/270";
		case 2:
			//Log.d("ENGINE_INIT_CLITN", "."+name);
			//spacecrafts.add(new Spacecraft(context, new Vector2D(50, 100), new Double(90.0),name));
			message += name+"/50/100/90";
		}
		return message;
	}
	
	public String protocolSendToServerClientSpacecraft(Spacecraft spacecraft){
		
		String message = "";
		
		message += spacecraft.getName()+"/";
		
		if(spacecraft.getLeft()){
			message += "left/";
		}else if(spacecraft.getRight()){
			message += "right/";
		}else if(spacecraft.getUp()){
			message += "up/";
		}else if(spacecraft.getDown()){
			message += "down/";
		}else{
			message +="/";
		}
		
		message += spacecraft.getNewShoot().toString()+"/";
		//Log.d("PROTOCOLO------------------------------------------", "."+message);
		return message;
		
		
	}
	
	public void protocolResponseClientSpacecraft(String[] values, List<Spacecraft> spacecrafts){
		
		for (Spacecraft spacecraft : spacecrafts) {
			if(spacecraft.getName().equals(values[1])){
				
				if(values[2].equalsIgnoreCase("left")){
					spacecraft.setLeft(true);
				
				}else if(values[2].equalsIgnoreCase("right")){
					spacecraft.setRight(true);
					
				}else if(values[2].equalsIgnoreCase("up")){
					spacecraft.setUp(true);
					
				}else if(values[2].equalsIgnoreCase("down")){
					spacecraft.setDown(true);
					
				}
				
				if(values.length > 3){
					if(values[3].equalsIgnoreCase("true")){
						spacecraft.newShoot();
					}
				}
				
				
			}
			
		}
	}
	
	public String protocolSendsAllSpacecraft(List<Spacecraft> spacecrafts){
		String message = "";
		
		for (Spacecraft spacecraft : spacecrafts) {
			message += spacecraft.getName()+"/"+spacecraft.getPosition().getX()+"/"
					+  spacecraft.getPosition().getY()+"/"
					+  spacecraft.getAngle()+"/";
		}
		
		return message;
		
	}
	
	
	public List<Spacecraft> protocolResponseAllSpacecrafts(String[] message, List<Spacecraft> spacecrafts){
		
		List<Spacecraft> newSpacecraft = new ArrayList<Spacecraft>();
		
		for (Spacecraft spacecraft : spacecrafts) {
			
			for(int x = 0; x < message.length; x++){
				if(spacecraft.getName().equalsIgnoreCase(message[x])){
					
					spacecraft.setPosition(new Vector2D(message[x+1], message[x+2]));
					spacecraft.setAngle(Double.parseDouble(message[x+3]));
					break;
				}
			}
		}
		
		
		
		
		return spacecrafts;
	}
	
	/*public List<Spacecraft> protocolResponseAllNewSpacecrafts(String[] message){
		
		List<Spacecraft> newSpacecrafts = new ArrayList<Spacecraft>();
		
		//for (Spacecraft spacecraft : spacecrafts) {
			
			for(int x = 1; x < message.length; x++){
				
				newSpacecrafts.add(new Spacecraft(new Vector2D(message[x+1], message[x+2]),Double.parseDouble(message[x+3]),message[x]));
				x += 3;
			}
		//}
		
		
		
		
		return newSpacecrafts;
	}*/
	
	
	
	public String protocolSendToClientsStatusGame(Spacecraft spacecraftServer, List<Spacecraft> spacecrafts,  
			List<MessageGame> messages, List<IEffect> shootsEffect){
		
		
		String buffer = "";
		
		if(spacecraftServer != null){
			buffer += "s/"
					   +  spacecraftServer.getPosition().getX()+"/"
					   +  spacecraftServer.getPosition().getY()+"/"
					   +  spacecraftServer.getAngle()+"/"
					   +  spacecraftServer.getColor()+"/";
				if(spacecraftServer.getShootsDrawables().size() != 0){
					for(IDrawBehavior shoot : spacecraftServer.getShootsDrawables()){
						buffer +="h/";
						buffer += shoot.getPosition().getX()+"/"
							   + shoot.getPosition().getY()+"/"
							   + shoot.getAngle()+"/"
							   + shoot.getColor()+"/";
					}
				}
		}
		
		
		for (Spacecraft spacecraft : spacecrafts) {
			buffer += "s/"+ 
				   +  spacecraft.getPosition().getX()+"/"
				   +  spacecraft.getPosition().getY()+"/"
				   +  spacecraft.getAngle()+"/"
				   +  spacecraft.getColor()+"/";
			
			if(spacecraft.getShootsDrawables().size() != 0){	
				for(IDrawBehavior shoot : spacecraft.getShootsDrawables()){
					buffer +="h/";
					buffer += shoot.getPosition().getX()+"/"
						   + shoot.getPosition().getY()+"/"
						   + shoot.getAngle()+"/"
						   + shoot.getColor()+"/";
				}
			}
		}
		int size = 0;
		for(MessageGame message : messages){
			buffer += "m/"
				   +  message.getText()+"/"
				   +  message.getPosition()+"/"
				   +  message.getAlpha()+"/"
				   +  message.getColorText()+"/";
			size += 1;
			if(size == 3){
				break;
			}
		}
		
		for(IEffect effect : shootsEffect){
			buffer += "e/"
				   +  effect.getPosition().getX()+"/"
				   +  effect.getPosition().getY()+"/"
				   +  effect.getAlpha()+"/";
		}
		
		Log.d("message",".."+messages.size());
		return buffer;
		
	}
	
	
	public List<IDraw> protocolReceiveToServerStatusGame(EngineGameClient engine, String[] values){
		

			List<IDraw> listDrawables = new ArrayList<IDraw>();
			
			int color = 0;
			
			for(int i = 1; i < values.length; i++){
				
				switch(values[i].charAt(0)){
				
				case 's':
					listDrawables.add(new Spacecraft(engine.getContext(), new Vector2D(values[i+1], values[i+2]),
							Double.parseDouble(values[i+3]), Integer.parseInt(values[i+4]), engine.getDisplay()));
					color = Integer.parseInt(values[i+4]);
					i += 4;
				break;
				
				case 'h':
					listDrawables.add(new SimpleShoot(engine.getContext(), new Vector2D(values[i+1], values[i+2]),
							Double.parseDouble(values[i+3]), Integer.parseInt(values[i+4]), engine.getDisplay()));
					i += 4;
				break;
				
				case 'm':
					listDrawables.add(new MessageGame(engine.getContext(), values[i+1], 
							Integer.parseInt(values[i+2]),Integer.parseInt(values[i+3]), values[i+4]));
					
					i += 4;
				break;
				
				case 'e':
					listDrawables.add(new EffectShoot(engine.getContext(), new Vector2D(values[i+1], values[i+2]),
							Integer.parseInt(values[i+3]), engine.getDisplay()));
					
					i +=3;
				break;
				}
			}
			return listDrawables;
		
		
		
	}
	
	public String protocolSentToClientsEndGame(Integer pointsTeamRed,
			Integer pointsTeamBlue, Integer pointsTeamYellow, Integer pointsTeamGreen,
			Spacecraft spacecraft,List<Spacecraft> spacecrafts){
				
		String buffer = "";
		
		buffer += "r/"+pointsTeamRed+"/";
		buffer += "b/"+pointsTeamBlue+"/";
		buffer += "y/"+pointsTeamYellow+"/";
		buffer += "g/"+pointsTeamGreen+"/";
		
		buffer += "s/"+spacecraft.getName()+"/c/"+spacecraft.getColor()+"/";
		
		for(Spacecraft space : spacecrafts){
			buffer += "s/"+space.getName()+"/c/"+space.getColor()+"/";
		}
		
		//Log.d("BUFFER MESSAGE","."+buffer);
		return buffer;
		
	}
	
		
}
