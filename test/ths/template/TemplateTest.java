package ths.template;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class TemplateTest {

	@Test
	public void test() {
		// Get engine (Singleton instance, multi-thread shared.)
		Engine engine = Engine.getEngine();

		// Create context (Prototype instance, thread-local hold.)
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("user", userService.findUser(userId));
		context.put("books", bookService.findUserBooks(userId));

		// Render template to response.
		engine.getTemplate("books.httl").render(context, response.getOutputStream());
	}

}
