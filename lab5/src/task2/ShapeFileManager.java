package task2;
import model.*;

import java.io.*;

public class ShapeFileManager {
    public void save(Shape[] shapes, String path) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path))) {
            out.writeObject(shapes);
        } catch (NotSerializableException e) {
            throw new IOException("клас " + e.getMessage() + " не підтримує серіалізацію", e);
        } catch (FileNotFoundException e) {
            throw new IOException("не вдалося створити файл \"" + path + "\" (перевірте шлях і права доступу)", e);
        } catch (IOException e) {
            throw new IOException("помилка запису: " + e.getMessage(), e);
        }
    }

    public Shape[] load(String path) throws IOException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(path))) {
            Object data = in.readObject();
            if (!(data instanceof Shape[])) {
                throw new IOException("файл не містить набору фігур");
            }
            return (Shape[]) data;

        } catch (FileNotFoundException e) {
            throw new IOException("файл \"" + path + "\" не знайдено", e);
        } catch (StreamCorruptedException e) {
            throw new IOException("файл пошкоджений або має неправильний формат", e);
        } catch (ClassNotFoundException e) {
            throw new IOException("у файлі невідомий клас: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new IOException("помилка читання: " + e.getMessage(), e);
        }
    }
}
