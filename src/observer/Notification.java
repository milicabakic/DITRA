package observer;

import observer.enums.NotificationCode;

public class Notification {
	
    private NotificationCode code;
    private Object data;
    
    public Notification(NotificationCode code, Object data) {
		super();
		this.code = code;
		this.data = data;
	}
    
	public NotificationCode getCode() {
		return code;
	}
    public Object getData() {
		return data;
	}
}
