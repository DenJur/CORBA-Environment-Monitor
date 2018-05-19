package Models;

import java.io.Serializable;

/**
 * Record to store agencies registered to receive notifications
 */
public class CenterToRecipientRecord implements Serializable{
    private String centerName;
    private String recipientName;
    private String recipientEmail;

    public CenterToRecipientRecord(String centerName,String recipientName,String recipientEmail){
        this.centerName=centerName;
        this.recipientEmail=recipientEmail;
        this.recipientName=recipientName;
    }

    public String getCenterName() {
        return centerName;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public String getRecipientName() {
        return recipientName;
    }
}
