package br.ufpb.dce.aps.coffeemachine.impl;

import java.util.HashMap;

import br.ufpb.dce.aps.coffeemachine.Button;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Dispenser;

public abstract class Bebida {

	protected Button button; 
	protected double poDeCafe = 0, agua = 0, creme = 0, poDeSopa = 0, acucar = 0, chocolate = 0, leite = 0; 
	protected int copo = 1;
	protected HashMap<String, Dispenser> dispensas = new HashMap<String, Dispenser>();
	
	public abstract void release(ComponentsFactory factory);
	public abstract boolean conferirIngredientes(ComponentsFactory factory, Button drink);
	
	public Button getDrink(){
		return this.button;
	}
		
	public double getAcucar() {
		return acucar;
	}

	public void setAcucar(double acucar) {
		this.acucar = acucar;
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
	public double getChocolate() {
		return chocolate;
	}
	public void setChocolate(double chocolate) {
		this.chocolate = chocolate;
	}
	public double getLeite() {
		return leite;
	}
	public void setLeite(double leite) {
		this.leite = leite;
	}

}

