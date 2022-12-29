package com.mediscreen.riskreport.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mediscreen.riskreport.model.PatientReport;

//@SpringBootTest
public class RiskAnalyzerTest {

	//@Autwired
	private RiskAnalyzer analyzer = new RiskAnalyzer();
	
	/*List<String> notes = new ArrayList<String>(); 
	notes.add("Microalbumine élevé.\nFumeur depuis 2 ans.");
	notes.add("La patiente se sent bien.");
	PatientReport report = new PatientReport("None","Test",LocalDate.of(1970, 12, 31),"f",null);*/
	
	@Test
	public void riskReportTest_None() {
		List<String> notes = new ArrayList<String>(); 
		notes.add("Microalbumine élevé.");
		notes.add("La patiente se sent bien.");
		PatientReport patient = new PatientReport("None","Test",LocalDate.of(1970, 12, 31),"f",notes);
		
		analyzer.report(patient);
		
		assertThat(patient.getRiskLevel()).isEqualTo("None");
	}
	
	@Test
	public void riskReportTest_Borderline() {
		List<String> notes = new ArrayList<String>(); 
		notes.add("Anticorps élevé.");
		notes.add("Fumeur depuis peu.");
		PatientReport patient = new PatientReport("Borderline","Test",LocalDate.of(1949, 12, 31),"m",notes);
		
		analyzer.report(patient);
		
		assertThat(patient.getRiskLevel()).isEqualTo("Borderline");
	}
	
	@Test
	public void riskReportTest_InDanger() {
		List<String> notes = new ArrayList<String>(); 
		notes.add("Poids anormal");
		notes.add("Vertige deuis peu");
		PatientReport patient = new PatientReport("InDanger","Test",LocalDate.of(2008, 12, 31),"m",notes);
		
		analyzer.report(patient);
		
		assertThat(patient.getRiskLevel()).isEqualTo("In danger");
	}
	
	@Test
	public void riskReportTest_EarlyOnset() {
		List<String> notes = new ArrayList<String>(); 
		notes.add("Microalbumine élevé.\nCholestérol anormal");
		notes.add("Poids et taille anormal\n Rechute");
		PatientReport patient = new PatientReport("EarlyOnset","Test",LocalDate.of(2010, 12, 31),"f",notes);
		
		analyzer.report(patient);
		
		assertThat(patient.getRiskLevel()).isEqualTo("Early onset");
	}
	
	@Test
	public void wordCountTest() {
		List<String> notes = new ArrayList<String>();
		notes.add("Fumeur depuis 2 ans.\nLe patient va bien");
		notes.add("Cholestérol anormal.\nPrésence d'anticorps");
		notes.add("\nEn rechute.");
		notes.add("  Le patient va bien.    ");
		
		assertThat(analyzer.wordCount(notes)).isEqualTo(5);
		
		
	}
	
	@Test
	public void evaluateTest_Over30F_None() {
		assertThat(analyzer.evaluate(30,"f",1)==0).isTrue();
	}
	
	@Test
	public void evaluateTest_Over30M_None() {
		assertThat(analyzer.evaluate(30,"m",1)==0).isTrue();
	}
	
	@Test
	public void evaluateTest_Under30F_None() {
		assertThat(analyzer.evaluate(29,"f",1)==0).isTrue();
	}
	
	@Test
	public void evaluateTest_Under30M_None() {
		assertThat(analyzer.evaluate(29,"m",1)==0).isTrue();
	}
	
	@Test
	public void evaluateTest_Over30F_Borderline() {
		assertThat(analyzer.evaluate(30,"f",2)==1).isTrue();
	}
	
	@Test
	public void evaluateTest_Over30M_Borderline() {
		assertThat(analyzer.evaluate(30,"m",2)==1).isTrue();
	}
	
	@Test
	public void evaluateTest_Over30F_InDanger() {
		assertThat(analyzer.evaluate(30,"f",6)==2).isTrue();
	}
	
	@Test
	public void evaluateTest_Over30M_InDanger() {
		assertThat(analyzer.evaluate(30,"m",6)==2).isTrue();
	}
	
	@Test
	public void evaluateTest_Under30F_InDanger() {
		assertThat(analyzer.evaluate(29,"f",4)==2).isTrue();
	}
	
	@Test
	public void evaluateTest_Under30M_InDanger() {
		assertThat(analyzer.evaluate(29,"m",3)==2).isTrue();
	}
	
	@Test
	public void evaluateTest_Over30F_EarlyOnset() {
		assertThat(analyzer.evaluate(30,"f",8)==3).isTrue();
	}
	
	@Test
	public void evaluateTest_Over30M_EarlyOnset() {
		assertThat(analyzer.evaluate(30,"m",8)==3).isTrue();
	}
	
	@Test
	public void evaluateTest_Under30F_EarlyOnset() {
		assertThat(analyzer.evaluate(29,"f",7)==3).isTrue();
	}
	
	@Test
	public void evaluateTest_Under30M_EarlyOnset() {
		assertThat(analyzer.evaluate(29,"m",5)==3).isTrue();
	}
}
