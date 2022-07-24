package hi.verkefni.vidmot;

import javafx.fxml.FXMLLoader;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

/******************************************************************************
 *  Nafn    : Hjörvar Sigurðsson
 *  T-póstur: hjs33@hi.is
 *
 *  Lýsing  : Klasi sem útfærir birtingu Snáks í notendaviðmóti.
 *
 *
 *****************************************************************************/

public class Snakur extends EiturSnakur{
    private SnakurController sc;

    /**
     * Smiður fyrir snák. Keyrir lesaSnakur aðgerðina.
     */
    public Snakur() {
        lesaSnakur();
    }

    /**
     * Tengir snákinn við útlit úr fxml skrá.
     */
    public void lesaSnakur() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("snakur-view.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Snýr snákinum í tiltekna átt.
     * @param r - Kommutala sem táknar áttina sem snúa skal í.
     */
    public void snua(double r) {
        this.setRotate(r);
    }

    /**
     * Lætur snákinn vaxa. Upphafstærð snáks er 20.0, en vöxtur er einnig 20.0.
     */
    public void vaxa() {
        //double a = this.getWidth();
        //double bil = 20.0;
        //this.setWidth(a + bil);

        Rectangle r = new Rectangle();
        //this.snakurRectangles.add(r);
        double x = this.getX();
        double y = this.getY();
        r.setX(x + 20);
        r.setY(y + 20);
        //System.out.println(snakurHali);
        //sc.fxPane.getChildren().add(r);
    }

    public void setSc(SnakurController sc) {
        this.sc = sc;
    }

    /**
     * Færir snákinn áfram miðað við þá átt sem hann stefnir í.
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
            this.setX(650 - (-1 * this.getX()));
        }
        if (this.getY() < 0) {
            this.setY(650 - (-1 * this.getY()));
        }
        if (this.getX() > 650) {
            this.setX(getX() - 650);
        }
        if (this.getY() > 650) {
            this.setY(getY() - 650);
        }
    }
}
