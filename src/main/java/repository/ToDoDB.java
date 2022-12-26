package repository;

import entity.ToDoEntity;

import javax.management.Query;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ToDoDB {
    private static String url = "jdbc:postgresql://localhost:5432/todo";
    private static String username = "bananza";
    private static String password = "123123";

    public int insert(ToDoEntity product) {

        try{
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){

                String sql = "INSERT INTO todo (created_at, status, text, updated_at) Values (?, ?, ?, ?)";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    preparedStatement.setDate(1, Date.valueOf(LocalDate.now()));
                    preparedStatement.setBoolean(2, product.getStatus());
                    preparedStatement.setString(3, product.getText());
                    preparedStatement.setDate(4, Date.valueOf(LocalDate.now()));

                    return  preparedStatement.executeUpdate();
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return 0;
    }

    public ArrayList<ToDoEntity> select(int pageNumber, int pageSize) {

        ArrayList<ToDoEntity> toDoEntities = new ArrayList<ToDoEntity>();
        int firstResult = pageNumber * pageSize;
        int maxResults = pageSize;
        try{
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){

                Statement statement = conn.createStatement();

                ResultSet resultSet = statement.executeQuery("SELECT * FROM todo OFFSET " + firstResult + " LIMIT " + pageSize);
                while(resultSet.next()){

                    Long id = resultSet.getLong(1);
                    Date created = resultSet.getDate(2);
                    Boolean status = resultSet.getBoolean(3);
                    String text = resultSet.getString(4);
                    Date updated = resultSet.getDate(5);
                    ToDoEntity todo = new ToDoEntity(created, id, status, text, updated);
                    toDoEntities.add(todo);
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return toDoEntities;
    }

    public ArrayList<ToDoEntity> select(int pageNumber, int pageSize, Boolean st) {

        ArrayList<ToDoEntity> toDoEntities = new ArrayList<ToDoEntity>();
        int firstResult = pageNumber * pageSize;
        int maxResults = pageSize;
        try{
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){

                Statement statement = conn.createStatement();

                ResultSet resultSet = statement.executeQuery("SELECT * FROM todo WHERE status = " + st + " OFFSET " + firstResult + " LIMIT " + pageSize);
                while(resultSet.next()){

                    Long id = resultSet.getLong(1);
                    Date created = resultSet.getDate(2);
                    Boolean status = resultSet.getBoolean(3);
                    String text = resultSet.getString(4);
                    Date updated = resultSet.getDate(5);
                    ToDoEntity todo = new ToDoEntity(created, id, status, text, updated);
                    toDoEntities.add(todo);
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return toDoEntities;
    }

    public void deleteAllReady() {

        try{
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                Statement statement = conn.createStatement();
                statement.executeQuery("delete from todo where status = true");
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }

    public void deleteById(Long id) {
        try{
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                Statement statement = conn.createStatement();
                statement.executeQuery("delete from todo where id =" + id);
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }

    public void changedStatus(Boolean status) {
        try{
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                Statement statement = conn.createStatement();
                statement.executeQuery("update todo set status = " + status + " where status = " + !status);
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }

    public void changeText(String text, Long id) {
        try{
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                Statement statement = conn.createStatement();
                statement.executeQuery("update todo set text = " + text + " where id = " + id);
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }

    public void changeStatus(Boolean status, Long id) {
        try{
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                Statement statement = conn.createStatement();
                statement.executeQuery("update todo set text = " + status + " where id = " + id);
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }

}
