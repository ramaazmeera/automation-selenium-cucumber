package automation.testing.framework.api.util;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.pdfbox.util.PDFTextStripper;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.junit.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class Base64Utils {

  public String fileToBase64StringConversion(String inputFilePath) throws IOException {

    String filePath = System.getProperty("user.dir") + "/src/test/resources/files/" + inputFilePath;
    File inputFile = new File(filePath);

    byte[] fileContent = FileUtils.readFileToByteArray(inputFile);
    String encodedString = Base64.getEncoder().encodeToString(fileContent);
    return encodedString;
  }

  public void assertPdf(String outputFilePath, String encodedPdfString) {
    File file = new File(System.getProperty("user.dir") + "/target/test-classes/" + outputFilePath);

    try (FileOutputStream fos = new FileOutputStream(file); ) {
      byte[] decoder = Base64.getDecoder().decode(encodedPdfString);

      fos.write(decoder);
      System.out.println("PDF File Saved");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public String readWordFile(String fileName) {
    File docFile =
        new File(System.getProperty("user.dir") + "/src/test/resources/files/" + fileName);
    String docText = null;
    try {
      FileInputStream fis = new FileInputStream(docFile);
      HWPFDocument wordDoc = new HWPFDocument(fis);
      WordExtractor extractor = new WordExtractor(wordDoc);
      docText = extractor.getText().trim();
      System.out.println(extractor.getText());
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return docText;
  }

  public String readPdf(String fileName) {
    String pdfText = null;
    try {
      PDDocument document =
          PDDocument.load(
              new File(System.getProperty("user.dir") + "/target/test-classes/" + fileName));
      if (!document.isEncrypted()) {
        PDFTextStripperByArea stripper = new PDFTextStripperByArea();
        stripper.setSortByPosition(true);
        PDFTextStripper Tstripper = new PDFTextStripper();
        pdfText = Tstripper.getText(document).trim();
        System.out.println("Text:" + pdfText);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return pdfText;
  }

  public void wordToPDFComparison(String file1, String file2) {
    Assert.assertEquals(readWordFile(file1), readPdf(file2));
  }
}
