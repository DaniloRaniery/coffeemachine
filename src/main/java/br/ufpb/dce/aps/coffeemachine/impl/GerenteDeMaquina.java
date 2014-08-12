package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class GerenteDeMaquina {

	private GerenteDeBebidas gerenteDeBebidas = new GerenteDeBebidas();

	public void iniciarPedidoDeBebida(ComponentsFactory factory,
			GerenteFinanceiro gerenteFinanceiro, Drink drink) {

		if (!gerenteFinanceiro.conferirDinheiroInserido(factory,
				this.gerenteDeBebidas.getValorDaBebida())) {
			return;
		}

		this.gerenteDeBebidas.iniciarDrink(factory, drink);

		if (!this.gerenteDeBebidas.conferirIngredientes(factory,drink)) {
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
		
		this.gerenteDeBebidas.Mix(factory);
		this.gerenteDeBebidas.release(factory);

		if (gerenteFinanceiro.getTotal()
				% this.gerenteDeBebidas.getValorDaBebida() != 0
				&& gerenteFinanceiro.getTotal() > this.gerenteDeBebidas
						.getValorDaBebida()) {
			gerenteFinanceiro.liberarTroco(factory,
					this.gerenteDeBebidas.getValorDaBebida());
		}

		factory.getDisplay().info(Messages.INSERT_COINS);

		gerenteFinanceiro.zerarMoedas();
	}
	
	public void apresentarMensagemInicial(ComponentsFactory factory){
		factory.getDisplay().info(Messages.INSERT_COINS);
	}

}
