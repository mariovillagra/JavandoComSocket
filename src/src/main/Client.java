package src.main;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import src.main.handlers.PacketReceiver;

public class Client {

	private Socket clientSocket; // Socket for outgoing messages
	private PrintStream out;
	private boolean connected = Boolean.FALSE; // connection status
	private ExecutorService es;
	private PacketReceiver receiver;

	public Client()	{
		if ( connected )
			return; // if already connected, return immediately

		try // open Socket connection
		{
			clientSocket = new Socket( InetAddress.getByName( "localhost" ), 3001);
			out = new PrintStream(clientSocket.getOutputStream());	   

			connected = Boolean.TRUE;

		} // end try
		catch ( IOException ioException ) 
		{
			ioException.printStackTrace();
		} // end catch
		
		es = Executors.newFixedThreadPool(10);
		
	}

	// disconnect from server and unregister given MessageListener
	public void disconnect()	{
		if ( !connected )
			return; // if not connected, return immediately

		try // stop listener and disconnect from server
		{     
			// notify server that client is disconnecting
			out.close();
			clientSocket.close();
		} // end try
		catch ( IOException ioException ) 
		{
			ioException.printStackTrace();
		} // end catch

		connected = Boolean.FALSE; // update connected flag
	} // end method disconnect

	// send message to server
	public void sendMessage( String message ) 
	{
		if ( !connected )
			return; // if not connected, return immediately

		out.println(message);
	} // end method sendMessage

	public void handleMessages(IPacketReceived handler){	
		receiver = new PacketReceiver(this.clientSocket, handler);
		
		es.execute(receiver);
	}
	
} 