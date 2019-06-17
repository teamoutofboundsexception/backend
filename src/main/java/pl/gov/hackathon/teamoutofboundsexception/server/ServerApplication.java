package pl.gov.hackathon.teamoutofboundsexception.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationHome;

import javax.annotation.PostConstruct;
import java.io.File;

@Slf4j
@SpringBootApplication
public class ServerApplication {

	@PostConstruct
	public void asd() {
		ApplicationHome home = new ApplicationHome(ServerApplication.class);

		File newDir = new File(home.getDir().getAbsolutePath() + File.separator + "temp/");

		if (!newDir.exists() && newDir.mkdirs()) {
			System.out.println("File created.");
		} else {
			System.out.println("File not created!!!");
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

}
