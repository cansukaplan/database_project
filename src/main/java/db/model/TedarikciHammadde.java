//TedarikciHammadde
package db.model;

import javax.persistence.*;

@Entity(name = "TedarikciHammadde")
@Table(name = "tedarikci_hammadde")
public class TedarikciHammadde  extends Tablo {

  @Column(name = "tedarikci_id")
  private long tedarikciId;
  @Column(name = "hammadde_id")
  private long hammaddeId;
  @Column(name = "miktar")
  private long miktar;
  @Column(name = "uretim_tarihi")
  private String uretimTarihi;
  @Column(name = "raf_omru")
  private long rafOmru;
  @Column(name = "satis_fiyati")
  private long satisFiyati;

  @Transient
  Tedarikci tedarikci;
  @Transient
  int toplamMaliyet;

  public TedarikciHammadde() {
  }

  public TedarikciHammadde(long tedarikciId, long hammaddeId, long miktar, String uretimTarihi, long rafOmru, long satisFiyati) {
    this.tedarikciId = tedarikciId;
    this.hammaddeId = hammaddeId;
    this.miktar = miktar;
    this.uretimTarihi = uretimTarihi;
    this.rafOmru = rafOmru;
    this.satisFiyati = satisFiyati;
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

  public long getRafOmru() {
    return rafOmru;
  }

  public void setRafOmru(long rafOmru) {
    this.rafOmru = rafOmru;
  }

  public long getSatisFiyati() {
    return satisFiyati;
  }

  public void setSatisFiyati(long satisFiyati) {
    this.satisFiyati = satisFiyati;
  }

  public Tedarikci getTedarikci() {
    return tedarikci;
  }

  public void setTedarikci(Tedarikci tedarikci) {
    this.tedarikci = tedarikci;
  }

  public int getToplamMaliyet() {
    return toplamMaliyet;
  }

  public void setToplamMaliyet(int toplamMaliyet) {
    this.toplamMaliyet = toplamMaliyet;
  }


}
