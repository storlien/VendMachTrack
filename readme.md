# Vending Machine Tracker (Vendmachtrack)

Velkommen til Vendmachtrack repository!


## Innhold

- [Lenker til andre dokumenter](#lenker-til-andre-dokumenter) 
- [Dokumentajson fra ulike releases](#dokumentasjon-fra-ulike-releases)
- [Hvordan kjøre applikasjonen i Eclipse Che](#hvordan-kjøre-applikasjonen-i-eclipse-che)
- [Hvordan kjøre applikasjonen lokalt](#hvordan-kjøre-applikasjonen-lokalt)  
- [Hvordan kjøre tester](#hvordan-kjøre-tester)
- [Hvordan se testdekningsgrad](#hvordan-se-testdekningsgrad)
- [Prosjektstruktur](#prosjektstruktur)

## Lenker til andre dokumenter

- [readme.md for kodingsprosjektet](/vendmachtrack/readme.md)
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

1. Sørg først for at Gitlab er koblet opp mot Eclipse Che med Access Token
2. [Åpne i Eclipse Che.](https://che.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2023/gr2338/gr2338?new)
3. Når IDE-en har startet opp, kopierer du "endpoint URL" for "6080-tcp-desktop-ui (6080/http)" under "Endpoints" nederst til venstre i IDE-en. Se skjermbildet:
![Alt text](/docs/images/endpoint_eclipse-che.png)
4. Åpne nettsiden i en ny fane med den kopierte URL-en.
5. Gå tilbake til IDE-en. Åpne en terminal.
6. Kjør kommando for å kopiere tracker.json-filen til home directory:
```bash
cp tracker.json /home/dev/
```
6. Kjør kommando for å navigere til vendmachtrack/:
```bash
cd vendmachtrack/
```
7. Kjør kommando:
```bash
mvn clean install
```
8. Kjør kommando:
```bash
mvn javafx:run -f ui/pom.xml
```
9. Applikasjonen kommer da til å starte i workspacet sitt desktop (nettsiden fra endpoint URL-en)

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

Repositoriet er organisert i flere mapper. selve kodeprosjektet ligger i mappen vendmachtrack/. I denne mappen ligger det flere mapper som inneholder kode for forskjellige deler av prosjektet:

- docs - inneholder relevant informasjon tilknyttet prosjektet
  - diagrams - inneholder fil for klassediagram for backend- struktur
  - images - inneholder bilde av hvor man finner endpoints
appen.
  - release1
    - Arbeidsflyt_1.md - inneholder info om kommunikasjon og arbeidsflyt innad i gruppen til release 1
    - Klassediagram.png - klassediagram over kildekoden til prosjektet
    - Krav_1.md - inneholder kravene for release 1
    - skjermbildeApp.png - bilde av brukergrensesnittet til release 1
    - readme.md - inneholder readme.md for release 1

  - release2
    - Arbeidsflyt_2.md - inneholder info om kommunikasjon og arbeidsflyt innad i gruppen til release 2
    - Krav_2.md - inneholder kravene for release 2
    - readme.md - inneholder informaasjon om hva som ble gjort i release 2

  
  - Brukerhistorier.md - inneholder brukerhistoriene til prosjektet

   

- vendmachtrack/ - Hovedmappe som inneholder all kildekoden for prosjektet

  - pom.xml - dette er rot pom.xml filen som hører til hele prosjektet



  - core/ - Inneholder backend- logikken til appen
    - pom.xml - Dette er Maven prosjekt filen som hører til core modulen
    - src/main/java/ - inneholder core- mappen og module-info.java(vendmachtrack/core/src/main/java/module-info.java)
        - core/ - Inneholder kildekoden til backend-koden:  
            - IItem.java
            - Item.java
            - IMachineTracker.java
            - MachineTracker.java
            - IVendingMachine.java
            - VendingMachine.java
     
    - src/test/java/core/ - inneholder tester:
      - ItemTest.java - inneholder tester for Item- klassen
      - MachineTrackerTest.java - inneholder tester for MachineTracker- klassen
      - VendingMachineTest.java - inneholder tester for Vendingmachine- klassen



  - jsonio/ - inneholder logikken for å lese-/skrive til fil
    - pom.xml - Dette er Maven prosjektfilen som hører til jsonio modulen
    - src/main/java/ - inneholder kildekoden til fil logikken og module-info.java (vendmachtrack/jsonio/src/main/java/module-info.java)
      - jsonio/ - inneholder kildekoden til fil- logikken: 
        - FromJson.java
        - IFromJson.java
        - ToJson.java
        - IToJson.java
      
    - src/test/java/jsonio/ - inneholder tester:
      - jsonioTest.java - inneholder testene til jsonio- mappen

  - ui/ - inneholder frontend- logikken til appen
    - pom.xml(vendmachtrack/ui/pom.xml) - Dette er Maven prosjektfilen som hører til ui- modulen
    - src/main - inneholder java og resources/ui - mappen
      - java/ - inneholder ui- mappen og module-info.java(vendmachtrack/ui/src/main/java/module-info.java)
        - ui/ - inneholder kildekoden til frontend-koden:   
            - App.java (vendmachtrack/ui/src/main/java/ui/App.java) 
            - VendAppController.java(vendmachtrack/ui/src/main/java/ui/vendAppController.java)

      - resources/ - inneholder fxml- filen: App.fxml (vendmachtrack/ui/src/main/resources/ui/App.fxml)  
    - src/test/java/ui/ - inneholder tester:
      - vendmachAppTest.java (vendmachtrack/ui/src/test/java/ui/vendmachAppTest.java) - inneholder testene til ui- mappen



