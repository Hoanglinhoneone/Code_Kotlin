import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TestWriteFile {
    public static void main(String[] args) throws IOException {
        String[] NameList = {"Hoang Ngoc Long",
                            "Hoang Ngoc Linh",
                            "Hoang Ngoc Duong",
                            "Dinh Thi Thủy"
                             } ;
        FileWriter file = new FileWriter("linhne.txt", true);
        BufferedWriter bw = new BufferedWriter(file);
        for (String s :  NameList) {
            bw.write(s);
            bw.newLine();
        }
        bw.flush();
        bw.close();
        file.close();
    }
}
