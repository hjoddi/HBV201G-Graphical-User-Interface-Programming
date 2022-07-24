package hi.verkefni.vidmot;

import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

/******************************************************************************
 *  Nafn    : Hjörvar Sigurðsson
 *  T-póstur: hjs33@hi.is
 *
 *  Lýsing  : Klasi sem útfærir birtingu spilaborðsins í notendaviðmóti. Þessi
 *              klasi er ekki í notkun í augnablikinu; hlutverk þessara klasa
 *              er útfært í SnakurController klasanum.
 *
 *
 *****************************************************************************/

public class SnakurBord extends Pane {

    private Snakur snakur = new Snakur();

    @FXML
    private Pane fxSnakurBordPane;

    ObservableList<EiturSnakur> Eitursnakar = new ObservableListBase<EiturSnakur>() {
        public EiturSnakur get(int index) {
            //return Eitursnakar.get(index);
            return null;
        }

        public int size() {
            //return Eitursnakar.size();
            return 0;
        }
    };

    public SnakurBord() {
        lesaSnakurBord();
        nyirEitursnakar(3);
        fxSnakurBordPane.getChildren().add(snakur);
    }

    private void lesaSnakurBord() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("snakurBord-view.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void nyirEitursnakar(int fjoldiEitursnaka) {
        for (int i = 0; i < fjoldiEitursnaka; i++) {
            EiturSnakur e = new EiturSnakur();
            Eitursnakar.add(e);
            fxSnakurBordPane.getChildren().add(e);

        }
    }
}


