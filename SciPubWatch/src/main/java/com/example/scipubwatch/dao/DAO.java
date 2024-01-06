package com.example.scipubwatch.dao;

import com.example.scipubwatch.entities.Teacher;

import java.util.List;

public interface DAO
{
    public List<Teacher> findTeacher(String nameForSearch);
    public Teacher getTeacherById(int id);
    public boolean addTeacher(Teacher.Type type, String[] nameSurnamePatronym, String linkgooglescholar, String linkscopus);
    public boolean deleteTeacher(int id);

    public String[] getJournalCategoryBNames();
    public String[] getAspirantSurnames();
}
