# Vending Machine Tracker (Vendmachtrack)

Applikasjonen Vending Machine Tracker er laget for å tilby en effektiv måte å spore og overvåke brusautomater for bedrifter. Brukere av applikasjonen skal kunne se informasjon som for eksempel varebeholdning på de ulike automatene i tillegg til at applikasjonen fungerer som et utsalgssted.

En brusautomat har en skjerm med Vending Machine Tracker-applikasjonen med "User View"-vinduet som vises til vanlig. En "kunde" har muligheten til å kjøpe varer fra automaten gjennom dette vinduet. Applikasjonen har som forutsetning at bekreftelse på betaling håndteres av en ekstern tjeneste, for eksempel:

- Kortterminal som håndterer salg
- Mulighet for å "vippse" til automaten som alternativ mbetalingsmåte

Applikasjonen inneholder derfor ingen logikk for betalingsbekreftelse.

En "eier" (en som jobber for bedriften) skal kunne gå ut av "User View" ved å taste inn et passord. Eieren får da tilgang til funksjoner som en "kunde" ikke har tilgang til. Se komplett oversikt over alle funksjoner [her.](#funksjonalitet)

Applikasjonen kan også kjøres på en dedikert PC i stedet for på en brusautomat. Dette gjør det mulig for bedriften å ikke være fysisk i nærheten av en brusautomat for å sjekke varebeholdningen til alle automater bedriften eier.

Se [brukerhistorier](/docs/Brukerhistorier.md) for konkrete scenarier.

## Innhold
- [Funksjonalitet](#funksjonalitet)
- [Lagring av data](#lagring-av-data)
- [Dokumentasjon av REST API](#dokumentasjon-av-rest-api)
  - [Server](#server)
  - [Klient](#klient)
    - [Fjerntilgang](#fjerntilgang)
    - [Lokaltilgang](#lokaltilgang)
- [Testing av applikasjonen og serveren](#testing-av-applikasjonen-og-serveren)
- [Bilder og diagrammer](#bilder-og-diagrammer)
  - [Klassediagram](#klassediagram)
  - [Pakkediagram](#pakkediagram)
  - [Sekvensdiagram](#sekvensdiagram)
  - [Diverse skjermbilder](#diverse-skjermbilder)


## Funksjonalitet


Funksjonalitet for bedriften/eieren:

- Se en oversikt over sine brusautomater med ID og lokasjon
- Trykke seg inn på en enkelt brusautomat og se varebeholdningen
- Oppdatere varebeholdning ved å fylle på med varer
  - Kunne velge en spesifikk brusautomat man vil etterfylle
  - Legge til en ny vare i brusautomaten og det antallet man ønsker
  - Oppdatere antallet av en vare som allerede finnes i automaten
  - Få en oversikt over oppdatert beholdning etter varer er lagt til
- Legge til og fjerne brusautomater
  - Legge inn ID og lokasjon til en automat man skal legge til
  - Legge inn ID-en til automaten man vil fjerne
  - Se oppdatert oversikt over alle brusautomater etterpå
- Kunne gå til side/vindu ment for kunder
  - Velge en spesifikk automat man vil åpne side/vindu ment for kunder
- Kunne gå tilbake til side/vindu ment for bedriften/eiere
- En kunde har ikke tilgang på funksjonaliteten som er tiltenkt bedriften/eieren
  - Tilgang på slik funksjonalitet er passordbeskyttet


Funksjonalitet for kunden:

- Se varebeholdning på den bestemte automaten
- Kunne kjøpe en vare
- Varebeholdningen vil oppdateres når varer kjøpes
  - En vare forsvinner fra varebeholdning hvis automaten selges tom for den varen


Teknisk funksjonalitet:
- Bedriften skal kunne bruke skytjenesteløsning
  - Applikasjonen jobber mot en REST API-server
  - Serveren skal kunne kjøre på en dedikert maskin
- Bedriften skal kunne bruke applikasjonen uten skytjenesteløsning
  - Applikasjonen aksesserer lokal fil direkte


## Lagring av data

Applikasjonen tar i bruk Google sitt Java-bibliotek, Gson, for oversette ("parse") objektene til JSON-data. Et MachineTracker-objekt blir gjort om til JSON og dermed lagret til fil, enten lokalt eller på server. Eksempel på hvordan JSON-dataen til et MachineTracker-objekt ser ut:

```json
{"machines":[

  {"status":{"Cola":5,"Pepsi":3},
    "id":1,
    "location":"Trondheim"},

  {"status":{"Tuborg":1},
    "id":2,
    "location":"Oslo"},

  {"status":{"Hansa":100,"Regnvann":10},
    "id":3,
    "location":"Bergen"}

]}
```
Dette eksemplet viser en liste over brusautomater, deres ID, beliggenhet og status som viser antall av hver drikke tilgjengelig i automaten.

Under oppstarten av applikasjonen blir man bedt om å angi URL til server og filnavn. Applikasjonen er tenkt å fungere mot flere filer. Bedriften kan dermed ha flere samlinger av brusautomater separert i ulike filer. Det kan for eksempel være ønskelig for en bedrift å skille brusautomater som befinner seg i forskjellige byer. Dette er ikke implementert i REST API-serveren ennå, men det fungerer for filer lokalt. Se [readme.md](/docs/release3/readme.md) for release 3 for mer informasjon om dette.

## Dokumentasjon av REST API

### Server

For å realisere en skytjenesteløsning har vi implementert et REST API ved hjelp av Spring Boot-rammeverket. Kodebasen til serveren er bygd på prinsipper for Model-View-Controller (MVC), noe som sikrer en ryddig laginndeling og separasjon av ansvar. Disse lagene er delt inn i egne pakker i prosjektet. Her en en oversikt over de ulike:

- Model: Dette laget representerer datastrukturene i applikasjonen. Her finnes representasjon av objekter som MachineTracker og VendingMachine.

- View: Siden et REST API ofte ikke har en tradisjonell "view" slik vi ser i GUI-applikasjoner, refererer derfor "view" til hvordan responsdata presenteres til klienten, i dette prosjektet i JSON-format. "View" er ingen egen pakken i dette prosjektet.

- Controller: Håndterer innkommende HTTP-forespørsler. Kaller på service for å bearbeider forespørslene ved hjelp av prosjektets "business logic". Returnerer et svar til klienten.

- Service: Utfører "business logic" på de innkommende forespørslene som controller i praksis kun sender videre.

- Repository: Dette laget er ansvarlig for datalagring og henting. I vår implementasjon jobber serveren direkte mot data lagret i en .json-fil i stedet for en tradisjonell database.

For å forenkle klient-server-interaksjonen og samtidig opprettholde et høyt nivå av abstraksjon i henhold til god objektorientert praksis, bruker vi ikke Data Transfer Objects (DTO). Dette betyr at klienten ikke trenger å forholde seg direkte til datastrukturene i MachineTracker eller VendingMachine. All logikk rundt datahåndtering og -transformasjon håndteres av serveren, noe som bidrar til enklere vedlikehold, og høyere skalerbarhet og sikkerhet.

Spring Boot-serveren er organisert som en egen modul i prosjektmappen. Dette gir fleksibilitet, da den kan separeres fra resten av prosjektet. Bedrifter kan derfor ha serveren kjørende på en dedikert maskin.
Ettersom det var ønskelig at applikasjonen kan jobbe direkte mot .json-filen dersom serveren ikke er tilgjengelig, ligger "model"-pakken i "core"-modulen for at både "ui"-modulen og "springboot"-modulen kan benytte seg av pakken. Hadde dette ikke vært et ønske, ville "model"-pakken ligget i "springboot"-modulen slik at Spring Boot-serveren ikke deler noen pakker/klasser med selve applikasjonen. Likevel er både Spring Boot-serveren og applikasjonen separate i den forstand at de kan kjøres på hver sin maskin.

Serveren er designet for å være "tilstandsløs" ("stateless"). Dette innebærer at serveren ikke opprettholder egne MachineTracker-objekter som jevnlig må lagres til filen. I stedet blir enhver endring eller skrivehandling gjennomført umiddelbart når en metode blir kalt. For eksempel, hvis en brus blir trukket fra varebeholdningen, oppdateres filen samtidig. Slik sikres det at applikasjonen er mer pålitelig og filens data alltid er ajourført, og en eventuell serverfeil ikke vil resultere i utdatert data. I tillegg tilrettelegger dette for at flere instanser av applikasjonen kan kobles mot samme server samtidig.

### Klient

I applikasjonen/"ui"-modulen:

Applikasjonen benytter Data Access Objects (DAO) for å interagere med filen, uavhengig av om det skjer via serveren eller ikke. Det finnes to distinkte aksessklasser samt et grensesnitt. Dette sikrer at alle aksessklassene implementerer de samme, nødvendige funksjonene for applikasjonen. Dermed er det ingen forskjell på interaksjonen med applikasjonen om den aksesserer filen direkte eller via serveren. 

Aksessklassene hverken krever som parameter eller returnerer objekter som krever at man kjenner til prosjektets modeller (dvs. klasser som MachineTracker og VendingMachine), ref. det som står skrevet under [server](#server) om å ikke bruke DTO-er.

#### Fjerntilgang

For aksessering av filen gjennom serveren blir et Remote Access-objekt (MachineTrackerAccessRemote) instansiert når serveren er tilgjengelig. Dette objektet har metoder som utfører HTTP-forespørsler mot REST API-et.

[Se komplett oversikt over støttede HTTP-forespørsler](../docs/rest_api.md)

#### Lokaltilgang

For aksessering av filen direkte blir et Local Access-objekt (MachineTrackerAccessLocal) instansiert når serveren ikke er tilgjengelig. Denne klassen har de samme metodene som Remote Access ettersom de implementerer samme grensesnitt, hvilket betyr at de for applikasjonen oppfører seg likt. Local Access bruker, på lik linje med Spring Boot-serveren, klasser i "jsonio"- og "core"-modulen for å gjøre nødvendige håndteringer av modellobjektene, og skrive- og leseoperasjoner av filen.

## Testing av applikasjonen og serveren

// TODO


## Bilder og diagrammer


### Klassediagram

Klassediagrammet viser en oversikt over alle klassene i modulen "springboot" og hvilke klasser utenfor modulen som de interagerer med.

[Diagrammet i PlantUML](/docs/diagrams/ClassDiagram2.puml)


[Åpne bildet av diagrammet](/docs/images/diagrams/ClassDiagram2.png)
![Diagram](/docs/images/diagrams/ClassDiagram2.png)


### Pakkediagram

Pakkediagrammet viser alle pakkene i modulen "springboot" og hvilke pakker utenfor modulen som de interagerer med.

[Diagrammet i PlantUML](/docs/diagrams/PackageDiagram.puml)


[Åpne bildet av diagrammet](/docs/images/diagrams/PackageDiagram.png)
![Diagram](/docs/images/diagrams/PackageDiagram.png)


### Sekvensdiagram

Sekvensdiagrammet viser sekvenser for når en bruker åpner applikasjonen, skriver inn server-URL og får hentet inn en oversikt over alle brusautomater.

[Diagrammet i PlantUML](/docs/diagrams/SequenceDiagram.puml)


[Åpne bildet av diagrammet](/docs/images/diagrams/SequenceDiagram.png)
![Diagram](/docs/images/diagrams/SequenceDiagram.png)


### Diverse skjermbilder

Her er diverse skjermbilder fra applikasjonen:


![Skjermbilde](/docs/images/screenshots/Screenshot1.png)


![Skjermbilde](/docs/images/screenshots/Screenshot2.png)


![Skjermbilde](/docs/images/screenshots/Screenshot3.png)


![Skjermbilde](/docs/images/screenshots/Screenshot4.png)


![Skjermbilde](/docs/images/screenshots/Screenshot5.png)