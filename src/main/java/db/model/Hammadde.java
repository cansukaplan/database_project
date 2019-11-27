//Hammadde
package db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Hammadde")
@Table(name = "hammadde")
public class Hammadde extends Tablo {


    @Column(name = "adi")
    private String adi;
    @Column(name = "sembolu")
    private String sembolu;

    public String getAdi() {
        return adi;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }

    public String getSembolu() {
        return sembolu;
    }

    public void setSembolu(String sembolu) {
        this.sembolu = sembolu;
    }

    @Override
    public String toString() {
        return adi;
    }
}
