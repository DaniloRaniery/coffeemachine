package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Button;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class GerenteDeMaquina {

	private GerenteDeBebidas gerenteDeBebidas = new GerenteDeBebidas();
	private static String modo = "";
	private int cracha = 0;
	public void iniciarPedidoDeBebida(ComponentsFactory factory,
			GerenteFinanceiro gerenteFinanceiro, Button drink) {
		if (modo.equals("cracha")){
			this.iniciarPedidoComCracha(factory, drink);
		}else{
			this.iniciarPedidoComMoedas(factory, gerenteFinanceiro, drink);
		}
		
	}
		
	public void iniciarPedidoComMoedas(ComponentsFactory factory,
			GerenteFinanceiro gerenteFinanceiro, Button button){
		this.gerenteDeBebidas.iniciarDrink(factory, button);
		if (!gerenteFinanceiro.conferirDinheiroInserido(factory,
				this.gerenteDeBebidas.getValorDaBebida())) {
			return;
		}

		if (!this.gerenteDeBebidas.conferirIngredientes(factory, button)) {
			gerenteFinanceiro.liberarMoedas(factory, false);
			return;
		}
		if (!this.gerenteDeBebidas.verificaAcucar(factory)) {
			gerenteFinanceiro.liberarMoedas(factory, false);
			return;
		}

		if (!gerenteFinanceiro.conferirDisponibiliadadeDeTroco(factory,
			this.gerenteDeBebidas.getValorDaBebida())) {
			return;
		}
	

		this.gerenteDeBebidas.Mix(factory, button);
		this.gerenteDeBebidas.release(factory);

		if (gerenteFinanceiro.getTotal() >= this.gerenteDeBebidas.getValorDaBebida()) {
					gerenteFinanceiro.liberarTroco(factory, this.gerenteDeBebidas.getValorDaBebida());
		}

		this.reIniciar(factory);
		
		gerenteFinanceiro.zerarMoedas();	
	}
	
	public void iniciarPedidoComCracha(ComponentsFactory factory, Button button){
		this.gerenteDeBebidas.iniciarDrink(factory, button);
		
		if (!this.gerenteDeBebidas.conferirIngredientes(factory, button)) {
			return;
		}
		if (!this.gerenteDeBebidas.verificaAcucar(factory)) {
			return;
		}
		if(!factory.getPayrollSystem().debit(gerenteDeBebidas.getValorDaBebida(), this.cracha)){
			factory.getDisplay().warn(Messages.UNKNOWN_BADGE_CODE);
			this.reIniciar(factory);
			return;
		}
		
		this.gerenteDeBebidas.Mix(factory, button);
		this.gerenteDeBebidas.release(factory);
		
		this.reIniciar(factory);
	}

	public void iniciar(ComponentsFactory factory) {	
			factory.getButtonDisplay().show("Black: $0.35", "White: $0.35",
					"Black with sugar: $0.35", "White with sugar: $0.35",
					"Bouillon: $0.25", null, null);
			factory.getDisplay().info(Messages.INSERT_COINS);
			GerenteDeMaquina.setModo("moedas");
	}
	
	public void iniciarComCracha(ComponentsFactory factory, GerenteFinanceiro gerenteAuxiliar, int cracha) {
		if(gerenteAuxiliar.getTotal()>0){
			factory.getDisplay().warn(Messages.CAN_NOT_READ_BADGE);
			return;
		}
		else{
			factory.getDisplay().info(Messages.BADGE_READ);
			this.cracha = cracha;
			GerenteDeMaquina.setModo("cracha");
		}
	}
		
	
	public static void setModo(String novoModo) {
		modo = novoModo;
	}
	
	public String getModo(){
		return modo;
	}
	
	public void reIniciar(ComponentsFactory factory){
		factory.getDisplay().info(Messages.INSERT_COINS);
		GerenteDeMaquina.setModo (" ");
	}

}
