package com.example.scipubwatch.entities;

import java.util.Calendar;

public class Publication
{
     // Как реализовать то, что не все поля могут быть заполнены?

     String[] authors;
     String name;
     //
     String establishment;
     String sourceData;
     //
     String publisherRequisites;
     int volume;
     int pagesQuantity;
     PublicationType publicationType;
     Calendar date;
     Integer year;
     String publisher;
     Integer quotations;
     int ISSN;
     int ISBN;
     int DOI;
     //String publicationType; // Лучше сделать дополнительный универсальный тип?

     public Publication(String[] authors, String name, String establishment, String sourceData,
                        PublicationType publicationType, Integer year, Integer quotations)
     {
          this.authors = authors;
          this.name = name;
          this.establishment = establishment;
          this.sourceData = sourceData;
          this.publicationType = publicationType;
          date = Calendar.getInstance();
          if(year != null) {date.set(Calendar.YEAR, year);}
          this.year = year;
          this.quotations = quotations;
     }

     public enum PublicationType
     {
          CONFERENCE_REPORT,
          JOURNAL_ARTICLE,
          BOOK_CHAPTER,
          MONOGRAPHY
     }


     // Узнать как делать вложенный конструктор !!
     //public Publication(String[] authors)
     //{
     //     this.Publication(authors, "");
     //}
     //================================================================


     //автори, назва, реквізити видання (назва, том(якщо є), сторінки, рік, видавець, ISSN чи ISBN (якщо є), DOI (якщо є)),
     //тип публікації (стаття у періодичному scopus/web of science виданні, стаття у фаховому виданні з указуванням категорії,
     //                стаття у науковому періодичному виданні (що не відноситься до попередніх 2 класифікацій),
     //монографія, тези scopus/web of science міжнародної наукової конференції, тези міжнародної наукової конференції,
     //тези всекраїнської наукової конференції).

     public String[] getAuthors() {return(authors);}
     public String getName() {return(name);}
     public String getEstablishment() {return(establishment);}
     public Calendar getDate() {return(date);}
     public Integer getYear() {return(year);}
     public String getSourceData() {return(sourceData);}
}
