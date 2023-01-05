package com.mediscreen.ui;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UiApplication{

	public static void main(String[] args) {
		SpringApplication.run(UiApplication.class, args);
	}

}
//HTML : nettoyer le code Js
//HTML: terminer le style (banniere, boutons(position, couleur), titre?)
//HTML: gérer l'affichage de 404 pour report