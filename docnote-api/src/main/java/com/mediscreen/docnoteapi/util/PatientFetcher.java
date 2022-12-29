package com.mediscreen.docnoteapi.util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.docnoteapi.model.Note;

@Component
public class PatientFetcher { //TODO: renommer NoteFiller ~
	
	public String fetchBody(String host, Integer patientId) throws IOException, InterruptedException {

		String uri = host+"/patientapi/get?id="+patientId;
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(uri))
				.build();
		
		HttpResponse<?> response = null;
		response = client.send(request, HttpResponse.BodyHandlers.ofString());
		
		return response.body().toString();
	}
	
	public Note fetchPatient(String host, Integer patientId) throws IOException, InterruptedException, ParseException {
		
		String response = fetchBody(host,patientId);
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(response);

		String firstname = node.findValue("Firstname").asText();
		String lastname = node.findValue("Lastname").asText();
		LocalDate birthdate = LocalDate.parse(node.findValue("Birthdate").asText(),DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String gender = node.findValue("Gender").asText();
		
		return new Note(patientId, firstname, lastname, birthdate, gender, null);
		
	}
}
