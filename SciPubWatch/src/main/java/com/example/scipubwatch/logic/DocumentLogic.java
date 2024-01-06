package com.example.scipubwatch.logic;

import com.example.scipubwatch.dao.DAO;
import com.example.scipubwatch.dao.DAOImpl;
import com.example.scipubwatch.entities.Publication;
import com.example.scipubwatch.entities.Teacher;
import org.docx4j.wml.P;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DocumentLogic
{
    public static boolean GetDataCreateDocument(int years)
    {
        try
        {
            Publication[] googleScholarPublications = removeCoincidences(getFromGoogleScholar(years));
            Publication[] scopusPublications = removeCoincidences(ScarpFromScopus(years));
            googleScholarPublications = removeCoincidencesInOneFromTwo(googleScholarPublications, scopusPublications);

            Object[] fromGoogleScholarSplit = splitIntoTwoFirstB(googleScholarPublications);
            Publication[] bPublications = (Publication[])fromGoogleScholarSplit[0];
            Publication[] otherPublications = (Publication[])fromGoogleScholarSplit[1];

            CreateADocument.create(bPublications, otherPublications, scopusPublications);

            return(true);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return(false);
        }
    }


    public static Publication[] ScarpFromScopus(int years)
    {
        try
        {
            ScraperService scraperService = new ScraperService
                    ("E:/D Programms/Mozilla Firefox/Firefox.exe",
                            "E://D_programming/RuntimeProgramms/WebDriver/geckodriver-v0.33.0-win64/geckodriver.exe",
                            3);
            TeacherLogic teacherLogic = new TeacherLogicImpl();
            List<Teacher> teacherList = teacherLogic.findTeacher("");

            List<Publication> publicationsList = new ArrayList<>();

            int year = LocalDate.now().getYear() - years + 1;
            for(Teacher teacher : teacherList)
            {
                if(teacher.getLinkScopus() != null)
                {
                    //scraperService.getUrl("https://www.scopus.com/authid/detail.uri?authorId=55368781500");
                    scraperService.getUrl(teacher.getLinkScopus());

                    for (int i = 0; i < 200; i++)
                    { scraperService.scrollBy(10); }
                    //scraperService.scrollBy(500);

                    try
                    {
                        scraperService.waitUntilNumberOfElementsToBeMoreThan(ScraperService.FindType.FIND_ELEMENT_BY_CLASS_NAME, "results-list-item", 9);
                    }
                    catch (Exception exception) { }
                    //scraperService.scrape(ScraperService2.FindType.FIND_ELEMENT_BY_CLASS_NAME, "results-list-item");
                    List<String[]> data = scraperService.scrapeAndGet(ScraperService.FindType.FIND_ELEMENT_BY_CLASS_NAME, "results-list-item");

                    Publication[] teacherPublications = dataToPublicationScopus(data);
                    for(Publication publication : teacherPublications)
                    {
                        if(publication.getYear() >= year)
                        {
                            publicationsList.add(publication);
                        }
                    }
                    //break;
                }
            }
            scraperService.stopFirefoxDriver();
            Publication[] publications = publicationsList.toArray(new Publication[publicationsList.size()]);

            return(publications);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return(null);
        }
    }


    private static Publication[] dataToPublicationScopus(List<String[]> data)
    {
        Publication[] publications = new Publication[data.size()];
        int i = 0;
        for (String[] pData : data)
        {
            int j = 0;
            Publication.PublicationType type;

            String typeString = pData[j];
            if(pData[j].split(" ").length > 2)
            {
                int secondSpaceIndex = typeString.indexOf(" ", typeString.indexOf(" ") + 1);
                typeString = typeString.substring(0, secondSpaceIndex);
            }
            if(typeString.equals("Article")) {type = Publication.PublicationType.JOURNAL_ARTICLE;}
            else if(typeString.equals("Conference Paper")) {type = Publication.PublicationType.CONFERENCE_REPORT;}
            else if(typeString.equals("Book Chapter")) {type = Publication.PublicationType.BOOK_CHAPTER;}
            else if(typeString.equals("Monography")) {type = Publication.PublicationType.MONOGRAPHY;}
            else {type =  null; }
            j++;

            String name = pData[j];
            j++;

            List<String> authorsList = new ArrayList<>();
            while(true)
            {
                authorsList.add(pData[j]);
                j++;
                if(pData[j].equals(",") || pData[j].equals(", ...")) { j++; }
                else { break; }
            }

            String[] authors = authorsList.toArray(new String[authorsList.size()]);
            //String[] authors = new String[authorsList.size()];
            //int iterator = 0;
            //for(String author : authorsList)
            //{
            //    authors[iterator] = author;
            //    iterator++;
            //}

            String sourceData = pData[j];
            j++;

            if(pData[j].equals("this link is disabled"))
            {
                j++;
                sourceData = sourceData + pData[j];
                j++;
            }

            if(sourceData.contains("страницы"))
            {
                int pagesIndex = sourceData.indexOf("страницы");
                sourceData = sourceData.substring(0, pagesIndex) + "pp." + sourceData.substring(pagesIndex + 8);

            }
            j = j + 3;
            int quotations = Integer.valueOf(pData[j]);

            //////////////////////////////////////////////////////////////////////////////////
            //String[] temp = sourceData.split(",");

            //String establishmnt = temp[0];
            //int year = Integer.parseInt(temp[temp.length - 3].trim());
            //int year = Integer.parseInt(temp[temp.length - 3].trim());

            //PublicationScopus publication = new PublicationScopus(authors, name, establishmnt, type, year, quotations);
            //publications[i] = new Publication(authors, name, establishmnt, sourceData, type, year, quotations);
            //i++;
            //////////////////////////////////////////////////////////////////////////////////

            //////////////////////////////////////////////////////////////////////////////////
            String[] orderedTemp = null;
            String[] temp = sourceData.split(",");
            int tempLength = temp.length;
            Integer yearId = null;
            for(int i1 = 0; i1 < tempLength; i1++)
            {
                try
                {
                    Integer.parseInt(temp[i1].trim());
                    // If it did not get exception - it is a year. Go on.
                    yearId = i1;
                    orderedTemp = new String[tempLength-yearId + 1];
                    int i3 = 0;
                    for(int i2 = 0; i2 < tempLength; i2++)
                    {
                        if((i2 < yearId - 1)&&(orderedTemp[i3] == null))
                        {
                            orderedTemp[i3] = temp[i2].trim() + ", ";
                        }
                        else if((i2 < yearId - 1)&&(orderedTemp[i3] != null))
                        {
                            orderedTemp[i3] = orderedTemp[i3] + temp[i2].trim() + ", ";
                        }
                        else if((i2 == yearId - 1)&&(orderedTemp[i3] != null))
                        {
                            orderedTemp[i3] = orderedTemp[i3] + temp[i2].trim();
                            i3++;
                        }
                        else
                        {
                            orderedTemp[i3] = temp[i2].trim();
                            i3++;
                        }
                    }
                    break; // If you do not break and there is another 4 digits numbers in temp, you will get error
                }
                catch(Exception e){}
            }

            String establishmnt = orderedTemp[0];
            //int year = Integer.parseInt(temp[temp.length - 3].trim());
            int year = Integer.parseInt(orderedTemp[1]);

            //PublicationScopus publication = new PublicationScopus(authors, name, establishmnt, type, year, quotations);
            publications[i] = new Publication(authors, name, establishmnt, sourceData, type, year, quotations);
            i++;
            /////////////////////////////////////////////////////////////////////////////////
        }
        return(publications);
    }


    private static Publication[] getFromGoogleScholar(int years)
    {
        TeacherLogic teacherLogic = new TeacherLogicImpl();
        PublicationManipulation publicationManipulation = new PublicationManipulation();

        List<Teacher> teacherList = teacherLogic.findTeacher("");
        List<Publication> publicationsList = new ArrayList<>();
        int year = LocalDate.now().getYear() - years + 1;
        for (Teacher teacher : teacherList) {
            if (teacher.getLinkGoogleScholar() != null) {
                try {
                    List<Publication> teacherPublicationsList
                            = publicationManipulation.findPublicationsFast(teacher, "date");
                    //Publication[] teacherPublications
                    //        = teacherPublicationsList.toArray(new Publication[teacherPublicationsList.size()]);

                    for (Publication publication : teacherPublicationsList) {
                        if (publication.getYear() == null) { }
                        else if (publication.getYear() >= year) {
                            publicationsList.add(publication);
                        }
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                    return (null);
                }
            }
        }

        Publication[] publications = publicationsList.toArray(new Publication[publicationsList.size()]);
        return(publications);
    }


    private static Object[] splitIntoTwoFirstB(Publication[] publicationsList)
    {
        List<Publication> bPublicationsList = new ArrayList<>();
        List<Publication> otherPublicationsList = new ArrayList<>();

        DAO dao = new DAOImpl();
        String[] journalCategoryBNames = dao.getJournalCategoryBNames();
        for (Publication publication : publicationsList)
        {
            for (String journalName : journalCategoryBNames)
            {
                //if( (publication.getEstablishment().length() >= journalName.length()) &&
                //    (publication.getEstablishment().substring(0, journalName.length() - 1).equals(journalName)) )
                if( (publication.getEstablishment().length() >= journalName.length()) &&
                    (publication.getEstablishment().contains(journalName)) )
                {
                    bPublicationsList.add(publication);
                    break;
                }
            }
            otherPublicationsList.add(publication);
        }

        Publication[] bPublications
                = bPublicationsList.toArray(new Publication[bPublicationsList.size()]);

        Publication[] otherPublications
                = otherPublicationsList.toArray(new Publication[otherPublicationsList.size()]);

        return(new Object[] {bPublications, otherPublications});
    }


    private static Publication[] removeCoincidences(Publication[] publications)
    {
        int length = publications.length;
        int removed = 0;
        for(int i = 0; i < length; i++)
        {
            if(publications[i] != null)
            {
                int coincidences = 0;
                for(int j = 0; j < length; j++)
                {
                    if(publications[j] != null)
                    {
                        if(publications[i].getName().equals(publications[j].getName())) {coincidences++;}
                        if(coincidences > 1) { publications[j] = null; removed++; }
                    }
                }
            }
        }

        Publication[] newPublications = new Publication[length - removed];
        int j = 0;
        for(int i = 0; i < length; i++)
        {
            if(publications[i] != null)
            {
                newPublications[j] = publications[i];
                j++;
            }
        }

        System.out.println("Removed " + removed + " coincidences in one Publications[] array.");
        return(newPublications);
    }

    private static Publication[] removeCoincidencesInOneFromTwo(Publication[] publications1, Publication[] publications2)
    {
        // Removes coincidence from the first array.
        // Both arrays have to have no coincidences inside.
        int length1 = publications1.length;
        int length2 = publications2.length;
        int removed = 0;
        for(int i = 0; i < length1; i++)
        {
            for(int j = 0; j < length2; j++)
            {
                if(publications1[i].getName().equals(publications2[j].getName()))
                {
                    publications1[i] = null;
                    removed++;
                    break;
                }
            }
        }

        Publication[] newPublications = new Publication[length1 - removed];
        int j = 0;
        for(int i = 0; i < length1; i++)
        {
            if(publications1[i] != null)
            {
                newPublications[j] = publications1[i];
                j++;
            }
        }

        System.out.println("Removed " + removed + " coincidences in one Publications[] array.");
        return(newPublications);
    }
}
