package ths.template.support.functions;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import ths.core.Resource;
import ths.core.Configurable;
import ths.template.TemplateConfiguration;
import ths.template.Context;
import ths.template.Template;
import ths.template.support.runtime.Cycle;
import ths.template.util.ClassUtils;
import ths.template.util.DateUtils;
import ths.template.util.IOUtils;
import ths.template.util.NumberUtils;
import ths.template.util.StringUtils;
import ths.template.util.UrlUtils;

/**
 * DefaultFunction. (SPI, Singleton, ThreadSafe)
 * 
 * @see com.googlecode.httl.Engine#addFunctions(Object...)
 * @see com.googlecode.httl.Engine#setFunctions(Object...)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class DefaultFunction implements Configurable<TemplateConfiguration> {
    
    private static final Random RANDOM = new Random();
    
    private String dateFormat;

    private String numberFormat;

    protected String[] importPackages;
    
    @Override
    public void configure(TemplateConfiguration config) {
        String format = config.getDateFormat();
        format = config.getNumberFormat();
        String packages = config.getImportPackages();
        
        if (format != null && format.trim().length() > 0) {
            format = format.trim();
            new SimpleDateFormat(format).format(new Date());
            this.dateFormat = format;
        }
        
        if (format != null && format.trim().length() > 0) {
            format = format.trim();
            new DecimalFormat(format).format(0);
            this.numberFormat = format;
        }
        
        if (packages != null && packages.trim().length() > 0) {
            importPackages = packages.trim().split("\\s*\\,\\s*");
        }
    }
    
    public Date now() {
        return new Date();
    }
    
    public int random() {
        return RANDOM.nextInt();
    }
    
    public UUID uuid() {
        return UUID.randomUUID();
    }

    public String include(String name) throws IOException, ParseException {
        return include(name, null);
    }
    
    public String include(String name, String encoding) throws IOException, ParseException {
        return parse(name, encoding).render();
    }
    
    public String read(String name) throws IOException, ParseException {
        return read(name, null);
    }
    
    public String read(String name, String encoding) throws IOException {
        return IOUtils.readToString(load(name, encoding).getReader());
    }
    
    public Template parse(String name) throws IOException, ParseException {
        return parse(name, null);
    }
    
    public Template parse(String name, String encoding) throws IOException, ParseException {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("include template name == null");
        }
        Template template = Context.getContext().getTemplate();
        if (template == null) {
            throw new IllegalArgumentException("include context template == null");
        }
        if (encoding == null || encoding.length() == 0) {
            encoding = template.getEncoding();
        }
        name = UrlUtils.relativeUrl(name, template.getName());
        return template.getEngine().getTemplate(name, encoding);
    }
    
    public Resource load(String name) throws IOException, ParseException {
        return load(name, null);
    }
    
    public Resource load(String name, String encoding) throws IOException {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("display template name == null");
        }
        Template template = Context.getContext().getTemplate();
        if (template == null) {
            throw new IllegalArgumentException("display context template == null");
        }
        if (encoding == null || encoding.length() == 0) {
            encoding = template.getEncoding();
        }
        name = UrlUtils.relativeUrl(name, template.getName());
        return template.getEngine().getLoader().load(name, encoding);
    }
    
    public Object evaluate(String expr) throws ParseException {
        Template template = Context.getContext().getTemplate();
        if (template == null) {
            throw new IllegalArgumentException("display context template == null");
        }
        return template.getEngine().getTranslator().translate(expr, template.getParameterTypes(), 0).evaluate(Context.getContext().getParameters());
    }
    
    public String escapeString(String value) {
        return StringUtils.escapeString(value);
    }

    public String escapeHtml(String value) {
        return StringUtils.escapeHtml(value);
    }
    
    public boolean toBoolean(String value) {
        return Boolean.parseBoolean(value);
    }
    
    public char toChar(String value) {
        return value == null || value.length() == 0 ? '\0' : value.charAt(0);
    }
    
    public byte toByte(String value) {
        return Byte.parseByte(value);
    }

    public short toShort(String value) {
        return Short.parseShort(value);
    }

    public int toInt(String value) {
        return Integer.parseInt(value);
    }

    public long toLong(String value) {
        return Long.parseLong(value);
    }

    public float toFloat(String value) {
        return Float.parseFloat(value);
    }

    public double toDouble(String value) {
        return Double.parseDouble(value);
    }
    
    public Class<?> toClass(String value) {
        return ClassUtils.forName(value);
    }
    
    public Object[] toArray(Collection<?> value, String type) {
        Class<?> cls = ClassUtils.forName(importPackages, type);
        return value.toArray((Object[])Array.newInstance(cls, 0));
    }
    
    public Date toDate(String value) {
        try {
            return DateUtils.toDate(value, dateFormat);
        } catch (Exception e) {
            try {
                return DateUtils.toDate(value, "yyyy-MM-dd");
            } catch (Exception e2) {
                return DateUtils.toDate(value, "yyyy-MM-dd HH:mm:ss");
            }
        }
    }
    
    public Date toDate(String value, String format) {
        return DateUtils.toDate(value, format);
    }
    
    public String toString(Date value) {
        return DateUtils.formatDate(value, dateFormat);
    }
    
    public String format(Date value, String format) {
        return DateUtils.formatDate(value, format);
    }
    
    public String toString(boolean value) {
        return String.valueOf(value);
    }
    
    public String toString(char value) {
        return String.valueOf(value);
    }
    
    public String toString(byte value) {
        return format(Byte.valueOf(value), numberFormat);
    }
    
    public String toString(short value) {
        return format(Short.valueOf(value), numberFormat);
    }
    
    public String toString(int value) {
        return format(Integer.valueOf(value), numberFormat);
    }
    
    public String toString(long value) {
        return format(Long.valueOf(value), numberFormat);
    }
    
    public String toString(float value) {
        return format(Float.valueOf(value), numberFormat);
    }
    
    public String toString(double value) {
        return format(Double.valueOf(value), numberFormat);
    }
    
    public String toString(Number value) {
        return format(value, numberFormat);
    }
    
    public String format(byte value, String format) {
        return format(Byte.valueOf(value), format);
    }
    
    public String format(short value, String format) {
        return format(Short.valueOf(value), format);
    }
    
    public String format(int value, String format) {
        return format(Integer.valueOf(value), format);
    }
    
    public String format(long value, String format) {
        return format(Long.valueOf(value), format);
    }
    
    public String format(float value, String format) {
        return format(Float.valueOf(value), format);
    }
    
    public String format(double value, String format) {
        return format(Double.valueOf(value), format);
    }
    
    public String format(Number value, String format) {
        return NumberUtils.formatNumber(value, format);
    }

    public Cycle toCycle(Collection<?> values) {
        return new Cycle(values);
    }

    public Cycle toCycle(Object[] values) {
        return new Cycle(values);
    }
    
    public Cycle toCycle(boolean[] values) {
        return new Cycle(values);
    }
    
    public Cycle toCycle(char[] values) {
        return new Cycle(values);
    }
    
    public Cycle toCycle(byte[] values) {
        return new Cycle(values);
    }
    
    public Cycle toCycle(short[] values) {
        return new Cycle(values);
    }
    
    public Cycle toCycle(int[] values) {
        return new Cycle(values);
    }
    
    public Cycle toCycle(long[] values) {
        return new Cycle(values);
    }
    
    public Cycle toCycle(float[] values) {
        return new Cycle(values);
    }
    
    public Cycle toCycle(double[] values) {
        return new Cycle(values);
    }
    
    public int length(Map<?, ?> values) {
        return values == null ? 0 : values.size();
    }
    
    public int length(Collection<?> values) {
        return values == null ? 0 : values.size();
    }

    public int length(Object[] values) {
        return values == null ? 0 : values.length;
    }
    
    public int length(boolean[] values) {
        return values == null ? 0 : values.length;
    }
    
    public int length(char[] values) {
        return values == null ? 0 : values.length;
    }
    
    public int length(byte[] values) {
        return values == null ? 0 : values.length;
    }
    
    public int length(short[] values) {
        return values == null ? 0 : values.length;
    }
    
    public int length(int[] values) {
        return values == null ? 0 : values.length;
    }
    
    public int length(long[] values) {
        return values == null ? 0 : values.length;
    }
    
    public int length(float[] values) {
        return values == null ? 0 : values.length;
    }
    
    public int length(double[] values) {
        return values == null ? 0 : values.length;
    }
    
    public String repeat(String value, int count) {
        if (value == null || value.length() == 0 || count <= 0) {
            return value;
        }
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < count; i ++) {
            buf.append(value);
        }
        return buf.toString();
    }
    
}
