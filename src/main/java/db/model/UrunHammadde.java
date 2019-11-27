//UrunHammadde
package db.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "UrunHammadde")
@Table(name = "urun_hammadde")
public class UrunHammadde  extends Tablo{
  @Column(name = "urun_id")
  private long urunId;
  @Column(name = "hammadde_id")
  private long hammaddeId;
  @Column(name = "miktar")
  private long miktar;

  public UrunHammadde() {
  }

  public UrunHammadde(long urunId, long hammaddeId, long miktar) {
    this.urunId = urunId;
    this.hammaddeId = hammaddeId;
    this.miktar = miktar;
  }

  public long getUrunId() {
    return urunId;
  }

  public void setUrunId(long urunId) {
    this.urunId = urunId;
  }


  public long getHammaddeId() {
    return hammaddeId;
  }

  public void setHammaddeId(long hammaddeId) {
    this.hammaddeId = hammaddeId;
  }


  public long getMiktar() {
    return miktar;
  }

  public void setMiktar(long miktar) {
    this.miktar = miktar;
  }

}
