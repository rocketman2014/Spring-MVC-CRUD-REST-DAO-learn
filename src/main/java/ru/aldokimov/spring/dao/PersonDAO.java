package ru.aldokimov.spring.dao;

import org.springframework.stereotype.Component;
import ru.aldokimov.spring.models.Person;
import ru.aldokimov.spring.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;
    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = Util.getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Person> index() {
        List<Person> people = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM person");
            while (resultSet.next()) {
                Person person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setAge(resultSet.getInt("age"));
                person.setEmail(resultSet.getString("email"));
                people.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return people;
    }

    public Person show(int id) {
        Person person = null;
        try {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM person WHERE id=?");
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            person = new Person();
            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setAge(resultSet.getInt("age"));
            person.setEmail(resultSet.getString("email"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    public void save(Person person) {

        try (PreparedStatement statement = connection.
                prepareStatement("INSERT INTO person VALUES (1, ?, ?, ?)")) {
            statement.setString(1, person.getName());
            statement.setInt(2, person.getAge());
            statement.setString(3, person.getEmail());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(int id, Person updatePerson) {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("UPDATE person SET name=?, age=?, email=? WHERE id =?");
            statement.setString(1, updatePerson.getName());
            statement.setInt(2, updatePerson.getAge());
            statement.setString(3, updatePerson.getEmail());
            statement.setInt(4, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try {
            PreparedStatement statement  =
                    connection.prepareStatement("DELETE FROM person WHERE id=?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}