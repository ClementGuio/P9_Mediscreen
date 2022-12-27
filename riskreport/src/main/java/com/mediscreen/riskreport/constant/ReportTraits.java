package com.mediscreen.riskreport.constant;

import org.springframework.stereotype.Component;

@Component
public final class ReportTraits {
	
	public static final String[] TRIGGER_TERMS = {"hémoglobine a1c","microalbumine","taille","poids","fumeur","anormal","cholestérol","vertige","rechute","réaction","anticorps"};

	public static final String[] RISK_LEVELS = {"None","Borderline","In danger","Early onset"};
	
}
