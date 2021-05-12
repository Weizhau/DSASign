package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kotlin.Pair;

import java.io.File;
import java.io.FileOutputStream;

public class Main/* extends Application*/ {

    /*@Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }*/

    public static void main(String[] args) {
        //launch(args);
        File file = new File("C:\\Users\\ASUS\\Desktop\\laby\\DSASign\\src\\sample\\testfile.txt");
        Pair<byte[], DsaSigner.SignatureKeys> signingOutput = DsaSigner.INSTANCE.signFile(file, 107, 643);
        File signedCopy = new File("C:\\Users\\ASUS\\Desktop\\laby\\DSASign\\src\\sample\\signedCopy.txt");

        try {
            FileOutputStream fos = new FileOutputStream(signedCopy, false);
            fos.write(signingOutput.getFirst());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(DsaSigner.INSTANCE.checkSign(signedCopy, 107, 643, signingOutput.getSecond().getG(), signingOutput.getSecond().getY()));
    }
}
