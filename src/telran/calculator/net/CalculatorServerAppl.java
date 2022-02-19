package telran.calculator.net;
//ilya40

import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import telran.calculator.service.Calculator;
import telran.calculator.service.CalculatorNonNetworkImpl;

public class CalculatorServerAppl { 

	private static final int PORT = 2000;

	public static void main(String[] args) throws Exception {
		ServerSocket serverSocket = new ServerSocket(PORT);
		System.out.println("Network Calculator server is listening on port " + PORT);
		while(true) {
			Socket socket = serverSocket.accept();
			runCalculatorProtocol(socket);
		}
	}

	private static void runCalculatorProtocol(Socket socket) {
	try(BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintStream writer = new PrintStream(socket.getOutputStream())) {
			while(true) {
				String line = reader.readLine();

				if(line == null) {
					System.out.println("Graceful closing connection");
					break;
				}
				
				line = getResponse(line);
				writer.println(line);
			}
		}catch (Exception e) {
			System.out.println("Client closed connection abnormally");
		}
		
	}

	private static String getResponse(String line) {
		String tokens[] = line.split("#");
		Set<String> operations = new HashSet<>(Arrays.asList("+","-","*","/"));
		if(tokens.length != 3) {
			return "Wrong request";
		} 
		if (!operations.contains(tokens[0])) {	
			return "Unknown request";
		} 
		try {
			Calculator calc = new CalculatorNonNetworkImpl();
			Double res = calc.compute(tokens[0], Double.parseDouble(tokens[1]), 
					Double.parseDouble(tokens[2]));
			return res.toString();
		} catch (Exception ex) {
			return "ParseDouble error";
		}
	}

	

}
