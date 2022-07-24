package hi.verkefni.vinnsla;

/******************************************************************************
 *  Nafn    : Hjörvar Sigurðsson
 *  T-póstur: hjs33@hi.is
 *
 *  Lýsing  : Vinnsluklasi fyrir Leik. Heldur utan um stig.
 *
 *
 *****************************************************************************/

public class Leikur {

    // Fasti
    private String stig;

    /**
     * Smiður fyrir nýjann leik.
     */
    public Leikur() {
        this.stig = String.valueOf(0);
    }

    /**
     * Skilar stigum.
     * @return stig - Stigafjöldi.
     */
    public String getStig() {
        return stig;
    }

    /**
     * Uppfærir stigafjölda.
     * @param s - stig sem bæta skal við stigafjölda.
     */
    public void setStig(int s) {
        int x = Integer.parseInt(this.getStig());
        x += s;
        this.stig = Integer.toString(x);
    }
}
