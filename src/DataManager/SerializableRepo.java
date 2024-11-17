package DataManager;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface SerializableRepo<T> {
    void saveData() throws IOException;
    void loadData() throws IOException, ClassNotFoundException, NoSuchAlgorithmException;
    T getData();

}
