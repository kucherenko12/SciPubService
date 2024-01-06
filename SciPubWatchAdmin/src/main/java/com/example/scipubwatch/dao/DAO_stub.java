package com.example.scipubwatch.dao;

import com.example.scipubwatch.entities.Teacher;

import java.util.ArrayList;
import java.util.List;

public class DAO_stub implements DAO {
    public List<Teacher> findTeacher(String nameForSearch)
    {
        List<Teacher> foundTeachers = new ArrayList<>();
        foundTeachers.add(getTeacherStub(0));
        foundTeachers.add(getTeacherStub(1));
        return (foundTeachers);
    }

    public Teacher getTeacherById(int id)
    {
        Teacher teacher;
        try {teacher = getTeacherStub(id);}
        catch(Exception exception)
        {
            throw new IllegalArgumentException("no teacher with id " + id + " found");
        }


        return (teacher);
    }

    @Override
    public boolean addTeacher(Teacher.Type type, String[] nameSurnamePatronym, String linkgooglescholar, String linkscopus)
    {
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        return false;
    }

    @Override
    public boolean deleteTeacher(int id)
    {
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        return false;
    }

    @Override
    public String[] getJournalCategoryBNames()
    {
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        return new String[0];
    }

    @Override
    public String[] getAspirantSurnames()
    {
        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        return new String[0];
    }

    private Teacher getTeacherStub(int id)
    {
        Teacher teacher;
        if (id == 0)
        {
            teacher = new Teacher(0, Teacher.Type.LECTURER, new String[]{"Стеценко", "Інна", "Вячеславівна"},
                    "https://scholar.google.com/citations?user=5D5r-kkAAAAJ", null);
        }
        else if (id == 1)
        {
            teacher = new Teacher(1, Teacher.Type.LECTURER, new String[]{"Букасов", "Максим", "Михайлович"},
                    "https://scholar.google.com/citations?user=RCl3nSoAAAAJ", null);
        }
        else {teacher = null;}


        return (teacher);
    }
}
