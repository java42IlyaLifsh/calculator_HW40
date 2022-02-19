package telran.calculator.controller;
//Ilya40
import java.util.ArrayList;
import telran.calculator.service.Calculator;
import telran.calculator.service.CalculatorNonNetworkImpl;
import telran.view.*;

public class CalculatorRegularAppl { 

	public static void main(String[] args) {
		InputOutput io = new ConsoleInputOutput();
		Calculator calculator = new CalculatorNonNetworkImpl();
		ArrayList<Item> items = CalculatorActions.getCalculatorActions(calculator);
		items.add(Item.exit());
		Menu menu = new Menu("Regular Calculator", items);
		menu.perform(io);

	}

}
