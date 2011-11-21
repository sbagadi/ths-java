package ths.spring;

/**
 * 用来创建proxy目标对象的工厂。
 *
 */
public interface ProxyTargetFactory {
    Object getObject();
}
