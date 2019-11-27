//VeritabaniIslemleri
package db;

import arac.Hesaplama;
import db.model.*;
import gui.Tablerow;
import gui.Util;
import gui.UyariGoster;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.hibernate.Query;
import org.hibernate.Session;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class VeritabaniIslemleri {
    private Session session = HibernateUtil.getSessionFactory().openSession();

    public Session getSession() {
        return session;
    }

    public List<Hammadde> getHammaddeler() {
        session.clear();
        String hql = "select e from Hammadde e";
        Query query = session.createQuery(hql);

        return query.list();
    }

    public List<Sehir> getSehirler() {
        session.clear();
        String hql = "select e from Sehir e";
        Query query = session.createQuery(hql);

        return query.list();
    }

    public List<Tedarikci> getTedarikciler() {
        session.clear();
        String hql = "select e from Tedarikci e";
        Query query = session.createQuery(hql);

        return query.list();
    }

    public List<TedarikciHammadde> getTedarikciHammaddeler() {
        session.clear();
        String hql = "select e from TedarikciHammadde e";
        Query query = session.createQuery(hql);

        return query.list();
    }

    public List<Ulke> getUlkeler() {
        session.clear();
        String hql = "select e from Ulke e";
        Query query = session.createQuery(hql);

        return query.list();
    }

    public List<Uretici> getUreticiler() {
        session.clear();
        String hql = "select e from Uretici e";
        Query query = session.createQuery(hql);

        return query.list();
    }

    public List<UreticiSatinAlinanHammadde> getUreticiSatinAlinanHammaddeler() {
        session.clear();
        String hql = "select e from UreticiSatinAlinanHammadde e";
        Query query = session.createQuery(hql);

        return query.list();
    }

    public List<Urun> getUrunler() {
        session.clear();
        String hql = "select e from Urun e";
        Query query = session.createQuery(hql);

        return query.list();
    }


    public List<UrunHammadde> getUrunHammaddeler() {
        session.clear();
        String hql = "select e from UrunHammadde e";
        Query query = session.createQuery(hql);

        return query.list();
    }


    public void kaydet(ObservableList<ObservableList> data, String updateSorgu, String insertSorgu, String deleteSorgu, Field[] fields) {
        session.beginTransaction();
        for (ObservableList datum : data) {
            Tablerow data_ = (Tablerow) datum;

            if (data_.isUpdated()) {
                Query query = session.createSQLQuery(updateSorgu);
                for (int i = 0; i < fields.length; i++) {
                    query.setParameter(i + "", data_.get(i + 1));
                }
                query.setParameter("id", data_.get(0));
                query.executeUpdate();
            } else if (data_.isInserted()) {
                Query query = session.createSQLQuery(insertSorgu);
                for (int i = 0; i < fields.length; i++) {
                    query.setParameter(i + "", data_.get(i + 1));
                }
                query.executeUpdate();
            } else if (data_.isDeleted()) {
                deleteSorgu += " " + data_.get(0);
                Query query = session.createSQLQuery(deleteSorgu);
                query.executeUpdate();
            }
        }
        session.getTransaction().commit();
    }


    public List<SehirMesafe> getSehirMesafeler() {
        session.clear();
        String hql = "select e from SehirMesafe e";
        Query query = session.createQuery(hql);

        return query.list();
    }

    public void kaydet(SehirMesafe sehirMesafe) {
        SehirMesafe sehirMesafe1 = getSehirMesafe(sehirMesafe);

        session.beginTransaction();
        if (sehirMesafe1 != null) {
            sehirMesafe1.setMesafe(sehirMesafe.getMesafe());
            session.merge(sehirMesafe1);
        } else {
            session.merge(sehirMesafe);
        }
        session.getTransaction().commit();
        session.clear();
        UyariGoster.showAlert(Alert.AlertType.INFORMATION, "Başarılı", "Veri tabanına Kayıt", "Mesafe başarıyla eklendi");
    }

    private SehirMesafe getSehirMesafe(SehirMesafe sehirMesafe) {
        String sql = "select e from SehirMesafe e where e.sehir1 = :sehir1 and e.sehir2 = :sehir2";
        Query query = session.createQuery(sql);
        query.setParameter("sehir1", sehirMesafe.getSehir1());
        query.setParameter("sehir2", sehirMesafe.getSehir2());

        List list = query.list();
        if (list == null || list.size() == 0 || list.get(0) == null) {
            return null;
        }
        return (SehirMesafe) query.list().get(0);
    }

    public void tedarikciyeHammaddeEkle(Tedarikci tedarikci, Hammadde hammadde, int miktar, String uretimTarihi, int rafOmru, int fiyat) {
        TedarikciHammadde tedarikciHammadde = getTedarikciHammadde(tedarikci, hammadde);

        session.beginTransaction();
        if (tedarikciHammadde != null) {
            tedarikciHammadde.setMiktar(miktar);
            tedarikciHammadde.setUretimTarihi(uretimTarihi);
            tedarikciHammadde.setRafOmru(rafOmru);
            tedarikciHammadde.setSatisFiyati(fiyat);
            session.merge(tedarikciHammadde);
        } else {
            tedarikciHammadde = new TedarikciHammadde();
            tedarikciHammadde.setTedarikciId(tedarikci.getId());
            tedarikciHammadde.setHammaddeId(hammadde.getId());
            tedarikciHammadde.setMiktar(miktar);
            tedarikciHammadde.setUretimTarihi(uretimTarihi);
            tedarikciHammadde.setRafOmru(rafOmru);
            tedarikciHammadde.setSatisFiyati(fiyat);
            session.save(tedarikciHammadde);
        }
        session.getTransaction().commit();
        session.clear();
        UyariGoster.showAlert(Alert.AlertType.INFORMATION, "Başarılı", "Veri tabanına Kayıt", tedarikci.getAdi() + " hammadde başarıyla eklendi");
    }

    public TedarikciHammadde getTedarikciHammadde(Tedarikci tedarikci, Hammadde hammadde) {
        String sql = "select e from TedarikciHammadde e where e.tedarikciId = :tedarikciId and e.hammaddeId = :hammaddeId";
        Query query = session.createQuery(sql);
        query.setParameter("tedarikciId", tedarikci.getId());
        query.setParameter("hammaddeId", hammadde.getId());

        List list = query.list();
        if (list == null || list.size() == 0 || list.get(0) == null) {
            return null;
        }
        return (TedarikciHammadde) list.get(0);
    }

    public List<TedarikciHammadde> hammaddeyeGoreTedarikcileriGetir(Hammadde secilenHammadde) {
        String sql = "select e from TedarikciHammadde e where  e.hammaddeId = :hammaddeId and e.miktar > 0";
        Query query = session.createQuery(sql);
        query.setParameter("hammaddeId", secilenHammadde.getId());
        List<TedarikciHammadde> list = query.list();

        Uretici uretici = getUretici();

        for (TedarikciHammadde tedarikciHammadde : list) {
            Tedarikci tedarikci = getTedarikci(tedarikciHammadde.getTedarikciId());
            tedarikciHammadde.setTedarikci(tedarikci);

            int maliyet = Hesaplama.kargoMaliyeti(getSehir(uretici.getSehir())
                , getSehir(tedarikci.getSehirId())
                , (int) getSehirMesafe(uretici.getSehir(), tedarikci.getSehirId()).getMesafe());

            tedarikciHammadde.setToplamMaliyet(maliyet);
        }
        session.clear();
        return list;
    }

    private Tedarikci getTedarikci(long tedarikciId) {
        Query query = session.createQuery("select e from Tedarikci e where e.id = " + tedarikciId);
        return (Tedarikci) query.list().get(0);
    }

    private Hammadde getHammadde(long hammaddeId) {
        Query query = session.createQuery("select e from Hammadde e where e.id = " + hammaddeId);
        return (Hammadde) query.list().get(0);
    }

    private Sehir getSehir(long sehirId) {
        Query query = session.createQuery("select e from Sehir e where e.id = " + sehirId);
        return (Sehir) query.list().get(0);
    }

    private SehirMesafe getSehirMesafe(long sehir1Id, long sehir2Id) {
        Query query = session.createQuery("select e from SehirMesafe e where   e.sehir1 = " + sehir1Id + " and e.sehir2 = " + sehir2Id + " " +
            "or e.sehir1 = " + sehir2Id + " and e.sehir2 = " + sehir1Id);
        return (SehirMesafe) query.list().get(0);
    }

    private Uretici getUretici() {
        Query query = session.createQuery("select e from Uretici e");
        List list = query.list();
        if (list == null || list.size() == 0 || list.get(0) == null) {
            return null;
        }
        return (Uretici) list.get(0);
    }

    public void ureticiSatinAlinanHammaddeKaydet(UreticiSatinAlinanHammadde ureticiSatinAlinanHammadde) {
        session.beginTransaction();
        session.merge(ureticiSatinAlinanHammadde);

        TedarikciHammadde tedarikciHammadde = getTedarikciHammadde(getTedarikci(ureticiSatinAlinanHammadde.getTedarikciId()), getHammadde(ureticiSatinAlinanHammadde.getHammaddeId()));
        tedarikciHammadde.setMiktar(tedarikciHammadde.getMiktar() - ureticiSatinAlinanHammadde.getStok());
        session.update(tedarikciHammadde);

        session.getTransaction().commit();
        session.clear();

        UyariGoster.showAlert(Alert.AlertType.INFORMATION, "Başarılı", "Veri tabanına Kayıt", " hammadde başarıyla satin alindi");
    }

    public List<Hammadde> getUreticidekiHammadeler() {
        String sql = "select e from Hammadde e, UreticiSatinAlinanHammadde f where e.id = f.hammaddeId";
        Query query = session.createQuery(sql);
        List list = query.list();
        if (list == null || list.size() == 0 || list.get(0) == null) {
            return null;
        }
        return (List<Hammadde>) list;
    }


    public void urunUret(String urunAdi, String uretimTarihi, String rafOmru, String iscilikMaliyeti, String satisFiyati, Map<Hammadde, Integer> secilenHammaddeler, int adet) {
        Urun urun = new Urun(urunAdi, adet, uretimTarihi, rafOmru, Hesaplama.iscilikHesaplama(adet), Long.parseLong(satisFiyati), adet);
        session.beginTransaction();
        Long id = (Long) session.save(urun);

        UrunHammadde urunHammadde;
        for (Map.Entry<Hammadde, Integer> hammaddeIntegerEntry : secilenHammaddeler.entrySet()) {
            urunHammadde = new UrunHammadde(urun.getId(), hammaddeIntegerEntry.getKey().getId(), hammaddeIntegerEntry.getValue());
            session.save(urunHammadde);
            UreticiSatinAlinanHammadde ureticiSatinAlinanHammadde = getUreticiSatinAlinanHammadde(hammaddeIntegerEntry.getKey());
            ureticiSatinAlinanHammadde.setStok(ureticiSatinAlinanHammadde.getStok() - hammaddeIntegerEntry.getValue());

            if (ureticiSatinAlinanHammadde.getStok() == 0) {
                session.delete(ureticiSatinAlinanHammadde);
            } else {
                session.merge(ureticiSatinAlinanHammadde);
            }
        }

        session.getTransaction().commit();
        session.clear();
        UyariGoster.showAlert(Alert.AlertType.INFORMATION, "Başarılı", "Ürütetildi", urunAdi + " Ürün üretildi");
    }

    public UreticiSatinAlinanHammadde getUreticiSatinAlinanHammadde(Hammadde hammadde) {
        Query query = session.createQuery("select e from UreticiSatinAlinanHammadde e where e.hammaddeId = " + hammadde.getId());
        List list = query.list();
        if (list == null || list.size() == 0 || list.get(0) == null) {
            return null;
        }
        return (UreticiSatinAlinanHammadde) list.get(0);

    }

    public List<Musteri> getMusteriler() {
        session.clear();
        String hql = "select e from Musteri e";
        Query query = session.createQuery(hql);

        return query.list();
    }


    public void urunSat(Musteri musteri, Urun urun, int adet) {
        if (urun.getAdet() < adet) {
            UyariGoster.showAlert(Alert.AlertType.ERROR, "Hata", "Yetersiz Ürün", "Elimizde " + urun.getAdet() + " adet Ürün Bulunmakta");
        } else {
            Calendar uretimTarihi = getCalendar(urun.getUretimTarihi());
            uretimTarihi.add(Calendar.YEAR, Integer.parseInt(urun.getRafOmru()));

            if (uretimTarihi.before(Calendar.getInstance())) {
                urun.setAdet(-1);
                urun.setMiktar(-1);
                session.beginTransaction();
                session.merge(urun);
                session.getTransaction().commit();
                UyariGoster.showAlert(Alert.AlertType.INFORMATION, "Uyarı", "Kayıt edildi", "Ürün Satışı Gerçekleştirilmedi çünkü son kullanım tarihi dolmuş");
            }
            else {
                session.beginTransaction();

                urun.setMiktar(urun.getMiktar() - adet);

                MusteriTalepUrun musteriTalepUrun = new MusteriTalepUrun(musteri.getId(), urun.getId(), adet);

                session.merge(urun);
                session.save(musteriTalepUrun);

                session.getTransaction().commit();
                UyariGoster.showAlert(Alert.AlertType.INFORMATION, "Başarılı", "Kayıt edildi", "Ürün Satışı Gerçekleştirildi");
            }
        }
    }

    private Calendar getCalendar(String tarih) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyyy");
        try {
            cal.setTime(sdf.parse(tarih));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
   }
}
