package chat.utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import chat.ivan.Server;

/**
 * @author Ivan Kovalenko
 * @version 1.0.0
 */
public class ServerClientReceiver implements Runnable {

	private ObjectInputStream in;

	private ObjectOutputStream out;

	public ServerClientReceiver(Socket socket) throws IOException {
		this.out = new ObjectOutputStream(socket.getOutputStream());
		this.in = new ObjectInputStream(socket.getInputStream());
	}

	@Override
	public void run() {
		try {
			
			System.out.println("[INFO]: Estoy en client handler run()");

			for (;;) {

				MessageComp messageComp = (MessageComp) in.readObject();

				// If you want to view the conversations in the chat

				/*
				 * System.out.printf("Mensaje recibido de %s:\t %s\n",
				 * messageObject.getNickname(), messageObject.getMessage());
				 */

				Server.broadcast(messageComp);

			}

		} catch (IOException | ClassNotFoundException e) {
			// ignore
		} finally {
			
			try {
				
				in.close();
				out.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void sendMessage(MessageComp messageObject) {
		try {

			out.writeObject(messageObject);
			
			out.flush();
			
		} catch (IOException e) {
			System.err.println("[ERROR]: Se ha producido un error en el hilo -> ServerClientReceiver -> sendMessage()");
			System.err.println("[ERROR]: Detalles: ");
			e.printStackTrace();
		}
	}
}
