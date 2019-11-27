package arac;

import db.model.Sehir;
import gui.Util;
import javafx.scene.control.Alert;

public class Hesaplama {
    static int ISCILIK_KAT_SAYI = 1;
    static double YURT_ICI_KAT_SAYI = 0.5;
    static int YURT_DISI_KAT_SAYI = 1;

    public static int iscilikHesaplama(int uretilenUrunMiktari) {
        return uretilenUrunMiktari * ISCILIK_KAT_SAYI;
    }

    public static int kargoMaliyeti(Sehir sehir1, Sehir sehir2, int mesafe) {
        if (mesafe < 1) {
            Util.showAlert(Alert.AlertType.ERROR, "Hata", "", sehir1.getAdi() + " -> " + sehir2.getAdi() + " arasindaki mesafe bilinmemektedi");
            return -1;
        } else if (sehir1.getUlkeId() != sehir2.getUlkeId()) {
            return YURT_DISI_KAT_SAYI * mesafe;
        } else {
            return (int) (YURT_ICI_KAT_SAYI * mesafe);
        }
    }
}
