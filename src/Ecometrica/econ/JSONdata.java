/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ecometrica.econ;

import Pojos.CountryData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author nickpsal
 */
public class JSONdata {   
    //Πάιρνουμε όλα τα Ολα τα δεδομένα και τα στέλνουμε στις υπόλοιπες κλάσεις
    public GDPandOIL getallData(String response) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        GdpOilDataset gdpd = gson.fromJson(response, GdpOilDataset.class);
        GDPandOIL gdp = gdpd.getDataset();
        response = gson.toJson(gdp);
        GDPandOIL gp = gson.fromJson(response, GDPandOIL.class);
        return gp;
    }
    
    //Παίρνουμε την ημερομηνία Έναρξης μετρήσεων απο τα δεδομένα JSON
    public Date dataGDPGetStartDate(String responseGDP) {
        GDPandOIL gp = getallData(responseGDP);
        Date Startdate = gp.getOldest_available_date();
        return Startdate;                
    }
    
    //Παίρνουμε την τελευταία ημερομηνία μετρήσεων απο τα δεδομένα JSON
    public Date dataGDPGetNewestDate(String responseGDP) {
        GDPandOIL gp = getallData(responseGDP);
        Date NewestDate = gp.getNewest_available_date();
        return NewestDate;                
    }
    
    //Παίρνουμε τα δεδομένα των μετρήσεων GDP απο τα δεδομένα JSON
    public List<CountryData> getGDPdata(String responseGDP) {
        List<CountryData> GDPdata = new ArrayList<>();
        GDPandOIL gp = getallData(responseGDP);
        for (int i = 0; i < gp.getData().size(); i++) {
            LocalDate date = LocalDate.parse(gp.getData().get(i).get(0));
            CountryData dataGDP = new CountryData();
            dataGDP.setDataYear(String.valueOf(date.getYear()));
            dataGDP.setValue(gp.getData().get(i).get(1));
            GDPdata.add(dataGDP);    
        }
        return GDPdata;
    }
    
    //Παίρνουμε την ημερομηνία Έναρξης μετρήσεων απο τα δεδομένα JSON
    public Date dataOILGetStartDate(String responseBP) {
        GDPandOIL gp = getallData(responseBP);
        Date Startdate = gp.getOldest_available_date();
        return Startdate;                
    }
    
    //Παίρνουμε την τελευταία ημερομηνία μετρήσεων απο τα δεδομένα JSON
    public Date dataOILGetNewestDate(String responseBP) {
        GDPandOIL gp = getallData(responseBP);
        Date NewestDate = gp.getNewest_available_date();
        return NewestDate;                
    }
    
    //Παίρνουμε τα δεδομένα των μετρήσεων BP OIL απο τα δεδομένα JSON
    public List<CountryData> getOILdata(String responseBP) {
        List<CountryData> OILdata = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("##.##");
        GDPandOIL gp = getallData(responseBP);
        for (int i = 0; i < gp.getData().size(); i++) {
            LocalDate date = LocalDate.parse(gp.getData().get(i).get(0));
            CountryData dataOIL = new CountryData();
            dataOIL.setDataYear(String.valueOf(date.getYear()));
            //dataOIL.setValue(df.format(Float.parseFloat(gp.getData().get(i).get(1))));
            dataOIL.setValue(gp.getData().get(i).get(1));
            OILdata.add(dataOIL);    
        }
        return OILdata;
    }
}
