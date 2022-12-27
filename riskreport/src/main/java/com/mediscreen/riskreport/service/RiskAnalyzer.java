package com.mediscreen.riskreport.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.lang.Iterable;

import org.springframework.stereotype.Service;

import com.mediscreen.riskreport.constant.ReportTraits;
import com.mediscreen.riskreport.model.PatientReport;

@Service
public class RiskAnalyzer {
	
	private Set<String> words;
	
	public RiskAnalyzer() {
		this.words = new HashSet<String>(Arrays.asList(ReportTraits.TRIGGER_TERMS));
	}
	//TODO: virer les System.out
	public void report(PatientReport patient) {
		Integer wordCount = 0;		
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
		patient.setRiskLevel(ReportTraits.RISK_LEVELS[riskLvl]);
	}
	
	private Integer evaluate(PatientReport patient, Integer wordCount) {
		Integer age = Period.between(patient.getBirthdate(),LocalDate.now()).getYears();
		System.out.println(age);
		if(wordCount<2) {
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
			if (patient.getGender().equals("M")||patient.getGender().equals("m")) {
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
}
