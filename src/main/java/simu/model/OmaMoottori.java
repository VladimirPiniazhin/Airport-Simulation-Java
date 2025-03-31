package simu.model;

import java.time.LocalDate;

import simu.eduni.distributions.Normal;
import simu.framework.Kello;
import simu.framework.Moottori;
import simu.framework.Saapumisprosessi;
import simu.framework.Tapahtuma;
import simu.view.Kontrolleri;
import simu.entity.*;
import simu.model.SimulaatioStatistiikka;

/** Moottori-luokka, joka sisaltaa simulaation paalogiikan */
public class OmaMoottori extends Moottori {

	/** Saapumisprosessi */
	private Saapumisprosessi saapumisprosessi;
	/** Palvelupisteet, joissa tapahtuu palvelua */
	private Palvelupiste[] palvelupisteet;
	/** Lippu, joka kertoo, ovatko kaikki asiakkaat valmiita */
	private boolean kaikkiAsiakkaatValmiit = false; // Lisatty lippu seuraamaan, ovatko kaikki asiakkaat valmiita
	/** Tulokset */
	private Tulokset tulokset;
	private SimulaatioStatistiikka statistiikka;

	/** Konstruktori, joka luo uuden moottorin */
	public OmaMoottori(Kontrolleri kontrolleri) {
		super(kontrolleri);
		statistiikka = new SimulaatioStatistiikka();

		palvelupisteet = new Palvelupiste[5];

		// Lahtoselvitys
		palvelupisteet[0] = new Palvelupiste(700, 533, "LS", kontrolleri.getLahtoselvitysMaara(),
				new Normal(kontrolleri.getLSpalveluNopeus(), kontrolleri.getLahtoselvitysVar()), tapahtumalista,
				TapahtumanTyyppi.DEP1, statistiikka);
		// Turvatarkastus
		palvelupisteet[1] = new Palvelupiste(590, 360, "TT", kontrolleri.getTurvatarkastusMaara(),
				new Normal(kontrolleri.getTTpalveluNopeus(), kontrolleri.getTurvatarkastusVar()), tapahtumalista,
				TapahtumanTyyppi.DEP2, statistiikka);
		// Passintarkistus
		palvelupisteet[2] = new Palvelupiste(880, 195, "PT", kontrolleri.getPassintarkastusMaara(),
				new Normal(kontrolleri.getPTpalveluNopeus(), kontrolleri.getPassintarkastusVar()), tapahtumalista,
				TapahtumanTyyppi.DEP3, statistiikka);
		// Lahtoportti kotimaanlennot
		palvelupisteet[3] = new Palvelupiste(300, 45, "T1", 1,
				new Normal(kontrolleri.getKotimaaKA(), kontrolleri.getKotimaaVar()), tapahtumalista,
				TapahtumanTyyppi.DEP4, statistiikka);
		// Lahtoportti ulkomaanlennot
		palvelupisteet[4] = new Palvelupiste(830, 45, "T2", 1,
				new Normal(kontrolleri.getUlkomaaKA(), kontrolleri.getUlkomaaVar()), tapahtumalista,
				TapahtumanTyyppi.DEP5, statistiikka);
		// Saapumisprosessi
		saapumisprosessi = new Saapumisprosessi(tapahtumalista, TapahtumanTyyppi.ULKO, kontrolleri.getLentojenVali(),
				kontrolleri.getLentojenVali2());
	}

	/** Palauttaa kaikki palvelupisteet */
	public Palvelupiste[] getPalvelupisteet() {
		return palvelupisteet;
	}

	/** Alustaa simulaation
	 * {@inheritDoc} */
	@Override
	protected void alustukset() {
		saapumisprosessi.generoiSeuraava(); // Asetetaan ensimmainen saapuminen jarjestelmaan
	}

	/** Suorittaa tapahtuman
	 * {@inheritDoc} */
	@Override
	protected void suoritaTapahtuma(Tapahtuma t) {
		Asiakas a;
		TapahtumanTyyppi tyyppi = (TapahtumanTyyppi) t.getTyyppi();
		
		switch (tyyppi) {
			case ARR1:
			case ARR2:
				a = new Asiakas(tyyppi);
				if (tyyppi == TapahtumanTyyppi.ARR1) {
					a.setUlkomaanlento();
				}
				palvelupisteet[0].lisaaJonoon(a);
				break;
				
			case DEP1:
				a = (Asiakas) palvelupisteet[0].otaJonosta();
				palvelupisteet[1].lisaaJonoon(a);
				break;
				
			case DEP2:
				a = (Asiakas) palvelupisteet[1].otaJonosta();
				palvelupisteet[a.isUlkomaanlento() ? 2 : 3].lisaaJonoon(a);
				break;
				
			case DEP3:
				a = (Asiakas) palvelupisteet[2].otaJonosta();
				palvelupisteet[4].lisaaJonoon(a);
				break;
				
			case DEP4:
			case DEP5:
				a = (Asiakas) palvelupisteet[tyyppi == TapahtumanTyyppi.DEP4 ? 3 : 4].otaJonosta();
				a.setPoistumisaika(Kello.getInstance().getAika());
				a.raportti();
				statistiikka.lisaaLennolleEhtinyt();
				statistiikka.lisaaPalvellutAsiakkaat();
				break;
				
			case ULKO:
				for (Palvelupiste palvelupiste : palvelupisteet) {
					palvelupiste.removeAsiakasARR1();
				}
				tapahtumalista.removeUlkomaanTapahtumat();
				break;
				
			case SISA:
				for (Palvelupiste palvelupiste : palvelupisteet) {
					palvelupiste.removeAsiakasARR2();
				}
				tapahtumalista.removeKotimaanTapahtumat();
				break;
		}
	}

	/** Yrittaa aloittaa palvelun
	 * {@inheritDoc}*/
	@Override
	protected void yritaCTapahtumat() {
		for (Palvelupiste p : palvelupisteet) {
			if (!p.onVarattu() && p.onJonossa()) {
				p.aloitaPalvelu();
			}
		}
	}

	/** Tarkistaa, onko kaikki asiakkaat valmiita
	 * {@inheritDoc} */
	@Override
	protected void tulokset() {

		System.out.println("\nSimulointi paattyi kello " + Kello.getInstance().getAika());

		// Laske ja nayta mittareita jokaiselle Palvelupisteelle
		for (int i = 0; i < palvelupisteet.length; i++) {
			Palvelupiste p = palvelupisteet[i];

			// Laske ja nayta nykyisen Palvelupisteen mittarit
			System.out.println("Tulokset Palvelupisteelle " + p.getNimi() + ":");

			// Laske ja nayta keskimaarainen odotusaika;
			System.out.println("Keskimaarainen odotusaika: " + p.getJonotusaika());

			// Laske ja nayta palvelun tehokkuus;
			System.out.println("Palvelutehokkuus: " + p.getSuoritusteho() + " %");

			// Palvelupisteiden kayttoasteen laskeminen ja naytto;
			System.out
					.println("Palvelupisteen kayttoaste: " + p.getKayttoaste() + " %");

			// Lisamittareita voi tarvittaessa laskea taalta...

			System.out.println("Palvelupiste " + (i + 1) + " palvellut asiakkaat: "
					+ palvelupisteet[i].getPalvelupisteessaPalvellutAsiakkaat());
			System.out.println(
					"Palvelupiste " + (i + 1) + " palveluaika: " + palvelupisteet[i].getPalvelupisteenPalveluAika());
			System.out.println("Simulointi paattyi kello " + Kello.getInstance().getAika());
			if (kaikkiAsiakkaatValmiit) {
				System.out.println("Kaikki asiakkaat ovat kulkeneet lapi.");
			} else {
				System.out.println("Kaikki asiakkaat eivat ole viela kulkeneet lapi.");
			}
		}
	}

	// Asetetaan tulokset
	/** Asettaa tulokset
	 * {@inheritDoc}*/
	@Override
	public void asetaTulokset() {
		Tulokset simunTulokset = null;
		Object[] tulokset = new Object[5];

		for (Palvelupiste p : palvelupisteet) {
			p.asetaPalvelupisteenTulokset(p, getSimulointiaika());
			
			switch (p.getNimi()) {
				case "LS":
					tulokset[0] = new LSTulos(p.getKayttoaste(), p.getSuoritusteho(), 
						p.getJonotusaika(), p.getJononPituus(), p.getMaara());
					break;
				case "PT":
					tulokset[1] = new PTTulos(p.getKayttoaste(), p.getSuoritusteho(), 
						p.getJonotusaika(), p.getJononPituus(), p.getMaara());
					break;
				case "TT":
					tulokset[2] = new TTTulos(p.getKayttoaste(), p.getSuoritusteho(), 
						p.getJonotusaika(), p.getJononPituus(), p.getMaara());
					break;
				case "T1":
					tulokset[3] = new T1Tulos(p.getKayttoaste(), p.getSuoritusteho(), 
						p.getJonotusaika(), p.getJononPituus());
					break;
				case "T2":
					tulokset[4] = new T2Tulos(p.getKayttoaste(), p.getSuoritusteho(), 
						p.getJonotusaika(), p.getJononPituus());
					break;
			}

			simunTulokset = new Tulokset(LocalDate.now(), getSimulointiaika(),
				statistiikka.getPalvellutAsiakkaat(), 
				statistiikka.getLennolleEhtineet(), 
				statistiikka.getYhteensaMyohastyneet(),
				statistiikka.getT1myohastyneet(), 
				statistiikka.getT2myohastyneet(),
				(LSTulos)tulokset[0], (TTTulos)tulokset[1], 
				(PTTulos)tulokset[2], (T1Tulos)tulokset[3], 
				(T2Tulos)tulokset[4]);
		}
		this.tulokset = simunTulokset;
	}

	// Tallennetaan tulokset tietokantaan
	/** Tallentaa tulokset tietokantaan */
	public void tallennaTulokset() {
		tuloksetDao.tallenna(this.tulokset);
	}

	// Tuloksien getteri kayttoliitymaa varten
	/** Palauttaa tulokset */
	public Tulokset getTulokset() {
		return tulokset;
	}
}