//UreticiSatinAlinanHammadde
package db.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "UreticiSatinAlinanHammadde")
@Table(name = "uretici_satin_alinan_hammadde")
public class UreticiSatinAlinanHammadde   extends Tablo{
  @Column(name = "uretici_id")
  private long ureticiId;
  @Column(name = "tedarikci_id")
  private long tedarikciId;
  @Column(name = "hammadde_id")
  private long hammaddeId;
  @Column(name = "kargo_ucreti")
  private long kargoUcreti;
  @Column(name = "stok")
  private long stok;

  public UreticiSatinAlinanHammadde() {
  }

  public UreticiSatinAlinanHammadde(long ureticiId, long tedarikciId, long hammaddeId, long kargoUcreti, long stok) {
    this.ureticiId = ureticiId;
    this.tedarikciId = tedarikciId;
    this.hammaddeId = hammaddeId;
    this.kargoUcreti = kargoUcreti;
    this.stok = stok;
  }

  public long getUreticiId() {
    return ureticiId;
  }

  public void setUreticiId(long ureticiId) {
    this.ureticiId = ureticiId;
  }


  public long getTedarikciId() {
    return tedarikciId;
  }

  public void setTedarikciId(long tedarikciId) {
    this.tedarikciId = tedarikciId;
  }


  public long getHammaddeId() {
    return hammaddeId;
  }

  public void setHammaddeId(long hammaddeId) {
    this.hammaddeId = hammaddeId;
  }


  public long getKargoUcreti() {
    return kargoUcreti;
  }

  public void setKargoUcreti(long kargoUcreti) {
    this.kargoUcreti = kargoUcreti;
  }


  public long getStok() {
    return stok;
  }

  public void setStok(long stok) {
    this.stok = stok;
  }

}
