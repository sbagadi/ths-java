package ths.template.support.runtime;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import ths.core.Resource;
import ths.template.Engine;
import ths.template.Template;
import ths.template.Context;
import ths.template.util.ClassUtils;
import ths.template.util.UnsafeStringWriter;

/**
 * Writer template. (SPI, Prototype, ThreadSafe)
 * 
 * @see com.googlecode.httl.Engine#getTemplate(String)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public abstract class WriterTemplate extends AbstractTemplate {
    
    private static final long serialVersionUID = 7127901461769617745L;
    
    public WriterTemplate(Engine engine, Resource resource){
        super(engine, resource);
    }
    
    public String render(Map<String, Object> parameters) {
        UnsafeStringWriter output = new UnsafeStringWriter();
        try {
            render(parameters, output);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return output.toString();
    }
    
    public void render(Map<String, Object> parameters, OutputStream output) throws IOException {
        render(parameters, new OutputStreamWriter(output));
    }
    
    public void render(Map<String, Object> parameters, Writer writer) throws IOException {
        if(parameters == null) {
            parameters = new HashMap<String, Object>();
        }
        Context context = Context.getContext();
        Template preTemplate = context.getTemplate();
        Map<String, Object> preParameters = context.getParameters();
        context.setTemplate(this).setParameters(parameters);
        try {
            doRender(parameters, writer);
        } catch (RuntimeException e) {
            throw (RuntimeException) e;
        } catch (IOException e) {
            throw (IOException) e;
        } catch (Exception e) {
            throw new IllegalStateException(ClassUtils.toString(e), e);
        } finally {
            context.setTemplate(preTemplate).setParameters(preParameters);
        }
    }
    
    protected abstract void doRender(Map<String, Object> parameters, Writer output) throws Exception;
    
}
