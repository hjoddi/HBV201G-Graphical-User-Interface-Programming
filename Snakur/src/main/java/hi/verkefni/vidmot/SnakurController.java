package hi.verkefni.vidmot;

import hi.verkefni.vinnsla.Leikur;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

/******************************************************************************
 *  Nafn    : Hjörvar Sigurðsson
 *  T-póstur: hjs33@hi.is
 *
 *  Lýsing  : Aðalstýriklasi fyrir forritið Snakur. Hefur tilviksbreytur fyrir
 *              helstu viðmótshluti. Hefur aðgerðir fyrir að hefja leik, birta
 *              nýja eitursnáka og fæðu, uppfæra stöðu eitursnáka, og athuga
 *              hvort snákur hefur rekist á fæðu og / eða eitursnák.
 *
 *
 *****************************************************************************/

public class SnakurController extends SnakurApplication implements Initializable {

    // Tilviksbreytur.
    private Snakur snakur; // Snákurinn.
    private ObservableList<EiturSnakur> eitursnakar; // Listi af eitursnákum.
    private ObservableList<Faeda> faedaListi; // Listi af fæðu.
    private KeyCode k; // KeyCode fyrir á hvaða örvatakka var ýtt.
    private int rot = 0; // Í hvaða átt snákur skal stefna.
    private Random randC = new Random(); // Random hlutur til að útfæra birtingu fæðu og birtingu og stefnu eitursnáka.
    private Leikur l; // Leikur sem heldur utan um stig.
    private ArrayList<Snakur> snakurRectangles;
    private SerstakurEiturSnakur ses;
    private ArrayList<SerstakurEiturSnakur> SESRectangles;
    private Timeline t;
    private int INTERVAL = 300;
    private int fjoldiMat = 3;


    // Viðmótshlutir.
    @FXML
    public Pane fxPane; // Spilaborðið.
    @FXML
    private HBox fxHBox; // Grunn-HBoxið sem spilaborðið og stigabirting er inni í.
    @FXML
    private Label fxStigDisplay; // Stigabirting.


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hefjaLeik();
    }

    /**
     * Byrjar leik. Keyrir aðgerðina nyirEitursnakar(), og aðgerðina eldaMat(), fyrir heiltöluna fjoldiMat.
     *  Uppfærir stöðu viðmótshluta á hverri sekúndu.
     *  Kannar hvort snákur hafi rekist á fæðu eða eiturrsnák á hverri sekúndu.
     */
    public void hefjaLeik() {
        spyrjaUmErfidleikastig();
        l = new Leikur();
        faedaListi = FXCollections.observableArrayList();
        ses = new SerstakurEiturSnakur();
        SESRectangles = new ArrayList<>();
        snakur = new Snakur();
        snakurRectangles = new ArrayList<>();
        snakurRectangles.add(snakur);
        SESRectangles.add(ses);
        eitursnakar = FXCollections.observableArrayList();
        nyirEitursnakar(3);
        snakur.setSc(this);
        fxPane.getChildren().add(ses);
        ses.setX(150);
        ses.setY(150);
        fxPane.getChildren().add(snakur);
        eldaMat(fjoldiMat);
        fxPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        KeyFrame k = new KeyFrame(Duration.millis(INTERVAL),
                e-> {
                    if (INTERVAL == 100) {
                        aframFaeda();
                    }
                    aframEitursnakar();
                    aframSnakur();
                    aframSerstakurEitursnakur();
                    borda();
                });
        t = new Timeline(k);
        t.setCycleCount(Timeline.INDEFINITE);
        t.play();
    }

    /**
     * Gettari fyrir Timeline hlutinn.
     * @return
     */
    public Timeline getT() {
        return t;
    }

    /**
     * Býr til eitursnáka og birtir þá í notendaviðmóti á miðju spilaborði.
     * @param fjoldiEitursnaka - Fjöldi eitursnáka sem skal búa til og birta.
     */
    public void nyirEitursnakar(int fjoldiEitursnaka) {
        for (int i = 0; i < fjoldiEitursnaka; i++) {
            EiturSnakur e = new EiturSnakur();
            fxPane.getChildren().add(e);
            eitursnakar.add(e);
            e.setX(200.0);
            e.setY(200.0);
        }
    }

    /**
     * Færir eitursnák áfram samkvæmt þeirri stefnu sem hann er á.
     */
    public void aframEitursnakar() {
        int s = eitursnakar.size();
        for (int i = 0; i < s; i++) {
            eitursnakar.get(i).afram();
            eitursnakar.get(i).snua();
        }
    }

    /**
     * Færir sérstakann eitursnák áfram samkvæmt þeirri stefnu sem hann er á.
     */
    public void aframSerstakurEitursnakur() {
        // Hreyfi halann.
        if (SESRectangles.size() > 1) {
            for (int i = SESRectangles.size() - 1; i > 0; i--) {
                SESRectangles.get(i).setX(SESRectangles.get(i-1).getX());
                SESRectangles.get(i).setY(SESRectangles.get(i-1).getY());
            }
        }

        // Hreyfi fremsta rectangle snáksins.
        ses.afram();
        ses.snua();
    }

    /**
     * Færir snákinn áfram samkvæmt þeirri stefnu sem hann er á.
     */
    public void aframSnakur() {
        // Hreyfi halann.
        if (snakurRectangles.size() > 1) {
            for (int i = snakurRectangles.size() - 1; i > 0; i--) {
                snakurRectangles.get(i).setX(snakurRectangles.get(i-1).getX());
                snakurRectangles.get(i).setY(snakurRectangles.get(i-1).getY());
            }
        }

        // Hreyfi fremsta rectangle snáksins.
        snakur.afram();
        snuaSnak();
    }

    /**
     * Snýr snákinum í átt sem stjórnast af örvatökkum. Nær í áttinu með föstunum k og rot.
     */
    public void snuaSnak() {
        k = getKc();
        rot = getRotation();

        if (k == KeyCode.RIGHT || rot == 360) {
            snakur.snua(360);
        }
        else if (k == KeyCode.LEFT || rot == 180) {
            snakur.snua(180);
        }
        else if (k == KeyCode.UP) {
            snakur.snua(270);
        }
        else if (k == KeyCode.DOWN) {
            snakur.snua(90);
        }
    }

    /**
     * Býr til eina fæðu og birtir hana í notendaviðmóti á handahófskenndum stað.
     */
    public void eldaMat() {
        int min = 0;
        int max = 650;
        int x = randC.nextInt(max - min + 1) + min;
        int y = randC.nextInt(max - min + 1) + min;

        Faeda f = new Faeda();
        fxPane.getChildren().add(f);
        f.setCenterX(x);
        f.setCenterY(y);
        faedaListi.add(f);
    }

    /**
     * Býr til n marga fæðu-hluti og birtir þá í notendaviðmóti á handahófskenndum stað.
     * @param n
     */
    public void eldaMat(int n) {
        for (int i = 0; i < n; i++) {
            int min = 0;
            int max = 650;
            int x = randC.nextInt(max - min + 1) + min;
            int y = randC.nextInt(max - min + 1) + min;

            Faeda f = new Faeda();
            fxPane.getChildren().add(f);
            f.setCenterX(x);
            f.setCenterY(y);
            faedaListi.add(f);
        }
    }

    /**
     * Kannar hvort snákur hafi borðað fæðu, eða eitursnákur hafi borðað snák. Ef snákur borðar fæðu, þá
     *  er búið til nýja fæðu, snákur vex, og stig uppfærist. Ef eitursnákur borðar snák, þá lýkur leiknum
     *  og notenda er tilkynnt það.
     */
    public void borda() {
        for (int i = 0; i < faedaListi.size(); i++) {

            // Kannar hvort snákur hafi rekist á fæðu-hlut.
            for (int snakar = 0; snakar < snakurRectangles.size(); snakar++) {
                Bounds s = snakurRectangles.get(snakar).getBoundsInParent();
                Bounds f = faedaListi.get(i).getBoundsInParent();
                if (s.intersects(f) || f.intersects(s)) {
                    fxPane.getChildren().remove(faedaListi.get(i));
                    faedaListi.remove(i);
                    l.setStig(1);
                    fxStigDisplay.setText(l.getStig());
                    eldaMat();

                    // Bæti við snák-rectangle-hlut fyrir aftan snákinn.
                    Snakur snkr = new Snakur();
                    int fjoldiSnakaHluta = snakurRectangles.size();
                    double x = snakurRectangles.get(fjoldiSnakaHluta - 1).getX();
                    double y = snakurRectangles.get(fjoldiSnakaHluta - 1).getY();
                    rot = getRotation();
                    if (rot == 360) {
                        snkr.setX(x - 20);
                        snkr.setY(y);
                    }
                    else if (rot == 180) {
                        snkr.setX(x + 20);
                        snkr.setY(y);
                    }
                    else if (rot == 270) {
                        snkr.setX(x);
                        snkr.setY(y - 20);
                    }
                    else if (rot == 90) {
                        snkr.setX(x);
                        snkr.setY(y + 20);
                    }

                    snakurRectangles.add(snkr);
                    fxPane.getChildren().add(snkr);

                    break;
                }
            }

            // Kannar hvort sérstakur eitursnákur hafi rekist á fæðu-hlut.
            for (int sesnakur = 0; sesnakur < SESRectangles.size(); sesnakur++) {
                Bounds sesb = SESRectangles.get(sesnakur).getBoundsInParent();
                Bounds f = faedaListi.get(i).getBoundsInParent();

                if (sesb.intersects(f) || f.intersects(sesb)) {
                    fxPane.getChildren().remove(faedaListi.get(i));
                    faedaListi.remove(i);
                    eldaMat();

                    // Bæti við ses-rectangle-hlut fyrir aftan ses.
                    SerstakurEiturSnakur snkr = new SerstakurEiturSnakur();
                    int fjoldiSnakaHluta = SESRectangles.size();
                    double x = SESRectangles.get(fjoldiSnakaHluta - 1).getX();
                    double y = SESRectangles.get(fjoldiSnakaHluta - 1).getY();
                    double rot = ses.getRotate();
                    if (rot == 360) {
                        snkr.setX(x - 20);
                        snkr.setY(y);
                    }
                    else if (rot == 180) {
                        snkr.setX(x + 20);
                        snkr.setY(y);
                    }
                    else if (rot == 270) {
                        snkr.setX(x);
                        snkr.setY(y - 20);
                    }
                    else if (rot == 90) {
                        snkr.setX(x);
                        snkr.setY(y + 20);
                    }

                    SESRectangles.add(snkr);
                    fxPane.getChildren().add(snkr);

                    break;
                }
            }
        }

        areksturloop:
        for (int snakur = 0; snakur < snakurRectangles.size(); snakur++) {
            Bounds sBounds = snakurRectangles.get(snakur).getBoundsInParent();

            // Ef snákur rekst á sérstakann eitursnák þá endar leikur.
            for (int ses = 0; ses < SESRectangles.size(); ses++) {
                Bounds sesBounds = SESRectangles.get(ses).getBoundsInParent();

                if (sBounds.intersects(sesBounds) || sesBounds.intersects(sBounds)) {
                    t.stop();
                    Platform.runLater(() -> synaAlert("Sérstakur eitursnákur náði þér. Þú hefur tapað."));
                    break areksturloop;
                }
            }

            // Ef snákur rekst á eitursnák þá endar leikur.
            for (int l = 0; l < eitursnakar.size(); l++) {
                Bounds eBounds = eitursnakar.get(l).getBoundsInParent();

                if (sBounds.intersects(eBounds) || eBounds.intersects(sBounds)) {
                    t.stop();
                    Platform.runLater(() -> synaAlert("Eitursnákur náði þér. Þú hefur tapað."));
                    break areksturloop;

                }
            }
        }
    }

    /**
     * Spyr notanda hvort hann vilji leika annan leik. Hefur nýjan leik ef svo er
     *
     * @param s
     */
    private void synaAlert(String s) {
        Alert a = new AdvorunDialog("", "Snákur", s + "Viltu halda áfram?");
        Optional<ButtonType> u = a.showAndWait();
        if (u.isPresent() && !u.get().getButtonData().isCancelButton()) {
            endurraesaLeik();
        }
        else {
            Platform.exit();
            System.exit(0);
        }
    }

    /**
     * Eyðir viðmótshlutum og keyrir fallið hefjaLeik.
     */
    private void endurraesaLeik() {
        // Eyði eitursnákum.
        for (int i = 0; i < eitursnakar.size(); i++) {
            fxPane.getChildren().remove(eitursnakar.get(i));
        }
        // Eyði fæðuhlutum.
        for (int f = 0; f < faedaListi.size(); f++) {
            fxPane.getChildren().remove(faedaListi.get(f));
        }
        // Eyði snák og hala.
        for (int s = 0; s < snakurRectangles.size(); s++) {
            fxPane.getChildren().remove(snakurRectangles.get(s));
        }
        // Eyði sérstökum eitursnák.
        for (int se = 0; se < SESRectangles.size(); se++) {
            fxPane.getChildren().remove(SESRectangles.get(se));
        }
        // Endurstilli stig.
        fxStigDisplay.setText("0");
        // Byrja nýjann leik.
        hefjaLeik();
    }

    /**
     * Biður spilanda um að velja erfiðleikastig.
     */
    public void spyrjaUmErfidleikastig() {
        Alert a = new ErfidleikastigDialog();
        Optional<ButtonType> u = a.showAndWait();
        if (u.isPresent()) {
            if (u.get().getText().equals("1")) {
                INTERVAL = 350;
                fjoldiMat = 5;
            }
            else if (u.get().getText().equals("2")) {
                INTERVAL = 200;
                fjoldiMat = 3;
            }
            else if (u.get().getText().equals("3")) {
                INTERVAL = 100;
                fjoldiMat = 1;
            }
            else if (u.get().getText().equals("Hætta við")) {
                Platform.exit();
                System.exit(0);
            }
        }
    }

    /**
     * Hreyfir fæðuhlut.
     */
    public void aframFaeda() {
        for (int i = 0; i < faedaListi.size(); i++) {
            faedaListi.get(i).snua();
            faedaListi.get(i).afram();
        }
    }
}
