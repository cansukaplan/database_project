package gui;

import db.VeritabaniIslemleri;
import db.model.Hammadde;
import db.model.Tedarikci;
import db.model.TedarikciHammadde;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TedarikciHammaddeEkleme {
    VeritabaniIslemleri veritabaniIslemleri;

    public TedarikciHammaddeEkleme(ComboBox<Tedarikci> cbTedarikciFirma, ComboBox<Hammadde> cbHammadde, TextField tfMiktar,
                                   TextField tfUretimTarihi, TextField tfRafomru, TextField tfFiyat, Button btnTedarikciHammaddeEkle, VeritabaniIslemleri veritabaniIslemleri) {
        this.veritabaniIslemleri = veritabaniIslemleri;
        comboxlariDoldur(cbTedarikciFirma, cbHammadde);

        btnTedarikciHammaddeEkle.setOnAction(e -> {
            Tedarikci tedarikci = cbTedarikciFirma.getSelectionModel().getSelectedItem();
            Hammadde hammadde = cbHammadde.getSelectionModel().getSelectedItem();
            int miktar = Integer.parseInt(tfMiktar.getText());

            String uretimTarihi = tfUretimTarihi.getText();
            int rafOmru = Integer.parseInt(tfRafomru.getText());
            int fiyat = Integer.parseInt(tfFiyat.getText());

            veritabaniIslemleri.tedarikciyeHammaddeEkle(tedarikci,hammadde,miktar,uretimTarihi,rafOmru,fiyat);
        });
    }

    private void comboxlariDoldur(ComboBox<Tedarikci> cbTedarikciFirma, ComboBox<Hammadde> cbHammadde) {
        List<Tedarikci> tedarikciler = veritabaniIslemleri.getTedarikciler();
        List<Hammadde> hammaddeler = veritabaniIslemleri.getHammaddeler();
        Collections.sort(hammaddeler, Comparator.comparing(Hammadde::getAdi));
        cbHammadde.setItems(FXCollections.observableArrayList(hammaddeler));
        cbTedarikciFirma.setItems(FXCollections.observableArrayList(tedarikciler));
    }
}
