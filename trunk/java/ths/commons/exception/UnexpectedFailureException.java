package ths.commons.exception;

/**
 * 代表未预期的失败。
 */
public class UnexpectedFailureException extends RuntimeException {
	
	private static final long serialVersionUID = -6030171114411464858L;

	public UnexpectedFailureException() {
		super();
	}

	public UnexpectedFailureException(String message) {
		super(message);
	}

	public UnexpectedFailureException(Throwable cause) {
		super(cause);
	}

	public UnexpectedFailureException(String message, Throwable cause) {
		super(message, cause);
	}
}
