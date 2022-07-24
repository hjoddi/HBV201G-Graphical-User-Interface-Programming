package hi.verkefni.vidmot;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

/******************************************************************************
 *  Nafn    : Hjörvar Sigurðsson
 *  T-póstur: hjs33@hi.is
 *
 *  Lýsing  : Aðalklasi fyrir forritið Snakur. Les inn aðalnotendaviðmótið.
 *
 *
 *****************************************************************************/

public class SnakurApplication extends Application {

    // Fastar
    private static KeyCode kc; //Segir til um hvaða örvatakka var ýtt á.
    private static int rotation = 0; //Segir til um hvaða örvatakka var ýtt á á heiltölusniði.

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SnakurApplication.class.getResource("snakurAdal-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Snakur");
        stage.setScene(scene);
        stage.show();

        // Fylgist með örvatökkunum, og uppfærir fastana þegar ýtt er á örvatakka.
        scene.addEventFilter(KeyEvent.ANY, event->{});
        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event){

                if (event.getCode() == KeyCode.RIGHT) {
                    kc = KeyCode.RIGHT;
                    rotation = 360;
                }
                else if (event.getCode() == KeyCode.LEFT) {
                    kc = KeyCode.LEFT;
                    rotation = 180;
                }
                else if (event.getCode() == KeyCode.UP) {
                    kc = KeyCode.UP;
                    rotation = 90;
                }
                else if (event.getCode() == KeyCode.DOWN) {
                    kc = KeyCode.DOWN;
                    rotation = 270;
                }
            }
        });

    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * Skilar fastanum kc, sem segir til um á hvaða örvatakka var ýtt.
     * @return KeyCode kc - Örvatakkinn sem ýtt var á.
     */
    public KeyCode getKc() {
        return kc;
    }

    /**
     * Skilar fastanum rotation, sem segir til um í hvaða átt snákur skal halda.
     * @return int rotation - Stefnan sem snákur skal halda.
     */
    public int getRotation() {
        return rotation;
    }
}
