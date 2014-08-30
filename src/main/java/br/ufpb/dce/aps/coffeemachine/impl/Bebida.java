package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.Button;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;

public abstract class Bebida {

	protected Button button; 
	protected double poDeCafe = 0, agua = 0, creme = 0, poDeSopa = 0; 
	protected int copo = 1;
	
	public abstract void release(ComponentsFactory factory);	
	
	public Button getDrink(){
		return this.button;
	}
	
	public double getPoDeCafe() {
		return this.poDeCafe;
	}

	public void setPoDeCafe(double po) {
		this.poDeCafe = po;
	}

	public double getAgua() {
		return this.agua;
	}

	public void setAgua(double agua) {
		this.agua = agua;
	}
	
	public double getCreme() {
		return this.creme;
	}

	public void setCreme(int creme) {
		this.creme = creme;
	}
	public double getPoDeSopa() {
		return poDeSopa;
	}

	public void setPoDeSopa(double poDeSopa) {
		this.poDeSopa = poDeSopa;
	}
	public int getCopo(){
		return this.copo;
	}
}

