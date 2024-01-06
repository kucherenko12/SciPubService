package com.example.scipubwatch.logic;

import com.example.scipubwatch.dao.DAO;
import com.example.scipubwatch.dao.DAOImpl;
import com.example.scipubwatch.entities.Publication;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;

import java.io.File;
import java.math.BigInteger;

public class CreateADocument
{
    static Logger logger = Logger.getLogger(CreateADocument.class);
    private static WordprocessingMLPackage  wordMLPackage;
    private static ObjectFactory factory;
    public static void create
            (Publication[] publicationsB, Publication[] publicationsWestern, Publication[] publicationsScopus)
    {
        // Create the package
        WordprocessingMLPackage wordMLPackage;
        try
        {
            // Set up a simple configuration that logs on the console.
            BasicConfigurator.configure();

            logger.info("Entering application.");
            //Bar bar = new Bar();
            //bar.doIt();

            try
            {
                //WordprocessingMLPackage wordPackage = WordprocessingMLPackage.createPackage();
                //mainDocumentPart.addStyledParagraphOfText("Title", "Показники");
                //mainDocumentPart.addStyledParagraphOfText("Title", "наукової та науково-технічної діяльності за 2022 рік");
                //mainDocumentPart.addParagraphOfText("Кафедра інформатики та програмної інженерії " +
                //        "Факультет інформатики та обчислювальної техніки");
                //mainDocumentPart.addParagraphOfText("науковий напрям кафедри Технічні науки");
                //mainDocumentPart.addParagraphOfText("……");
                //mainDocumentPart.addParagraphOfText("");
                //---------------------------------------------------------------------------------------
                WordprocessingMLPackage wordPackage = load("C://MyTemp/Приклад подання публікацій style.docx");
                MainDocumentPart mainDocumentPart = wordPackage.getMainDocumentPart();

                mainDocumentPart.addParagraphOfText("8.1. Опубліковано монографій");

                mainDocumentPart.addParagraphOfText("8.2. Публікації (статті) у виданнях (фахових категорії Б; наукових виданнях країн ОЄСР; виданнях, що індексуються наукометричними базами Scopus/Web of Science (Copernicus для суспільних і гуманітарних наук)");

                mainDocumentPart.addParagraphOfText("8.2.1. Публікації у фахових виданнях категорії Б");

                mainDocumentPart.addObject(addTable(mainDocumentPart, publicationsB, getCells(1)));

                mainDocumentPart.addParagraphOfText("8.2.2. Інші публікації");
                mainDocumentPart.addObject(addTable(mainDocumentPart, publicationsWestern, getCells(1)));
                //mainDocumentPart.add
                mainDocumentPart.addParagraphOfText("8.2.3. Праці у виданнях, " +
                        "що індексуються наукометричними базами Scopus / Web of Science/ " +
                        "Copernicus для суспільних і гуманітарних наук");
                mainDocumentPart.addObject(addTable(mainDocumentPart, publicationsScopus, getCells(2)));
                File exportFile = new File("C://MyTemp/report.docx");
                wordPackage.save(exportFile);
            }
            catch(Exception exception){}

            logger.info("Exiting application.");
        }
        catch(Exception exception){}
    }

    private static Tbl addTable(MainDocumentPart mainDocumentPart, Publication[] publications, String[][] cells)
    {
        try
        {
            wordMLPackage = WordprocessingMLPackage.createPackage();
            factory = Context.getWmlObjectFactory();

            Tbl table = factory.createTbl();
            Tr tableHeaderRow = factory.createTr();
            addTableCell(mainDocumentPart, tableHeaderRow, cells[0][0]);
            addTableCell(mainDocumentPart, tableHeaderRow, cells[0][1]);
            addTableCell(mainDocumentPart, tableHeaderRow, cells[0][2]);
            addTableCell(mainDocumentPart, tableHeaderRow, cells[0][3]);
            addTableCell(mainDocumentPart, tableHeaderRow, cells[0][4]);
            if(cells[0].length == 6) {addTableCell(mainDocumentPart, tableHeaderRow, cells[0][5]);}
            table.getContent().add(tableHeaderRow);

            // Aspirant search ////////////////////////////////////////////////////////////////
            DAO dao = new DAOImpl();
            String[] aspirantSurnames = dao.getAspirantSurnames();
            boolean isAspirantAmongAuthors = false;

            int rowQuantity = publications.length;
            for(int i = 0; i < rowQuantity; i++)
            {
                for(String aspirantSurname : aspirantSurnames)
                {
                    for(String author : publications[i].getAuthors())
                    {
                        if(author.contains(aspirantSurname))
                        {
                            isAspirantAmongAuthors = true;
                            break;
                        }
                    }
                    if(isAspirantAmongAuthors ==  true) { break; }
                }
                ///////////////////////////////////////////////////////////////////////////

                String name = "";
                for(String author : publications[i].getAuthors())
                {
                    if(!author.equals(publications[i].getAuthors()[0])) {name = name + ", ";}
                    name = name + author;
                }
                name = name + " " + publications[i].getName() + " " + publications[i].getSourceData();
                Tr row = factory.createTr();
                addTableCell(mainDocumentPart, row, String.valueOf(i + 1));
                addTableCell(mainDocumentPart, row, name);
                addTableCell(mainDocumentPart, row, cells[1][2]);
                addTableCell(mainDocumentPart, row, cells[1][3]);
                if(cells[0].length == 6) {addTableCell(mainDocumentPart, row, cells[1][4]);}
                if(isAspirantAmongAuthors == true) {addTableCell(mainDocumentPart, row, "Так");}
                else {addTableCell(mainDocumentPart, row, "Ні");}
                table.getContent().add(row);
            }

            addBorders(table);

            //wordMLPackage.getMainDocumentPart().addObject(table);
            //wordMLPackage.save(new java.io.File("src/main/files/HelloWord4.docx"));
            return(table);
        }
        catch (Exception exception){exception.printStackTrace();}
        return null;
    }

    private static void addBorders(Tbl table)
    {
        table.setTblPr(new TblPr());
        CTBorder border = new CTBorder();
        border.setColor("auto");
        border.setSz(new BigInteger("4"));
        border.setSpace(new BigInteger("0"));
        border.setVal(STBorder.SINGLE);

        TblBorders borders = new TblBorders();
        borders.setBottom(border);
        borders.setLeft(border);
        borders.setRight(border);
        borders.setTop(border);
        borders.setInsideH(border);
        borders.setInsideV(border);
        table.getTblPr().setTblBorders(borders);
    }

    private static void addTableCell(MainDocumentPart mainDocumentPart, Tr tableRow, String content)
    {
        Tc tableCell = factory.createTc();
        tableCell.getContent().add(mainDocumentPart.createParagraphOfText(content));
        tableRow.getContent().add(tableCell);
    }

    /** Load a Docx Document from a File */
    public static WordprocessingMLPackage load(String fileLocation) throws Docx4JException
    {
        File file = new File(fileLocation);
        return WordprocessingMLPackage.load(file);
    }

    private static String[][] getCells(int type)
    {
        if(type == 1)
        {
            String[][] cells = new String[][]
            {
                {
                    "№ з/п",
                    "Бібліографічні дані \n" + "(автори, назва публікації, видання, № випуску, сторінки)\n",
                    "DOI",
                    "Чи є у співавторах студенти (так/ні)\n" +
                        "Якщо стаття опубліковано виключно студентами – вказати «самостійно»\n",
                    "Чи є у співавторах молоді вчені (так/ні)"
                },
                {
                    null,
                    null,
                    "",
                    "",
                    ""
                }
            };
            return(cells);
        }
        if(type == 2)
        {
            String[][] cells = new String[][]
            {
                {
                    "№ з/п",
                    "Бібліографічні дані \n" + "(автори, назва публікації, видання, № випуску, сторінки)\n",
                    "DOI",
                    "Індексація Scopus/Web of Science/ " +
                        "Copernicus для суспільних і гуманітарних наук (вказати базу, де видання індексується)",
                    "Чи є у співавторах студенти (так/ні)\n" +
                        "Якщо стаття опубліковано виключно студентами – вказати «самостійно»\n",
                    "Чи є у співавторах молоді вчені (так/ні)"
                },
                {
                    null,
                    null,
                    "",
                    "Scopus",
                    "",
                    ""
                }
            };
            return(cells);
        }
        return(null);
    }
}