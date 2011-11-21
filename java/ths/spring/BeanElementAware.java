package ths.spring;

/**
 * 假如某个<code>BeanDefinitionParser</code>或者<code>BeanDefinitionDecorator</code>
 * 实现了这个接口，那么它所属的<code>Contribution</code>实例将被设置进去。
 *
 */
public interface BeanElementAware {
    void setBeanElement(BeanElement element);
}
