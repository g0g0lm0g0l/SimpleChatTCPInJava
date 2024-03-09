package chat.ivan;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import chat.utils.MessageComp;
import chat.utils.ServerClientReceiver;
/**
 * @author Ivan Kovalenko
 * @version 1.0.0
 * @see Semantic Versioning 2.0.0 ==> https://semver.org/
 */
public class Server {

	private static final String 								CHANNEL_PREFIX = "#";
	
	private static final String 								PRIVATE_MESSAGE_PREFIX = "=";

	private static final int     								BACKLOG = 50;

	private static final int    								PORT = 9090;

	private static final String  								HOST = "localhost";

	public static List<ServerClientReceiver>                    LIST_SOCKETS = new CopyOnWriteArrayList<ServerClientReceiver>();
	
	/*
	 * NOTE(IVAN): para '@version 1.0.1' => Implementar mensajes privados que sólo recibirá el cliente destinatario del mensaje
	 */
	
	public static void main(String[] args) {

		try {
			
			final InetAddress bindAddr = InetAddress.getByName(HOST);

			try (ServerSocket serverSocket = new ServerSocket(PORT, BACKLOG, bindAddr)) {

				System.out.format(
						"El servidor está conectado al puerto: %d\tIP : %s puede recibir hasta: %d clientes...\n",
						PORT,
						bindAddr,
						BACKLOG);

				for (;;) {

					Socket socket = serverSocket.accept();

					System.out.printf("[INFO]: Un cliente se ha conectado IP : %s\n", socket.getInetAddress());

					ServerClientReceiver clientHandler = new ServerClientReceiver(socket);

					System.out.println("[INFO]: Una instancia de ServerClientReceiver ha sido creada");

					LIST_SOCKETS.add(clientHandler);

					System.out.println("[INFO]: Se ha añadido el cliente a la lista");

					Thread thread = new Thread(clientHandler);

					System.out.println("[INFO]: El hilo ha sido creado");

					thread.start();

					System.out.println("[INFO]: El hilo ha empezado su ejecución");

				}
			}

		} catch (IOException e) {
			System.err.println("[ERROR]: Se ha producido error ==> Server");
			System.err.println("[ERROR]: Detalles: ");
			e.printStackTrace();
		}
	}

	public static void broadcast(MessageComp messageObject) {
		if (messageObject.getMessage().startsWith(CHANNEL_PREFIX)) {
			/*
			 * NOTE(IVAN): para '@version 1.0.1' => Implementar Canales/rooms
			 * 
			 */
		} else if (messageObject.getMessage().startsWith(PRIVATE_MESSAGE_PREFIX)) {
		
			/*
			 * NOTE(IVAN): para '@version 1.0.1' => Implementar mensajes privados que sólo recibirá el cliente destinatario del mensaje
			 */
		} else {
			LIST_SOCKETS.forEach(socket -> socket.sendMessage(messageObject));
		}
	}

}
