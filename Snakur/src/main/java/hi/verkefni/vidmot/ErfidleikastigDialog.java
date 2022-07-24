package hi.verkefni.vidmot;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public class ErfidleikastigDialog extends Alert {
    public static final ButtonType Diff1 = new ButtonType("1",
            ButtonBar.ButtonData.OTHER);
    public static final ButtonType Diff2 = new ButtonType("2",
            ButtonBar.ButtonData.OTHER);
    public static final ButtonType Diff3 = new ButtonType("3",
            ButtonBar.ButtonData.OTHER);
    public static final ButtonType HTYPE = new ButtonType("Hætta við",
            ButtonBar.ButtonData.CANCEL_CLOSE);

    public ErfidleikastigDialog() {
        super(AlertType.NONE, "Veldu erfiðleikastig", Diff1, Diff2, Diff3, HTYPE);
        setTitle("Erfiðleikastig");
        setHeaderText("Snákur");
    }
}
