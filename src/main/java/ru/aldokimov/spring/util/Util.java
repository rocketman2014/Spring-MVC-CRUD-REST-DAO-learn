package ru.aldokimov.spring.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.aldokimov.spring.models.Person;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/person";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static SessionFactory getCorrennSession() {
        SessionFactory sessionFactory = new Configuration().addAnnotatedClass(Person.class)
                .buildSessionFactory();
        return sessionFactory;
    }
}
