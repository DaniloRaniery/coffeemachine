package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;

public abstract class Drinks {

	protected Drink drink;
	protected ComponentsFactory factory;
	
	public void getDrinks(ComponentsFactory factory){
		this.factory = factory;
	}
	public boolean conferirIngredientes() {
		if (!this.factory.getCupDispenser().contains(1)) {
			this.factory.getDisplay().warn("Out of Cup");
			return false;
		}
		if (!this.factory.getWaterDispenser().contains(3)) {
			this.factory.getDisplay().warn("Out of Water");
			return false;
		}
		if (!this.factory.getCoffeePowderDispenser().contains(200)) {
			this.factory.getDisplay().warn("Out of Coffee Powder");
			return false;
		} else {
			return true;
		}
	}
	
	public Drinks getDrink(Drink drink){
		if (drink == drink.BLACK ||drink == drink.BLACK_SUGAR){
			return new Black (drink);
		}
		else{
			return new White (drink);
		}
	}
	
	public void Mix (){
		this.factory.getDisplay().info("Mixing ingredients.");
		this.factory.getCoffeePowderDispenser().release(200);
		this.factory.getWaterDispenser().release(3);
	}
	
	public abstract void release();
	
	public void releaseFinal(){
		this.factory.getDisplay().info("Releasing drink.");
		this.factory.getCupDispenser().release(1);
		this.factory.getDrinkDispenser().release(1);
		this.factory.getDisplay().info("Please, take your drink.");
		this.factory.getDisplay().info("Insert coins and select a drink!");
	}
}
