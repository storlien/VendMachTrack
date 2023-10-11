# Vending Machine Tracker (Vendmachtrack)

Velkommen til Vendmachtrack repository! Denne applikasjonen er designet for å hjelpe eiere av flere brusautomater holde oversikt og analysere deres brusautomat bedrift.

[Åpne i Eclipse Che](https://che.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2023/gr2338/gr2338?new)

## Innhold

- [Lenker til andre dokumenter](#lenker-til-andre-dokumenter) 
- [Dokumentajson fra ulike releases](#dokumentasjon-fra-ulike-releases)
- [Hvordan kjøre applikasjonen](#hvordan-kjøre-applikasjonen)  
- [Hvordan kjøre tester](#hvordan-kjøre-tester)
- [Hvordan se testdekningsgrad](#hvordan-se-testdekningsgrad)
- [FAQ](#faq)
- [Prosjektstruktur](#prosjektstruktur)

## Lenker til andre dokumenter

- [Alle brukerhistorier](/docs/Brukerhistorier.md)

## Dokumentasjon fra ulike releases

- release 1:
  - [readme.md](/docs/release1/readme.md)
  - [Brukerhistorier](/docs/release1/Brukerhistorier.md)
  - [Krav](/docs/release1/Krav.md)


- release 2:
  - readme.md
  - Brukerhistorier
  - Krav


<br>

## Funksjonalitet

Vendmachtrack er en app som er ment for å hjelpe eiere av flere brusautomater holde oversikt og analysere deres brusautomat bedrift.

- [Skjermbilde av appen](/docs/release1/skjermbildeApp.png)  
 - [Klassediagram](/docs/release1/Klassediagram%20venmachtrack%20øving%201%20(1)-1.png)

<br>

På GUI (når appen er ferdig) skal brukeren kunne:

- Se en oversikt over sine brusautomater
- Se total inntjening
- Trykke seg inn på en enkelt brusautomat og se detaljert informasjon om denne, dette kan være:
  - Hvor mye av hver vare som er igjen i brusautomaten
  - Inntjening
- Legge til en ny brusautomat
- Slette en brusautomat
- Endre informasjon om en brusautomat (fylle opp varer, endre navn)
- Se overiskt over sitt lager av varer
- Se kostnader av varer
- Oppdatere varebeholdning på lager

<br>

Annen funksjonalitet:

- Lagring/henting av data fra fil/skyen

<br>


## Hvordan kjøre applikasjonen

1. Sørg for å ha installert riktig versjon av Java og Maven, du må ha:
    - Java versjon 17.0.5 eller nyere
    - Apache Maven 3.8.7 eller nyere
    - Andre repoer som prosjektet er avhengig av vil man få automatisk lastet ned og installert ved hjelp av Maven
2. Klon dette repositoret til din lokale maskin
3. Kopier filen "tracker.json" til ditt "home directory":

- På Linux er dette under /home/ditt-brukernavn
- På Windows er dette under C:\Users\ditt-brukernavn
- På Mac er dette under /Users/ditt-brukernavn


4. Åpne en ønsket terminal (for eksempel i VSCode)
5. Kjør følgende kommando i terminalen:

```bash
 mvn clean install 
```

5.Kjør følgende kommando i terminalen:

```bash
mvn javafx:run -f ui/pom.xml
```

<br>

## Hvordan kjøre tester

- Tester skal kjøres ved hjelp av Maven.
- Sørg for at du befinner deg i rotmappen for prosjektet (gr2338/vendmachtrack)
- Skriv følgende kommando i terminalen:  

```bash
mvn clean install 
```

```bash
mvn test 
```

<br>

## Hvordan se testdekningsgrad

1. Kjør følgende kommando i terminalen for å generere JaCoCo-rapporten:

```bash
mvn test jacoco:report
```

2.Etter at kommandoen er fullført, vil en ny mappe kalt 'target' bli generert i hver modul (core, jsonio, ui).
3.Inne i 'target' mappen, naviger til 'site/jacoco' mappen. Her vil du finne en 'index.html' fil.
4.Åpne 'index.html' filen i en nettleser for å se testdekningen rapportert av JaCoCo.

<br>

## FAQ

- Kommer snart

<br>

## Prosjektstruktur

Repositoriet er organisert i flere mapper. selve kodeprosjektet ligger i mappen vendmachtrack/. I denne mappen ligger det flere mapper som inneholder kode for forskjellige deler av prosjektet:

- docs - inneholder relevant informasjon tilknyttet prosjektet
  - [how_to_git](docs/how_to_git.md) - enkel bruksanvisning for viktige git kommandoer
  - [how_to_run](docs/how_to_run.md) - Info om hvordan man kjører appen.
  - /release1
    - [requirements](docs/release1/requirements.md) - inneholder kravene for prosjektet
    - [Brukerhistorier.md](docs/release1/Brukerhistorier.md) - inneholder brukerhistoriene for release 1
    - [readme.md](docs/release1/readme.md) - inneholder readme.md for release 1
    - [Klassediagram venmachtrack øving 1](docs/release1/Klassediagram.png) - inneholder klassediagrammet for release 1
    - [Funksjonalitet](docs/release1/Funksjonalitet.md) - inneholder funksjonaliteten til release 1
    - [skjermbildeApp](docs/release1/skjermbildeApp.png) - inneholder skjermbilde av appen til release 1

- [vendmachtrack/](vendmachtrack/) - Hovedmappe som inneholder all kildekoden for prosjektet

- [pom.xml](vendmachtrack/pom.xml) - dette er rot pom.xml filen som hører til hele prosjektet

- core/ - Inneholder backend logikken til appen
  - [pom.xml](vendmachtrack/core/pom.xml) - Dette er Maven prosjekt filen som hører til core modulen
  - src/main/java/ - inneholder core mappen og [module-info.java](vendmachtrack/core/src/main/java/module-info.java)
    - core/ - Inneholder kildekoden til backend-koden:  
      - [IItem.java](vendmachtrack/core/src/main/java/core/IItem.java)
      - [Item.java](vendmachtrack/core/src/main/java/core/Item.java)
      - [IMachineTracker.java](vendmachtrack/core/src/main/java/core/IMachineTracker.java)
      - [MachineTracker.java](vendmachtrack/core/src/main/java/core/MachineTracker.java)
      - [IVendingMachine.java](vendmachtrack/core/src/main/java/core/IVendingMachine.java)
      - [VendingMachine.java](vendmachtrack/core/src/main/java/core/VendingMachine.java)
  - src/test/java/core/ - inneholder tester:
    - [venmachtrackTest.java](vendmachtrack/core/src/test/java/core/venmachtrackTest.java) - inneholder testene til core mappen

- jsonio/ - inneholder logikken for å lese-/skrive til fil
  - [pom.xml](vendmachtrack/jsonio/pom.xml) - Dette er Maven prosjekt filen som hører til jsonio modulen
  - src/main/java/ - inneholder kildekoden til fil logikken og [module-info.java](vendmachtrack/jsonio/src/main/java/module-info.java)
    - jsonio/ - inneholder kildekoden til fil logikken: 
      - [FromJson.java](vendmachtrack/jsonio/src/main/java/jsonio/FromJson.java)
      - [ToJson.java](vendmachtrack/jsonio/src/main/java/jsonio/ToJson.java)
      - [IToJson.java](vendmachtrack/jsonio/src/main/java/jsonio/IToJson.java)
      - [IFromJson.java](vendmachtrack/jsonio/src/main/java/jsonio/IFromJson.java)
  - src/test/java/jsonio/ - inneholder tester:
    - [jsonioTest.java](vendmachtrack/jsonio/src/test/java/jsonio/jsonioTest.java) - inneholder testene til jsonio mappen

- ui/ - inneholder fronten d logikken til appen
- [pom.xml](vendmachtrack/ui/pom.xml) - Dette er Maven prosjekt filen som hører til ui modulen
  - src/ - inneholder main mappen og test mappen
    - main/ inneholder java mappen og resources mappen
      - java/ - inneholder ui mappen og [module-info.java](vendmachtrack/ui/src/main/java/module-info.java)
        - ui/ - inneholder kildekoden til frontend-koden:   
          - [vendmachApp.java](vendmachtrack/ui/src/main/java/ui/App.java) 
          - [VendAppController.java](vendmachtrack/ui/src/main/java/ui/vendAppController.java)
      - resources/ - inneholder fxml filen: [App.fxml](vendmachtrack/ui/src/main/resources/ui/App.fxml)  
    - test/java/ui/ - inneholder tester:
      - [vendmachAppTest.java](vendmachtrack/ui/src/test/java/ui/vendmachAppTest.java) - inneholder testene til ui mappen

- [Brukerhistorier](Brukerhistorier.md) - inneholder brukerhistoriene til prosjektet