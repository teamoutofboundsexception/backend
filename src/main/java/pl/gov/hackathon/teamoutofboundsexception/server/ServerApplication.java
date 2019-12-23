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
	public void createTempDirectory() {
		ApplicationHome home = new ApplicationHome(ServerApplication.class);

		File newDir = new File(home.getDir().getAbsolutePath() + File.separator + "temp/");

		if (!newDir.exists()) {
			if (newDir.mkdirs()) {
				log.info("File created.");
			} else {
				log.error("File not created.");
			}
		} else {
			log.info("File already exists.");
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}
}
// TODO POGODA
// TODO OPEN STREET MAP - > xy -> adres i odwrotnie
