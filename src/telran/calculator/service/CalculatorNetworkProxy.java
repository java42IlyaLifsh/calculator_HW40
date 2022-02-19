package telran.calculator.service;
//Ilya40 
import java.io.*;


public class CalculatorNetworkProxy implements Calculator { 
	private BufferedReader reader;
	private PrintStream writer;

	public CalculatorNetworkProxy(BufferedReader reader, PrintStream writer) {
		this.reader = reader;
		this.writer = writer;
	}

	@Override
	public double compute(String operator, double op1, double op2) {
		try {
			writer.println(operator + "#" + Double.toString(op1) + "#" + Double.toString(op2));
			return Double.parseDouble(reader.readLine());
		} catch (Exception ex) {
			throw new RuntimeException(ex.toString());
		}
	}
}
