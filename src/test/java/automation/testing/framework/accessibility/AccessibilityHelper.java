package automation.testing.framework.accessibility;

import automation.testing.framework.selenium.WebDriverHandler;
import com.deque.axe.AXE;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertTrue;

public class AccessibilityHelper {

    private WebDriverHandler driverHandler;


    public AccessibilityHelper(WebDriverHandler driverHandler) {
        this.driverHandler = driverHandler;
    }


    public URL scriptUrl = AccessibilityHelper.class.getResource("/axe.min.js");

    static String fileName_suffix = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());


    public void testAccessibility(String page) {
        JSONObject responseJSON = new AXE.Builder(driverHandler.getDriver(), scriptUrl).options("{runOnly:{type: 'tag', values: ['wcag2a', 'wcag2aa']}}").analyze();

        JSONArray violations = responseJSON.getJSONArray("violations");

        if (violations.length() == 0) {
            assertTrue("No violations found", true);
        } else {
            AXE.writeResults("target/AXE accessibility tests", responseJSON);
            System.out.println(AXE.report(violations));
            AccessibilityHelper.writeToFileApacheCommonIO(AXE.report(violations), page);
//      assertTrue(AXE.report(violations), false);


        }

    }
    public static void writeToFileApacheCommonIO(String msg, String page){
        try {
            // 3rd parameter boolean append = true
            page = "Page:  " + page + System.lineSeparator() + System.lineSeparator();
//       FileUtils.writeStringToFile(new File("target/AXE_Run_report" + fileName_suffix + ".txt"), page, "UTF-8", true);
//       FileUtils.writeStringToFile(new File("target/AXE_Run_report" + fileName_suffix + ".txt"), msg + System.lineSeparator(), "UTF-8", true);
            FileUtils.writeStringToFile(new File("target/AXE-report/AXE-Report.html"), "<p> Page: " + page + "</p><p>" + msg + "</p>", "UTF-8", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
