package com.mediscreen.riskreport.util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.riskreport.exception.NoteNotFoundException;
import com.mediscreen.riskreport.model.PatientReport;


public class NotesFetcher { //TODO: Renommer la classe

	private static final HttpResponse<String> fetchBody(String host, Integer patientId) throws IOException, InterruptedException {

		String uri = host+"/docnoteapi/get/patient?patientId="+patientId;
		System.out.println(uri);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(uri))
				.build();
		System.out.println(request.uri());
		HttpResponse<String> response = null;
		response = client.send(request, HttpResponse.BodyHandlers.ofString());
		
		return response;
	}
	
	public static final PatientReport fetchNote(String host, Integer patientId) throws IOException, InterruptedException, ParseException, NoteNotFoundException {
		
		HttpResponse<String> response = fetchBody(host,patientId);
		if (response.body().isEmpty()) {
			throw new NoteNotFoundException("This patient has no notes.");
		}
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(response.body().toString());
	
		List<String> comments = new ArrayList<String>();
		for (JsonNode subNode : node.findValues("comment")) {
			comments.add(subNode.asText());
			System.out.println(subNode.asText());
		}
		String firstname = node.findValues("firstname").get(0).asText();
		String lastname = node.findValues("lastname").get(0).asText();
		LocalDate birthdate = LocalDate.parse(node.findValues("birthdate").get(0).asText(),DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String gender = node.findValues("gender").get(0).asText();
		
		return new PatientReport(firstname,lastname,birthdate,gender,comments);
		
	}
}
