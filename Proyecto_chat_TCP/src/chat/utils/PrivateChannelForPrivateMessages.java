package chat.utils;

/**
 * @author Ivan Kovalenko
 * @version 1.0.0
 */
/*
 * NOTE(IVAN): para '@version 1.0.1' => Implementar mensajes privados que sólo
 * recibirá el cliente destinatario del mensaje
 */
public class PrivateChannelForPrivateMessages {

	private String user1;
	private String user2;

	public PrivateChannelForPrivateMessages(String user1, String user2) {
		this.user1 = user1;
		this.user2 = user2;
	}

	public boolean containsUser(String nickname) {
		return user1.equals(nickname) || user2.equals(nickname);
	}
}
