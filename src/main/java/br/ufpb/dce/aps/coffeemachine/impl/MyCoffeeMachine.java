package br.ufpb.dce.aps.coffeemachine.impl;

import java.util.ArrayList;

import br.ufpb.dce.aps.coffeemachine.CashBox;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Dispenser;
import br.ufpb.dce.aps.coffeemachine.Display;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.DrinkDispenser;

public class MyCoffeeMachine implements CoffeeMachine {

	private int total, indice;
	private ComponentsFactory factory;
	private CashBox cashBox;
	private Coin coin;
	private ArrayList<Coin> coins = new ArrayList<Coin>();
	private Drinks drinks;

	public MyCoffeeMachine(ComponentsFactory factory) {
		this.factory = factory;
		this.drinks = new Drinks();
		this.factory.getDisplay().info("Insert coins and select a drink!");
	}

	public void insertCoin(Coin coin) throws CoffeeMachineException {
		try {
			this.total += coin.getValue();
			this.coin = coin;
			this.coins.add(coin);
			this.indice++;
			this.factory.getDisplay().info(
					"Total: US$ " + this.total / 100 + "." + this.total % 100);
		} catch (NullPointerException e) {
			throw new CoffeeMachineException("Moeda inválida");
		}
	}

	public void cancel() throws CoffeeMachineException {
		if (this.total == 0) {
			throw new CoffeeMachineException("Não houve moeda inserida");
		}

		if (this.coins.size() > 0) {
			Coin[] reverso = Coin.reverse();
			int troco = this.coin.getValue();
			this.factory.getDisplay().warn(
					"Cancelling drink. Please, get your coins.");
			for (Coin re : reverso) {
				for (Coin aux : this.coins) {
					if (aux == re) {
						this.factory.getCashBox().release(aux);
					}
				}
			}

			this.coins.clear();
		}
		this.factory.getDisplay().info("Insert coins and select a drink!");
	}

	public void cancelWithoutIngredients() {
		Coin[] reverso = Coin.reverse();
		int troco = this.coin.getValue();
		for (Coin re : reverso) {
			for (Coin aux : this.coins) {
				if (aux == re) {
					this.factory.getCashBox().release(aux);
				}
			}
		}
		this.coins.clear();
		this.factory.getDisplay().info("Insert coins and select a drink!");
	}

	public void select(Drink drink) {
		
		this.drinks = this.drinks.getDrink(drink);
		
		if (!this.drinks.conferirIngredientes()){
			this.cancelWithoutIngredients();
		}
				
		if(this.drinks == null){
			this.cancelWithoutIngredients();
		}
		
		this.drinks.Mix();
	
		this.drinks.release();
		
		this.coins.clear();
	}
}
