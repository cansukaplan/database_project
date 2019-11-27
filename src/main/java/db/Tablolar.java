//Tablolar
package db;

public enum Tablolar {
    hammadde("hammadde"),
    sehir("sehir"),
    tedarikci("tedarikci"),
    tedarikciHammadde("tedarikci_hammadde"),
    ulke("ulke"),
    uretici("uretici"),
    ureticiSatinAlinanHammadde("uretici_satin_alinan_hammadde"),
    urun("urun"),
    urunHammadde("urun_hammadde"),
    musteri("musteri"),
    musteriTalepUrun("musteri_talep_urun"),
    sehirMesafe("sehir_mesafe");

    public String tabloAdi;

    Tablolar(String tabloAdi) {
        this.tabloAdi = tabloAdi;
    }
}
