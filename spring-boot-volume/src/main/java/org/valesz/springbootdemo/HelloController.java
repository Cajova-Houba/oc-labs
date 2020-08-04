package org.valesz.springbootdemo;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

@RestController
public class HelloController {

	public static final String FILE_NAME = "/var/test-data/data.txt";

	@RequestMapping("/")
	public String index() {
		String text = loadFileContents(FILE_NAME);
		return "This is my cool Spring Boot app which I'm using to test the OC. File content:<br>"+text;
	}

	/**
	 * Checks if the file exists and adds one line to its' content if it does. If it does not, empty file is created.
	 * @param fileName Name of the file.
	 * @return Content of the file.
	 */
	private String loadFileContents(String fileName) {
		File f = new File(fileName);

		final StringBuilder content = new StringBuilder();

		if (!f.exists()) {
			try {
				if (!f.createNewFile()) {
					content.append("New file not created.<br>");
				}
			} catch (IOException e) {
				content.append("Error while creating file: ")
						.append(e.getMessage())
						.append("<br>");
			}
		}

		if (f.exists()) {
			try {
				Files.write(f.toPath(), new Date().toString().getBytes());
				Files.readAllLines(f.toPath()).forEach(l -> content.append(l).append("<br>"));
			} catch (IOException e) {
				content.append("Error while reading the file: ")
						.append(e.getMessage())
						.append("<br>");
			}
		}

		return content.toString();
	}

}