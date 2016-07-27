package src.main.message;

import java.io.Serializable;
import java.math.BigDecimal;

public class Response implements Serializable{

	private static final long serialVersionUID = 1L;
	public static final String APROVED = "100";
	public static final String REJECTED = "101";
	
	private String cod;
	private String message;
	private BigDecimal amount;
	
}
