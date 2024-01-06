package com.example.scipubwatch.logic;

import com.example.scipubwatch.entities.Teacher;

import java.util.List;

public interface TeacherLogic
{
    public List<Teacher> findTeacher(String nameForSearch);
    public Teacher getTeacherById(int id);
    public boolean addTeacher(String type, String[] nameSurnamePatronym, String linkgooglescholar, String linkscopus);

    public boolean deleteTeacher(int id);
}
