package website;

import static spark.Spark.get;
import static spark.Spark.post;


public class Main {
	
	public static void main(String[] args) {
		// The only page viewable
		// Simple opens up an html file and returns its contents
		get("/", (req, res) -> {
			Philplate page = Philplate.fromFile("index.html");
			return page.render();
		});
		
		// The encryption page
		get("/encrypt", (req, res) -> {
			return null;
		});
		
		
		// This is queried when the form is submitted
		// If all fields are valid return "percentage=some_number"
		// If at least 1 field is invalid, return all invalid fields
		// in a space separated list
		post("/encrypting", (req, res) -> {
			return null;
		});
	}
}
