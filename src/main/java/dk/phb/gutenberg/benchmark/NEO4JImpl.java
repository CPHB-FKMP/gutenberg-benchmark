/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.phb.gutenberg.benchmark;

import org.neo4j.driver.v1.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.neo4j.driver.v1.summary.Plan;

public class NEO4JImpl {

    public Double getAverage(List<Double> timetable) {
        Double avg = 0.0;
        for (int i = 0; i < timetable.size(); i++) {
            avg += timetable.get(i);
        }

        return avg / timetable.size();
    }

    public Double getMedian(List<Double> timetable) {
        int median = timetable.size() / 2;
        median = median % 2 == 0 ? median - 1 : median;
        return timetable.get(median);
    }

    public void getBooksByCity(String city, List<Double> timetable) {
        Driver driver = DBConnection.getInstance();

        Session session = driver.session();
        String query = String.format("MATCH (book:Book)-[r:CONTAINS]->(city:City {name: \""+city+"\"}) RETURN book,r,city;");
        
        long startTime = System.nanoTime();
        StatementResult res = session.run(query);
        res.summary().plan();
        long endtime = System.nanoTime() - startTime;
        timetable.add((double) endtime / 1000000.0);
      
        DBConnection.closeDriver();

    }

    public void getAllCitiesInBook(String title, List<Double> timetable) {
        Driver driver = DBConnection.getInstance();

        Session session = driver.session();
        String query = String.format("MATCH (:Book{title: \""+title+"\"})-[:CONTAINS]-(city:City) RETURN city");
        
        long startTime = System.nanoTime();
        StatementResult res = session.run(query);
        res.summary().plan();
        long endtime = System.nanoTime() - startTime;
        timetable.add((double) endtime / 1000000.0);

        DBConnection.closeDriver();

    }

    public void getBooksAndCitiesByAuthor(String author, List<Double> timetable) {
        Driver driver = DBConnection.getInstance();

        Session session = driver.session();
        String query = String.format("MATCH (:Author{name: \""+author+"\"})-[:WRITTEN_BY]-(book:Book)-[:CONTAINS]-(city:City) RETURN book.title, city");
        
        long startTime = System.nanoTime();
        StatementResult res = session.run(query);
        res.summary().plan();
        long endtime = System.nanoTime() - startTime;
        timetable.add((double) endtime / 1000000.0);

        DBConnection.closeDriver();

    }

    public void getAllBooksWrittenNearby(String lat, String lng, List<Double> timetable) {
        Driver driver = DBConnection.getInstance();

        Session session = driver.session();
        String query = String.format("WITH "+lat+" AS lat, "+lng+" AS lon\n" +
            "MATCH (l:City) \n" +
            "WHERE 2 * 6371 * asin(sqrt(haversin(radians(lat - toFloat(split(l.location, \",\")[0])))+ cos(radians(lat))* cos(radians(toFloat(split(l.location, \",\")[0])))* haversin(radians(lon - toFloat(split(l.location, \",\")[1]))))) <= 20\n" +
            "MATCH (l)-[:CONTAINS]-(book:Book)\n" +
            "RETURN book");
        
        long startTime = System.nanoTime();
        StatementResult res = session.run(query);
        res.summary().plan();
        long endtime = System.nanoTime() - startTime;
        timetable.add((double) endtime / 1000000.0);

        DBConnection.closeDriver();

    }
}
