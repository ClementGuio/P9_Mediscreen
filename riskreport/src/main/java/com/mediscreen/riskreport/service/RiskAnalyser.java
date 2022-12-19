package com.mediscreen.riskreport.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.mediscreen.riskreport.constant.ReportTraits;
import com.mediscreen.riskreport.model.PatientInfo;

@Service
public class RiskAnalyser {
	
	private Set<String> words;
	
	public RiskAnalyser() {
		this.words = new HashSet<String>(Arrays.asList(ReportTraits.TERMS));
	}
	
	public String report(PatientInfo patient) {
		Integer wordCount = 0;
		//List<List<String>> notes = splitNotes(patient.getNotes());
		
		for (String note : patient.getNotes()) {
			System.out.println(note);
			for (String word : words) {
				if (note.toLowerCase().contains(word)) {
					wordCount++;
					System.out.println("\t-> "+word+" : wordCount = "+wordCount);
				}
			}
		}
		System.out.println("WordCount = "+wordCount);
		Integer riskLvl = evaluate(patient, wordCount);
		return ReportTraits.LEVELS[riskLvl];
	}
	
	private Integer evaluate(PatientInfo patient, Integer wordCount) {
		Integer age = Period.between(toLocalDate(patient.getBirthdate()),LocalDate.now()).getYears();
		if(wordCount==0) {
			return 0;
		}
		if (age>=30) {
			if (wordCount>=8) {
				return 3;
			}
			if (wordCount>=6) {
				return 2;
			}
			if (wordCount>=2) {
				return 1;
			}
		}else {
			if (patient.getGender().equals("M")) {
				if (wordCount>=5) {
					return 3;
				}
				if (wordCount>=3) {
					return 2;
				}
			}else {
				if (wordCount>=7) {
					return 3;
				}
				if (wordCount>=4) {
					return 2;
				}
			}
		}
		return 0;
	}

	public LocalDate toLocalDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
}
