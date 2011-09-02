package ths.core.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

public class IOUtils {
	
	/**
	 * 
	 * @param input
	 */
    public static void closeQuietly(Reader input) {
        closeQuietly((Closeable)input);
    }

    /**
     * Unconditionally close a <code>Writer</code>.
     *
     * @param output  the Writer to close, may be null or already closed
     */
    public static void closeQuietly(Writer output) {
        closeQuietly((Closeable)output);
    }

    /**
     * Unconditionally close an <code>InputStream</code>.
     * 
     * @param input  the InputStream to close, may be null or already closed
     */
    public static void closeQuietly(InputStream input) {
        closeQuietly((Closeable)input);
    }
    
    /**
     * 
     * @param output
     */
    public static void closeQuietly(OutputStream output) {
        closeQuietly((Closeable)output);
    }
    
    /**
     * Unconditionally close an <code>OutputStream</code>.
     * <p>
     * Equivalent to {@link OutputStream#close()}, except any exceptions will be ignored.
     * This is typically used in finally blocks.
     * <p>
     * Example code:
     * <pre>
     * byte[] data = "Hello, World".getBytes();
     *
     * OutputStream out = null;
     * try {
     *     out = new FileOutputStream("foo.txt");
     *     out.write(data);
     *     out.close(); //close errors are handled
     * } catch (IOException e) {
     *     // error handling
     * } finally {
     *     IOUtils.closeQuietly(out);
     * }
     * </pre>
     *
     * @param output  the OutputStream to close, may be null or already closed
     */   
    public static void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }
}
