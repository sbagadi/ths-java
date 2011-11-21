package ths.spring;

public class ServiceNamespaceException extends RuntimeException {
    
	private static final long serialVersionUID = -8270969902649401442L;

    public ServiceNamespaceException() {
        super();
    }

    public ServiceNamespaceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceNamespaceException(String message) {
        super(message);
    }

    public ServiceNamespaceException(Throwable cause) {
        super(cause);
    }

}
