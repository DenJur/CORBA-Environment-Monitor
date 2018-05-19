import Models.CenterToRecipientRecord;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotificationRecordTest {
    @Test
    void nameTest() {
        String name = "testName";
        String email= "testEmail";
        String to="testTo";
        CenterToRecipientRecord r = new CenterToRecipientRecord(name,to,email);
        assertEquals(to,r.getRecipientName());
        assertEquals(name,r.getCenterName());
        assertEquals(email,r.getRecipientEmail());
    }
}
