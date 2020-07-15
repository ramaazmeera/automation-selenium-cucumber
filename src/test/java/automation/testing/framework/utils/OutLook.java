package automation.testing.framework.utils;

import com.testautomationguru.utility.PDFUtil;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.search.SubjectTerm;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class OutLook {

    private Message[] messages;
    private Folder folder;

    public enum EmailFolder {
        INBOX("INBOX");

        private String text;

        private EmailFolder(String text){
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }


    public void OutlookEmail(String username, String password, String server, EmailFolder emailFolder) throws Exception {
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imap");
        props.setProperty("mail.imap.ssl.enable", "true");
        props.setProperty("mail.imaps.partialfetch", "false");
        props.put("mail.mime.base64.ignoreerrors", "true");

        Session mailSession = Session.getInstance(props);
        mailSession.setDebug(true);
        Store store = mailSession.getStore("imap");
        store.connect(server, username, password);


        Folder folder = store.getFolder(emailFolder.getText());
        folder.open(Folder.READ_WRITE);

        System.out.println("Total Message:" + folder.getMessageCount());
        System.out.println("Unread Message:" + folder.getUnreadMessageCount());

        messages = folder.getMessages();

        for (Message mail : messages) {
            if (!mail.isSet(Flags.Flag.SEEN)) {

                System.out.println("***************************************************");
                System.out.println("MESSAGE : \n");

                System.out.println("Subject: " + mail.getSubject());
                System.out.println("From: " + mail.getFrom()[0]);
                System.out.println("To: " + mail.getAllRecipients()[0]);
                System.out.println("Date: " + mail.getReceivedDate());
                System.out.println("Size: " + mail.getSize());
                System.out.println("Flags: " + mail.getFlags());
                System.out.println("ContentType: " + mail.getContentType());
//                System.out.println("Body: \n" + getEmailBody(mail));
                System.out.println("Has Attachments: " + hasAttachments(mail));

            }
        }
    }

    public Message[] getMessagesBySubject(String subject, boolean unreadOnly, int maxToSearch) throws Exception{
        Map<String, Integer> indices = getStartAndEndIndices(maxToSearch);

        Message messages[] = folder.search(
                new SubjectTerm(subject),
                folder.getMessages(indices.get("startIndex"), indices.get("endIndex")));

        if(unreadOnly){
            List<Message> unreadMessages = new ArrayList<Message>();
            for (Message message : messages) {
                if(isMessageUnread(message)) {
                    unreadMessages.add(message);
                }
            }
            messages = unreadMessages.toArray(new Message[]{});
        }

        return messages;
    }

    public int getNumberOfMessages() throws MessagingException {
        return folder.getMessageCount();
    }

    public boolean isMessageUnread(Message message) throws Exception {
        return !message.isSet(Flags.Flag.SEEN);
    }


    private Map<String, Integer> getStartAndEndIndices(int max) throws MessagingException {
        int endIndex = getNumberOfMessages();
        int startIndex = endIndex - max;

        //In event that maxToGet is greater than number of messages that exist
        if(startIndex < 1){
            startIndex = 1;
        }

        Map<String, Integer> indices = new HashMap<String, Integer>();
        indices.put("startIndex", startIndex);
        indices.put("endIndex", endIndex);

        return indices;
    }

    public boolean hasAttachments(Message email) throws Exception {

        // suppose 'message' is an object of type Message
        String contentType = email.getContentType();
        System.out.println(contentType);

        if (contentType.toLowerCase().contains("multipart/mixed")) {
            // this message must contain attachment
            Multipart multiPart = (Multipart) email.getContent();

            for (int i = 0; i < multiPart.getCount(); i++) {
                MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(i);
                if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                    System.out.println("Attached filename is:" + part.getFileName());

                    MimeBodyPart mimeBodyPart = (MimeBodyPart) part;
                    String fileName = mimeBodyPart.getFileName();

                    String destFilePath = System.getProperty("user.dir") + "\\Resources\\";

                    File fileToSave = new File(fileName);
                    mimeBodyPart.saveFile(destFilePath + fileToSave);

                    // download the pdf file in the resource folder to be read by PDFUTIL api.

                    PDFUtil pdfUtil = new PDFUtil();
                    String pdfContent = pdfUtil.getText(destFilePath + fileToSave);

                    System.out.println("******---------------********");
                    System.out.println("\n");
                    System.out.println("Started reading the pdfContent of the attachment:==");


                    System.out.println(pdfContent);

                    System.out.println("\n");
                    System.out.println("******---------------********");

                    Path fileToDeletePath = Paths.get(destFilePath + fileToSave);
                    Files.delete(fileToDeletePath);
                }
            }

            return true;
        }

        return false;
    }

//    public String getEmailBody(Message email) throws IOException, MessagingException {
//
//        String line, emailContentEncoded;
//        StringBuffer bufferEmailContentEncoded = new StringBuffer();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(email.getInputStream()));
//        while ((line = reader.readLine()) != null) {
//            bufferEmailContentEncoded.append(line);
//        }
//
//        System.out.println("**************************************************");
//
//        System.out.println(bufferEmailContentEncoded);
//
//        System.out.println("**************************************************");
//
//        emailContentEncoded = bufferEmailContentEncoded.toString();
//
//        if (email.getContentType().toLowerCase().contains("multipart/related")) {
//
//            emailContentEncoded = emailContentEncoded.substring(emailContentEncoded.indexOf("base64") + 6);
//            emailContentEncoded = emailContentEncoded.substring(0, emailContentEncoded.indexOf("Content-Type") - 1);
//
//            System.out.println(emailContentEncoded);
//
//            String emailContentDecoded = new String(new Base64().decode(emailContentEncoded.toString().getBytes()));
//            return emailContentDecoded;
//        }
//
//        return emailContentEncoded;
//
//    }
}
