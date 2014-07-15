package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Display;

public class MyCoffeeMachine implements CoffeeMachine{

	int total;
	
	private ComponentsFactory factory;

	public MyCoffeeMachine (ComponentsFactory factory){
		this.factory = factory;
		factory.getDisplay().info ("Insert coins and select a drink!");
	}

	public void insertCoin(Coin coin) {
		total += coin.getValue();
		factory.getDisplay().info ("Total: US$ "+total/100+"." + total%100);
	}
}
