package sisrh.exception;

import javax.xml.ws.WebFault;

@WebFault(name = "SisRh")
public class SisRhException extends Exception {

	private static final long serialVersionUID = 1L;

	public SisRhException(String mensagem) {
		super(mensagem);
	}
	
	
}
