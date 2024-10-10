import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TestWriteObject {
    public static void main(String[] args) throws IOException {
        Student s = new Student("Hoang Ngọc Linh", "21", "Ba Vi");
        FileWriter file = new FileWriter("linhObject.txt");
        BufferedWriter bw = new BufferedWriter(file);
        bw.write(s.toString());

        bw.flush();
        bw.close();
        file.close();
    }
}
