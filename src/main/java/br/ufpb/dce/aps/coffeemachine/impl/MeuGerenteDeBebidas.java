package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class MeuGerenteDeBebidas {
	
	private ComponentsFactory factory;
	private Drinks drinks;
	private double valor = 35;
	
	public MeuGerenteDeBebidas(ComponentsFactory factory){
		this.factory = factory;
	}
	
	public void iniciarDrink(Drink drink){
		if (drink == drink.BLACK ||drink == drink.BLACK_SUGAR){
			this.drinks = new Black (drink, this.factory);
		}
		else{
			this.drinks =  new White (drink, this.factory);
		}
	}
	
	public boolean conferirIngredientes() {
		if (!this.factory.getCupDispenser().contains(1)) {
			this.factory.getDisplay().warn(Messages.OUT_OF_CUP);
			return false;
		}
		else if (!this.factory.getWaterDispenser().contains(3)) {
			this.factory.getDisplay().warn(Messages.OUT_OF_WATER);
			return false;
		}
		else if (!this.factory.getCoffeePowderDispenser().contains(200)) {
			this.factory.getDisplay().warn(Messages.OUT_OF_COFFEE_POWDER);
			return false;
		}
		else if (this.drinks.getDrink() == Drink.WHITE || this.drinks.getDrink() == Drink.WHITE_SUGAR){
			if (!this.factory.getCreamerDispenser().contains(150)){
				return false;
			}
		}
			return true;
	}
	
	public boolean verificaAcucar(){
		if(this.drinks.getDrink() == Drink.BLACK_SUGAR || this.drinks.getDrink() == Drink.WHITE_SUGAR){
			if (!this.factory.getSugarDispenser().contains(200)) {
				this.factory.getDisplay().warn(Messages.OUT_OF_SUGAR);
				return false;
			}
		}		
		return true;
	}	
	
	public void Mix (){
		this.factory.getDisplay().info(Messages.MIXING);
		this.factory.getCoffeePowderDispenser().release(200);
		this.factory.getWaterDispenser().release(3);
	}
	
	public void release(){
		this.drinks.release();
		this.factory.getDisplay().info(Messages.RELEASING);
		this.factory.getCupDispenser().release(1);
		this.factory.getDrinkDispenser().release(1);
		this.factory.getDisplay().info(Messages.TAKE_DRINK);
		
	}	

	public double getValorDaBebida(){
		return this.valor;
	}
}

