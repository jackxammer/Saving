import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress start = new GameProgress(20, 1, 1, 10.5);
        GameProgress middle = new GameProgress(63, 4, 7, 300);
        GameProgress endgame = new GameProgress(100, 10, 9000, 784.3);

        List<GameProgress> list = new ArrayList<>();
        list.add(start);
        list.add(middle);
        list.add(endgame);

        Iterator<GameProgress> iterator = list.iterator();
        int i = 0;
        List<String> saveFiles = new ArrayList<>();
        while (iterator.hasNext()) {
            i++;
            saveGame("/home/jackxammer/Documents/Games/savegames/save" + i + ".dat", iterator.next());
            saveFiles.add("/home/jackxammer/Documents/Games/savegames/save" + i + ".dat");
        }

        zipFiles("/home/jackxammer/Documents/Games/savegames/zip.zip", saveFiles);
    }

    public static void saveGame(String path, GameProgress progress) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(progress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String path, List<String> saves) {
        int i = 0;
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path))) {
            for (String name: saves) {
                i++;
                try (FileInputStream fis = new FileInputStream(name)) {
                    ZipEntry entry = new ZipEntry("packed_save" + i);
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

