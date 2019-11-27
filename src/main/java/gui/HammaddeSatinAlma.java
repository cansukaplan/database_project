package gui;

import db.VeritabaniIslemleri;
import db.model.Hammadde;
import db.model.Tedarikci;
import db.model.TedarikciHammadde;
import db.model.UreticiSatinAlinanHammadde;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HammaddeSatinAlma {
    private TextArea taHammadeSatinAl;
    private ComboBox<Tedarikci> cbFirmaHammadeSat;
    private List<TedarikciHammadde> tedarikciHammaddes;
    private TextField tfKargoUcreti;

    public HammaddeSatinAlma(ComboBox<Hammadde> cbHammadeSatinAl, TextArea taHammadeSatinAl, ComboBox<Tedarikci> cbFirmaHammadeSat, TextField tfAdetHammaddeSatinAl, TextField tfKargoUcreti, Button btnHammadeSatinAl, VeritabaniIslemleri veritabaniIslemleri) {
        this.taHammadeSatinAl = taHammadeSatinAl;
        this.cbFirmaHammadeSat = cbFirmaHammadeSat;
        comboboxlariDoldur(cbHammadeSatinAl, veritabaniIslemleri);
        this.tfKargoUcreti = tfKargoUcreti;
        tfKargoUcreti.setEditable(false);

        /*
        uretici_id
        tedarikci_id
        hammadde_id
        kargo_ucreti
        stok
         */
        btnHammadeSatinAl.setOnAction(e -> {
            UreticiSatinAlinanHammadde ureticiSatinAlinanHammadde = new UreticiSatinAlinanHammadde(1l, cbFirmaHammadeSat.getSelectionModel().getSelectedItem().getId(),  cbHammadeSatinAl.getSelectionModel().getSelectedItem().getId(),
                    Long.parseLong(tfKargoUcreti.getText()),
                    Long.parseLong(tfAdetHammaddeSatinAl.getText()));
            veritabaniIslemleri.ureticiSatinAlinanHammaddeKaydet(ureticiSatinAlinanHammadde);
        });
    }

    private void comboboxlariDoldur(ComboBox<Hammadde> cbHammadeSatinAl, VeritabaniIslemleri veritabaniIslemleri) {
        List<Hammadde> hammaddeler = veritabaniIslemleri.getHammaddeler();
        Collections.sort(hammaddeler, Comparator.comparing(Hammadde::getAdi));
        cbHammadeSatinAl.setItems(FXCollections.observableArrayList(hammaddeler));
        cbHammadeSatinAl.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Hammadde>() {
            @Override
            public void changed(ObservableValue<? extends Hammadde> observable, Hammadde oldValue, Hammadde secilenHammadde) {
                taHammadeSatinAl.setText("");
                if(secilenHammadde!=null){
                    tedarikciHammaddes = veritabaniIslemleri.hammaddeyeGoreTedarikcileriGetir(secilenHammadde);

                    List<Tedarikci> tedarikciler = new ArrayList<>();
                    if(tedarikciHammaddes == null ||tedarikciHammaddes.size() == 0){
                        taHammadeSatinAl.setText("");
                        cbFirmaHammadeSat.getSelectionModel().clearSelection();
                        UyariGoster.showAlert(Alert.AlertType.WARNING,"Uyarı","Hammadde",secilenHammadde+" Hammaddesi hiçbir tedarikçide bulunamadı!!!");
                    }else{
                        for (TedarikciHammadde tedarikciHammadde : tedarikciHammaddes) {
                            tedarikciler.add(tedarikciHammadde.getTedarikci());
                            taHammadeSatinAl.appendText(tedarikciHammadde.getTedarikci().getAdi() + "\tMaliyeti: " + tedarikciHammadde.getToplamMaliyet() + "\n");
                        }
                        cbFirmaHammadeSat.setItems(FXCollections.observableArrayList(tedarikciler));
                    }
                }

            }
        });

        cbFirmaHammadeSat.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            TedarikciHammadde tedarikciHammadde = getTedarikciHammadde(newValue.getId());
            this.tfKargoUcreti.setText(tedarikciHammadde.getToplamMaliyet() + "");
        });
    }

    private TedarikciHammadde getTedarikciHammadde(long id) {
        for (TedarikciHammadde tedarikciHammadde : tedarikciHammaddes) {
            if (tedarikciHammadde.getTedarikciId() == id) {
                return tedarikciHammadde;
            }
        }
        return null;
    }
}
