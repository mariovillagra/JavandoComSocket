package src.main.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

import src.main.IPacketReceived;

public class PacketReceiver implements Runnable {

	private Socket parent_con;
	private IPacketReceived ipk;
	private InputStreamReader in;

	public PacketReceiver( Socket parent, IPacketReceived handler) {
		this.parent_con = parent;
		this.ipk = handler;

		try {
			in = new InputStreamReader(parent_con.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		String received = "";

		try {
			BufferedReader br = new BufferedReader(in);
			Boolean keeplistening = parent_con.isConnected();

			while ( keeplistening ) {
				System.out.println("Listening <" + keeplistening + "> for incomming messages");

				received = br.readLine();
				
				if (received != null)	{
				
					switch (received) {
					case "disconnected":
						keeplistening = Boolean.FALSE;
						break;
					default:
						ipk.received(received);
						break;
					}
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
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
