package gui;

import db.VeritabaniIslemleri;
import db.model.Musteri;
import db.model.Urun;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.List;

public class UrunSatis {
    public UrunSatis(ComboBox<Musteri> cbMusteri, ComboBox<Urun> cbUrun, Button btnSat, TextField tfAdetUrunSatis, VeritabaniIslemleri veritabaniIslemleri) {
        comboboxlariDoldur(cbMusteri, cbUrun, veritabaniIslemleri);

        btnSat.setOnAction(e -> {
            int adet = Integer.parseInt(tfAdetUrunSatis.getText());
            veritabaniIslemleri.urunSat(cbMusteri.getSelectionModel().getSelectedItem(), cbUrun.getSelectionModel().getSelectedItem(), adet);
        });
    }

    private void comboboxlariDoldur(ComboBox<Musteri> cbMusteri, ComboBox<Urun> cbUrun, VeritabaniIslemleri veritabaniIslemleri) {
        List<Musteri> musteriler = veritabaniIslemleri.getMusteriler();
        if (musteriler != null || musteriler.size() > 0)
            cbMusteri.setItems(FXCollections.observableArrayList(musteriler));

        List<Urun> urunler = veritabaniIslemleri.getUrunler();
        if (urunler != null || urunler.size() > 0)
            cbUrun.setItems(FXCollections.observableArrayList(urunler));

    }
}
