package Utils;

import Models.CenterToRecipientRecord;
import Models.RegionalCenterRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Used to store program state so that it is able to be restored on a different launch
 */
public class StateStorage {

    /**
     * Saves list of regional centre records to file
     * @param records - list of regional centre records
     * @param path - path where it should be saved
     * @throws IOException
     */
    public static void saveCenterList(ObservableList<RegionalCenterRecord> records, String path) throws IOException {
        saveObject(new ArrayList<>(records),path);
    }

    /**
     * Loads list of regional centre records from file
     * @param path - path to file
     * @return - ObservableList of regional centre records
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static ObservableList<RegionalCenterRecord> loadCenterList(String path) throws IOException, ClassNotFoundException {
        return FXCollections.observableArrayList((ArrayList<RegionalCenterRecord>) loadObject(path));
    }

    /**
     * Saves list of notification recipients toa file
     * @param notificationRecords - list of recipient records
     * @param path - path to a file
     * @throws IOException
     */
    public static void saveRecipients(List<CenterToRecipientRecord> notificationRecords, String path) throws IOException {
        saveObject(notificationRecords,path);
    }

    /**
     * Load list of notification recipients from file
     * @param path - path to a file to load
     * @return List of recipient records
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static List<CenterToRecipientRecord> loadRecipients(String path) throws IOException, ClassNotFoundException {
        return (List<CenterToRecipientRecord>) loadObject(path);
    }

    /**
     * Save an arbitrary object to file
     * @param o - object to save
     * @param path - path to a file
     * @throws IOException
     */
    private static synchronized void saveObject(Object o,String path) throws IOException {
        FileOutputStream fos = new FileOutputStream(path);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(o);
        oos.flush();
        oos.close();
        fos.close();
    }

    /**
     * Load an arbitrary object from file
     * @param path - path to a file
     * @return - object that was loaded
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static Object loadObject(String path) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(path);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object o = ois.readObject();
        ois.close();
        fis.close();
        return o;
    }

}
