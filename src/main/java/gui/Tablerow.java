package gui;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;
public class Tablerow extends ObservableListWrapper<String> {
    private int id;
    private boolean updated;
    private boolean deleted;
    private boolean inserted;



    public Tablerow() {
        super(new ArrayList<String>());
    }

    public Tablerow(List<String> list) {
        super(list);
    }

    public Tablerow(List<String> list, Callback<String, Observable[]> extractor) {
        super(list, extractor);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isUpdated() {
        return updated;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isInserted() {
        return inserted;
    }

    public void setInserted(boolean inserted) {
        this.inserted = inserted;
    }
}
