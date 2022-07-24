package hi.verkefni.vidmot;

import javafx.fxml.FXMLLoader;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.Random;

public class SerstakurEiturSnakur extends Rectangle {
    // Fastar fyrir stefnu.
    private static final int UPP = 90;
    private static final int NIDUR = 270;
    private static final int VINSTRI = 180;
    private static final int HAEGRI = 360;
    // Random hlutur til að útfæra handahófskennda stefnubreytingu.
    Random rand = new Random();

    /**
     * Smiður fyrir sérstakann eitursnák. Keyrir lesaSerstakurEiturSnakur aðgerðina.
     */
    public SerstakurEiturSnakur() {
        lesaSerstakurEiturSnakur();
    }

    /**
     * Tengir sérstaka eitursnákinn við útlit úr fxml skrá.
     */
    public void lesaSerstakurEiturSnakur() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("serstakurEiturSnakur-view.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     *  Færir sérstaka eitursnákinn áfram á þeirri stefnu sem hann er á.
     */
    public void afram() {
        double r = this.getRotate();
        double x = this.getX();
        double y = this.getY();
        double OFFSET = 20.0;

        if (r == 0.0 || r == 360.0) {
            this.setX(x + OFFSET);
        }
        else if (r == 90.0) {
            this.setY(y + OFFSET);
        }
        else if (r == 180.0) {
            this.setX(x - OFFSET);
        }
        else if (r == 270.0) {
            this.setY(y - OFFSET);
        }

        if (this.getX() < 0) {
            this.setX(650 - this.getX());
        }
        if (this.getY() < 0) {
            this.setY(650 - this.getY());
        }
        if (this.getX() > 650) {
            this.setX(getX() - 650);
        }
        if (this.getY() > 650) {
            this.setY(getY() - 650);
        }
    }

    /**
     * Snýr eitursnákinum í handahófskennda átt.
     */
    public void snua() {
        int min = 0;
        int max = 4;
        int x = rand.nextInt(max - min + 1) + min;
        if (x == 0) {
            this.setRotate((double) UPP);
        }
        else if (x == 1) {
            this.setRotate((double) NIDUR);
        }
        else if (x == 2) {
            this.setRotate((double) VINSTRI);
        }
        else if (x == 3) {
            this.setRotate((double) HAEGRI);
        }
    }
}
