package ths.commons.collection;

import static ths.commons.lang.Assert.*;
import static ths.commons.lang.Constants.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * 容器对象的工具。
 * 
 */
public final class CollectionUtils {
    /**
     * 创建一个<code>List</code>。
     * <p>
     * 和{@code createArrayList(args)}不同，本方法会返回一个不可变长度的列表，且性能高于
     * {@code createArrayList(args)}。
     * </p>
     */
    public static <T> List<T> asList(T... args) {
        if (args == null || args.length == 0) {
            return Collections.emptyList();
        } else {
            return Arrays.asList(args);
        }
    }    
    /**
     * 将列表中的对象连接成字符串。
     */
    public static String join(Iterable<?> objs, String sep) {
        StringBuilder buf = new StringBuilder();

        join(buf, objs, sep);

        return buf.toString();
    }

    /**
     * 将列表中的对象连接起来。
     */
    public static void join(StringBuilder buf, Iterable<?> objs, String sep) {
        try {
            join((Appendable) buf, objs, sep);
        } catch (IOException e) {
            unexpectedException(e);
        }
    }

    /**
     * 将列表中的对象连接起来。
     */
    public static void join(Appendable buf, Iterable<?> objs, String sep) throws IOException {
        if (objs == null) {
            return;
        }

        if (sep == null) {
            sep = EMPTY_STRING;
        }

        for (Iterator<?> i = objs.iterator(); i.hasNext();) {
            buf.append(String.valueOf(i.next()));

            if (i.hasNext()) {
                buf.append(sep);
            }
        }
    }
}
