package hi.verkefni.vidmot;

import javafx.fxml.FXMLLoader;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.util.Random;

/******************************************************************************
 *  Nafn    : Hjörvar Sigurðsson
 *  T-póstur: hjs33@hi.is
 *
 *  Lýsing  : Klasi sem útfærir birtingu Fæðu í notendaviðmóti.
 *
 *
 *****************************************************************************/

public class Faeda extends Circle {
    // Fastar fyrir stefnu.
    private int stefna;
    private static final int UPP = 90;
    private static final int NIDUR = 270;
    private static final int VINSTRI = 180;
    private static final int HAEGRI = 360;
    // Random hlutur til að útfæra handahófskennda stefnubreytingu.
    Random rand = new Random();

    /**
     * Smiður fyrir fæðu. Keyrir lesaFaeda aðgerðina.
     */
    public Faeda() {
        this.stefna = 0;
        lesaFaeda();
    }

    /**
     * Tengir fæðuna við útlit úr fxml skrá.
     */
    public void lesaFaeda() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("faeda-view.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Setur stefnu á tiltekinn fæðu-hlut.
     */
    public void snua() {
        if (this.stefna == 0) {
            int min = 0;
            int max = 4;
            int x = rand.nextInt(max - min + 1) + min;
            if (x == 0) {
                this.setRotate((double) UPP);
                this.stefna = UPP;
            }
            else if (x == 1) {
                this.setRotate((double) NIDUR);
                this.stefna = NIDUR;
            }
            else if (x == 2) {
                this.setRotate((double) VINSTRI);
                this.stefna = VINSTRI;
            }
            else if (x == 3) {
                this.setRotate((double) HAEGRI);
                this.stefna = HAEGRI;
            }
        }
    }

    /**
     * Hreyfir fæðuhlutinn samkvæmt stefnu.
     */
    public void afram() {
        double r = this.getRotate();
        double x = this.getCenterX();
        double y = this.getCenterY();
        double OFFSET = 20.0;

        if (r == 0.0 || r == 360.0) {
            this.setCenterX(x + OFFSET);
        }
        else if (r == 90.0) {
            this.setCenterY(y + OFFSET);
        }
        else if (r == 180.0) {
            this.setCenterX(x - OFFSET);
        }
        else if (r == 270.0) {
            this.setCenterY(y - OFFSET);
        }

        if (this.getCenterX() < 10) {
            this.setCenterX(650 - 10);
        }
        if (this.getCenterY() < 10) {
            this.setCenterY(650 - 10);
        }
        if (this.getCenterX() > 650) {
            this.setCenterX(getCenterX() - 650);
        }
        if (this.getCenterY() > 650) {
            this.setCenterY(getCenterY() - 650);
        }
    }
}
