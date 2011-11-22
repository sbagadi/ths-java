package ths.template;

import static org.junit.Assert.*;

import org.junit.Test;

import ths.core.exception.ConfigurationException;
import ths.template.base.TemplateConfigurable;
import ths.template.base.TemplateConfiguration;

public class EngineTest {

	@Test
	public void test() {
		TemplateConfiguration configuration = new TemplateConfiguration();
		configuration.load("/E:/code/shop/java/META-INF/httl-default.properties");
		TemplateConfigurable configurable = new TemplateConfigurable();
		try {
			configurable.configure(configuration);
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(configurable.getInputEncoding());
	}

}
