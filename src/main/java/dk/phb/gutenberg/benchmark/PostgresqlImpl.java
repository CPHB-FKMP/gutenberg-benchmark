/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.phb.gutenberg.benchmark;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PostgresqlImpl {

    public void GetBooksByCity(String city, List<Double> timetable) throws SQLException {
        Connection con = PostDBConnection.getConnection();
        con.createStatement().execute("SET search_path TO gutenberg;");
        try {
            long startTime = System.nanoTime();
            ResultSet res = con.createStatement().executeQuery("SELECT * FROM books " +
            "INNER JOIN books_cities ON (books.book_id = books_cities.book_id) " +
            "INNER JOIN cities ON (books_cities.latitude = cities.latitude AND books_cities.longitude = cities.longitude) " +
            "INNER JOIN authors_books ON (books.book_id = authors_books.book_id) " +
            "INNER JOIN authors ON (authors_books.author_id = authors.author_id) WHERE cities.name = '"+city+"';");
            long endtime = System.nanoTime() - startTime;
            timetable.add((double) endtime / 1000000.0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void GetAllcitiesInBook(String title, List<Double> timetable) {
        Connection con = PostDBConnection.getConnection();

        try {
            long startTime = System.nanoTime();
            ResultSet res = con.createStatement().executeQuery(""
                    + "SELECT DISTINCT ON (books.book_id) books.book_id,"
                    + " books.title, cities.name, cities.latitude,"
                    + " cities.longitude FROM cities"
                    + " NATURAL JOIN books_cities"
                    + " NATURAL JOIN books"
                    + " WHERE books.title = '"+title+"';");
            long endtime = System.nanoTime() - startTime;
            timetable.add((double) endtime / 1000000.0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void GetBooksAndCitiesByAuthor(String author, List<Double> timetable) {
        Connection con = PostDBConnection.getConnection();

        try {
            long startTime = System.nanoTime();
            ResultSet res = con.createStatement().executeQuery(""
                    + "SELECT DISTINCT ON (books.book_id) books.book_id,"
                    + " books.title, cities.latitude, cities.longitude,"
                    + " cities.name FROM books"
                    + " NATURAL JOIN authors_books"
                    + " NATURAL JOIN books_cities"
                    + " NATURAL JOIN cities"
                    + " WHERE author_id IN (SELECT author_id FROM authors "
                    + "WHERE name = '"+author+"');");
            long endtime = System.nanoTime() - startTime;
            timetable.add((double) endtime / 1000000.0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void GetAllBooksWrittenNearby(String lat, String lng, List<Double> timetable) {
        Connection con = PostDBConnection.getConnection();

        try {
            long startTime = System.nanoTime();
            ResultSet res = con.createStatement().executeQuery(""
                    + "SELECT DISTINCT ON (books.book_id) title, books.book_id "
                    + "FROM books "
                    + "JOIN books_cities ON (books.book_id = books_cities.book_id) "
                    + "JOIN cities ON (books_cities.latitude = cities.latitude "
                    + "AND books_cities.longitude = cities.longitude) "
                    + "GROUP BY (title, books.book_id, cities.latitude, cities.longitude) "
                    + "HAVING geodistance(cities.latitude, cities.longitude, ( "+lat+" ), ( "+lng+" )) <= 20;");
            long endtime = System.nanoTime() - startTime;
            timetable.add((double) endtime / 1000000.0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
