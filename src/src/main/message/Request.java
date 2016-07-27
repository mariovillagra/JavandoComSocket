package src.main.message;

import java.io.Serializable;

public class Request implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String method;
	private String user;
	private String PIN;
	
}
