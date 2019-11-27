//Urun
package db.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Urun")
@Table(name = "urun")
public class Urun   extends Tablo{
  @Column(name = "adi")
  private String adi;
  @Column(name = "miktar")
  private long miktar;
  @Column(name = "uretim_tarihi")
  private String uretimTarihi;
  @Column(name = "raf_omru")
  private String rafOmru;
  @Column(name = "iscilik_maliyeti")
  private long iscilikMaliyeti;
  @Column(name = "satif_fiyati")
  private long satifFiyati;
  @Column(name = "adet")
  private int adet;

  public Urun() {
  }

  public Urun(String adi, long miktar, String uretimTarihi, String rafOmru, long iscilikMaliyeti, long satifFiyati,int adet) {
    this.adi = adi;
    this.miktar = miktar;
    this.uretimTarihi = uretimTarihi;
    this.rafOmru = rafOmru;
    this.iscilikMaliyeti = iscilikMaliyeti;
    this.satifFiyati = satifFiyati;
    this.adet = adet;
  }

  public String getAdi() {
    return adi;
  }

  public void setAdi(String adi) {
    this.adi = adi;
  }


  public long getMiktar() {
    return miktar;
  }

  public void setMiktar(long miktar) {
    this.miktar = miktar;
  }


  public String getUretimTarihi() {
    return uretimTarihi;
  }

  public void setUretimTarihi(String uretimTarihi) {
    this.uretimTarihi = uretimTarihi;
  }


  public String getRafOmru() {
    return rafOmru;
  }

  public void setRafOmru(String rafOmru) {
    this.rafOmru = rafOmru;
  }


  public long getIscilikMaliyeti() {
    return iscilikMaliyeti;
  }

  public void setIscilikMaliyeti(long iscilikMaliyeti) {
    this.iscilikMaliyeti = iscilikMaliyeti;
  }


  public long getSatifFiyati() {
    return satifFiyati;
  }

  public void setSatifFiyati(long satifFiyati) {
    this.satifFiyati = satifFiyati;
  }

  public int getAdet() {
    return adet;
  }

  public void setAdet(int adet) {
    this.adet = adet;
  }

  public String toString(){
      return adi;
  }
}
