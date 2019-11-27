//Uretici
package db.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Uretici")
@Table(name = "uretici")
public class Uretici extends Tablo {

    @Column(name = "adi")
    private String adi;
    @Column(name = "sehir")
    private long sehir;

    public String getAdi() {
        return adi;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }


    public long getSehir() {
        return sehir;
    }

    public void setSehir(long sehir) {
        this.sehir = sehir;
    }

}
