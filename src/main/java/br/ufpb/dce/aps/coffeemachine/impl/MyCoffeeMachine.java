package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.CashBox;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Display;

public class MyCoffeeMachine implements CoffeeMachine{

	private int total;
	private ComponentsFactory factory;
	private CashBox cashBox;
	private Coin coin;
	
	public MyCoffeeMachine (ComponentsFactory factory){
		this.factory = factory;
		factory.getDisplay().info ("Insert coins and select a drink!");
	}

	public void insertCoin(Coin coin) throws CoffeeMachineException{
		try{
			this.total += coin.getValue();
			this.coin = coin;
			this.factory.getDisplay().info ("Total: US$ "+this.total/100+"." + this.total%100);
		}
		catch(NullPointerException e){
			throw new CoffeeMachineException("Moeda inválida"); 
		}
	}

	public void cancel() throws CoffeeMachineException {
		if( this.total == 0){
			throw new CoffeeMachineException("Não houve moeda inserida"); 	
		}
		else{
			factory.getDisplay().warn ("Cancelling drink. Please, get your coins.");
			factory.getCashBox().release(coin);
			factory.getDisplay().info ("Insert coins and select a drink!");
		}
	}
}
