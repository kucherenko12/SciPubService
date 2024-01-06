package com.example.scipubwatch.entities;

public class Teacher
{
    public int id;
    public String[] surnameNamePatronym;
    public String linkGoogleScholar;
    public String linkScopus;
    public Type type;

    public Teacher(int id, Type type, String[] surnameNamePatronym, String linkGoogleScholar, String linkScopus)
    {
        this.id = id;
        this.type = type;
        this.surnameNamePatronym = surnameNamePatronym;
        this.linkGoogleScholar = linkGoogleScholar;
        this.linkScopus = linkScopus;
    }

    public int getId() { return(id); }
    public String[] getSurnameNamePatronym() { return(surnameNamePatronym); }
    public String getLinkGoogleScholar() { return(linkGoogleScholar); }
    public String getLinkScopus() { return(linkScopus); }

    public enum Type
    {
        ASPIRANT,
        LECTURER
    }

    public static Type typeStringToEnum(String type)
    {
        type = type.toUpperCase();
        if(type.equals("ASPIRANT") || type.equals("LECTURER")) {return Type.valueOf(type);}
        else { return(null); }
    }
}
