# Release 3

Vi har i denne iterasjonen jobbet ut ifra [brukerhistorie US-2 til og med US-5.](/docs/Brukerhistorier.md)

Se [arbeidsflyt](/docs/release3/Arbeidsflyt_3.md) for å lese om hvordan arbeidsflyten i gruppen har vært med tanke på arbeidsvaner, metodikk og kodekvalitet.

### Hva er nytt i denne releasen?

- Komplett REST API-server bygget rundt Spring Boot-rammeverket.
  - Vi har testet at serveren kan kjøre på en dedikert maskin som ikke har Java installert fra før. Det fungerer.
- Utvidet det grafiske brukergrensesnittet med ny funksjonalitet:
  - Fjerne og legge til brusautomater
  - Gå i "User View", som er siden/vinduet som vises til kunder.
    - Kunden kan se varebeholdning i den aktuelle automaten.
    - Kunden kan kjøpe varer
    - Kunden kan se oppdatert varebeholdning etter kjøp
    - "User View" krever passord for å få tilgang til funksjonalitet tiltenkt bedriften/eiere
  - Kunne fylle på med varer
    - Legge til x antall av ny vare
    - Etterfylle x antall av eksisterende vare
  - Kunne skrive inn server-URL og filnavn idet applikasjonen starter opp
    - Applikasjonen jobber mot serveren hvis denne er tilgjengelig
    - Applikasjonen jobber direkte mot lokal fil hvis serveren ikke er tilgjengelig
- Implementert mulighet for å bygge installasjonsfiler til VendMachTrackServer og VendMachTrackApp
  - Installasjonen inneholder alt av Java-plattformen som er nødvendig for å kjøre server og applikasjon på maskiner som ikke har Java installert fra før.
- Økt testdekningsgraden til prosjektet
  - Laget tester for alle klasser
  - Laget integrasjonstester for å teste full samhandling mellom applikasjon og server, og alle lagene i serveren
- Oppdatert og lagt til JavaDocs-dokumentasjon for alle klasser
- Oppdatert readme-dokumentasjon
  - Dokumentasjon på hvordan man kjører applikasjonen
  - Dokumentasjon på hvordan man installerer applikasjonen
  - Dokumentasjon av testing
  - Dokumentasjon av klient-server-interaksjonen
  - Dokumentasjon av alle mulige HTTP-forespørsler til REST API

### Merknader

1. Det grafiske brukergrensesnittet kan oppfattes som fryst hvis serveren bruker lang tid på å svare på forespørseler. Dette burde løses ved å sende forespørseler i egne threads så UI heller kan oppdateres fortløpende uten at det henger seg opp. Dette er kun implementert for instansieringen av Access-objektet, altså når det er stor sannsynlighet for at serveren bruker lang tid på å svare eller ikke er tilgjengelig i det hele tatt.

2. Det er dårlig sikkerhet å ha det hashede passordet lagret som fil i javaprosjektet. Det er en mulighet for å bytte ut denne filen med et hashet passord man selv kjenner. Dette fordrer at "angriperen" har tilgang til å gjøre slike endringer, noe som er svært usannsynlig gjennom en touchskjerm på en brusautomat.

3. Ettersom det var ønskelig at applikasjonen kan aksessere .json-filen både gjennom REST API-server og direkte lokalt, var det ikke mulig å legge modellklassene i Spring Boot sin modul. Det medførte Maven-problemer med "sykliske avhengigheter". Et mer korrekt oppsett etter MVC-prinsipper ville vært å ha modellklassene i en modellpakke i Spring Boot sin modul, men de er pr. i dag en del av core-modulen.

4. Vi har forsøkt i størst mulig grad å stramme inn på eksponering av pakker, det inkluderer både "opens" og "exports" i module-info.java-filen til modulene. Vi har etter mye research ikke kommet frem til hvilken modul som er gitt som "unnamed module" av Maven når vi forsøker å stramme inn. Dermed har vi i noen tilfeller måttet godta at pakkene blir eksponert til "alle" ved å ikke spesifisere hvilken modul som pakkene skal eksponeres til.

5. Applikasjonen lager ingen ny fil dersom både server og lokal fil er utilgjengelig. Dette medfører en feilmelding om at Vending Machine Tracker ikke er funnet. Serveren returnerer også en lignende feilmelding dersom tracker.json-filen ikke er tilgjengelig på serveren.

6. For at det skal være mulig å kjøre alle Tester er det viktig at man ikke har serveren startet og kjørende i bakgrunn. Dette vil føre til til at testene feiler ettersom porten 8080 allerede er i bruk.

### Neste release

Her er en liste over ting som ville blitt fikset eller implementert under en eventuell ny iterasjon. Dette er ting som vi ikke har hatt tid til å gjøre under denne iterasjonen:

- Implementere TypeAdapter som en løsning på å slippe "opens" til Gson, ref. punkt 4 under [Merknader](#merknader)

- Legge inn støtte for at serveren kan ha flere filer, f.eks. ved at dette representeres i endepunktet til REST API-serveren slik: "/vendmachtrack/tracker1/..."

- Dersom både serveren og filen er utilgjengelig/ikke-eksisterende, skal applikasjonen lage en ny lokal fil med et "tilfeldig" navn, f.eks. "tracker-X.json". Ref. punkt 5 under [Merknader](#merknader)
  - Det samme skal serveren gjøre dersom filen ikke eksisterer der, men serveren er tilgjengelig.

- Det finnes en Item-klasse for varer, men denne er ikke tatt i bruk. Den var i bruk tidligere i prosjektets løp, men varebeholdningen til en brusautomat ble endret til String i stedet for Item ettersom vi ikke lenger var interessert i en oversikt over inntjening. Dette er noe vi har lyst til å gjeninnføre.

- Maven rapporterer en del Warnings når det opprettes uber-jar-filer (shaded jar). Disse advarslene er ikke kritiske, til tross for hva det kan virke som. Vi har ikke hatt tid til å fikse disse, men det er noe vi vil gjøre under neste iterasjon.
  - Eksempler på Warnings:
    - [WARNING] Discovered module-info.class. Shading will break its strong encapsulation.

    - [WARNING]   - META-INF/ ... (Advarsler på at de avhengige modulene inneholder mange av de samme filene)

- Fikse punkt 1 under [Merknader](#merknader)

- Fikse punkt 2 under [Merknader](#merknader)

- Legge til flere Checks/moduler i CheckStyle-konfigurasjonsfilen for flere tilbakemeldinger på kodekvalitet med hensyn på syntaks/oppsett.
