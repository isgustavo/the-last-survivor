package br.com.thelastsurvivor.engine.effect;

public enum TypeEffect {

	
	spacecraft("1"), shoot("2"), asteroid("3");
	
	
	public final String type;  
    private TypeEffect(String type){  
        this.type = type;  
    }  
   
}
