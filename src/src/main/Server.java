package src.main;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import src.main.handlers.ServerHandler;


public class Server implements Runnable {

	private final ServerSocket ss;
	private final ExecutorService pool;

	public Server() throws IOException {
		ss = new ServerSocket(3001);

		InetAddress inet = ss.getInetAddress();
		System.out.println("Server Messenger listening on: " + inet.getHostName() + " : 3001");

		pool = Executors.newFixedThreadPool(100);
	}

	@Override
	public void run() {
		try {
			while(Boolean.TRUE) {
				pool.execute( new ServerHandler(ss.accept())); 
			}
		} catch (Exception e) {
			e.printStackTrace();
			pool.shutdown();
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server sr = new Server();

					ExecutorService slave = Executors.newFixedThreadPool(1);
					slave.execute(sr);
					slave.shutdown();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}