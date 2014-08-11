package br.ufpb.dce.aps.coffeemachine.impl;

import java.util.ArrayList;

import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class GerenteFinanceiro {

	private Coin[] reverso = Coin.reverse();
	private int total, indice;

	private Coin coin;
	private ArrayList<Coin> coins = new ArrayList<Coin>();

	public void inserirMoeda(ComponentsFactory factory, Coin coin) throws CoffeeMachineException {
		try {
			this.total += coin.getValue();
			this.coin = coin;
			this.coins.add(coin);
			this.indice++;
			factory.getDisplay().info(
					"Total: US$ " + this.total / 100 + "." + this.total % 100);
		} catch (NullPointerException e) {
			throw new CoffeeMachineException("Moeda inválida");
		}
	}

	public void cancelar(ComponentsFactory factory) throws CoffeeMachineException {
		if (this.total == 0) {
			throw new CoffeeMachineException("Não houve moeda inserida");
		}
		this.liberarMoedas(factory, true);

	}

	public void liberarMoedas(ComponentsFactory factory, Boolean confirmacao) {
		if (confirmacao) {
			factory.getDisplay().warn(Messages.CANCEL);
		}
		for (Coin re : this.reverso) {
			for (Coin aux : this.coins) {
				if (aux == re) {
					factory.getCashBox().release(aux);
				}
			}
		}
		this.total = 0;
		this.zerarMoedas();
		factory.getDisplay().info(Messages.INSERT_COINS);
	}

	public boolean planoDeLiberarTroco(ComponentsFactory factory, double troco) {
		this.reverso = Coin.reverse();
		for (Coin c : this.reverso) {
			if (c.getValue() <= troco && factory.getCashBox().count(c) > 0) {
				troco -= c.getValue();
			}
		}
		return (troco == 0);
	}

	public void liberarTroco(ComponentsFactory factory, double valorDaBebida) {
		double troco = this.total - valorDaBebida;
		this.reverso = Coin.reverse();
		for (Coin c : this.reverso) {
			while (c.getValue() <= troco) {
				factory.getCashBox().release(c);
				troco -= c.getValue();
			}
		}
	}

	public boolean conferirDinheiroInserido(ComponentsFactory factory, double valorDaBebida) {
		if (this.total < valorDaBebida || this.total == 0) {
			factory.getDisplay().warn(Messages.NO_ENOUGHT_MONEY);
			this.liberarMoedas(factory,false);
			return false;
		}
		return true;
	}

	public boolean conferirDisponibiliadadeDeTroco(ComponentsFactory factory, double valorDaBebida) {
		if (this.total % valorDaBebida != 0 && this.total > valorDaBebida) {
			if (!this.planoDeLiberarTroco(factory ,this.total - valorDaBebida)) {
				factory.getDisplay().warn(Messages.NO_ENOUGHT_CHANGE);
				this.liberarMoedas(factory, false);
				return false;
			}
		}
		return true;
	}

	public void zerarMoedas() {
		this.coins.clear();
	}

	public int getTotal() {
		return total;
	}
}
