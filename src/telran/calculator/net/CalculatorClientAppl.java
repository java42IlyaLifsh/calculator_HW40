package telran.calculator.net;
//Ilya40

import java.io.*;

import java.net.*;
import java.util.ArrayList;

import telran.calculator.controller.CalculatorActions;
import telran.calculator.service.Calculator;
import telran.calculator.service.CalculatorNetworkProxy;
import telran.view.*;


public class CalculatorClientAppl {
	private static final int PORT = 2000;
	
	public static void main(String[] args)  { 
		Socket socket;
		BufferedReader reader;
		PrintStream writer;
		
		try {
			socket = new Socket("localhost", PORT);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintStream(socket.getOutputStream());
		} catch (IOException ex) {
			System.out.println("IO Exception: " + ex.toString());
			return;
		}
		Calculator calculator =  new CalculatorNetworkProxy(reader, writer);
		ArrayList<Item> items = CalculatorActions.getCalculatorActions(calculator);
		items.add(Item.of("Exit", iop -> {
					try {
						socket.close();
						reader.close();
						writer.close();
					} catch (IOException ex) {
						System.out.println("Socket close whith Exception: " + ex.toString());
					}
				}, true));
		InputOutput io = new ConsoleInputOutput();
		Menu menu = new Menu("Network Calculator", items);
		menu.perform(io);
	}

}
