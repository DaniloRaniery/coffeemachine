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
import br.ufpb.dce.aps.coffeemachine.Messages;

public class MyCoffeeMachine implements CoffeeMachine {

	private int total, indice;
	private ComponentsFactory factory;
	private Coin coin;
	private ArrayList<Coin> coins = new ArrayList<Coin>();
	private MeuGerenteDeBebidas gerente;
	private Coin[] reverso = Coin.reverse();

	public MyCoffeeMachine(ComponentsFactory factory) {
		this.factory = factory;
		this.gerente = new MeuGerenteDeBebidas(this.factory);
		this.factory.getDisplay().info(Messages.INSERT_COINS);
	}

	public void insertCoin(Coin coin) throws CoffeeMachineException {
		try {
			this.total += coin.getValue();
			this.coin = coin;
			this.coins.add(coin);
			this.indice++;
			this.factory.getDisplay().info("Total: US$ " + this.total / 100 + "." + this.total % 100);
		} catch (NullPointerException e) {
			throw new CoffeeMachineException("Moeda inválida");
		}
	}

	public void cancel() throws CoffeeMachineException {
		if (this.total == 0) {
			throw new CoffeeMachineException("Não houve moeda inserida");
		}
		this.cancel(true);
		
	}
		
	public void cancel(Boolean confirm) throws CoffeeMachineException{
		if (this.coins.size() > 0) {
			if(confirm){
				this.factory.getDisplay().warn(Messages.CANCEL);
			}
			for (Coin re : this.reverso) {
				for (Coin aux : this.coins) {
					if (aux == re) {
						this.factory.getCashBox().release(aux);
					}
				}
			}
			this.total = 0;
			this.coins.clear();
		}
		this.factory.getDisplay().info(Messages.INSERT_COINS);
	}
	
	public void PlanoDeLiberarTroco (double troco){
		double trocoProvisorio = troco;
		this.reverso = Coin.reverse();
		for(Coin c : this.reverso){
			while(c.getValue() <= trocoProvisorio ){
				this.factory.getCashBox().count (c);
				trocoProvisorio -= c.getValue(); 
			}
		}
	}
	
	public void liberarTroco (double troco){
		this.reverso = Coin.reverse();
		for(Coin c : this.reverso){
			while(c.getValue() <= troco ){
				this.factory.getCashBox().release (c);
				troco -= c.getValue(); 
			}
		}
	}
	
	public void select(Drink drink) {
		
		if(this.total < this.gerente.getValorDaBebida() || this.total == 0){
			this.factory.getDisplay().warn(Messages.NO_ENOUGHT_MONEY);
			this.cancel(false);
			return;
		}

		this.gerente.iniciarDrink(drink);
		
		if (!this.gerente.conferirIngredientes()) {
			this.cancel(false);
			return;
		}
		if(!this.gerente.verificaAcucar()){ 
			this.cancel(false);
			return;
		} 
		if(this.total % this.gerente.getValorDaBebida() != 0 && this.total > this.gerente.getValorDaBebida()){
			this.PlanoDeLiberarTroco(this.total - this.gerente.getValorDaBebida());
		}
				
		this.gerente.Mix();
		this.gerente.release();
		
		if(this.total % this.gerente.getValorDaBebida() != 0 && this.total > this.gerente.getValorDaBebida()){
			this.liberarTroco(this.total - this.gerente.getValorDaBebida());
		}
		this.factory.getDisplay().info(Messages.INSERT_COINS);
		this.coins.clear();
	}
}
