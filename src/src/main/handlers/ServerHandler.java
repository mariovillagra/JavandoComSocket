package src.main.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;

import src.main.Billetera;

public class ServerHandler implements Runnable{
	private final Socket socket;
	private InputStreamReader in;

	public ServerHandler(Socket socket) { 
		this.socket = socket;

		try {
			in = new InputStreamReader(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
		System.out.println("Transaction from: " + socket.getInetAddress().getHostAddress());
		String received = "";

		BufferedReader br = new BufferedReader(in);

		try {

			PrintStream pw = new PrintStream(socket.getOutputStream());
			
			Boolean keeplistening = socket.isConnected();

			while ( keeplistening ) {

				System.out.println("Socket connected status: " + !socket.isClosed());

				received = br.readLine();

				if (received != null)	{
					if (received.equalsIgnoreCase("disconnected")) {
						keeplistening = Boolean.FALSE;
						pw.println("disconnected");
						System.out.println("Server: Disconnecting");					
						br.close();
						in.close();
						pw.close();		
					}
					String[] tokens = received.split("|");
					String nro_telefono = tokens[0];
					String Monto = tokens[1];
					String Pin = tokens[2];
					
					// Apply to Billetera
					Billetera b = new Billetera();
					// invoke methods
					// Repond to client
				}
			}

		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
	}
}