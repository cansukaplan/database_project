//Program
import db.HibernateUtil;
import db.VeritabaniIslemleri;
import gui.AnasayfaController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Program extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Anasayfa.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Tedarikçi");


        Scene scene = new Scene(root);
        primaryStage.setScene(scene);


        primaryStage.show();
        /**
         * veritabanını baslat
         */
        new Thread(() -> {
            final VeritabaniIslemleri veritabaniIslemleri = new VeritabaniIslemleri();
            AnasayfaController controller = loader.getController();
            if (veritabaniIslemleri.getSession() != null) {
                controller.setBaglandi(veritabaniIslemleri);

                primaryStage.setOnCloseRequest(event -> {
                    HibernateUtil.shutdown();
                });
            }

        }).start();
    }

}
