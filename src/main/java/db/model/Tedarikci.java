//Tedarikci
package db.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Tedarikci")
@Table(name = "tedarikci")
public class Tedarikci extends Tablo {
    @Column(name = "adi")
    private String adi;
    @Column(name = "SEHIR_ID")
    private long sehirId;

    public String getAdi() {
        return adi;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }


    public long getSehirId() {
        return sehirId;
    }

    public void setSehirId(long sehirId) {
        this.sehirId = sehirId;
    }


    @Override
    public String toString() {
        return adi;
    }
}
