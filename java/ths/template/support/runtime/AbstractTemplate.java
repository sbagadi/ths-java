package ths.template.support.runtime;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ths.core.Loader;
import ths.core.Resource;

import ths.template.Context;
import ths.template.Engine;
import ths.template.Template;
import ths.template.support.Filter;
import ths.template.support.Formatter;
import ths.template.support.formatters.MultiFormatter;
import ths.template.util.IOUtils;
import ths.template.util.StringUtils;
import ths.template.util.UnsafeByteArrayOutputStream;

/**
 * Abstract template. (SPI, Prototype, ThreadSafe)
 * 
 * @see com.googlecode.httl.Engine#getTemplate(String)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public abstract class AbstractTemplate implements Template, Serializable {
    
    private static final long serialVersionUID = 8780375327644594903L;
    
    private transient final Engine engine;
    
    private transient final Filter filter;
    
    private transient final Formatter<Object> formatter;
    
    private transient final Formatter<Boolean> booleanFormatter;
    
    private transient final Formatter<Number> byteFormatter;
    
    private transient final Formatter<Character> charFormatter;
    
    private transient final Formatter<Number> shortFormatter;
    
    private transient final Formatter<Number> intFormatter;
    
    private transient final Formatter<Number> longFormatter;
    
    private transient final Formatter<Number> floatFormatter;
    
    private transient final Formatter<Number> doubleFormatter;

    private transient final Formatter<Number> numberFormatter;

    private transient final Formatter<Date> dateFormatter;

    private transient final String nullValue;

    private transient final String trueValue;

    private transient final String falseValue;

    private final String name;
    
    private final String encoding;

	private final long lastModified;

    private final long length;
    
	private final String source;
	
	@SuppressWarnings("unchecked")
    public AbstractTemplate(Engine engine, Resource resource) {
		this.engine = engine;
		this.name = resource.getName();
		this.encoding = resource.getEncoding();
		this.lastModified = resource.getLastModified();
		this.length = resource.getLength();
		try {
            this.source = IOUtils.readToString(resource.getReader());
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
		this.filter = engine.getFilter();
		this.formatter = (Formatter<Object>) engine.getFormatter();
		if (formatter instanceof MultiFormatter) {
		    MultiFormatter multi = (MultiFormatter) formatter;
    		this.numberFormatter = multi.get(Number.class);
    		this.booleanFormatter = multi.get(Boolean.class);
    		this.byteFormatter = getFormatter(multi, Byte.class, numberFormatter);
    		this.charFormatter = multi.get(Character.class);
    		this.shortFormatter = getFormatter(multi, Short.class, numberFormatter);
    		this.intFormatter = getFormatter(multi, Integer.class, numberFormatter);
    		this.longFormatter = getFormatter(multi, Long.class, numberFormatter);
    		this.floatFormatter = getFormatter(multi, Float.class, numberFormatter);
    		this.doubleFormatter = getFormatter(multi, Double.class, numberFormatter);
    		this.dateFormatter = multi.get(Date.class);
		} else {
		    this.numberFormatter = null;
            this.booleanFormatter = null;
            this.byteFormatter = null;
            this.charFormatter = null;
            this.shortFormatter = null;
            this.intFormatter = null;
            this.longFormatter = null;
            this.floatFormatter = null;
            this.doubleFormatter = null;
            this.dateFormatter = null;
		}
		this.nullValue = engine.getConfiguration().getNullValue();
		this.trueValue = engine.getConfiguration().getTrueValue();
		this.falseValue = engine.getConfiguration().getFalseValue();
	}
	
	@SuppressWarnings("unchecked")
    private static Formatter<Number> getFormatter(MultiFormatter multi, Class<? extends Number> type, Formatter<Number> defaultFormatter) {
	    Formatter<Number> formatter = multi.get((Class<Number>)type);
	    if (formatter == null) {
	        return defaultFormatter;
	    }
	    return formatter;
	}
	
	public Engine getEngine() {
		return engine;
	}
	
	public String getSource() throws IOException {
        return IOUtils.readToString(getReader());
    }

    public Loader getLoader() {
        return getEngine().getLoader();
    }

	public String getName() {
		return name;
	}

    public String getEncoding() {
        return encoding;
    }

    public long getLastModified() {
        return lastModified;
    }

    public long getLength() {
        return length;
    }

    public Reader getReader() throws IOException {
        return new StringReader(source);
    }

    public Object evaluate(Map<String, Object> parameters) throws ParseException {
        return render(parameters);
    }

    public String render() {
        return render(Context.getContext().getParameters());
    }

    public void render(Writer writer) throws IOException {
        render(Context.getContext().getParameters(), writer);
    }
    
    public void render(OutputStream output) throws IOException {
        render(Context.getContext().getParameters(), output);
    }
    
    public String render(Object[] parameters) {
        return render(toMap(parameters));
    }

    public void render(Object[] parameters, Writer writer) throws IOException {
        render(toMap(parameters), writer);
    }
    
    public void render(Object[] parameters, OutputStream output) throws IOException {
        render(toMap(parameters), output);
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        String name = getName();
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        AbstractTemplate other = (AbstractTemplate) obj;
        String name = getName();
        String otherName = other.getName();
        if (name == null) {
            if (otherName != null) return false;
        } else if (!name.equals(otherName)) return false;
        return true;
    }
    
    @Override
    public String toString() {
        return render();
    }

    private Map<String, Object> toMap(Object[] parameters) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (parameters == null || parameters.length == 0) {
            return map;
        }
        String[] names = getParameterTypes().keySet().toArray(new String[0]);
        if (names == null || names.length < parameters.length) {
            throw new IllegalArgumentException("Mismatch template parameters. names: " + Arrays.toString(names) + ", values: " + Arrays.toString(parameters));
        }
        for (int i = 0; i < parameters.length; i ++) {
            map.put(names[i], parameters[i]);
        }
        return map;
    }
    
    protected String toString(UnsafeByteArrayOutputStream output) {
        String encoding = engine.getConfiguration().getOutputEncoding();
        if (encoding != null && encoding.length() > 0) {
            try {
                return new String(output.toByteArray(), encoding);
            } catch (UnsupportedEncodingException e) {
                throw new IllegalStateException(e);
            }
        } else {
            return new String(output.toByteArray());
        }
    }
    
    protected String filter(String value) {
        if (filter != null)
            return filter.filter(value);
        return value;
    }

    protected String format(Object value) {
        if (formatter != null)
            return formatter.format(value);
        if (value == null)
            return nullValue;
        return StringUtils.toString(value);
    }
    
    protected String format(String value) {
        if (value == null)
            return nullValue;
        return value;
    }
    
    protected String format(boolean value) {
        if (booleanFormatter != null)
            return booleanFormatter.format(value);
        return value ? trueValue : falseValue;
    }
    
    protected String format(byte value) {
        if (byteFormatter != null)
            return byteFormatter.format(value);
        return String.valueOf(value);
    }

    protected String format(char value) {
        if (charFormatter != null)
            return charFormatter.format(value);
        return String.valueOf(value);
    }

    protected String format(short value) {
        if (shortFormatter != null)
            return shortFormatter.format(value);
        return String.valueOf(value);
    }

    protected String format(int value) {
        if (intFormatter != null)
            return intFormatter.format(value);
        return String.valueOf(value);
    }

    protected String format(long value) {
        if (longFormatter != null)
            return longFormatter.format(value);
        return String.valueOf(value);
    }

    protected String format(float value) {
        if (floatFormatter != null)
            return floatFormatter.format(value);
        return String.valueOf(value);
    }
    
    protected String format(double value) {
        if (doubleFormatter != null)
            return doubleFormatter.format(value);
        return String.valueOf(value);
    }
    
    protected String format(Boolean value) {
        if (booleanFormatter != null) 
            return booleanFormatter.format(value);
        if (value == null)
            return nullValue;
        return value.booleanValue() ? trueValue : falseValue;
    }
    
    protected String format(Byte value) {
        if (byteFormatter != null) 
            return byteFormatter.format(value);
        if (value == null)
            return nullValue;
        return value.toString();
    }

    protected String format(Character value) {
        if (charFormatter != null) 
            return charFormatter.format(value);
        if (value == null)
            return nullValue;
        return value.toString();
    }

    protected String format(Short value) {
        if (shortFormatter != null) 
            return shortFormatter.format(value);
        if (value == null)
            return nullValue;
        return value.toString();
    }

    protected String format(Integer value) {
        if (intFormatter != null) 
            return intFormatter.format(value);
        if (value == null)
            return nullValue;
        return value.toString();
    }
    
    protected String format(Long value) {
        if (longFormatter != null) 
            return longFormatter.format(value);
        if (value == null)
            return nullValue;
        return value.toString();
    }
    
    protected String format(Double value) {
        if (doubleFormatter != null) 
            return doubleFormatter.format(value);
        if (value == null)
            return nullValue;
        return value.toString();
    }
    
    protected String format(Number value) {
        if (numberFormatter != null) 
            return numberFormatter.format(value);
        if (value == null)
            return nullValue;
        return value.toString();
    }
    
    protected String format(Date value) {
        if (dateFormatter != null) 
            return dateFormatter.format(value);
        if (value == null)
            return nullValue;
        return value.toString();
    }
    
    protected byte[] serialize(String value) {
        return value == null ? new byte[0] : value.getBytes();
    }

}
