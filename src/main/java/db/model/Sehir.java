
//Sehir
package db.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Sehir")
@Table(name = "sehir")
public class Sehir  extends Tablo {

  @Column(name = "adi")
  private String adi;
  @Column(name = "ulke_id")
  private long ulkeId;

  public String getAdi() {
    return adi;
  }

  public void setAdi(String adi) {
    this.adi = adi;
  }


  public long getUlkeId() {
    return ulkeId;
  }

  public void setUlkeId(long ulkeId) {
    this.ulkeId = ulkeId;
  }

  @Override
  public String toString() {
    return adi;
  }
}
