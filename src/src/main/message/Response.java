package src.main.message;

import java.io.Serializable;
import java.math.BigDecimal;

public class Response implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String cod;
	private String message;
	private BigDecimal amount;
	
}
