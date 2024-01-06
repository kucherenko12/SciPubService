package com.example.scipubwatch.logic;

import com.example.scipubwatch.dao.DAO;
import com.example.scipubwatch.dao.DAOImpl;
import com.example.scipubwatch.entities.Teacher;

import java.util.List;

public class TeacherLogicImpl implements TeacherLogic
{
    //DAO dao = new DAO_stub();
    DAO dao = new DAOImpl();

    @Override
    public List<Teacher> findTeacher (String nameForSearch)
    {
        return(dao.findTeacher(nameForSearch));
    }

    @Override
    public Teacher getTeacherById(int id)
    {
        return(dao.getTeacherById(id));
    }

    @Override
    public boolean addTeacher(String type, String[] nameSurnamePatronym, String linkgooglescholar, String linkscopus)
    {
        Object typeObject = Teacher.typeStringToEnum(type);
        if(typeObject == null) { return(false); }
        else { return(dao.addTeacher((Teacher.Type)typeObject, nameSurnamePatronym, linkgooglescholar, linkscopus)); }
    }

    @Override
    public boolean deleteTeacher(int id)
    {
        return dao.deleteTeacher(id);
    }
}
