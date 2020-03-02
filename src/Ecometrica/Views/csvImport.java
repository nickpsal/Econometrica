/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ecometrica.Views;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author nickpsal
 */
public class csvImport {
    private String csvFile = "iso-countries.csv";       
    private String line = "";
    private String cvsSplitBy = ";";
    private String[] countries = new String[240];
    private String[] codes = new String[240];
    
    public String[] importCountryNames() {        
        //Διάβασμα του αρχείου csv και αποθήκευση των ονομάττων των χωρών
        //στον Πίνακα countries.
        //Στην πρώτη θέση του πίνακα βάζουμε τον τίτλο Επιλογή Χώρας 
        countries[0] = "Επιλογή Χώρας";
        int i = 1;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            //να μην διαβάσει την πρώτη γραμμή που είναι οι ετικετες
            br.readLine();
            while ((line = br.readLine()) != null) {
                // use semicoin as separator
                String[] country = line.split(cvsSplitBy);
                countries[i] = country[0];               
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return countries;
    }
    
    public String[] importCountryCodes() {     
        //Διάβασμα του αρχείου csv και αποθήκευση των κωδικών των χωρών
        //στον Πίνακα codes.
        //Στην πρώτη θέση του πίνακα την αφήνουμε κενή
        int i = 1;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            //να μην διαβάσει την πρώτη γραμμή που είναι οι ετικετες
            br.readLine();
            while ((line = br.readLine()) != null) {
                // use semicoin as separator
                String[] country = line.split(cvsSplitBy);
                codes[i] = country[2];                
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return codes;
    }
}