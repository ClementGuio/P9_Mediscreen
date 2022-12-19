package com.mediscreen.riskreport;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mediscreen.riskreport.model.PatientInfo;
import com.mediscreen.riskreport.service.RiskAnalyser;

@SpringBootApplication
public class RiskreportApplication implements CommandLineRunner{

	@Autowired
	RiskAnalyser analyser;
	
	public static void main(String[] args) {
		SpringApplication.run(RiskreportApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String[] tabNotes = {"Le patient déclare qu'il « se sent très bien »\nPoids égal ou inférieur au poids recommandé",
				"Le patient déclare qu'il se sent fatigué pendant la journée\nIl se plaint également de douleurs musculaires\nTests de laboratoire indiquant une microalbumine élevée",
				"Le patient déclare qu'il ne se sent pas si fatigué que ça\nFumeur, il a arrêté dans les 12 mois précédents\nTests de laboratoire indiquant que les anticorps sont élevés"};
		List<String> notes = Arrays.asList(tabNotes);
		
		PatientInfo info = new PatientInfo("firstname","Ferguson",Date.from(LocalDate.of(1990, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()),"M",notes);
		System.out.println(analyser.report(info));
	}

}
