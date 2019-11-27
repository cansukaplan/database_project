package gui;

import db.VeritabaniIslemleri;
import db.model.Sehir;
import db.model.SehirMesafe;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class SehirMesafeEkleme {
    VeritabaniIslemleri veritabaniIslemleri;
    public SehirMesafeEkleme(ComboBox<Sehir> cbSehir1, ComboBox<Sehir> cbSehir2, TextField tfMesafe, Button btnSehirMesafeEkle, VeritabaniIslemleri veritabaniIslemleri) {
        this.veritabaniIslemleri = veritabaniIslemleri;
        combolariDoldur(cbSehir1, cbSehir2);

        btnSehirMesafeEkle.setOnAction(e->{
            Sehir sehir1 = cbSehir1.getValue();
            Sehir sehir2 = cbSehir2.getValue();

            int mesafe = Integer.parseInt(tfMesafe.getText());

            SehirMesafe sehirMesafe = new SehirMesafe(sehir1.getId(),sehir2.getId(),mesafe);
            veritabaniIslemleri.kaydet(sehirMesafe);
        });
    }

    private void combolariDoldur(ComboBox<Sehir> cbSehir1, ComboBox<Sehir> cbSehir2) {

        List<Sehir> sehirler = veritabaniIslemleri.getSehirler();
        Collections.sort(sehirler, Comparator.comparing(Sehir::getAdi));
        cbSehir1.getItems().setAll(sehirler);
        cbSehir2.getItems().setAll(sehirler);
    }
}
