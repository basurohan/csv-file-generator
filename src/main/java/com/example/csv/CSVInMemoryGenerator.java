package com.example.csv;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class CSVInMemoryGenerator implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        sendEmail();
        System.out.println("Done");
    }

    private String getReport() throws IOException {
        List<Report> reports = new ArrayList<>();

        reports.add(new Report(1234, 101, "Store1"));
        reports.add(new Report(1235, 102, "Store2"));
        reports.add(new Report(1236, null, "Store3"));

        StringBuilder sb = new StringBuilder();
        reports.forEach(report -> sb
                .append(report.getStoreNbr())
                .append(",")
                .append(report.getTagId())
                .append(",")
                .append(report.getStoreName())
                .append("\n")
        );
        return sb.toString();
    }

    public void sendEmail() throws IOException, MessagingException {
        String to = "example@email.com";
        String from = "example@email.com";

        final String username = "example@email.com";
        final String password = "";


        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.smtp.socketFactory.fallback", "false");

        Session session = Session.getDefaultInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject("This is the Subject Line!");

        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("Mail Body");

        MimeBodyPart attachmentPart = new MimeBodyPart();
        attachmentPart.setDataHandler(new DataHandler(new ByteArrayDataSource(getReport(), "text/csv")));
        attachmentPart.setFileName("report.csv");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        multipart.addBodyPart(attachmentPart);

        message.setContent(multipart);

        // Send message
        Transport.send(message);
    }
}
