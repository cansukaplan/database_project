package gui;

import arac.Hesaplama;
import db.VeritabaniIslemleri;
import db.model.Hammadde;
import db.model.UreticiSatinAlinanHammadde;
import javafx.scene.control.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrunUret {

    private Map<Hammadde, Integer> secilenHammaddeler = new HashMap<>();
    private int iscilikHesaplama;

    public UrunUret(TextField tfAdUrunUret, TextField tfRafOmruUrunUret, TextField tfSatisFiyatiUrunUret, TextField tfAdetUrunUret, Button btnUret, TextField tfUretimTarihiUrunUret, VeritabaniIslemleri veritabaniIslemleri) {
        List<Hammadde> ureticidekiHammadeler = veritabaniIslemleri.getUreticidekiHammadeler();

        btnUret.setOnAction(e -> {
            String urunAdi = tfAdUrunUret.getText();
            int adet = Integer.parseInt(tfAdetUrunUret.getText());
            boolean b = urunIcinYeteriKadarHammaddeVarmi(urunAdi, ureticidekiHammadeler, veritabaniIslemleri, adet);

            if (b) {
                veritabaniIslemleri.urunUret(tfAdUrunUret.getText(),
                    tfUretimTarihiUrunUret.getText(),
                    tfRafOmruUrunUret.getText(),
                    iscilikHesaplama + "",
                    tfSatisFiyatiUrunUret.getText(),
                    secilenHammaddeler,
                    adet);
            }
        });
        tfAdetUrunUret.setOnAction(e -> {
            iscilikHesaplama = Hesaplama.iscilikHesaplama(Integer.parseInt(tfAdetUrunUret.getText()));
        });
    }

    private boolean urunIcinYeteriKadarHammaddeVarmi(String urunAdi, List<Hammadde> ureticidekiHammadeler, VeritabaniIslemleri veritabaniIslemleri, int adet) {
        List<String> parsed = parse(urunAdi);
        for (int i = 0; i < parsed.size(); i++) {
            if (i % 2 == 0) {
                Hammadde hammadde = getHammadde(parsed.get(i).toUpperCase(), ureticidekiHammadeler);
                if (hammadde == null) {
                    UyariGoster.showAlert(Alert.AlertType.ERROR, "Hata", "Hammadde bulunamadı", "Ureticinin elinde " + parsed.get(i) + " yok!!!");
                    return false;
                } else {
                    List<UreticiSatinAlinanHammadde> ureticiSatinAlinanHammaddeler = veritabaniIslemleri.getUreticiSatinAlinanHammaddeler();
                    int gerekliMiktar = 0;

                    if (parsed.size() <= i + 1) {
                        gerekliMiktar = 1;
                    } else {
                        Integer.parseInt(parsed.get(i + 1));
                    }

                    gerekliMiktar *= adet;
                    if (yeteriKadarHammadeVarmi(ureticiSatinAlinanHammaddeler, hammadde, gerekliMiktar)) {
                        secilenHammaddeler.put(hammadde, gerekliMiktar);
                    } else {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean yeteriKadarHammadeVarmi(List<UreticiSatinAlinanHammadde> ureticiSatinAlinanHammaddeler, Hammadde hammadde, int miktar) {
        for (UreticiSatinAlinanHammadde ureticiSatinAlinanHammadde : ureticiSatinAlinanHammaddeler) {
            if (ureticiSatinAlinanHammadde.getHammaddeId() == hammadde.getId()) {
                if (miktar <= ureticiSatinAlinanHammadde.getStok()) {
                    return true;
                } else {
                    UyariGoster.showAlert(Alert.AlertType.ERROR, "Hata", "Hammadde yeterisz", "Ureticinin elinde " + ureticiSatinAlinanHammadde.getStok() + " tane " + hammadde.getAdi() + " var!!!");
                }
            }
        }
        return false;
    }

    private Hammadde getHammadde(String s, List<Hammadde> ureticidekiHammadeler) {
        for (Hammadde hammadde : ureticidekiHammadeler) {
            if (hammadde.getAdi().equalsIgnoreCase(s)) {
                return hammadde;
            }
        }
        return null;
    }


    private static final Pattern VALID_PATTERN = Pattern.compile("[0-9]+|[A-Z]+");

    private List<String> parse(String toParse) {
        List<String> chunks = new LinkedList<String>();
        Matcher matcher = VALID_PATTERN.matcher(toParse);
        while (matcher.find()) {
            chunks.add(matcher.group());
        }
        return chunks;
    }
}

