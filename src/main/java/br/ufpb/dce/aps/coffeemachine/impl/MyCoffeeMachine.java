package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Button;

public class MyCoffeeMachine implements CoffeeMachine {

	private ComponentsFactory factory;
	private GerenteFinanceiro gerenteFinanceiro =  new GerenteFinanceiro(); 
	private GerenteDeMaquina gerenteDeMaquina = new GerenteDeMaquina();
		
	public MyCoffeeMachine() {
	}
	
	public void insertCoin(Coin coin) {
		this.gerenteFinanceiro.inserirMoeda(this.factory, coin, this.gerenteDeMaquina.getModo());
	}

	public void cancel(){
		this.gerenteFinanceiro.cancelar(this.factory);	
	}
	
	public void select(Button button) {		
		this.gerenteDeMaquina.iniciarPedidoDeBebida(this.factory, this.gerenteFinanceiro, button);
	}

	public void setFactory(ComponentsFactory factory) {
		this.factory = factory;		
		this.gerenteDeMaquina.iniciar(factory);
	}

	public void readBadge(int badgeCode) {
		this.gerenteDeMaquina.iniciarComCracha(factory, this.gerenteFinanceiro, badgeCode);
	}

	public void setPrice(Button drink, int priceCents) {
		this.gerenteDeMaquina.setPrecoDaBebida(drink,priceCents);
		this.gerenteDeMaquina.mensagemInicial(factory);
		
	}
}
