package br.ufpb.dce.aps.coffeemachine;

public interface ComponentsFactory {

	Display getDisplay();

	CashBox getCashBox();
	
	Dispenser getCoffeePowderDispenser();

	Dispenser getWaterDispenser();

	Dispenser getCupDispenser();

	DrinkDispenser getDrinkDispenser();

	Dispenser getSugarDispenser();

	Dispenser getCreamerDispenser();

	Dispenser getBouillonDispenser();

	PayrollSystem getPayrollSystem();

	ButtonDisplay getButtonDisplay();

	Steamer getSteamer();

}
