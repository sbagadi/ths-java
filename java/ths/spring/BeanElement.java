package ths.spring;

public class BeanElement {
	private final SingleNamespace sn;
	private final BeanElementKey key;
	private final String parserClassName;
	
	public BeanElement(SingleNamespace sn, BeanElementType type, String name, String parserClassName) {
		this.sn = sn;
		this.key = new BeanElementKey(name, type);
		this.parserClassName = parserClassName;
	}
	
    public SingleNamespace getSingleNamespace() {
        return sn;
    }

    public BeanElementType getType() {
        return key.getType();
    }

    public String getName() {
        return key.getName();
    }

    public BeanElementKey getKey() {
        return key;
    }

    public String getParserClassName() {
        return parserClassName;
    }

    public String getDescription() {
        return String.format("Contribution[%s:%s]", getSingleNamespace().getName(), getName());
    }    
	
}
