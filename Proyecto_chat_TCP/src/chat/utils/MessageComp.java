package chat.utils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Ivan Kovalenko
 * @version 1.0.0
 */
public class MessageComp implements Serializable {

	private static final long serialVersionUID = 1L;

	private LocalDateTime localDateTime;

	private String nickname;

	private String message;

	public MessageComp(LocalDateTime localDateTime, String nickname, String message) {
		super();
		this.localDateTime = localDateTime;
		this.nickname = nickname;
		this.message = message;
	}

	public MessageComp(LocalDateTime localDateTime, String nickname) {
		this.localDateTime = localDateTime;
		this.nickname = nickname;
	}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
