package chat.ivan;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Scanner;

import chat.utils.ClientMessagesReceiver;
import chat.utils.Helper;
import chat.utils.MessageComp;

/**
 * @author Ivan Kovalenko
 * @version 1.0.0
 * @see Semantic Versioning 2.0.0 ==> https://semver.org/
 */
public class Client {

	private static final int PORT = 9090;

	private static final Duration TIME_RECONECTAR = Duration.ofMillis(5000); // 5 sec para reconectar

	private static String NICK = null;

	private static final String HOST = "localhost";

	public static void main(String[] args) {

		for (;;) {
			
			try {
				
				InetAddress address = InetAddress.getByName(HOST);

				try (Socket socket = new Socket(address, PORT);
						
					 ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
						
					 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
						
					 Scanner scanner = new Scanner(System.in)) {
					

					Thread listeningThread = new Thread(new ClientMessagesReceiver(in));

					listeningThread.start();

					Helper.getMessage();
					
					for (int i = 0;; ++i) {
						
						if (i == 0) {
							System.out.println("Ingresa tu nickname: ");
							NICK = scanner.nextLine();
						}
						
						System.out.println("Ingresa el mensaje: ");
						
						String message = scanner.nextLine();

						switch (message) {

						case ":q":
							//NOTE (IVAN): In the future, the implementation of removing from the list of sockets will be needed
							return;

						case ":h":
							
							Helper.getHelp();
							
							break;
							
						default:
							
							out.writeObject(new MessageComp(LocalDateTime.now(), NICK, message));
							
							out.flush();
							
							break;
							
						}
						
						System.out.println("[INFO]: Mensaje ha sido enviado");
						
					}

				} catch (IOException e) {
					System.err.println("[ERROR]: Error de conexión: " + e.getMessage());
					System.err.println("[ERROR]: Intentando reconectar en 5 segundos...");
					Thread.sleep(TIME_RECONECTAR);
				}

			} catch (UnknownHostException e) {
				System.err.println("[ERROR]: Host desconocido: " + e.getMessage());
				break;
			} catch (InterruptedException e) {
				System.err.println("[ERROR]: En el hilo de espera: " + e.getMessage());
				break;
			}
		}
	}
}
