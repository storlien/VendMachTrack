# Vending Machine Tracker (Vendmachtrack)

Velkommen til prosjektet Vendmachtrack!

## Innhold

- [Lenker til andre dokumenter](#lenker-til-andre-dokumenter) 
- [Dokumentajson fra ulike releases](#dokumentasjon-fra-ulike-releases)
- [Hvordan kjøre applikasjonen i Eclipse Che](#hvordan-kjøre-applikasjonen-i-eclipse-che)
- [Hvordan kjøre applikasjonen lokalt](#hvordan-kjøre-applikasjonen-lokalt)
- [Hvordan bruke applikasjonen](#hvordan-bruke-applikasjonen) 
- [Hvordan kjøre tester](#hvordan-kjøre-tester)
- [Hvordan se testresultater](#hvordan-se-testresultater)
- [Prosjektstruktur](#prosjektstruktur)

## Lenker til andre dokumenter

- [readme.md for kodeprosjektet](/vendmachtrack/readme.md)
- [Dokumentasjon av REST API-et](/docs/rest_api.md)
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
5. Åpne nettsiden i en ny fane med den kopierte URL-en.
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
11. Kjør kommando:
```bash
mvn javafx:run -f ui/pom.xml
```

Applikasjonen kommer da til å starte i workspacet sitt desktop (nettsiden fra endpoint URL-en)

Se videre [hvordan man bruker applikasjonen](#hvordan-bruke-applikasjonen) for å komme i gang.

## Hvordan kjøre applikasjonen lokalt

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
<br>

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

<br>

## Hvordan kjøre tester

- Tester skal kjøres ved hjelp av Maven.
- Sørg for at du befinner deg i prosjektmappen (gr2338/vendmachtrack)
- Skriv følgende kommando i terminalen:

```bash
mvn clean verify
```
<br>

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

<br>

## Prosjektstruktur

Repoet er organisert i flere mapper. Selve kodeprosjektet ligger i mappen vendmachtrack/. I denne mappen ligger det flere mapper som inneholder kode for forskjellige deler av prosjektet.

- docs/ - dokumentasjon
  - diagrams/ - inneholder diagrammer
  - images/ - inneholder bilder
  - release1/
    - Arbeidsflyt_1.md - inneholder info om kommunikasjon og arbeidsflyt innad i gruppen til release 1
    - Klassediagram.png - klassediagram over kildekoden til prosjektet
    - Krav_1.md - inneholder kravene for release 1
    - readme.md - inneholder readme.md for release 1
    - skjermbildeApp.png - bilde av brukergrensesnittet til release 1
  - release2/
    - Arbeidsflyt_2.md - inneholder info om kommunikasjon og arbeidsflyt innad i gruppen til release 2
    - Krav_2.md - inneholder kravene for release 2
    - readme.md - inneholder informasjon om hva som ble gjort i release 2
  - release3/
    - Arbeidsflyt_3.md - inneholder info om kommunikasjon og arbeidsflyt innad i gruppen til release 3
    - Krav_3.md - inneholder kravene for release 3
    - readme.md - inneholder informasjon om hva som ble gjort i release 3
  - Brukerhistorier.md - inneholder brukerhistoriene til prosjektet
  - rest_api.mc - inneholder informasjon om rest-api
- vendmachtrack/ - Hovedmappe som inneholder all kildekoden for prosjektet
  - pom.xml - Dette er rot-pom.xml filen som hører til hele prosjektet
  - readme.md - inneholder informasjon om vår applikasjon
  - core/ - Inneholder backend- logikken til appen
    - pom.xml - Dette er Maven prosjekt filen som hører til core modulen
    - src/main/java/
        - core/ - Inneholder kildekoden til backend-koden
            - util/
        - module-info.java
    - src/main/resources/core/
      - password.txt
    - src/test/java/core/
  - jacoco- aggregator
    - pom.xml
  - jsonio/
    - pom.xml - Dette er Maven prosjektfilen som hører til jsonio-modulen
    - src/main/java/
      - jsonio/ - inneholder kildekoden til filhåndtering: 
        - internal - inneholder kode for å lese og skrive fra Json
        - VendmachtrackPersistence.java
      - module-info.java
    - src/test/java/jsonio/ - inneholder tester
  - springboot/
    - pom.xml
    - src/main/java/
      - springboot/
        - controller
        - exception
        - repository
        - service
        - SpringbootApplication.java
    - module-info.java
    - test/java/springboot/ - testklasser for springboot
      - controller
      - repository
      - service
  - test-results/ - alle testresultater for ulike deler av applikasjonen
    - checkstyle
      - core.xml
      - jacoco-aggregator.xml
      - jsonio.xml
      - springboot.xml
      - ui.xml
      - vendmachtrack.xml
    - jacoco
      - core
      - jacoco-resources
      - jsonio
      - springboot
      - ui
      - index.html
      - jacoco-sessions.html
      - jacoco.csv
      - jacoco.xml
    - junit
      - core
      - jsonio
      - springboot
      - ui
    - spotbugs
      - core
      - jsonio
      - springboot
      - ui
  - ui/ - inneholder frontend-logikken til appen
    - pom.xml
    - src/main/java
      - module-info.java
      - ui/ - kildekode til frontend
        - access
    - src/main/resources/ui - inneholder alle fxml-filer for ui
    - src/test/java/ui - inneholder tester for ui-mappen
      - access - tester for access- klassene









