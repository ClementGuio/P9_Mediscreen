package com.mediscreen.docnoteapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DocnoteApiApplication{

	public static void main(String[] args) {
		SpringApplication.run(DocnoteApiApplication.class, args);
	}

}
//HTML: alléger history -> (prénom, nom en dehors de la table & ne pas afficher birthdate, gender) + ajouter date de la note
//HTML: get -> bouton annuler ne fait rien