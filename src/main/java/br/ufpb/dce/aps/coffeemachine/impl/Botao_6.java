package br.ufpb.dce.aps.coffeemachine.impl;

import java.util.HashMap;

import br.ufpb.dce.aps.coffeemachine.Button;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Dispenser;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class Botao_6 extends Bebida {

	public Botao_6 (Button button, HashMap <String, Dispenser> dispensas) {
		this.button = Button.BUTTON_6;
		this.dispensas.putAll(dispensas);
		if(!this.dispensas.isEmpty()){
			this.setAgua(100);
			this.setCreme(25);
			this.setAcucar(15);
		}
		else{
			this.setLeite(120);
			this.setChocolate(20);
			this.setAcucar(5);
		}
	}

	@Override
	public void release(ComponentsFactory factory) {
		factory.getWaterDispenser().release(this.agua);
		factory.getCreamerDispenser().release(this.creme);
		factory.getSugarDispenser().release(this.acucar);
		factory.getDisplay().info(Messages.RELEASING);
		factory.getCupDispenser().release(this.copo);
	}
	
	public boolean conferirIngredientes(ComponentsFactory factory, Button drink) {
		factory.getCupDispenser().contains(this.copo);
		
		if(!this.dispensas.isEmpty()){
			this.dispensas.get("Milk").contains(120.0);
			this.dispensas.get("Chocolate Powder").contains(this.chocolate);
		}
		
		if (!factory.getWaterDispenser().contains(this.agua)) {
			factory.getDisplay().warn(Messages.OUT_OF_WATER);
			return false;
		}
		
		if (!factory.getSugarDispenser().contains(this.acucar)) {
			factory.getDisplay().warn(Messages.OUT_OF_SUGAR);
			return false;
		}	
		
		if (!factory.getCreamerDispenser().contains(this.creme)) {
				factory.getDisplay().warn(Messages.OUT_OF_CREAMER);
				return false;
		}
			
		return true;
	}
}
