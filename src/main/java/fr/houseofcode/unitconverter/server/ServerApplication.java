package fr.houseofcode.unitconverter.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class ServerApplication {
	// String home() {
	// return "helooworld";
	// }

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ServerApplication.class, args);
		System.out.println("hiFromLyon");
	}

}
