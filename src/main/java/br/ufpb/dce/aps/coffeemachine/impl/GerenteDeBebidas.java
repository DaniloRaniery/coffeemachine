package br.ufpb.dce.aps.coffeemachine.impl;

import java.util.HashMap;

import br.ufpb.dce.aps.coffeemachine.Button;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Dispenser;
import br.ufpb.dce.aps.coffeemachine.Messages;
import br.ufpb.dce.aps.coffeemachine.Recipe;

public class GerenteDeBebidas {

	private Bebida bebida;
	private int valorBlack = 35, valorBlackWithSugar = 35, valorWhite = 35, valorWhiteWithSugar = 35;
	private int valorBouillon = 25, valorSweetCream = 50;
	private boolean chave = false;
	private HashMap<String, Dispenser> dispensa = new HashMap<String, Dispenser>();
	
	public void iniciarDrink(Button button, HashMap<String, Dispenser> dispensa) {
		this.dispensa.putAll(dispensa);
		
		if (button == Button.BUTTON_1 || button == Button.BUTTON_3) {
			this.bebida = new Black(button);
		} else if (button == Button.BUTTON_2 || button == Button.BUTTON_4) {
			this.bebida = new White(button);
		} else if (button == Button.BUTTON_5){
			this.bebida = new Bouillon(button);
		}else{
			this.bebida = new Botao_6 (button, dispensa);
		}
	}
	
	public void Mix(ComponentsFactory factory, Button drink) {
		factory.getDisplay().info(Messages.MIXING);
	}

	public void release(ComponentsFactory factory) {
		this.bebida.release(factory);
		factory.getDrinkDispenser().release();
		factory.getDisplay().info(Messages.TAKE_DRINK);

	}

	public boolean conferirIngredientes(ComponentsFactory factory, Button drink) {
		return this.bebida.conferirIngredientes(factory, drink);
	}
		
	
	public int getValorBlack() {
		return valorBlack;
	}

	public void setValorBlack(int valorBlack) {
		this.valorBlack = valorBlack;
	}

	public int getValorBlackWithSugar() {
		return valorBlackWithSugar;
	}

	public void setValorBlackWithSugar(int valorBlackWithSugar) {
		this.valorBlackWithSugar = valorBlackWithSugar;
	}

	public int getValorWhite() {
		return valorWhite;
	}

	public void setValorWhite(int valorWhite) {
		this.valorWhite = valorWhite;
	}

	public int getValorWhiteWithSugar() {
		return valorWhiteWithSugar;
	}

	public void setValorWhiteWithSugar(int valorWhiteWithSugar) {
		this.valorWhiteWithSugar = valorWhiteWithSugar;
	}	
	
	public int getValorBouillon(){
		return valorBouillon;
	}

	public void setValorBouillon(int valorBouillon) {
		this.valorBouillon = valorBouillon;
	}
	
	public int getValorSweetCream() {
		return valorSweetCream;
	}

	public void setValorSweetCream(int valorSweetCream) {
		this.valorSweetCream = valorSweetCream;
	}

	public int getValorDaBebida(Button button){
		if(button == Button.BUTTON_1){
			return this.getValorBlack();
		}
		else if(button == Button.BUTTON_2){
			return this.getValorWhite();
		}
		else if(button == Button.BUTTON_3){
			return this.getValorBlackWithSugar();
		}
		else if(button == Button.BUTTON_4){
			return this.getValorWhiteWithSugar();
		}
		else if (button == Button.BUTTON_5){
			return getValorBouillon();
		}
		else{
			return getValorSweetCream();
		}
	}

	public void setPrecoDaBebida(Button button, int priceCents) {
		if(button == Button.BUTTON_1){
			this.setValorBlack(priceCents);
		}
		else if(button == Button.BUTTON_2){
			this.setValorWhite(priceCents);
		}
		else if(button == Button.BUTTON_3){
			this.setValorBlackWithSugar(priceCents);
		}
		else if(button == Button.BUTTON_4){
			this.setValorWhiteWithSugar(priceCents);
		}
		else if(button == Button.BUTTON_5){
			this.setValorBouillon(priceCents);
		}
		else{
			this.setValorSweetCream(priceCents);
		}
	}

	public void mudarReceita(Button drink, Recipe recipe) {
		
		this.iniciarDrink(drink, this.dispensa);
		
		if(null != recipe.getIngredientQuantity("Water")){
			this.bebida.setAgua(recipe.getIngredientQuantity("Water"));
		}
		if(null != recipe.getIngredientQuantity("Coffee Powder")){
			this.bebida.setPoDeCafe(recipe.getIngredientQuantity("Coffee Powder"));
		}	
		if(recipe.getPriceCents()>0){
			this.setPrecoDaBebida(drink,recipe.getPriceCents());
		}
		
		this.chave = true;
	}
	
	public boolean getChave(){
		return this.chave;
	}
	
	public void setChave(Boolean novaChave){
		this.chave = novaChave;
	}

	public Bebida getDrink(){
		return this.bebida;
	}
}
