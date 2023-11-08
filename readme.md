# Vending Machine Tracker (VendMachTrack)

Velkommen til prosjektet VendMachTrack!

[Klikk her](#hvordan-kjøre-applikasjonen-lokalt-med-installasjon) for å komme raskt i gang med den desidert enkleste måten på kjøre applikasjonen på.


## Innhold


- [Lenker til andre dokumenter](#lenker-til-andre-dokumenter)
- [Dokumentasjon fra ulike releases](#dokumentasjon-fra-ulike-releases)
- [Hvordan kjøre applikasjonen i Eclipse Che](#hvordan-kjøre-applikasjonen-i-eclipse-che)
- [Hvordan kjøre applikasjonen lokalt uten installasjon](#hvordan-kjøre-applikasjonen-lokalt-uten-installasjon)
- [Hvordan kjøre applikasjonen lokalt med installasjon](#hvordan-kjøre-applikasjonen-lokalt-med-installasjon)
  - [Hvordan lage egen installasjonsfil](#hvordan-lage-egen-installasjonsfil)
- [Hvordan bruke applikasjonen](#hvordan-bruke-applikasjonen)
- [Hvordan kjøre tester](#hvordan-kjøre-tester)
- [Hvordan se testresultater](#hvordan-se-testresultater)
  - [JUnit](#junit)
  - [Checkstyle og SpotBugs](#checkstyle-og-spotbugs)
  - [JaCoCo](#jacoco)
- [Prosjektstruktur](#prosjektstruktur)


## Lenker til andre dokumenter

- [readme.md for kodeprosjektet](/vendmachtrack/readme.md)
- [HTTP-forespørsler til REST API-et](/docs/rest_api.md)
- [Brukerhistorier](/docs/Brukerhistorier.md)


## Dokumentasjon fra ulike releases

- release 1:
  - [readme.md](/docs/release1/readme.md)
  - [Arbeidsflyt](/docs/release1/Arbeidsflyt_1.md)
  - [Krav](/docs/release1/Krav_1.md)


- release 2:
  - [readme.md](/docs/release2/readme.md)
  - [Arbeidsflyt](/docs/release2/Arbeidsflyt_2.md)
  - [Krav](/docs/release2/Krav_2.md)

- release 3:
  - [readme.md](/docs/release3/readme.md)
  - [Arbeidsflyt](/docs/release3/Arbeidsflyt_3.md)
  - [Krav](/docs/release3/Krav_3.md)


## Hvordan kjøre applikasjonen i Eclipse Che

1. Sørg først for at Eclipse Che er koblet opp mot Gitlab med Personal Access Token.
2. [Åpne prosjektet i Eclipse Che.](https://che.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2023/gr2338/gr2338?new)
3. Sørg for at prosjektet er oppdatert ved å kjøre følgende kommando i terminalen:
```bash
git pull
```
4. Når IDE-en har startet opp, kopierer du "endpoint URL" for "6080-tcp-desktop-ui (6080/http)" under "Endpoints" nederst til venstre i IDE-en. Se skjermbildet:
![Alt text](/docs/images/endpoint_eclipse-che.png)
5. Åpne nettsiden i en ny fane med den kopierte URL-en. Dersom det dukker opp et påloggingvindu for brukeren "dev", så skriver du inn passordet "dev".
6. Gå tilbake til IDE-en. Åpne en terminal.
7. Kjør kommando for å kopiere tracker.json-filen til home directory:
```bash
cp tracker.json /home/dev/
```
8. Kjør kommando for å navigere til vendmachtrack/:
```bash
cd vendmachtrack/
```
9. Kjør kommando:
```bash
mvn clean install
```
10. Kjør kommando:
```bash
mvn spring-boot:run -f springboot/pom.xml
```
11. Åpne en ny terminal og naviger til vendmachtrack/:
```bash
cd vendmachtrack/
```
12. Kjør kommando:
```bash
mvn javafx:run -f ui/pom.xml
```

Applikasjonen kommer da til å starte i workspacet sitt desktop (nettsiden fra endpoint URL-en).

Se videre [hvordan man bruker applikasjonen](#hvordan-bruke-applikasjonen) for å komme i gang.

**Stopp applikasjon og server:**
- Stopp applikasjonen ved å trykke Exit i øvre hjørne.
- Stopp serveren ved å trykke Ctrl+C eller Cmd+C i terminalen som startet Spring Boot.


## Hvordan kjøre applikasjonen lokalt uten installasjon

1. Sørg for å ha installert riktig versjon av Java og Maven, du må ha:
    - Java versjon 17.0.5 eller nyere
    - Apache Maven 3.8.7 eller nyere
    - Andre repoer som prosjektet er avhengig av vil man få automatisk lastet ned og installert ved hjelp av Maven
2. Klon eller last ned dette kodelageret til din maskin.
3. Kopier filen "tracker.json" til ditt "home directory". Filen ligger i rotnivå av repoet.

    - På Linux er dette under /home/ditt-brukernavn
    - På Windows er dette under C:\Users\ditt-brukernavn
    - På Mac er dette under /Users/ditt-brukernavn


4. Åpne en ønsket terminal.
5. Sørg for at filstien er: vendmachtrack/
6. Kjør kommando:

```bash
 mvn clean install 
```
7. Kjør kommando:
```bash
mvn spring-boot:run -f springboot/pom.xml
```
8. Kjør kommando:

```bash
mvn javafx:run -f ui/pom.xml
```

Applikasjonen skal da dukke opp på skjermen.

Se videre [hvordan man bruker applikasjonen](#hvordan-bruke-applikasjonen) for å komme i gang.

**Stopp applikasjon og server:**
- Stopp applikasjonen ved å trykke Exit i øvre hjørne.
- Stopp serveren ved å trykke Ctrl+C eller Cmd+C i terminalen som startet Spring Boot.


## Hvordan kjøre applikasjonen lokalt med installasjon

1. Last ned nyeste installasjonsfil for både applikasjonen (VendMachTrackApp) og serveren (VendMachTrackServer) for ditt operativsystem [her.](https://gitlab.stud.idi.ntnu.no/it1901/groups-2023/gr2338/gr2338/-/packages) Dersom det ikke finnes installasjonsfil for ditt operativsystem på GitLab, les [her](#hvordan-lage-egen-installasjonsfil)  om hvordan du lager egen.
2. Last ned filen "tracker.json" fra repoet på GitLab. Filen ligger i rotnivå.
3.  Kopier filen "tracker.json" til ditt "home directory".
    - På Linux er dette under /home/ditt-brukernavn
    - På Windows er dette under C:\Users\ditt-brukernavn
    - På Mac er dette under /Users/ditt-brukernavn
4. Installer både applikasjonen og serveren på vanlig vis for ditt operativsystem, her er noen eksempler:
    - For Linux:
      1. Naviger til mappen med installasjonsfilen til serveren.
      2. Kjør kommando for å installere:
          ```bash
          sudo dpkg -i vendmachtrackserver_*.deb
          ```
      3. Naviger til mappen med installasjonsfilen til applikasjonen.
      4. Kjør kommando for å installere:
          ```bash
          sudo dpkg -i vendmachtrackapp_*.deb
          ```
    - For Windows:
      1. Naviger til mappen med installasjonsfilen til serveren.
      2. Installer serveren på vanlig vis med .exe-filen ved å trykke deg igjennom Installation Wizard.
      3. Naviger til mappen med installasjonsfilen til applikasjonen.
      4. Installer applikasjonen på vanlig vis med .exe-filen ved å trykke deg igjennom Installation Wizard.
    - For MacOS:
      1. Naviger til mappen med installasjonsfilen til serveren.
      2. Installer serveren på vanlig vis med .dmg-filen ved å legge den til "Applikasjoner".
      3. Naviger til mappen med installasjonsfilen til applikasjonen.
      4. Installer applikasjonen på vanlig vis med .dmg-filen ved å legge den til "Applikasjoner".
5. Kjør applikasjonen. Her er noen eksempler på hvordan:
    - For Linux i terminal:
      1. For å kjøre serveren fra terminalen, kjør kommando:
          ```bash
          /opt/vendmachtrackserver/bin/VendMachTrackServer
          ```
          Serveren vil da starte og skriver til terminalen.
      2. For å kjøre applikasjonen fra terminalen, kjør kommando:
          ```bash
          /opt/vendmachtrackapp/bin/VendMachTrackApp
          ```
          Applikasjonen vil da starte. Noen meldinger kan leses i terminalen.
    - For Linux utenom terminal:
      1. Kjør serveren ved å søke opp VendMachTrackServer blant programmer.
      2. Kjør applikasjonen ved å søke opp VendMachTrackApp blant programmer.
      3. Applikasjonen vil da dukke opp mens serveren kjører i bakgrunnen.
    - For Windows:
      1. Trykk Windows-tasten og søk opp VendMachTrackServer. Kjør programmet (det legger seg i bakgrunnen og skriver ingenting til terminal).
      2. Trykk Windows-tasten og søk opp VendMachTrackApp. Kjør programmet og applikasjonen vil dukke opp.
    - For MacOS:
      1. Naviger til mappen der applikasjoner blir lagt, typisk under "/Applications/". Kjør VendMachTrackServer (programmet legger seg i bakgrunnen og skriver ingenting til terminal).
      2. Kjør VendMachTrackApp og applikasjonen vil dukke opp.

**NB:** For alle måter å kjøre serveren på utenom i terminalen til Linux, vil det ikke være mulig avslutte serveren på en enkel måte. Den legger seg i bakgrunnen i den forstand at man ikke direkte kan stoppe serveren ved å ta Ctrl+C eller Cmd+C i terminalen. For å stoppe serveren må dette gjøres ved å stoppe prosessen som kjøres i bakgrunnen, her er noen eksempler på hvordan:

- For Linux:
  1. Finn prosess-ID til serveren:
      ```bash
      ps -e | grep VendMachTrack
      ```
  2.  Stopp prosessen:
      ```bash
      kill <prossess-ID>
      ```
- For Windows:
  1. Åpne Oppgavebehandling
  2. Søk opp VendMachTrackServer
  3. Høyreklikk på prosessen og avslutt
- For MacOS:
  1. Følg samme steg som for Linux

### Hvordan lage egen installasjonsfil

Gjør følgende for å lage installasjonsfil tilpasset ditt operativsystem:

1. Gjør punkt 1 til og med 6 under [hvordan kjøre applikasjonen lokalt uten installasjon](#hvordan-kjøre-applikasjonen-lokalt-uten-installasjon)
2. Kjør følgende kommando for å lage installasjonsfil til serveren:
```bash
 mvn jpackage:jpackage -f springboot/pom.xml
```
2. Kjør følgende kommando for å lage installasjonsfil til applikasjonen:
```bash
 mvn jpackage:jpackage -f ui/pom.xml
```
3. Installasjonsfilen til serveren og applikasjonen ligger i sin respektive mappe, "springboot/target/jpackage/" og "ui/target/jpackage/".


## Hvordan bruke applikasjonen

Applikasjonen kommuniserer med Spring Boot REST API-serveren som kjører på samme maskin.

Hvis man sløyfer kommandoen for å starte Spring Boot (punkt 10 for kjøring i Eclipse Che eller punkt 7 for kjøring lokalt), vil applikasjonen heller jobbe direkte mot samme fil i stedet for å gjøre det gjennom REST API-et.

1. Skriv inn Server URL og filnavn i vinduet som vises når man kjører applikasjonen. For dette prosjektet skal følgende brukes:

**Server URL**

```
http://localhost:8080
```
**Tracker File Name**
```
tracker.json
```

2. Passordet for å komme seg ut av "User View" for dette prosjektet er:
```
Brus123
```


## Hvordan kjøre tester

- Tester skal kjøres ved hjelp av Maven.
- Sørg for at du befinner deg i prosjektmappen (gr2338/vendmachtrack)
- Skriv følgende kommando i terminalen:

```bash
mvn clean verify
```


## Hvordan se testresultater

Disse stegene forutsetter at man har kjørt en av følgende kommandoer i forkant:

```bash
mvn clean install
```
eller
```bash
mvn verify
```


### JUnit

For å se en komplett oversikt over alle JUnit-tester kjørt:

1. Naviger til mappen 'vendmachtrack/test-results/junit'
2. Her ligger det én mappe for hver modul. I hver modulmappe ligger det én .txt-fil med testresultatet for hver klasse som har blitt testet.

Man kan eventuelt lese det som blir skrevet til terminalen når tester blir kjørt, men det er det samme som ligger i .txt-filene.


### Checkstyle og SpotBugs

For å se en komplett oversikt over alle anmerkninger gitt av Checkstyle og SpotBugs:

1. Naviger til mappen 'vendmachtrack/test-results/'
2. I mappen 'vendmachtrack/test-results/checkstyle/' ligger resultatet fra Checkstyle til alle modulene i hver sin .xml-fil.
3. I mappen 'vendmachtrack/test-results/spotbugs/' ligger resultatet fra SpotBugs til alle modulene i hvert sin .xml-fil i sin respektive mappe.

Det kan være tungvint å lese .xml-filene, men de gir en komplett oversikt over alt som er rapportert. Eventuelt kan man lese det som blir skrevet til terminalen når tester blir kjørt, men det er det samme som ligger i .xml-filene.


### JaCoCo

For å se testdekningsgrad gitt av JaCoCo:

1. Naviger til mappen 'vendmachtrack/test-results/jacoco'
3. Åpne filen 'index.html' i en nettleser. Rapporten vil da dukke opp i nettleseren. Her vises testdekningsgraden for prosjektet.


## Prosjektstruktur

Repoet er organisert i flere mapper. Selve kodeprosjektet ligger i mappen vendmachtrack/. I denne mappen ligger det flere mapper som inneholder kode for forskjellige deler av prosjektet. Her er en oversikt over modulene og pakkene, samt noen viktige filer, til prosjektet:

- docs/ - Dokumentasjon
  - diagrams/ - Diagrammer
  - images/ - Bilder
    - diagrams/
    - screenshots/
  - release1/ - Dokumentasjon for release 1
  - release2/ - Dokumentasjon for release 2
  - release3/ - Dokumentasjon for release 3
- vendmachtrack/ - Hovedmappe som inneholder all kildekoden for prosjektet
  - pom.xml - Konfigurasjonsfil for Maven
  - config/ - Konfigurasjonsfiler til Checkstyle og SpotBugs
  - core/ - Kildekode for core-modulen
    - pom.xml - Konfigurasjonsfil for Maven
    - src/main/java/
        - vendmachtrack/core/
          - model/ - Modellklasser
          - util/ - Verktøyklasser
        - module-info.java
    - src/main/resources/core/util/
      - password.txt
    - src/test/java/vendmachtrack/core/ - Tester for core-modulen
      - model/
      - util/
  - jacoco-aggregator/ - Modul for samling av JaCoCo-rapporter til én samlet
    - pom.xml - Konfigurasjonsfil for Maven
  - jsonio/ - Kildekode for jsonio-modulen
    - pom.xml - Konfigurasjonsfil for Maven
    - src/main/java/
      - vendmachtrack/jsonio/ - Kildekode for filhåndtering
        - internal/
      - module-info.java
    - src/test/java/vendmachtrack/jsonio/internal - Tester for jsonio-modulen
  - springboot/ - Kildekode for springboot-modulen
    - pom.xml - Konfigurasjonsfil for Maven
    - src/main/java/
      - vendmachtrack/springboot/
        - controller/ - Spring Boot Controller-klasser
        - exception/ - Exception-klasser
        - repository/ - Spring Boot Repository-klasser
        - service/ - Spring Boot Service-klasser
      - module-info.java
    - src/test/java/vendmachtrack/springboot/ - Tester for springboot-modulen
      - controller/
      - repository/
      - service/
  - test-results/ - Alle testresultater samlet
    - checkstyle/
    - jacoco/
    - junit/
    - spotbugs/
  - ui/ - Kildekode til grafisk brukergrensesnitt
    - pom.xml - Konfigurasjonsfil for Maven
    - src/main/java/
      - vendmachtrack/ui/
        - access/ - Aksessklasser
        - controller/ - JavaFX Controller-klasser
      - module-info.java
    - src/main/resources/vendmachtrack/ui/controller/ - CSS- og FXML-filer
    - src/test/java/vendmachtrack/ui/ - Tester for ui-modulen
      - access/
      - controller/
  - integrationtests/ - Kildekode til integrasjonstest
    - pom.xml - Konfigurasjonsfil for Maven
    - src/
      - main/java/
        - module-info.java
      - test/java/vendmachtrack/integrationtests