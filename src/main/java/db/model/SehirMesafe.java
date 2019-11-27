//SehirMesafe
package db.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "SehirMesafe")
@Table(name = "sehir_mesafe")
public class SehirMesafe extends Tablo {
    @Column(name = "sehir1")
    private long sehir1;
    @Column(name = "sehir2")
    private long sehir2;
    @Column(name = "mesafe")
    private long mesafe;

    public SehirMesafe(){}

    public SehirMesafe(long sehir1, long sehir2, long mesafe) {
        this.sehir1 = sehir1;
        this.sehir2 = sehir2;
        this.mesafe = mesafe;
    }

    public long getSehir1() {
        return sehir1;
    }

    public void setSehir1(long sehir1) {
        this.sehir1 = sehir1;
    }

    public long getSehir2() {
        return sehir2;
    }

    public void setSehir2(long sehir2) {
        this.sehir2 = sehir2;
    }

    public long getMesafe() {
        return mesafe;
    }

    public void setMesafe(long mesafe) {
        this.mesafe = mesafe;
    }
}
