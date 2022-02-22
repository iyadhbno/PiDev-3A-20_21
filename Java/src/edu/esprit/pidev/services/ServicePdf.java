/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.pidev.services;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import edu.esprit.pidev.entities.Admin;
import edu.esprit.pidev.entities.Entreprise;
import edu.esprit.pidev.entities.Etudiant;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

/**
 *
 * @author SeifBS
 */
public class ServicePdf {

    public void liste_admins(Admin a) throws FileNotFoundException, DocumentException {
        ServiceAdmin sa = new ServiceAdmin();
        String message = "M/MME :" + a.getNom();
        String messageEmail = "EMAIL :" + a.getEmail();

        String file_name = "PDF/liste_admins.pdf";
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(file_name));
        document.open();
        Paragraph para = new Paragraph(message);
        Paragraph paraEmail = new Paragraph(messageEmail);

        document.add(para);
        document.add(paraEmail);
        List<Admin> liste_admins = sa.afficherAdmins();
        PdfPTable table = new PdfPTable(3);

        PdfPCell cl = new PdfPCell(new Phrase("Nom"));
        table.addCell(cl);
        PdfPCell cl1 = new PdfPCell(new Phrase("Prenom"));
        table.addCell(cl1);
        PdfPCell cl11 = new PdfPCell(new Phrase("Email"));
        table.addCell(cl11);

        table.setHeaderRows(1);
        document.add(table);

        int i = 0;
        for (i = 0; i < liste_admins.size(); i++) {
            table.addCell(liste_admins.get(i).getNom());
            table.addCell(liste_admins.get(i).getEmail());
            table.addCell(liste_admins.get(i).getTel());

        }
        document.add(table);

        document.close();

    }

    public void listeEtudiant(Admin et) throws FileNotFoundException, DocumentException {

        ServiceEtudiant sc = new ServiceEtudiant();
        String message = "M/MME :" + et.getNom();
        String messageEmail = "EMAIL :" + et.getEmail();

        String file_name = "PDF/listeEtudiants.pdf";
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(file_name));
        document.open();
        Paragraph para = new Paragraph(message);
        Paragraph paraEmail = new Paragraph(messageEmail);

        document.add(para);
        document.add(paraEmail);

        List<Etudiant> liste_coachs = sc.afficherEtudiants();

        PdfPTable table = new PdfPTable(3);

        PdfPCell cl = new PdfPCell(new Phrase("Nom"));
        table.addCell(cl);
        PdfPCell cl1 = new PdfPCell(new Phrase("Email"));
        table.addCell(cl1);
        PdfPCell cl2 = new PdfPCell(new Phrase("Tel"));
        table.addCell(cl2);

        table.setHeaderRows(3);
        document.add(table);

        int i = 0;
        for (i = 0; i < liste_coachs.size(); i++) {
            table.addCell(liste_coachs.get(i).getNom());
            table.addCell(liste_coachs.get(i).getEmail());
            table.addCell(liste_coachs.get(i).getTel());

        }
        document.add(table);

        document.close();

    }

    public void listeEntreprise(Entreprise e) throws FileNotFoundException, DocumentException {

        ServiceEntreprise sc = new ServiceEntreprise();
       String message = "M/MME :" + e.getNom();
        String messageEmail = "EMAIL :" + e.getEmail();

        String file_name = "PDF/listeEntreprise.pdf";
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(file_name));
        document.open();
        Paragraph para = new Paragraph(message);
        Paragraph paraEmail = new Paragraph(messageEmail);

        document.add(para);
        document.add(paraEmail);

        List<Entreprise> liste_clients = sc.afficherEntreprises();
        PdfPTable table = new PdfPTable(3);

        PdfPCell cl = new PdfPCell(new Phrase("Nom"));
        table.addCell(cl);
        PdfPCell cl1 = new PdfPCell(new Phrase("Email"));
        table.addCell(cl1);
        PdfPCell cl2 = new PdfPCell(new Phrase("Tel"));
        table.addCell(cl2);
        table.setHeaderRows(1);
        document.add(table);

        int i = 0;
        for (i = 0; i < liste_clients.size(); i++) {
            table.addCell(liste_clients.get(i).getNom());
            table.addCell(liste_clients.get(i).getEmail());
            table.addCell(liste_clients.get(i).getTel());

        }
        document.add(table);

        document.close();

    }

}
