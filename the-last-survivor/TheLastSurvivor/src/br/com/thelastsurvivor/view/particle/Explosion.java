package br.com.thelastsurvivor.view.particle;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import br.com.thelastsurvivor.engine.util.EOrientation;
import br.com.thelastsurvivor.util.Vector2D;

public class Explosion {

	
	//public static final int STATE_ALIVE 	= 0;	
	//public static final int STATE_DEAD 		= 1;	
	
	public Boolean isAlive;
	public Integer explosionSize;
	
	private List<Particle> particles;			

	                         
	public Explosion(Integer explosionSize) {
		this.init();
		
		this.explosionSize = explosionSize;
	
	}
	
	public void init(){
		
		this.isAlive = true;
		this.particles = new ArrayList<Particle>();
		
		
	 	
			//Particle p = new Particle(new Vector2D(200,200));
			//this.particles[i] = p;
	 		this.particles.add(new Particle());
	 	
	 	
	}

	public void update() {
		
		//for (int i = 0; i < 1; i++) {
			//Particle p = new Particle(new Vector2D(200,200));
			//this.particles[i] = p;
	 		this.particles.add(new Particle());
	 	//}
		
		if (this.isAlive) {
			for (Particle particle : particles) {
				if(particle.isAlive()){
					particle.update();
				}
			}
			this.cleanListParticules();
		}
		
		this.cleanListParticules();
	}

	public void update(EOrientation orientation) {
		
		//for (int i = 0; i < 1; i++) {
			//Particle p = new Particle(new Vector2D(200,200));
			//this.particles[i] = p;
	 		this.particles.add(new Particle());
	 	//}
		
		if (this.isAlive) {
			for (Particle particle : particles) {
				if(particle.isAlive()){
					particle.update(orientation);
				}
			}
			this.cleanListParticules();
		}
		
		this.cleanListParticules();
	}
	
	public void updateInGame(Vector2D camera) {
		
		//for (int i = 0; i < 1; i++) {
			//Particle p = new Particle(new Vector2D(200,200));
			//this.particles[i] = p;
	 		this.particles.add(new Particle());
	 	//}
		
		if (this.isAlive) {
			for (Particle particle : particles) {
				if(particle.isAlive()){
			//		particle.update(camera);
				}
			}
			this.cleanListParticules();
		}
		
		this.cleanListParticules();
	}
		
	
	public void draw(Canvas c) {
		if (this.isAlive) {
			for (Particle particle : particles) {
				if(particle.isAlive()){
					particle.draw(c);
				}
			}
		}
	}
	
	
	private void cleanListParticules(){
		List<Particle> particleAlives = new ArrayList<Particle>();
		
		for (Particle particle : particles) {
			if(particle.isAlive()){
				particleAlives.add(particle);
			}
		}
		
		this.particles.clear();
		this.particles.addAll(particleAlives);
	}
	
	public Boolean getIsAlive() {
		return isAlive;
	}
	
	
	
}
