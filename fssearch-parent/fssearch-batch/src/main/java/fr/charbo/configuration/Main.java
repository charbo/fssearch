package fr.charbo.configuration;

import org.apache.tika.Tika;

import java.io.File;

public class Main {
	
	public static void main(String[] args) {

    Tika tika = new Tika();
    try {
      String content =  tika.parseToString(new File("C:\\temp_es\\doc\\SC_FIC_INT_ChargeurEtask_G05R03C04.doc"));
      System.out.println(content);
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }

}
