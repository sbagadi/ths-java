package ths.core.exception;

/**
 * 代表<code>META-INF/services/</code>中的文件未找到或读文件失败的异常。
 */
public class ServiceNotFoundException extends ClassNotFoundException {
    private static final long serialVersionUID = -2993107602317534281L;

    public ServiceNotFoundException() {
        super();
    }

    public ServiceNotFoundException(String message) {
        super(message);
    }

    public ServiceNotFoundException(Throwable cause) {
        super(null, cause);
    }

    public ServiceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
