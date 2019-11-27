//Ulke
package db.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Ulke")
@Table(name = "ulke")
public class Ulke   extends Tablo {

  @Column(name = "kisa_adi")
  private String kisaAdi;
  @Column(name = "adi")
  private String adi;
  @Column(name = "telefon_kodu")
  private long telefonKodu;

  public String getKisaAdi() {
    return kisaAdi;
  }

  public void setKisaAdi(String kisaAdi) {
    this.kisaAdi = kisaAdi;
  }


  public String getAdi() {
    return adi;
  }

  public void setAdi(String adi) {
    this.adi = adi;
  }


  public long getTelefonKodu() {
    return telefonKodu;
  }

  public void setTelefonKodu(long telefonKodu) {
    this.telefonKodu = telefonKodu;
  }

}
