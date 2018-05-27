package dk.phb.gutenberg.benchmark;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DifferentQueries {
    public static void main(String[] args) throws SQLException {
         NEO4JImpl neo = new NEO4JImpl();
        PostgresqlImpl pos = new PostgresqlImpl();
        
        int times = 10;
        
        List<Double> neo4jCities = new ArrayList<>();
        List<Double> neo4jTitles = new ArrayList<>();
        List<Double> neo4jAuthors = new ArrayList<>();
        List<Double> neo4jLocations = new ArrayList<>();
        List<Double> postCities = new ArrayList<>();
        List<Double> postTitles = new ArrayList<>();
        List<Double> postAuthors = new ArrayList<>();
        List<Double> postLocations = new ArrayList<>();
        
        String[] cities = {
            "Copenhagen", 
            "Greve", 
            "London", 
            "Milton", 
            "Venice"
        };
        
        String[] titles = {
            "War in the Garden of Eden",
            "La Boheme",
            "Social Value A Study in Economic Theory Critical and Constructive",
            "North America",
            "Hesiod, The Homeric Hymns, and Homerica"
        };
        
        String[] authors = {
            "Georgiana Cavendish",
            "Lilian Garis",
            "Maxime Provost",
            "James Cook",
            "Kermit Roosevelt"
        };
        
        String[][] locations = {
            {"34.366500", "-89.519250"},
            {"42.440630", "-76.496610"},
            {"34.802430", "-86.972190"},
            {"43.983330", "25.333330"},
            {"42.728410", "-73.691790"}
        };
        
        System.out.println("NEO4J QUERIES");
        System.out.println("----------------------------------------------------------------");
        System.out.println("Neo4J Cities");
        for (String city : cities) {
            for (int i = 0; i < times; i++) {
                neo.getBooksByCity(city, neo4jCities);  
            }
        }
        System.out.println("Get all books that mention city");
        System.out.println("Average time in milliseconds for this Query: " + neo.getAverage(neo4jCities));
        System.out.println("Median time in milliseconds for this Query: " + neo.getMedian(neo4jCities));
        System.out.println("----------------------------------------------------------------");
        
        System.out.println("Neo4J Titles");
        for (String title : titles) {
            for (int i = 0; i < times; i++) {
                neo.getAllCitiesInBook(title, neo4jTitles);  
            }
            
        }
        System.out.println("Average time in milliseconds for this Query: " + neo.getAverage(neo4jTitles));
        System.out.println("Median time in milliseconds for this Query: " + neo.getMedian(neo4jTitles));
        System.out.println("----------------------------------------------------------------");
        
        System.out.println("Neo4J Authors");     
        for (String author : authors) {
            for (int i = 0; i < times; i++) {
                neo.getBooksAndCitiesByAuthor(author, neo4jAuthors);  
            }
        }
        System.out.println("Average time in milliseconds for this Query: " + neo.getAverage(neo4jAuthors));
        System.out.println("Median time in milliseconds for this Query: " + neo.getMedian(neo4jAuthors));
        System.out.println("----------------------------------------------------------------");
        
        System.out.println("Neo4J Locations");
        for (String[] location : locations) {
            for (int i = 0; i < times; i++) {
                neo.getAllBooksWrittenNearby(location[0], location[1], neo4jLocations);
            }
        }
        System.out.println("Average time in milliseconds for this Query: " + neo.getAverage(neo4jLocations));
        System.out.println("Median time in milliseconds for this Query: " + neo.getMedian(neo4jLocations));
        System.out.println("----------------------------------------------------------------");

        System.out.println("POSTGRESQL QUERIES");
        System.out.println("----------------------------------------------------------------");
        System.out.println("Postgres Cities");
        for (String city : cities) {
            for (int i = 0; i < times; i++) {
                pos.GetBooksByCity(city, postCities);  
            }
        }
        System.out.println("Average time in milliseconds for this Query: " + neo.getAverage(postCities));
        System.out.println("Median time in milliseconds for this Query: " + neo.getMedian(postCities));
        System.out.println("----------------------------------------------------------------");
        
        System.out.println("Postgres Titles");
        for (String title : titles) {
            for (int i = 0; i < times; i++) {
                pos.GetAllcitiesInBook(title, postTitles);  
            }
        }
        System.out.println("Average time in milliseconds for this Query: " + neo.getAverage(postTitles));
        System.out.println("Median time in milliseconds for this Query: " + neo.getMedian(postTitles));
        System.out.println("----------------------------------------------------------------");
        
        System.out.println("Postgres Authors");     
        for (String author : authors) {
            for (int i = 0; i < times; i++) {
                pos.GetBooksAndCitiesByAuthor(author, postAuthors);  
            }
        }
        System.out.println("Average time in milliseconds for this Query: " + neo.getAverage(postAuthors));
        System.out.println("Median time in milliseconds for this Query: " + neo.getMedian(postAuthors));
        System.out.println("----------------------------------------------------------------");
        
        System.out.println("Postgres Locations");
        for (String[] location : locations) {
            for (int i = 0; i < times; i++) {
                pos.GetAllBooksWrittenNearby(location[0], location[1], postLocations);
            }
        }
        System.out.println("Average time in milliseconds for this Query: " + neo.getAverage(postLocations));
        System.out.println("Median time in milliseconds for this Query: " + neo.getMedian(postLocations));
        System.out.println("----------------------------------------------------------------");
    }

}

