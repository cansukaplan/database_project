//Tablo
package db.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@MappedSuperclass
public class Tablo {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
