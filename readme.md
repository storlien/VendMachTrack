# Group gr2338 repository 

## Vending machine tracker (Vendmachtrack)

velkommen til Vendmachtrack repository! Denne appen er designet for å hjelpe eiere av flere brusautomater holde oversikt og analysere deres brusautomat bedrift.

## Innhold
- [Prosjektstruktur](#Prosjektstruktur) 
- [Hvordan starte](#Hvordan-starte) 
- [hvordan kjøre tester](#hvordan-kjøre-tester)
- [Brukerhistorier](Brukerhistorier.md)
- [FAQ](#FAQ)

## Prosjektstruktur 

Repositoriet er organisert i flere mapper. selve kodeprosjektet ligger i mappen vendmachtrack. I denne mappen ligger det flere mapper som inneholder kode for forskjellige deler av prosjektet:

- docs - inneholder relevant informasjon tilknyttet prosjektet
    - [how_to_git](docs/how_to_git.md) - enkel bruksavsnitt for git for viktige funksjoner
    -  [how_to_run](docs/how_to_run.md) - Info om hvordan man kjører appen.
    - /release1
        - [requirements](docs/release1/requirements.md) - inneholder kravene for prosjektet

- [vendmachtrack](vendmachtrack) - hovedmappe som inneholder all kildekoden for prosjektet
    - /core - Inneholder backend logikken til appen
        - [pom.xml](vendmachtrack/core/pom.xml) - Dette er Maven prosjekt filen som hører til core modulen
            - /src/main/java - inneholder core mappen og module-info.java
                -  /core - Inneholder kildekoden til backend-koden: [APPNAVN.java](vendmachtrack/core/src/main/java/core/Calc.java)
        - /target - inneholder XXXXXX   
        - /test - inneholder tester (ikke implementert)
    
  - /ui - inneholder frontend logikken til appen    
    - /src - inneholder main mappen og test mappen
        - /main inneholder java mappen og resources mappen
            - /java - inneholder ui mappen og module-info.java
                - /ui - inneholder kildekoden til frontend-koden: [APP.java](vendmachtrack/ui/src/main/java/ui/App.java) og [APPController.java](vendmachtrack/ui/src/main/java/ui/AppController.java)
            - /resources - inneholder fxml filen: [APP.fxml](vendmachtrack/ui/src/main/resources/App.fxml)  
        - /test - inneholder tester (ikke implementert) og en readme.md fil som beskriver testene
        - /target - inneholder XXXXXX
        - [/pom.xml](vendmachtrack/ui/pom.xml) - Dette er Maven prosjekt filen som hører til ui modulen

    - [/pom.xml](vendmachtrack/pom.xml) - dette er rot pom.xml filen som hører til hele prosjektet
- [Brukerhistorier](Brukerhistorier.md) - inneholder brukerhistoriene til prosjektet   
           

## Hvordan starte
1. Sørg for å ha installert riktig versjon av Java og Maven, du må ha: 
    - java versjon 17.0.5 eller nyere
    - Apache Maven 3.8.7 eller nyere 
2. Klon dette repositoret til din lokale maskin
3. Åpne en ønsket terminal (for eksempel i VSCode)
4. Naviger til mappen gr2338/vendmachtrack
5. [Følg Instruksjoner på how_to_run for å kjøre appen ](docs/how_to_run.md)




