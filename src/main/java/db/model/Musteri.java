package db.model;

//Musteri
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "Musteri")
@Table(name = "musteri")
public class Musteri extends Tablo {

    @Column(name = "ad")
    private String adi;
    @Column(name = "adres")
    private String adres;

    public String getAdi() {
        return adi;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }


    public String getAdres() {
        return adres;
    }

    public void setAdres(String sehir) {
        this.adres = sehir;
    }

    @Override
    public String toString() {
        return adi;
    }
}
