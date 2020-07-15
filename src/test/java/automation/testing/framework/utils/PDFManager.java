package automation.testing.framework.utils;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class PDFManager {

    private WebDriver driver;

    public void testVerifyPDFInURL() {
        driver = new FirefoxDriver();
        driver.get("http://www.princexml.com/samples/");
        driver.findElement(By.linkText("PDF flyer")).click();
        String getURL = driver.getCurrentUrl();
        Assert.assertTrue(getURL.contains(".pdf"));
    }

    public void testVerifyPDFTextInBrowser() {

        driver.get("http://www.princexml.com/samples/");
        driver.findElement(By.linkText("PDF flyer")).click();
        Assert.assertTrue(verifyPDFContent(driver.getCurrentUrl(), "Prince Cascading"));
    }
    public boolean verifyPDFContent(String strURL, String reqTextInPDF) {

        boolean flag = false;

        PDFTextStripper pdfStripper = null;
        PDDocument pdDoc = null;
        COSDocument cosDoc = null;
        String parsedText = null;

        try {
            URL url = new URL(strURL);
            BufferedInputStream file = new BufferedInputStream(url.openStream());
            PDFParser parser = new PDFParser((file));

//            File file = new File("D:/Paynetsbicardbill.pdf");
//            PDFParser parser = new PDFParser(new FileInputStream(file));

            parser.parse();
            cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            pdfStripper.setStartPage(1);
            pdfStripper.setEndPage(1);

            pdDoc = new PDDocument(cosDoc);
            parsedText = pdfStripper.getText(pdDoc);
        } catch (MalformedURLException e2) {
            System.err.println("URL string could not be parsed "+e2.getMessage());
        } catch (IOException e) {
            System.err.println("Unable to open PDF Parser. " + e.getMessage());
            try {
                if (cosDoc != null)
                    cosDoc.close();
                if (pdDoc != null)
                    pdDoc.close();
            } catch (Exception e1) {
                e.printStackTrace();
            }
        }

        System.out.println("+++++++++++++++++");
        System.out.println(parsedText);
        System.out.println("+++++++++++++++++");

        if(parsedText.contains(reqTextInPDF)) {
            flag=true;
        }

        return flag;
    }


    public void ReadPDF() throws Exception {
        URL TestURL = new URL("http://www.axmag.com/download/pdfurl-guide.pdf");

        BufferedInputStream TestFile = new BufferedInputStream(TestURL.openStream());
        PDFParser TestPDF = new PDFParser(TestFile);
        TestPDF.parse();
        String TestText = new PDFTextStripper().getText(TestPDF.getPDDocument());

        Assert.assertTrue(TestText.contains("Open the setting.xml, you can see it is like this"));

    }

}
