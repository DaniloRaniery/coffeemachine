package br.ufpb.dce.aps.coffeemachine.impl;

import java.util.HashMap;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Button;
import br.ufpb.dce.aps.coffeemachine.Dispenser;
import br.ufpb.dce.aps.coffeemachine.Messages;
import br.ufpb.dce.aps.coffeemachine.Recipe;

public class GerenteDeMaquina {

	private GerenteDeBebidas gerenteDeBebidas = new GerenteDeBebidas();
	private static String modo = "";
	private int cracha = 0;
	private HashMap<String, Dispenser> dispensa = new HashMap<String, Dispenser>();
	

	public void iniciarPedidoDeBebida(ComponentsFactory factory,
			GerenteFinanceiro gerenteFinanceiro, Button drink) {
		if (modo.equals("cracha")) {
			this.iniciarPedidoComCracha(factory, drink);
		} else {
			this.iniciarPedidoComMoedas(factory, gerenteFinanceiro, drink);
		}
	}

	public void iniciarPedidoComMoedas(ComponentsFactory factory,
			GerenteFinanceiro gerenteFinanceiro, Button button) {

		if (!this.gerenteDeBebidas.getChave()) {
			this.gerenteDeBebidas.iniciarDrink(button, this.dispensa);
		}

		if (!gerenteFinanceiro.conferirDinheiroInserido(factory,
				this.gerenteDeBebidas.getValorDaBebida(button))) {
			return;
		}

		if (!this.gerenteDeBebidas.conferirIngredientes(factory, button)) {
			gerenteFinanceiro.liberarMoedas(factory, false);
			return;
		}
		
		if (!gerenteFinanceiro.conferirDisponibiliadadeDeTroco(factory,
				this.gerenteDeBebidas.getValorDaBebida(button))) {
			return;
		}

		this.gerenteDeBebidas.Mix(factory, button);
		this.gerenteDeBebidas.release(factory);

		if (gerenteFinanceiro.getTotal() >= this.gerenteDeBebidas
				.getValorDaBebida(button)) {
			gerenteFinanceiro.liberarTroco(factory,
					this.gerenteDeBebidas.getValorDaBebida(button));
		}

		this.reIniciar(factory);
		gerenteFinanceiro.zerarMoedas();
		this.gerenteDeBebidas.setChave(false);
	}

	public void iniciarPedidoComCracha(ComponentsFactory factory, Button button) {
		this.gerenteDeBebidas.iniciarDrink(button, this.dispensa);

		if (!this.gerenteDeBebidas.conferirIngredientes(factory, button)) {
			return;
		}
		
		if (!factory.getPayrollSystem().debit(
				gerenteDeBebidas.getValorDaBebida(button), this.cracha)) {
			factory.getDisplay().warn(Messages.UNKNOWN_BADGE_CODE);
			this.reIniciar(factory);
			return;
		}

		this.gerenteDeBebidas.Mix(factory, button);
		this.gerenteDeBebidas.release(factory);

		this.reIniciar(factory);
	}

	public void iniciar(ComponentsFactory factory) {
		this.mensagemInicial(factory);
		factory.getDisplay().info(Messages.INSERT_COINS);
		GerenteDeMaquina.setModo("moedas");
	}

	public void mensagemInicial(ComponentsFactory factory) {
		this.mensagemInicial(factory, Button.BUTTON_1);
	}

	public void mensagemInicial(ComponentsFactory factory, Button button) {
		if (button.equals(Button.BUTTON_6)) {
			factory.getButtonDisplay().show(
					"Black: $0." + this.gerenteDeBebidas.getValorBlack(),
					"White: $0." + this.gerenteDeBebidas.getValorWhite(),
					"Black with sugar: $0."
							+ this.gerenteDeBebidas.getValorBlackWithSugar(),
					"White with sugar: $0."
							+ this.gerenteDeBebidas.getValorWhiteWithSugar(),
					"Bouillon: $0." + this.gerenteDeBebidas.getValorBouillon(),
					"Sweet cream: $0."
							+ this.gerenteDeBebidas.getValorSweetCream(), null);
		} else {
			factory.getButtonDisplay().show(
					"Black: $0." + this.gerenteDeBebidas.getValorBlack(),
					"White: $0." + this.gerenteDeBebidas.getValorWhite(),
					"Black with sugar: $0."
							+ this.gerenteDeBebidas.getValorBlackWithSugar(),
					"White with sugar: $0."
							+ this.gerenteDeBebidas.getValorWhiteWithSugar(),
					"Bouillon: $0." + this.gerenteDeBebidas.getValorBouillon(),
					null, null);
		}
	}

	public void iniciarComCracha(ComponentsFactory factory,
			GerenteFinanceiro gerenteAuxiliar, int cracha) {
		if (gerenteAuxiliar.getTotal() > 0) {
			factory.getDisplay().warn(Messages.CAN_NOT_READ_BADGE);
			return;
		} else {
			factory.getDisplay().info(Messages.BADGE_READ);
			this.cracha = cracha;
			GerenteDeMaquina.setModo("cracha");
		}
	}

	public static void setModo(String novoModo) {
		modo = novoModo;
	}

	public String getModo() {
		return modo;
	}

	public void reIniciar(ComponentsFactory factory) {
		factory.getDisplay().info(Messages.INSERT_COINS);
		GerenteDeMaquina.setModo(" ");
	}

	public void setPrecoDaBebida(Button drink, int priceCents) {
		this.gerenteDeBebidas.setPrecoDaBebida(drink, priceCents);
	}

	public void mudarReceita(Button drink, Recipe recipe) {
		this.gerenteDeBebidas.mudarReceita(drink, recipe);

	}

	public void adicionarDispensa(String ingredient, Dispenser dispenser) {
		this.dispensa.put(ingredient, dispenser);
	}

}
