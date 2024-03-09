package chat.utils;

import java.io.IOException;
import java.io.ObjectInputStream;

import chat.ivan.Server;
/**
 * @author Ivan Kovalenko
 * @version 1.0.0
 */
public class ClientMessagesReceiver implements Runnable {

	private ObjectInputStream in;

	public ClientMessagesReceiver(ObjectInputStream in) {
		this.in = in;
	}

	@Override
	public void run() {
		
		try {
			
			for (;;) {
				
				MessageComp messageObject = (MessageComp) in.readObject();
				
				System.out.printf("%s => %s:\t %s\n",messageObject.getLocalDateTime(),messageObject.getNickname(), messageObject.getMessage());
				
				Server.broadcast(messageObject);
			
			}

		} catch (IOException | ClassNotFoundException e) {
			//ignore
		}

	}

}
