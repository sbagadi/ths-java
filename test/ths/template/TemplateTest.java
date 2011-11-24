package ths.template;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import ths.template.model.Book;
import ths.template.model.User;

public class TemplateTest {

	@Test
	public void test() throws IOException, ParseException {
	   int size = 2;
	    Random random = new Random();
	    Book[] books = new Book[size];
	    for (int i = 0; i < size; i ++) {
	        books[i] = new Book(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), new Date(), random.nextInt(100) + 10, random.nextInt(60) + 30);
	    }
	    Map<String, Object> context = new HashMap<String, Object>();
	    context.put("user", new User("liangfei", "admin"));
	    context.put("books", books);
	        
		// Get engine (Singleton instance, multi-thread shared.)
		Engine engine = Engine.getEngine("ths.properties");

		// Create context (Prototype instance, thread-local hold.)
		//Map<String, Object> context = new HashMap<String, Object>();
		//context.put("user", userService.findUser(userId));
		//context.put("books", bookService.findUserBooks(userId));

		// Render template to response.
		//Map<String, Object> context = new HashMap<String, Object>();
		StringWriter writer = new StringWriter();
		engine.getTemplate("templates/books.html").render(context, writer);
		System.out.println(writer.getBuffer().toString());
		Assert.assertTrue(true);
	}

}
