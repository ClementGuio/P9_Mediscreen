package com.mediscreen.riskreport.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

import com.mediscreen.riskreport.constant.ReportTraits;
import com.mediscreen.riskreport.model.PatientReport;

@Service
public class RiskAnalyzer {
	
	private Set<String> words;
	
	public RiskAnalyzer() {
		this.words = new HashSet<String>(Arrays.asList(ReportTraits.TRIGGER_TERMS));
	}

	public void report(PatientReport patient) {
		Integer age = Period.between(patient.getBirthdate(),LocalDate.now()).getYears();
		Integer riskLvl = evaluate(age, patient.getGender(), wordCount(patient.getNotes()));
		patient.setRiskLevel(ReportTraits.RISK_LEVELS[riskLvl]);
	}
	
	public Integer wordCount(List<String> notes) {
		Integer wordCount = 0;		
		for (String note : notes) {
			for (String word : words) {
				if (note.toLowerCase().contains(word)) {
					wordCount++;
				}
			}
		}
		return wordCount;
	}
	
	public Integer evaluate(Integer age, String gender, Integer wordCount) {
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
			if (gender.equals("M")||gender.equals("m")) {
				if (wordCount>=5) {
					return 3;
				}
				if (wordCount>=3) {
					return 2;
				}
			}
			if (gender.equals("F")||gender.equals("f")){
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
