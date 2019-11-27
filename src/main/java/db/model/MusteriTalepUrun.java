//MusteriTalepUrun
package db.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "MusteriTalepUrun")
@Table(name = "musteri_talep_urun")
public class MusteriTalepUrun extends Tablo {
    @Column(name = "musteri_id")
    private long musteriId;
    @Column(name = "urun_id")
    private long urunId;
    @Column(name = "adet")
    private int adet;

    public MusteriTalepUrun(long musteriId, long urunId, int adet) {
        this.musteriId = musteriId;
        this.urunId = urunId;
        this.adet = adet;
    }

    public long getMusteriId() {
        return musteriId;
    }

    public void setMusteriId(long musteriId) {
        this.musteriId = musteriId;
    }

    public long getUrunId() {
        return urunId;
    }

    public void setUrunId(long urunId) {
        this.urunId = urunId;
    }

    public int getAdet() {
        return adet;
    }

    public void setAdet(int adet) {
        this.adet = adet;
    }
}
