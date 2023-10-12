# Vending Machine Tracker (Vendmachtrack)

Velkommen til prosjektet Vendmachtrack!


## Innhold

- [Lenker til andre dokumenter](#lenker-til-andre-dokumenter) 
- [Dokumentajson fra ulike releases](#dokumentasjon-fra-ulike-releases)
- [Hvordan kjøre applikasjonen i Eclipse Che](#hvordan-kjøre-applikasjonen-i-eclipse-che)
- [Hvordan kjøre applikasjonen lokalt](#hvordan-kjøre-applikasjonen-lokalt)  
- [Hvordan kjøre tester](#hvordan-kjøre-tester)
- [Hvordan se testdekningsgrad](#hvordan-se-testdekningsgrad)
- [Prosjektstruktur](#prosjektstruktur)

## Lenker til andre dokumenter

- [readme.md for kodeprosjektet](/vendmachtrack/readme.md)
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

## Hvordan kjøre applikasjonen i Eclipse Che

1. Sørg først for at Eclipse Che er koblet opp mot Gitlab med Personal Access Token.
2. [Åpne prosjektet i Eclipse Che.](https://che.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2023/gr2338/gr2338?new)
3. Når IDE-en har startet opp, kopierer du "endpoint URL" for "6080-tcp-desktop-ui (6080/http)" under "Endpoints" nederst til venstre i IDE-en. Se skjermbildet:
![Alt text](/docs/images/endpoint_eclipse-che.png)
4. Åpne nettsiden i en ny fane med den kopierte URL-en.
5. Gå tilbake til IDE-en. Åpne en terminal.
6. Kjør kommando for å kopiere tracker.json-filen til home directory:
```bash
cp tracker.json /home/dev/
```
7. Kjør kommando for å navigere til vendmachtrack/:
```bash
cd vendmachtrack/
```
8. Kjør kommando:
```bash
mvn clean install
```
9. Kjør kommando:
```bash
mvn javafx:run -f ui/pom.xml
```
10. Applikasjonen kommer da til å starte i workspacet sitt desktop (nettsiden fra endpoint URL-en)

## Hvordan kjøre applikasjonen lokalt

1. Sørg for å ha installert riktig versjon av Java og Maven, du må ha:
    - Java versjon 17.0.5 eller nyere
    - Apache Maven 3.8.7 eller nyere
    - Andre repoer som prosjektet er avhengig av vil man få automatisk lastet ned og installert ved hjelp av Maven
2. Klon dette repositoret til din lokale maskin
3. Kopier filen "tracker.json" til ditt "home directory". Filen ligger i rotnivå av repoet.

    - På Linux er dette under /home/ditt-brukernavn
    - På Windows er dette under C:\Users\ditt-brukernavn
    - På Mac er dette under /Users/ditt-brukernavn


4. Åpne en ønsket terminal (for eksempel i VSCode).
5. Sørg for at filstien er: vendmachtrack/
6. Kjør følgende kommando i terminalen:

```bash
 mvn clean install 
```

7. Kjør følgende kommando i terminalen:

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

1. Last ned en live server extension til din IDE (for eksempel Live Server extension til VSCode)
2. Kjør følgende kommando i terminalen for å generere JaCoCo-rapporten:

```bash
mvn test jacoco:report
```

3. Etter at kommandoen er fullført, vil en ny mappe kalt 'target' bli generert i hver modul (core, jsonio, ui).
4. Inne i 'target' mappen, naviger til 'site/jacoco' mappen. Her vil du finne en 'index.html' fil.
5. Åpne denne filen i en nettleser ved hjelp av live server extensionen.


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
    - skjermbildeApp.png - bilde av brukergrensesnittet til release 1
    - readme.md - inneholder readme.md for release 1
  - release2/
    - Arbeidsflyt_2.md - inneholder info om kommunikasjon og arbeidsflyt innad i gruppen til release 2
    - Krav_2.md - inneholder kravene for release 2
    - readme.md - inneholder informaasjon om hva som ble gjort i release 2
  - Brukerhistorier.md - inneholder brukerhistoriene til prosjektet
- vendmachtrack/ - Hovedmappe som inneholder all kildekoden for prosjektet
  - pom.xml - Dette er rot-pom.xml filen som hører til hele prosjektet
  - core/ - Inneholder backend- logikken til appen
    - pom.xml - Dette er Maven prosjekt filen som hører til core modulen
    - src/main/java/
        - core/ - Inneholder kildekoden til backend-koden  
            - IItem.java
            - Item.java
            - IMachineTracker.java
            - MachineTracker.java
            - IVendingMachine.java
            - VendingMachine.java
        - module-info.java
    - src/test/java/core/
      - ItemTest.java - inneholder tester for Item- klassen
      - MachineTrackerTest.java - inneholder tester for MachineTracker- klassen
      - VendingMachineTest.java - inneholder tester for Vendingmachine- klassen
  - jsonio/
    - pom.xml - Dette er Maven prosjektfilen som hører til jsonio-modulen
    - src/main/java/
      - jsonio/ - inneholder kildekoden til filhåndtering: 
        - FromJson.java
        - IFromJson.java
        - ToJson.java
        - IToJson.java
      - module-info.java
    - src/test/java/jsonio/ - inneholder tester:
      - jsonioTest.java - inneholder testene til jsonio-mappen
  - ui/ - inneholder frontend-logikken til appen
    - pom.xml - Dette er Maven prosjektfilen som hører til ui-modulen
    - src/main - inneholder java og resources/ui - mappen
      - java/
        - ui/
            - App.java
            - VendAppController.java - kildekode til frontend
      - resources/
        - App.fxml
    - src/test/java/ui/
      - VendMachAppTest.java - inneholder testene til ui-mappen
      - AppTest.java



