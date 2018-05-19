package Models;

import java.io.Serializable;

/**
 * Record used to store regional centres that have registered with the environmental centre
 */
public class RegionalCenterRecord implements Serializable{
    private String name;
    private boolean alarm;

    public RegionalCenterRecord(String name){
        this.name=name;
        this.alarm=false;
    }

    public String getName(){
        return name;
    }

    public void triggerAlarm(){
        alarm=true;
    }

    public void resetAlarm(){
        alarm=false;
    }

    public boolean getAlarm(){
        return alarm;
    }
}
