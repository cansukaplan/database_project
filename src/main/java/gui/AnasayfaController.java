package gui;

import db.Tablolar;
import db.VeritabaniIslemleri;
import db.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.hibernate.persister.entity.AbstractEntityPersister;

import javax.persistence.Transient;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class AnasayfaController {
    @FXML
    BorderPane root;

    @FXML
    TabPane tabPane;

    @FXML
    Button btnYenile;
    @FXML
    Button btnKaydet;
    @FXML
    Button btnSil;
    @FXML
    Button btnEkle;

    @FXML
    Label lblVeritabaniDurumu;

    @FXML
    TableView table;
    @FXML
    ComboBox cbTablolar;

    @FXML
    TextArea taLog;

    /**
     * sehir ekleme
     */
    @FXML
    ComboBox<Sehir> cbSehir1;
    @FXML
    ComboBox<Sehir> cbSehir2;
    @FXML
    TextField tfMesafe;
    @FXML
    Button btnSehirMesafeEkle;

    /**
     * tedarikci hammadde ekleme
     */
    @FXML
    ComboBox<Tedarikci> cbTedarikciFirma;
    @FXML
    ComboBox<Hammadde> cbHammadde;
    @FXML
    TextField tfMiktar;
    @FXML
    TextField tfUretimTarihi;
    @FXML
    TextField tfRafomru;
    @FXML
    TextField tfFiyat;
    @FXML
    Button btnTedarikciHammaddeEkle;

    /**
     * HAMMADDE SATIN ALMA
     */
    @FXML
    ComboBox<Hammadde> cbHammadeSatinAl;
    @FXML
    TextArea taHammadeSatinAl;
    @FXML
    ComboBox<Tedarikci> cbFirmaHammadeSat;
    @FXML
    TextField tfAdetHammaddeSatinAl;
    @FXML
    TextField tfKargoUcreti;
    @FXML
    Button btnHammadeSatinAl;

    /**
     * URUN URET
     */
    @FXML
    TextField tfAdUrunUret;
    @FXML
    TextField tfUretimTarihiUrunUret;
    @FXML
    TextField tfRafOmruUrunUret;
    @FXML
    TextField tfSatisFiyatiUrunUret;
    @FXML
    TextField tfAdetUrunUret;
    @FXML
    Button btnUret;

    @FXML
    Button btnSat;
    @FXML
    ComboBox<Musteri> cbMusteri;
    @FXML
    ComboBox<Urun> cbUrun;

    @FXML
    TextField tfAdetUrunSatis;

    public static final String tabloPaketleri = "db.model.";

    private VeritabaniIslemleri veritabaniIslemleri;
    private ObservableList<ObservableList> data;

    @FXML
    public void initialize() {
        root.setDisable(true);
        createComboBox();
        createButtons();
        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                menuleriDoldur();
            }
        });
    }


    private void createButtons() {
        btnYenile.setOnAction(e -> {
            yenile();
        });

        btnEkle.setOnAction(e -> {
            ObservableList<String> row = new Tablerow();
            ((Tablerow) row).setInserted(true);
            ((Tablerow) row).setUpdated(false);
            ((Tablerow) row).setDeleted(false);

            Field[] fields = getFields();
            row.add("");
            for (Field field : fields) {
                row.add("");
            }

            data.add(0, row);
            table.getItems().setAll(data);
            table.refresh();
            table.getSelectionModel().select(data.size() - 1);
        });

        btnSil.setOnAction(e -> {
            ObservableList selectedCells = table.getSelectionModel().getSelectedCells();
            if (selectedCells.size() > 0) {
                for (Object selectedCell : selectedCells) {
                    TablePosition tablePosition = (TablePosition) selectedCell;
                    ((Tablerow) data.get(tablePosition.getRow())).setDeleted(true);
                    ((Tablerow) data.get(tablePosition.getRow())).setInserted(false);
                    ((Tablerow) data.get(tablePosition.getRow())).setUpdated(false);
                }

                table.getItems().setAll(data);
                table.refresh();
            }
        });

        btnKaydet.setOnAction(e -> {
            String secilenTablo = cbTablolar.getSelectionModel().getSelectedItem().toString();
            secimiKaydet(Tablolar.valueOf(secilenTablo).tabloAdi);
            yenile();
        });
    }

    private void yenile() {
        try {
            tabloSecildi(cbTablolar.getSelectionModel().getSelectedItem().toString());
        } catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException | IntrospectionException e1) {
            e1.printStackTrace();
        }
    }

    private void secimiKaydet(String secilenTablo) {
        String updateSorgu = updateSorguOlustur(secilenTablo);
        String insertSorgu = insertSorguOlustur(secilenTablo);
        String deleteSorgu = deleteSorguOlustur(secilenTablo);

        veritabaniIslemleri.kaydet(data, updateSorgu, insertSorgu, deleteSorgu, getFields());
    }

    private String deleteSorguOlustur(String secilenTablo) {
        String tabloAdi = tabloAdiniEnumaCevir(secilenTablo);
        String s = "delete from " + Tablolar.valueOf(tabloAdi).tabloAdi + " where id = ";
        return s;
    }

    private String insertSorguOlustur(String secilenTablo) {
        String s = "insert into " + secilenTablo + " (";

        Field[] fields = getFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(Transient.class)) {
                s += getSatirAdi(secilenTablo, field.getName()) + " ,";
            }
        }
        s = s.substring(0, s.length() - 1);
        s += ") select";
        int i = 0;
        for (Field field : fields) {
            if (!field.isAnnotationPresent(Transient.class)) {
                s += " :" + i + " ,";
                i++;
            }
        }
        s = s.substring(0, s.length() - 1);
        return s;
    }

    private String updateSorguOlustur(String secilenTablo) {
        String s = "update " + secilenTablo + " set ";

        Field[] fields = getFields();
        int i = 0;
        for (Field field : fields) {
            if (!field.isAnnotationPresent(Transient.class)) {
                s += getSatirAdi(secilenTablo, field.getName()) + " = :" + i + " ,";
                i++;
            }
        }
        s = s.substring(0, s.length() - 1);
        s += " where id = :id";
        return s;
    }


    public Field[] getFields() {
        String secilenTablo = cbTablolar.getSelectionModel().getSelectedItem().toString();
        Class cls = null;
        try {
            cls = Class.forName(tabloPaketleri + secilenTablo.substring(0, 1).toUpperCase() + secilenTablo.substring(1));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Field[] fields = cls.getDeclaredFields();
        List<Field> fieldsWithoutAnnot = new ArrayList<>();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(Transient.class)) {
                fieldsWithoutAnnot.add(field);
            }
        }

        Field[] fields2 = new Field[fieldsWithoutAnnot.size()];
        for (int i = 0; i < fieldsWithoutAnnot.size(); i++) {
            fields2[i] = fieldsWithoutAnnot.get(i);
        }

        return fields2;
    }

    private boolean baglandi = false;

    private void createComboBox() {
        table.setVisible(false);
        cbTablolar.getItems().addAll(Tablolar.values());
        cbTablolar.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.toString().equals("")) {
                table.setVisible(true);

                try {
                    tabloSecildi(newValue.toString());
                } catch (ClassNotFoundException e) {
                    taLog.appendText(e.getMessage() + "\n");
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IntrospectionException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void tabloSecildi(String secilenTablo) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, IntrospectionException {
        table.getColumns().clear();
        Class cls = Class.forName(tabloPaketleri + secilenTablo.substring(0, 1).toUpperCase() + secilenTablo.substring(1));
        Field[] fields = cls.getDeclaredFields();

        insertIdField();
        int i = 1;
        for (Field field : fields) {
            if (field.isAnnotationPresent(Transient.class)) {
                continue;
            }
            int j = i;
            final TableColumn col = new TableColumn(field.getName());
            col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param ->
                new SimpleStringProperty(param.getValue().get(j).toString()));
            col.setCellFactory(EditCell.<ObservableList, Double>forTableColumn(
                new StringConverter() {
                    @Override
                    public String toString(Object object) {
                        return object.toString();
                    }

                    @Override
                    public Object fromString(String string) {
                        return string;
                    }
                }));
            table.getColumns().add(col);
            col.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
                @Override
                public void handle(TableColumn.CellEditEvent event) {
                    System.out.println("Old Value: " + event.getOldValue()
                        + " New Value: " + event.getNewValue()
                        + "Row num: " + event.getTablePosition().getRow());

                    Tablerow tablerow = (Tablerow) table.getItems().get(event.getTablePosition().getRow());
                    tablerow.set(j, event.getNewValue().toString());
                    if (!tablerow.isInserted()) {
                        tablerow.setDeleted(false);
                        tablerow.setInserted(false);
                        tablerow.setUpdated(true);
                    }
                }
            });
            i++;
        }
        List<Hammadde> hammaddeler;
        List<Sehir> sehirler;
        List<Tedarikci> tedarikciler;
        List<TedarikciHammadde> tedarikciHammaddeler;
        List<Ulke> ulkeler;
        List<Uretici> ureticiler;
        List<UreticiSatinAlinanHammadde> ureticiSatinAlinanHammaddeler;
        List<Urun> urunler;
        List<UrunHammadde> urunHammaddeler;
        List<SehirMesafe> sehirMesafeLer;
        if (secilenTablo.equals(Tablolar.hammadde.name())) {
            hammaddeler = veritabaniIslemleri.getHammaddeler();
            setData(hammaddeler);
        } else if (secilenTablo.equals(Tablolar.sehir.name())) {
            sehirler = veritabaniIslemleri.getSehirler();
            setData(sehirler);
        } else if (secilenTablo.equals(Tablolar.tedarikci.name())) {
            tedarikciler = veritabaniIslemleri.getTedarikciler();
            setData(tedarikciler);
        } else if (secilenTablo.equals(Tablolar.tedarikciHammadde.name())) {
            tedarikciHammaddeler = veritabaniIslemleri.getTedarikciHammaddeler();
            setData(tedarikciHammaddeler);
        } else if (secilenTablo.equals(Tablolar.ulke.name())) {
            ulkeler = veritabaniIslemleri.getUlkeler();
            setData(ulkeler);
        } else if (secilenTablo.equals(Tablolar.uretici.name())) {
            ureticiler = veritabaniIslemleri.getUreticiler();
            setData(ureticiler);
        } else if (secilenTablo.equals(Tablolar.ureticiSatinAlinanHammadde.name())) {
            ureticiSatinAlinanHammaddeler = veritabaniIslemleri.getUreticiSatinAlinanHammaddeler();
            setData(ureticiSatinAlinanHammaddeler);
        } else if (secilenTablo.equals(Tablolar.urun.name())) {
            urunler = veritabaniIslemleri.getUrunler();
            setData(urunler);
        } else if (secilenTablo.equals(Tablolar.urunHammadde.name())) {
            urunHammaddeler = veritabaniIslemleri.getUrunHammaddeler();
            setData(urunHammaddeler);
        } else if (secilenTablo.equals(Tablolar.sehirMesafe.name())) {
            sehirMesafeLer = veritabaniIslemleri.getSehirMesafeler();
            setData(sehirMesafeLer);
        } else if (secilenTablo.equals(Tablolar.musteri.name())) {
            List<Musteri> musteriler = veritabaniIslemleri.getMusteriler();
            setData(musteriler);
        }else if(secilenTablo.equalsIgnoreCase(Tablolar.musteriTalepUrun.name())){
        }
    }

    private void insertIdField() {
        final TableColumn col = new TableColumn("ID");
        col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param ->
            new SimpleStringProperty(param.getValue().get(0).toString()));
        col.setCellFactory(EditCell.<ObservableList, Double>forTableColumn(
            new StringConverter() {
                @Override
                public String toString(Object object) {
                    return object.toString();
                }

                @Override
                public Object fromString(String string) {
                    return string;
                }
            }));
        table.getColumns().add(col);
        col.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                System.out.println("Old Value: " + event.getOldValue()
                    + " New Value: " + event.getNewValue()
                    + "Row num: " + event.getTablePosition().getRow());

                Tablerow tablerow = (Tablerow) table.getItems().get(event.getTablePosition().getRow());
                tablerow.set(0, event.getNewValue().toString());
                if (!tablerow.isInserted()) {
                    tablerow.setDeleted(false);
                    tablerow.setInserted(false);
                    tablerow.setUpdated(true);
                }
            }
        });
    }

    public void setData(List liste) throws InvocationTargetException, IllegalAccessException, IntrospectionException {
        data = FXCollections.observableArrayList();
        ObservableList<String> row;

        for (Object o : liste) {
            Field[] fields = o.getClass().getDeclaredFields();
            row = new Tablerow();
            row.add(((Tablo) o).getId() + "");
            for (Field field : fields) {
                if (!field.isAnnotationPresent(Transient.class)) {
                    String eklenecekDeger = o != null ? new PropertyDescriptor(field.getName(), o.getClass()).getReadMethod().invoke(o).toString() : "";
                    row.add(eklenecekDeger);
                }
            }
            data.add(row);
        }

        table.getItems().setAll(data);
        table.refresh();
    }

    public void setBaglandi(VeritabaniIslemleri veritabaniIslemleri) {
        lblVeritabaniDurumu.setStyle(" -fx-background-color: green;-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
        baglandi = true;
        this.veritabaniIslemleri = veritabaniIslemleri;
        root.setDisable(false);

        menuleriDoldur();
    }

    private void menuleriDoldur() {
        new SehirMesafeEkleme(cbSehir1, cbSehir2, tfMesafe, btnSehirMesafeEkle, veritabaniIslemleri);
        new TedarikciHammaddeEkleme(cbTedarikciFirma, cbHammadde, tfMiktar, tfUretimTarihi, tfRafomru, tfFiyat, btnTedarikciHammaddeEkle, veritabaniIslemleri);
        new HammaddeSatinAlma(cbHammadeSatinAl, taHammadeSatinAl, cbFirmaHammadeSat, tfAdetHammaddeSatinAl, tfKargoUcreti, btnHammadeSatinAl, veritabaniIslemleri);
        new UrunUret(tfAdUrunUret, tfRafOmruUrunUret, tfSatisFiyatiUrunUret, tfAdetUrunUret, btnUret, tfUretimTarihiUrunUret, veritabaniIslemleri);
        new UrunSatis(cbMusteri, cbUrun, btnSat, tfAdetUrunSatis, veritabaniIslemleri);
    }


    private String getSatirAdi(String secilenTablo, String alan) {
        String tabloAdi = tabloAdiniEnumaCevir(secilenTablo);

        return ((AbstractEntityPersister) veritabaniIslemleri.getSession().getSessionFactory()
            .getClassMetadata(AnasayfaController.tabloPaketleri + tabloAdi.substring(0, 1).toUpperCase() + tabloAdi.substring(1)))
            .getPropertyColumnNames(alan)[0];
    }

    public static String tabloAdiniEnumaCevir(String tabloAdi) {
        String sonuc = "";
        Tablolar[] values = Tablolar.values();

        for (Tablolar value : values) {
            if (value.tabloAdi.equals(tabloAdi)) {
                return value.name();
            }
        }
        return null;
    }
}
