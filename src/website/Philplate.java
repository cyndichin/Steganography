package website;

import java.util.Map;
import java.util.HashMap;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class Philplate {
	private String text;
	private Map<String, String> vars;

	private Philplate(String text) {
		this.text = text;
		this.vars = new HashMap<>();
	}

	public Philplate replace(String from, String to) {
		this.vars.put(from, to);
		return this;
	}

	public String render() {
		String output = text;
		for(String key : vars.keySet()) {
			output = output.replace("{{"+key+"}}", vars.get(key));
		}
		return output;
	}

	public String toString() {
		return this.render();
	}

	public static Philplate fromFile(String file) {
		try {
			byte[] encoded = Files.readAllBytes(Paths.get(file));
			String text = new String(encoded, Charset.defaultCharset());

			return new Philplate(text);
		} catch(IOException e) {
			return null;
		}
	}
}
