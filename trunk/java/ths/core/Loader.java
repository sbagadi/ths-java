package ths.core;

import java.io.IOException;
import java.util.List;

public interface Loader {
	List<String> list() throws IOException;
	Resource load(String name, String encoding) throws IOException;
}
