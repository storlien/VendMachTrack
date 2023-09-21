# Group gr2338 repository

## Vending machine tracker (Vendmachtrack)

Velkommen til Vendmachtrack repository! Denne appen er designet for å hjelpe eiere av flere brusautomater holde oversikt og analysere deres brusautomat bedrift.

## Innhold

- [Prosjektstruktur](#prosjektstruktur)
- [Hvordan starte](#hvordan-starte)  
- [hvordan kjøre tester](#hvordan-kjøre-tester)
- [Brukerhistorier](Brukerhistorier.md)
- [FAQ](#faq)

## Dokumentasjon fra ulike øvinger

- [readme.md for øving 1](/docs/release1/readme_oving1.md)
- [Brukerhistorier for øving 1](/docs/release1/Brukerhistorier_oving1.md)

## Prosjektstruktur  

Repositoriet er organisert i flere mapper. selve kodeprosjektet ligger i mappen vendmachtrack. I denne mappen ligger det flere mapper som inneholder kode for forskjellige deler av prosjektet:

- docs - inneholder relevant informasjon tilknyttet prosjektet
  - [how_to_git](docs/how_to_git.md) - enkel bruksanvisning for viktige git kommandoer
    - [how_to_run](docs/how_to_run.md) - Info om hvordan man kjører appen.
    - /release1
      - [requirements](docs/release1/requirements.md) - inneholder kravene for prosjektet
      - [brukerhistorier_oving1.md](docs/release1/Brukerhistorier_oving1.md) - inneholder brukerhistoriene for øving 1
      - [readme_oving1.md](docs/release1/readme_oving1.md) - inneholder readme.md for øving 1

<br>


- ## [vendmachtrack](vendmachtrack) - Hovedmappe som inneholder all kildekoden for prosjektet

- /core - Inneholder backend logikken til appen
  - [/pom.xml](vendmachtrack/core/pom.xml) - Dette er Maven prosjekt filen som hører til core modulen
    - /src/main/java - inneholder core mappen og [module-info.java](vendmachtrack/core/src/main/java/module-info.java)
      - /core - Inneholder kildekoden til backend-koden:  
        - [IItem.java](vendmachtrack/core/src/main/java/core/IItem.java)
        - [Item.java](vendmachtrack/core/src/main/java/core/Item.java) 
        - [IMachineTracker.java](vendmachtrack/core/src/main/java/core/IMachineTracker.java)
        - [MachineTracker.java](vendmachtrack/core/src/main/java/core/MachineTracker.java)
        - [IVendingMachine.java](vendmachtrack/core/src/main/java/core/IVendingMachine.java)
        - [VendingMachine.java](vendmachtrack/core/src/main/java/core/VendingMachine.java)
  - /target - inneholder XXXXXX
  - /test - inneholder tester (ikke implementert)

<br>

- /jsonio - inneholder logikken for å lese-/skrive til fil
  - /src/main/java - inneholder kildekoden til fil logikken og [module-info.java](vendmachtrack/jsonio/src/main/java/module-info.java)
    - /jsonio - inneholder kildekoden til fil logikken: 
        - [FromJson.java](/vendmachtrack/jsonio/src/main/java/jsonio/FromJson.java)
        - [ToJson.java](/vendmachtrack/jsonio/src/main/java/jsonio/ToJson.java)
        - [IToJson.java](/vendmachtrack/jsonio/src/main/java/jsonio/IToJson.java)
        - [IFromJson.java](/vendmachtrack/jsonio/src/main/java/jsonio/IFromJson.java)
  - [/pom.xml](vendmachtrack/jsonio/pom.xml) - Dette er Maven prosjekt filen som hører til jsonio modulen
  - /target - inneholder XXXXXX   

<br>

- /ui - inneholder fronten d logikken til appen
  - /src - inneholder main mappen og test mappen
    - /main inneholder java mappen og resources mappen
      - /java - inneholder ui mappen og [module-info.java](vendmachtrack/ui/src/main/java/module-info.java)
        - /ui - inneholder kildekoden til frontend-koden:   
            - [vendmachApp.java](vendmachtrack/ui/src/main/java/ui/vendmachAPP.java) 
            - [VendAppController.java](vendmachtrack/ui/src/main/java/ui/vendAppController)
      - /resources - inneholder fxml filen: [VendmachApp.fxml](vendmachtrack/ui/src/main/resources/ui/VendmachApp.fxml)  
    - /test - inneholder tester (ikke implementert) og en readme.md fil som beskriver testene
    - /target - inneholder XXXXXX
    - [/pom.xml](vendmachtrack/ui/pom.xml) - Dette er Maven prosjekt filen som hører til ui modulen

  - [/pom.xml](vendmachtrack/pom.xml) - dette er rot pom.xml filen som hører til hele prosjektet
- [Brukerhistorier](Brukerhistorier.md) - inneholder brukerhistoriene til prosjektet

<br>

## Hvordan starte

1. Sørg for å ha installert riktig versjon av Java og Maven, du må ha:
    - java versjon 17.0.5 eller nyere
    - Apache Maven 3.8.7 eller nyere
2. Klon dette repositoret til din lokale maskin
3. Åpne en ønsket terminal (for eksempel i VSCode)
4. Naviger til mappen gr2338/vendmachtrack
5. [Følg Instruksjoner på how_to_run for å kjøre appen](docs/how_to_run.md)

<br>

# Hvordan kjøre tester

- Tester skal kjøres ved hjelp av Maven.
- sørg for at du befinner deg i rotmappen for prosjektet (gr2338/vendmachtrack)
- skriv følgende kommando i terminalen:  
```
mvn test 
```

<br>

## FAQ

- kommer snart
