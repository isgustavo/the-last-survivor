package br.com.thelastsurvivor.engine;

import br.com.thelastsurvivor.util.Vector2D;

public final class Orientation {
	

	public static void getNewPosition(Double angle, Vector2D position){
		switch((int)Math.round(angle)){
		   
		   case 0:
			   position.addY(-18);
		   break;
		   
		   
		   case 5:
			   position.addX(1);
		       position.addY(-17);
		   break;
		   case 10:
			    position.addX(2);
		    	position.addY(-16);
		   break;
		   case 15:
			  position.addX(3);
		       position.addY(-15);
		   break;
		   case 20:
			    position.addX(4);
		    	position.addY(-14);
		   break;
		   case 25:
			   position.addX(5);
		       position.addY(-13);
		   break;
		   case 30:
			    position.addX(6);
		    	position.addY(-12);
		   break;
		   case 35:
			   position.addX(7);
		       position.addY(-11);
		   break;
		   case 40:
			    position.addX(8);
		    	position.addY(-10);
		   break;
		   case 45:
			   position.addX(9);
		       position.addY(-9);
		   break;
		   case 50:
			    position.addX(10);
		    	position.addY(-8);
		   break;
		   case 55:
			    position.addX(11);
		    	position.addY(-7);
		   break;
		   case 60:
			    position.addX(12);
		    	position.addY(-6);
		   break;
		   case 65:
			   position.addX(13);
		       position.addY(-5);
		   break;
		   case 70:
			    position.addX(14);
		    	position.addY(-4);
		   break;
		   case 75:
			   position.addX(15);
		       position.addY(-3);
		   break;
		   case 80:
			   position.addX(16);
			   position.addY(-2);
		   break;
		   case 85:
			   position.addX(17);
		    	position.addY(-1);
		   break;
		   
		   
		   case 90:
			    position.addX(18);
		   break;
		   
		   
		   case 95:
			    position.addX(17);
		    	position.addY(1);
		   break;
		   case 100:
			    position.addX(16);
		    	position.addY(2);
		   break;
		   case 105:
			    position.addX(15);
		    	position.addY(3);
		   break;
		   case 110:
			    position.addX(14);
		    	position.addY(4);
		   break;
		   case 115:
			    position.addX(13);
		    	position.addY(5);
		   break;
		   case 1220:
			    position.addX(12);
		    	position.addY(6);
		   break;
		   case 125:
			    position.addX(11);
		    	position.addY(7);
		   break;
		   case 130:
			   position.addX(10);
		    	position.addY(8);
		   break;
		   case 135:
			    position.addX(9);
		    	position.addY(9);
		   break;
		   case 140:
			    position.addX(8);
		    	position.addY(10);
		   break;
		   case 145:
			   position.addX(7);
		    	position.addY(11);
		   break;
		   case 150:
			    position.addX(6);
		    	position.addY(12);
		   break;
		   case 155:
			   position.addX(5);
		    	position.addY(13);
		   break;
		   case 160:
			    position.addX(4);
		    	position.addY(14);
		   break;
		   case 165:
			    position.addX(3);
		    	position.addY(15);
		   break;
		   case 170:
			    position.addX(2);
		    	position.addY(16);
		   break;
		   case 175:
			    position.addX(1);
		    	position.addY(17);
		   break;
		   
		   
		   case 180:
		    	position.addY(18);
		   break;
		  
		   
		   case 185:
			    position.addX(-1);
		    	position.addY(17);
		   break;
		   case 190:
			    position.addX(-2);
		    	position.addY(16);
		   break;
		   case 195:
			    position.addX(-3);
		    	position.addY(15);
		   break;
		   case 200:
			    position.addX(-4);
		    	position.addY(14);
		   break;
		   case 205:
			    position.addX(-5);
		    	position.addY(13);
		   break;
		   case 210:
			    position.addX(-6);
		    	position.addY(12);
		   break;
		   case 215:
			   position.addX(-7);
		    	position.addY(11);
		   break;
		   case 220:
			    position.addX(-8);
		    	position.addY(10);
		   break;
		   case 225:
			    position.addX(-9);
		    	position.addY(9);
		   break;
		   case 230:
			    position.addX(-10);
		    	position.addY(8);
		   break;
		   case 235:
			    position.addX(-11);
		    	position.addY(7);
		   break;
		   case 240:
			   position.addX(-12);
		    	position.addY(6);
		   break;
		   case 245:
			    position.addX(-13);
		    	position.addY(5);
		   break;
		   case 250:
			    position.addX(-14);
		    	position.addY(4);
		   break;
		   case 255:
			   position.addX(-15);
		    	position.addY(3);
		   break;
		   case 260:
			   position.addX(-16);
		    	position.addY(2);
		   break;
		   case 265:
			    position.addX(-17);
		    	position.addY(1);
		   break;
		   
		   
		   case 270:
			    position.addX(-18);
		   break;
		   
		   
		   case 275:
			    position.addX(-17);
		    	position.addY(-1);
		   break;
		   case 280:
			    position.addX(-16);
		    	position.addY(-2);
		   break;
		   case 285:
			    position.addX(-15);
		    	position.addY(-3);
		   break;
		   case 290:
			    position.addX(-14);
		    	position.addY(-4);
		   break;
		   case 295:
			   position.addX(-13);
		    	position.addY(-5);
		   break;
		   case 300:
			    position.addX(-12);
		    	position.addY(-6);
		   break;
		   case 305:
			    position.addX(-11);
		    	position.addY(-7);
		   break;
		   case 310:
			    position.addX(-10);
		    	position.addY(-8);
		   break;
		   case 315:
			    position.addX(-9);
		    	position.addY(-9);
		   break;
		   case 320:
			    position.addX(-8);
		    	position.addY(-10);
		   break;
		   case 325:
			    position.addX(-7);
		    	position.addY(-11);
		   break;
		   case 330:
			    position.addX(-6);
		    	position.addY(-12);
		   break;
		   case 335:
			   position.addX(-5);
		    	position.addY(-13);
		   break;
		   case 340:
			    position.addX(-4);
		    	position.addY(-14);
		   break;
		   case 345:
			    position.addX(-3);
		    	position.addY(-15);
		   break;
		   case 350:
			    position.addX(-2);
		    	position.addY(-16);
		   break;
		   case 355:
			    position.addX(-1);
		    	position.addY(-17);
		   break;
		   
		   }
		   
	   }
	
	
}
