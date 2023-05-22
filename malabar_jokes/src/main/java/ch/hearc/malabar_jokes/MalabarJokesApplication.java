package ch.hearc.malabar_jokes;

import ch.hearc.malabar_jokes.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.logging.Level;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.JmsListener;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
public class MalabarJokesApplication {

	@Autowired
	ObjectMapper objectMapper;

	public static void main(String[] args) {
		SpringApplication.run(MalabarJokesApplication.class, args);
	}

	@JmsListener(destination = "${spring.activemq.json-queue}")
	public void readInprogressJsonMessage(final Message jsonMessage) throws JMSException {
		System.out.println("Received message " + jsonMessage);
		ObjectMapper objectMapper = new ObjectMapper();

		TextMessage textMessage = (TextMessage) jsonMessage;
		String messageData = textMessage.getText();

		Log message = null;

		try {
			message = objectMapper.readValue(messageData, Log.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String folderPath = "log";
		String filename = "logs.txt";
		String filePath = folderPath + "/" + filename;

		try {
			Path path = Paths.get(folderPath);
			if (!Files.exists(path)) {
				Files.createDirectories(path);
			}

			FileWriter fw = new FileWriter(filePath, true);
			fw.write(message.toString() + "\n");
			fw.close();

			System.out.println("Message written to file " + filePath);
		} catch (IOException ioe) {
			System.err.println("IOException: " + ioe.getMessage());
		}
	}
}
