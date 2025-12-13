import java.io.*;
import java.util.ArrayList;

public class StorageManager {

    public static <T> void save(String filename, ArrayList<T> list) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(list);
        } catch (IOException e) {
            System.out.println("[WARN] could not save " + filename + " : " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> ArrayList<T> load(String filename) {
        File f = new File(filename);
        if (!f.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (ArrayList<T>) ois.readObject();
        } catch (Exception e) {
            System.out.println("[WARN] could not load " + filename + " : " + e.getMessage());
            return new ArrayList<>();
        }
    }
}

