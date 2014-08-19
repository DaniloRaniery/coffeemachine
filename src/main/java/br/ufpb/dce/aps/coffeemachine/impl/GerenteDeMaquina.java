package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class GerenteDeMaquina {

	private GerenteDeBebidas gerenteDeBebidas = new GerenteDeBebidas();
	private static String modo = "";
	private int cracha = 0;

	public void iniciarPedidoDeBebida(ComponentsFactory factory,
			GerenteFinanceiro gerenteFinanceiro, Drink drink) {
		this.cracha = 0;
		this.gerenteDeBebidas.iniciarDrink(factory, drink);
		if (!gerenteFinanceiro.conferirDinheiroInserido(factory,
				this.gerenteDeBebidas.getValorDaBebida())) {
			return;
		}

		if (!this.gerenteDeBebidas.conferirIngredientes(factory, drink)) {
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
	

		this.gerenteDeBebidas.Mix(factory, drink);
		this.gerenteDeBebidas.release(factory);

		if (gerenteFinanceiro.getTotal() >= this.gerenteDeBebidas.getValorDaBebida()) {
					gerenteFinanceiro.liberarTroco(factory, this.gerenteDeBebidas.getValorDaBebida());
		}

		factory.getDisplay().info(Messages.INSERT_COINS);
		GerenteDeMaquina.setModo (" ");
		
		gerenteFinanceiro.zerarMoedas();
		
	}

	public void iniciarComMoedas(ComponentsFactory factory) {
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

}
