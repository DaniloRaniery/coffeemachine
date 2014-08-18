package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;

public class MyCoffeeMachine implements CoffeeMachine {

	private ComponentsFactory factory;
	private GerenteFinanceiro gerenteFinanceiro =  new GerenteFinanceiro(); 
	private GerenteDeMaquina gerenteDeMaquina = new GerenteDeMaquina();
		
	public MyCoffeeMachine() {
	}
	
	public void insertCoin(Coin coin) {
		this.gerenteFinanceiro.inserirMoeda(this.factory, coin);
	}

	public void cancel(){
		this.gerenteFinanceiro.cancelar(this.factory);	
	}
	
	public void select(Drink drink) {		
		this.gerenteDeMaquina.iniciarPedidoDeBebida(this.factory, this.gerenteFinanceiro, drink);
	}

	public void setFactory(ComponentsFactory factory) {
		this.factory = factory;		
		this.gerenteDeMaquina.apresentarMensagemInicial(factory);
	}
}
