package simu.entity;

import java.time.LocalDate;
import java.sql.Date;

import jakarta.persistence.*;

// Tietokantataulu
@Entity
@Table(name = "tulokset")
/**
 * Tulokset-luokka sisaltaa tulokset tietokantataulun tiedot
 * @param id
 * @param paivamaara
 * @param aika
 * @param asiakkaat
 * @param lennolle_ehtineet
 * @param lennolta_myohastyneet
 * @param myohastyneet_t1
 * @param myohastyneet_t2
 * @param lsTulos
 * @param ttTulos
 * @param ptTulos
 * @param t1Tulos
 * @param t2Tulos
 */
public class Tulokset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tulokset_id")
    /**
     * Tulokset-luokan id
     */
    private int id;
    @Column(name = "paivamaara")
    /**
     * Tulokset-luokan paivamaara
     */
    private Date paivamaara;
    @Column(name = "aika")
    /**
     * Tulokset-luokan aika
     */
    private double aika;
    @Column(name = "asiakkaat")
    /**
     * Tulokset-luokan asiakkaat
     */
    private int asiakkaat;
    @Column(name = "lennolle_ehtineet")
    /**
     * Tulokset-luokan lennolle_ehtineet
     */
    private int lennolle_ehtineet;
    @Column(name = "myohastyneet")
    /**
     * Tulokset-luokan lennolta_myohastyneet
     */
    private int lennolta_myohastyneet;
    @Column(name = "myohastyneet_t1")
    /**
     * Tulokset-luokan myohastyneet_t1
     */
    private int myohastyneet_t1;
    @Column(name = "myohastyneet_t2")
    /**
     * Tulokset-luokan myohastyneet_t2
     */
    private int myohastyneet_t2;
    @ManyToOne(cascade = CascadeType.PERSIST)
    /**
     * Tulokset-luokan lsTulos
     */
    private LSTulos lsTulos;
    @ManyToOne(cascade = CascadeType.PERSIST)
    /**
     * Tulokset-luokan ttTulos
     */
    private TTTulos ttTulos;
    @ManyToOne(cascade = CascadeType.PERSIST)
    /**
     * Tulokset-luokan ptTulos
     */
    private PTTulos ptTulos;
    @ManyToOne(cascade = CascadeType.PERSIST)
    /**
     * Tulokset-luokan t1Tulos
     */
    private T1Tulos t1Tulos;
    @ManyToOne(cascade = CascadeType.PERSIST)
    /**
     * Tulokset-luokan t2Tulos
     */
    private T2Tulos t2Tulos;

    /**
     * Tulokset-luokan konstruktori
     * @param paivamaara
     * @param aika
     * @param asiakkaat
     * @param lennolle_ehtineet
     * @param lennolta_myohastyneet
     * @param myohastyneet_t1
     * @param myohastyneet_t2
     * @param lsTulos
     * @param ttTulos
     * @param ptTulos
     * @param t1Tulos
     * @param t2Tulos
     */
    public Tulokset(LocalDate paivamaara, double aika, int asiakkaat, int lennolle_ehtineet, int lennolta_myohastyneet, int myohastyneet_t1, int myohastyneet_t2, LSTulos lsTulos, TTTulos ttTulos, PTTulos ptTulos, T1Tulos t1Tulos, T2Tulos t2Tulos) {
        this.paivamaara = Date.valueOf(paivamaara);
        this.aika = aika;
        this.asiakkaat = asiakkaat;
        this.lennolle_ehtineet = lennolle_ehtineet;
        this.lennolta_myohastyneet = lennolta_myohastyneet;
        this.myohastyneet_t1 = myohastyneet_t1;
        this.myohastyneet_t2 = myohastyneet_t2;
        this.lsTulos = lsTulos;
        this.ttTulos = ttTulos;
        this.ptTulos = ptTulos;
        this.t1Tulos = t1Tulos;
        this.t2Tulos = t2Tulos;
    }

    /**
     * Tulokset-luokan tyhja konstruktori
     */
    public Tulokset() {
    }


    //Setterit

    /** Asetetaan id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /** Asetetaan paivamaara
     * @param paivamaara
     */
    public void setPaivamaara(LocalDate paivamaara) {
        this.paivamaara =  Date.valueOf(paivamaara);
    }

    /** Asetetaan aika
     * @param aika
     */
    public void setAika(double aika) {
        this.aika = aika;
    }
    /** Asetetaan asiakkaiden maara
     * @param asiakkaat
     */
    public void setAsiakkaat(int asiakkaat) {
        this.asiakkaat = asiakkaat;
    }
    /** Asetetaan lennolle ehtineet
     * @param lennolle_ehtineet
     */
    public void setLennolle_ehtineet(int lennolle_ehtineet) {
        this.lennolle_ehtineet = lennolle_ehtineet;
    }
    /** Asetetaan lennolta myohastyneet
     * @param lennolta_myohastyneet
     */
    public void setLennolta_myohastyneet(int lennolta_myohastyneet) {
        this.lennolta_myohastyneet = lennolta_myohastyneet;
    }
    /** Asetetaan T1 palvelupisteen myohastynee
     * @param myohastyneet_t1
     */
    public void setMyohastyneet_t1(int myohastyneet_t1) {
        this.myohastyneet_t1 = myohastyneet_t1;
    }
    /** Asetetaan T2 palvelupisteen myohastyneet
     * @param myohastyneet_t2
     */
    public void setMyohastyneet_t2(int myohastyneet_t2) {
        this.myohastyneet_t2 = myohastyneet_t2;
    }
    /** Asetetaan Lahtoselvitys palvelupisteen tulos
     * @param lsTulos
     */
    public void setLSTulos(LSTulos lsTulos) {
        this.lsTulos = lsTulos;
    }
    /** Asetetaan Turvatarkstus palvelupisteen tulos
     * @param ttTulos
     */
    public void setTTTulos(TTTulos ttTulos) {
        this.ttTulos = ttTulos;
    }
    /** Asetetaan Passintarkastus palvelupisteen tulos
     * @param ptTulos
     */
    public void setPTTulos(PTTulos ptTulos) {
        this.ptTulos = ptTulos;
    }
    /** Asetetaan T1 palvelupisteen tulos
     * @param t1Tulos
     */
    public void setT1Tulos(T1Tulos t1Tulos) {
        this.t1Tulos = t1Tulos;
    }
    /** Asetetaan T2 palvelupisteen tulos
     * @param t2Tulos
     */
    public void setT2Tulos(T2Tulos t2Tulos) {
        this.t2Tulos = t2Tulos;
    }

    //Getterit

    /** Saadaan id
     * @return int
     */
    public int getId() {
        return id;
    }

    /** Saadaan paivamaara
     * @return LocalDate
     */
    public LocalDate getPaivamaara() {
        return paivamaara.toLocalDate();
    }
    /** Saadaan aika
     * @return double
     */
    public double getAika() {
        return aika;
    }
    /** Saadaan asiakkaiden maara
     * @return int
     */
    public int getAsiakkaat() {
        return asiakkaat;
    }
    /** Saadaan lennolle ehtineet
     * @return int
     */
    public int getLennolle_ehtineet() {
        return lennolle_ehtineet;
    }
    /** Saadaan lennolta myohastyneet
     * @return int
     */
    public int getLennolta_myohastyneet() {
        return lennolta_myohastyneet;
    }
    /** Saadaan T1 palvelupisteen myohastynee
     * @return int
     */
    public int getMyohastyneet_t1() {
        return myohastyneet_t1;
    }
    /** Saadaan T2 palvelupisteen myohastyneet
     * @return int
     */
    public int getMyohastyneet_t2() {
        return myohastyneet_t2;
    }
    /** Saadaan Lahtoselvitys palvelupisteen tulos
     * @return LSTulos
     */
    public LSTulos getLSTulos() {
        return lsTulos;
    }
    /** Saadaan Turvatarkstus palvelupisteen tulos
     * @return TTTulos
     */
    public TTTulos getTTTulos() {
        return ttTulos;
    }
    /** Saadaan Passintarkastus palvelupisteen tulos
     * @return PTTulos
     */
    public PTTulos getPTTulos() {
        return ptTulos;
    }
    /** Saadaan t1 palvelupisteen tulos
     * @return T1Tulos
     */
    public T1Tulos getT1Tulos() {
        return t1Tulos;
    }
    /** Saadaan T2 palvelupisteen tulos
     * @return T2Tulos
     */
    public T2Tulos getT2Tulos() {
        return t2Tulos;
    }

    // Override toString jotta saadaan ListViewiin paivamaara nakyviin Objectin sijaan
    /** Metodilla saadaan paivamaara nakyviin ListViewiin
     * @return String
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.paivamaara.toString();
    }
}
