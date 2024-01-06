package com.example.scipubwatch.dao;

import com.example.scipubwatch.entities.Publication;
import com.example.scipubwatch.entities.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOImpl implements DAO
{
    public Connection getConnection()
    {
        Connection connection = null;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/scipubwatch", "root", "root");
        }
        catch (Exception e) { System.out.println(e); }
        return connection;
    }

    @Override
    public List<Teacher> findTeacher(String nameForSearch)
    {
        List<Teacher> teachers = new ArrayList<>();
        try
        {
            Connection connection = getConnection();
            PreparedStatement preparedStatement;
            if(nameForSearch.equals(""))
            {
                preparedStatement = connection.prepareStatement ("select * from teachers");
            }
            else
            {
                preparedStatement = connection.prepareStatement
                        ("select * from teachers " +
                                "WHERE surname LIKE '"+nameForSearch+"' " +
                                "   OR name LIKE '"+nameForSearch+"' " +
                                "   OR patronym LIKE '"+nameForSearch+"' ");
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                int id = resultSet.getInt("id");
                Teacher.Type type = Teacher.Type.valueOf(resultSet.getString("type"));
                String[] surnameNamePatronym = new String[]
                        {
                                resultSet.getString("surname"),
                                resultSet.getString("name"),
                                resultSet.getString("patronym"),
                        };
                String linkGoogleScholar = resultSet.getString("linkgooglescholar");
                String linkScopus = resultSet.getString("linkscopus");
                teachers.add(new Teacher(id, type, surnameNamePatronym, linkGoogleScholar, linkScopus));
            }
        }
        catch (Exception e) { System.out.println(e); }
        return teachers;
    }

    @Override
    public Teacher getTeacherById(int id)
    {
        Teacher teacher = null;
        try
        {
            Connection connection = getConnection();
            PreparedStatement preparedStatement
                    = connection.prepareStatement("select * from teachers where id=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                Teacher.Type type = Teacher.Type.valueOf(resultSet.getString("type"));
                String[] surnameNamePatronym = new String[]
                {
                    resultSet.getString("surname"),
                    resultSet.getString("name"),
                    resultSet.getString("patronym"),
                };
                String linkGoogleScholar = resultSet.getString("linkgooglescholar");
                String linkScopus = resultSet.getString("linkscopus");
                teacher = new Teacher(id, type, surnameNamePatronym, linkGoogleScholar, linkScopus);
            }
        }
        catch (Exception e) { System.out.println(e); }
        return teacher;
    }

    @Override
    public boolean addTeacher(Teacher.Type type, String[] SurnameNamePatronym, String linkgooglescholar, String linkscopus)
    {
        try
        {
            // Open a connection
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            // Execute a query
            System.out.println("Inserting records into the table...");

            if (linkgooglescholar.equals("")) {linkgooglescholar = null;}
            if (linkscopus.equals("")) {linkscopus = null;}

            if(linkgooglescholar == null && linkscopus == null)
            {
                String sql = "INSERT INTO teachers (type, surname, name, patronym, linkgooglescholar, linkscopus)" +
                        "VALUES ('"+String.valueOf(type)+"', '"+SurnameNamePatronym[0]+"', '"+SurnameNamePatronym[1]+"', '" +
                        SurnameNamePatronym[2]+"', NULL, NULL);";
                statement.executeUpdate(sql);
            }
            else if(linkscopus == null)
            {
                String sql = "INSERT INTO teachers (type, surname, name, patronym, linkgooglescholar, linkscopus)" +
                        "VALUES ('"+String.valueOf(type)+"', '"+SurnameNamePatronym[0]+"', '"+SurnameNamePatronym[1]+"', '" +
                        SurnameNamePatronym[2]+"', '"+linkgooglescholar.toString() +"', NULL, );";
                statement.executeUpdate(sql);
            }
            else if(linkgooglescholar == null)
            {
                String sql = "INSERT INTO teachers (type, surname, name, patronym, linkgooglescholar, linkscopus)" +
                        "VALUES ('"+String.valueOf(type)+"', '"+SurnameNamePatronym[0]+"', '"+SurnameNamePatronym[1]+"', '" +
                        SurnameNamePatronym[2]+"', NULL,'"+linkscopus.toString() +"');";
                statement.executeUpdate(sql);
            }
            else
            {

            }

            System.out.println("Inserted records into the table...");
            return(true);
        }
        catch (SQLException e) { e.printStackTrace(); return(false);}
    }

    @Override
    public boolean deleteTeacher(int id)
    {
        try
        {
            // Open a connection
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            // Execute a query
            System.out.println("Deleting records from the table...");
            String sql = "DELETE FROM teachers WHERE id="+id+";";
            statement.executeUpdate(sql);
            System.out.println("Deleted records from the table...");
            return(true);
        }
        catch (SQLException e) { e.printStackTrace(); return(false);}
    }

    @Override
    public String[] getJournalCategoryBNames()
    {
        String[] journalCategoryBNames = null;
        try
        {
            List<String> journalCategoryBNamesList = new ArrayList<>();
            Connection connection = getConnection();
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement ("select * from journalscategoryb;");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                String journalName = resultSet.getString("name");
                journalCategoryBNamesList.add(journalName);
            }
            journalCategoryBNames = journalCategoryBNamesList.toArray(new String[journalCategoryBNamesList.size()]);
        }
        catch (Exception e) { System.out.println(e); }

        return(journalCategoryBNames);
    }

    @Override
    public String[] getAspirantSurnames()
    {
        String[] aspirantSurnames = null;
        try
        {
            List<String> aspirantSurnamesList = new ArrayList<>();
            Connection connection = getConnection();
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement ("select surname from teachers where type='ASPIRANT';");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                String aspirantSurname = resultSet.getString("surname");
                aspirantSurnamesList.add(aspirantSurname);
            }
            aspirantSurnames = aspirantSurnamesList.toArray(new String[aspirantSurnamesList.size()]);
        }
        catch (Exception e) { System.out.println(e); }

        return(aspirantSurnames);
    }
}
