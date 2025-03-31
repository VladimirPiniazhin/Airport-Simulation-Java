package simu.model;

/**
 * Luokka simulaation tilastojen tallentamiseen
 */
public class SimulaatioStatistiikka {
    private int lennolleEhtineet = 0;
    private int t1myohastyneet = 0;
    private int t2myohastyneet = 0;
    private int palvellutAsiakkaat = 0;

    public void lisaaLennolleEhtinyt() {
        lennolleEhtineet++;
    }

    public void lisaaT1myohastynyt() {
        t1myohastyneet++;
    }

    public void lisaaT2myohastynyt() {
        t2myohastyneet++;
    }

    public void lisaaPalvellutAsiakkaat() {
        palvellutAsiakkaat++;
    }

    public int getLennolleEhtineet() {
        return lennolleEhtineet;
    }

    public int getT1myohastyneet() {
        return t1myohastyneet;
    }

    public int getT2myohastyneet() {
        return t2myohastyneet;
    }

    public int getPalvellutAsiakkaat() {
        return palvellutAsiakkaat;
    }

    public int getYhteensaMyohastyneet() {
        return t1myohastyneet + t2myohastyneet;
    }
} 