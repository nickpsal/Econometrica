/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ecometrica.econ;

import Pojos.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author nickpsal
 */
public class DataDB {
    private EntityManager em;
    private EntityManagerFactory emf;
        
    private boolean succ;
    private Country country1 = new Country();
    private CountryDataset dataset1 = new CountryDataset();
    private CountryDataset dataset2 = new CountryDataset();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
    
    //ΈΛεγχος αν μπορεί να συνδεθεί στην βδ
    public boolean TestConnection() {
        boolean conn = true;
        try{
            emf = Persistence.createEntityManagerFactory("EconometricaPU");
            em = emf.createEntityManager();
            em.getTransaction().begin();
        }catch (Exception exp){
            conn = false;
        }
        return conn;
    }
    
    public boolean InsertCountryData(String[] xora, String[] kodikos) {
        //Εισαγωγή δεδομένων στον Πίνακα Country
        try {
            emf = Persistence.createEntityManagerFactory("EconometricaPU");
            em = emf.createEntityManager();
            em.getTransaction().begin();
            List<Country> countries = getCountry();
            if (countries.isEmpty()){
                // Εισαγωγή Δεδομένων στον Πινακας Country 
                for (int i = 1; i<xora.length;i++) {
                    Country country = new Country();
                    country.setName(xora[i]);
                    country.setIsoCode(kodikos[i]);
                    em.persist(country);
                }
                em.flush();
                em.getTransaction().commit(); 
            }
            succ = true;
        }catch (Exception exp) {
            succ = false;
        }finally{
            em.close();
        }
        return succ;
    }
    
    public boolean InsertDatasetGDP(String code, Date StartDateGDP,Date EndDateGDP,String NameGDP){
        //Εισαγωγή δεδομένων στον Πίνακα Country_Dataset
        try {
            emf = Persistence.createEntityManagerFactory("EconometricaPU");
            em = emf.createEntityManager();
            em.getTransaction().begin();
            //Εισαγωγη Δεδομένων στον Πίνακα Country_Dataset για τα δεδομενα GDP
            dataset1.setDatasetId(null);
            dataset1.setName(NameGDP);
            country1.setIsoCode(code);
            dataset1.setCountryCode(country1);
            dataset1.setDescription(NameGDP);
            dataset1.setStartYear(sdf.format(StartDateGDP));
            dataset1.setEndYear(sdf.format(EndDateGDP));
            em.persist(dataset1);
            em.flush();
            em.getTransaction().commit(); 
        } catch (Exception exp) {
            succ = false;
        }finally{
            em.close();
        }
        return succ;
    }
    
    public boolean InsertDatasetOIL(Date StartDateOIL,Date EndDateOIL,String NameOIL,String Desc){
        //Εισαγωγή δεδομένων στον Πίνακα Country_Dataset
        try{
            emf = Persistence.createEntityManagerFactory("EconometricaPU");
            em = emf.createEntityManager();
            em.getTransaction().begin();
            dataset2.setDatasetId(null);
            dataset2.setName(NameOIL);
            dataset2.setCountryCode(country1);
            dataset2.setDescription(Desc);
            dataset2.setStartYear(sdf.format(StartDateOIL));
            dataset2.setEndYear(sdf.format(EndDateOIL));
            em.persist(dataset2);
            em.flush();
            em.getTransaction().commit();
        } catch (Exception exp) {
            succ = false;
        }finally{
            em.close();
        }        
        return succ;
    }
    
    public boolean InsertCountryDataGDP(List<CountryData> GDPdata) {
        //Εισαγωγή δεδομένων στον Πίνακα Country_Data
        try {
            emf = Persistence.createEntityManagerFactory("EconometricaPU");
            em = emf.createEntityManager();
            em.getTransaction().begin();
            for (int i = 0; i<GDPdata.size(); i++) {
                CountryData dataofGDP = new CountryData();
                dataofGDP.setDataset(dataset1);
                dataofGDP.setDataYear(GDPdata.get(i).getDataYear());
                dataofGDP.setValue(GDPdata.get(i).getValue());
                em.persist(dataofGDP);
            }
            em.flush();
            em.getTransaction().commit(); 
            succ = true;
        }
        catch (Exception exp) {
            succ = false;
        }finally{
            em.close();
        }
        return succ;
    }
    
    public boolean InsertCountryDataOIL(List<CountryData> OILdata){
        //Εισαγωγή δεδομένων στον Πίνακα Country_Data
        try {
            emf = Persistence.createEntityManagerFactory("EconometricaPU");
            em = emf.createEntityManager();
            em.getTransaction().begin();
            for (int i = 0; i < OILdata.size(); i++) {
                CountryData dataofOIL = new CountryData();
                dataofOIL.setDataset(dataset2);
                dataofOIL.setDataYear(OILdata.get(i).getDataYear());
                dataofOIL.setValue(OILdata.get(i).getValue());
                em.persist(dataofOIL);
            }
            em.flush();
            em.getTransaction().commit(); 
            succ = true;
        }catch (Exception exp) {
            succ = false;
        }finally{
            em.close();
        }
        return succ;
    }

    public boolean checkCountryData(String code) {
        //ΈΛεγχος αν έχουν αποθηκευτεί δεδομένα απο την χώρα που επιλέξαμε
        try {
            emf = Persistence.createEntityManagerFactory("EconometricaPU");
            em = emf.createEntityManager();
            em.getTransaction().begin();
            List<CountryDataset> dataset = getCountryDataset();
            succ = true;
            country1.setIsoCode(code);
            CountryDataset dset = new CountryDataset();
            dset.setCountryCode(country1);
            if (!dataset.isEmpty()){
                for (CountryDataset data:dataset){
                    if (data.getCountryCode().equals(dset.getCountryCode())){
                        succ = false;
                    }
                }
            }
        }catch (Exception exp) {
            succ = false;
        }finally{
            em.close();
        }
        return succ;
    }
    
    public CountryDataset getDatesGDP(String Ccode, String Country) {
        // Παίρνουμε τις ημερομηνίες Πρωτης και τελευταίας μέτρησης απο την ΒΔ
        emf = Persistence.createEntityManagerFactory("EconometricaPU");
        em = emf.createEntityManager();
        em.getTransaction().begin();
        CountryDataset dataset = new CountryDataset();
        String name = "GDP (Current LCU) for " + Country;     
        country1.setIsoCode(Ccode);
        dataset1.setCountryCode(country1);
        TypedQuery<CountryDataset> q1 = em.createQuery("SELECT c FROM CountryDataset c WHERE c.countryCode = :dataset1 AND c.name =:name",CountryDataset.class);
        q1.setParameter("dataset1", country1);
        q1.setParameter("name", name);
        dataset = q1.getSingleResult();
        em.close();
        return dataset;
    }
    
    public CountryDataset getDateOIL(String Ccode, String Country) {
        // Παίρνουμε τις ημερομηνίες Πρωτης και τελευταίας μέτρησης απο την ΒΔ
        emf = Persistence.createEntityManagerFactory("EconometricaPU");
        em = emf.createEntityManager();
        em.getTransaction().begin();
        CountryDataset dataset = new CountryDataset();
        String name = "Oil Consumption - " + Country;      
        country1.setIsoCode(Ccode);
        dataset1.setCountryCode(country1);
        TypedQuery<CountryDataset> q2 = em.createQuery("SELECT c FROM CountryDataset c WHERE c.countryCode = :dataset1 AND c.name =:name",CountryDataset.class);
        q2.setParameter("dataset1", country1);
        q2.setParameter("name", name);
        dataset = q2.getSingleResult();
        System.out.println(dataset.getStartYear());
        em.close();
        return dataset;
    }
    
    public  List<CountryData> getDataGDP(String Ccode, String Country) {
        //Παίρνουμε τα δεδομένα απο την ΒΔ
        int xoraid = 0;
        List<CountryData> dataGPD = new ArrayList<>();
        emf = Persistence.createEntityManagerFactory("EconometricaPU");
        em = emf.createEntityManager();
        em.getTransaction().begin();
        List<CountryDataset> dataset = getCountryDataset();
        String name = "GDP (Current LCU) for " + Country; 
        country1.setIsoCode(Ccode);
        dataset2.setCountryCode(country1);
        for (CountryDataset data:dataset) {
            if ((data.getCountryCode().equals(dataset2.getCountryCode())) && (data.getName().equalsIgnoreCase(name))){
                xoraid = data.getDatasetId();
            }
        }
        Query q = em.createQuery("SELECT c FROM CountryData c WHERE c.dataset.datasetId = :xoraid ORDER BY c.dataYear DESC",CountryData.class);
        q.setParameter("xoraid", xoraid);
        dataGPD.addAll(q.getResultList());
        em.close();
        return dataGPD;
    }
    
    public  List<CountryData> getDataOIL(String Ccode, String Country) {
        //Παίρνουμε τα δεδομένα απο την ΒΔ
        int xoraid = 0;
        List<CountryData> dataOIL = new ArrayList<>();
        emf = Persistence.createEntityManagerFactory("EconometricaPU");
        em = emf.createEntityManager();
        em.getTransaction().begin();
        List<CountryDataset> dataset = getCountryDataset();
        String name = "Oil Consumption - " + Country; 
        for (CountryDataset data:dataset) {
            if ((data.getCountryCode().getIsoCode().equalsIgnoreCase(Ccode)) && (data.getName().equalsIgnoreCase(name))){
                xoraid = data.getDatasetId();
                System.out.println(xoraid);
            }
        }
        Query q = em.createQuery("SELECT c FROM CountryData c WHERE c.dataset.datasetId = :xoraid ORDER BY c.dataYear DESC",CountryData.class);
        q.setParameter("xoraid", xoraid);
        dataOIL.addAll(q.getResultList());
        em.close();
        return dataOIL;
    }    

    public boolean emptyDB() {
        //Άδειασμα Πινάκων Βάσης Δεδομένων
        try {
            emf = Persistence.createEntityManagerFactory("EconometricaPU");
            em = emf.createEntityManager();
            em.getTransaction().begin();
            List<Country> countries = getCountry();
            List<CountryData> data = getCountryData();
            List<CountryDataset> dataset = getCountryDataset();

            for (Country xores : countries) {
                em.remove(xores);              
            }
            
            for (CountryData datas : data) {
                em.remove(datas);
            }

            for (CountryDataset datasets : dataset) {
                em.remove(datasets);
            }
            em.flush();
            em.getTransaction().commit();    
            succ = true;
        }catch (Exception exp) {
            succ = false;
        }finally{
            em.close();
        }
        return succ;
   }
    
    //getters για τα δεδομένα του κάθε πίνακα τηε Βλασης Δεδομένων    
    public List<Country> getCountry() {
        Query query1 = em.createNamedQuery("Country.findAll", Country.class);
        return query1.getResultList();
    }  
    
    public List<CountryData> getCountryData() {
        Query query2 = em.createNamedQuery("CountryData.findAll", CountryData.class );
        return query2.getResultList();
    }
    
    public List<CountryDataset> getCountryDataset() {
         Query query3 = em.createNamedQuery("CountryDataset.findAll", CountryDataset.class);
         return query3.getResultList();
    }
}
