## Prosjektstruktur release 1 

Repositoriet er organisert i flere mapper. selve kodeprosjektet ligger i mappen vendmachtrack/. I denne mappen ligger det flere mapper som inneholder kode for forskjellige deler av prosjektet:

- docs - inneholder relevant informasjon tilknyttet prosjektet
  - [how_to_git](../how_to_git.md) - enkel bruksanvisning for viktige git kommandoer
  - [how_to_run](../how_to_run.md) - Info om hvordan man kjører appen.
  - /release1
    - [requirements](/docs/release1/requirements.md) - inneholder kravene for prosjektet
    - [Brukerhistorier.md](/docs/release1/Brukerhistorier.md) - inneholder brukerhistoriene for release 1
    - [readme.md](/docs/release1/readme.md) - inneholder readme.md for release 1
    - [Klassediagram venmachtrack øving 1](/docs/release1/Klassediagram.png) - inneholder klassediagrammet for release 1
    - [Funksjonalitet](/docs/release1/Funksjonalitet.md) - inneholder funksjonaliteten til release 1
    - [skjermbildeApp](/docs/release1/skjermbildeApp.png) - inneholder skjermbilde av appen til release 1

<br>


- ## [vendmachtrack/](../../vendmachtrack/) - Hovedmappe som inneholder all kildekoden for prosjektet


- [pom.xml](../../vendmachtrack/pom.xml) - dette er rot pom.xml filen som hører til hele prosjektet

<br>

- core/ - Inneholder backend logikken til appen
  - [pom.xml](../../vendmachtrack/core/pom.xml) - Dette er Maven prosjekt filen som hører til core modulen
  - src/main/java/ - inneholder core mappen og [module-info.java](../../vendmachtrack/core/src/main/java/module-info.java)
    - core/ - Inneholder kildekoden til backend-koden:  
      - [IItem.java](../../vendmachtrack/core/src/main/java/core/IItem.java)
      - [Item.java](../../vendmachtrack/core/src/main/java/core/Item.java)
      - [IMachineTracker.java](../../vendmachtrack/core/src/main/java/core/IMachineTracker.java)
      - [MachineTracker.java](../../vendmachtrack/core/src/main/java/core/MachineTracker.java)
      - [IVendingMachine.java](../../vendmachtrack/core/src/main/java/core/IVendingMachine.java)
      - [VendingMachine.java](../../vendmachtrack/core/src/main/java/core/VendingMachine.java)
  - src/test/java/core/ - inneholder tester:
    - [venmachtrackTest.java](../../vendmachtrack/core/src/test/java/core/venmachtrackTest.java) - inneholder testene til core mappen

<br>

- jsonio/ - inneholder logikken for å lese-/skrive til fil
  - [pom.xml](../../vendmachtrack/jsonio/pom.xml) - Dette er Maven prosjekt filen som hører til jsonio modulen
  - src/main/java/ - inneholder kildekoden til fil logikken og [module-info.java](../../vendmachtrack/jsonio/src/main/java/module-info.java)
    - jsonio/ - inneholder kildekoden til fil logikken: 
      - [FromJson.java](../../vendmachtrack/jsonio/src/main/java/jsonio/FromJson.java)
      - [ToJson.java](../../vendmachtrack/jsonio/src/main/java/jsonio/ToJson.java)
      - [IToJson.java](../../vendmachtrack/jsonio/src/main/java/jsonio/IToJson.java)
      - [IFromJson.java](../../vendmachtrack/jsonio/src/main/java/jsonio/IFromJson.java)
  - src/test/java/jsonio/ - inneholder tester:
    - [jsonioTest.java](../../vendmachtrack/jsonio/src/test/java/jsonio/jsonioTest.java) - inneholder testene til jsonio mappen
  

  

<br>

- ui/ - inneholder fronten d logikken til appen
- [pom.xml](../../vendmachtrack/ui/pom.xml) - Dette er Maven prosjekt filen som hører til ui modulen
  - src/ - inneholder main mappen og test mappen
    - main/ inneholder java mappen og resources mappen
      - java/ - inneholder ui mappen og [module-info.java](../../vendmachtrack/ui/src/main/java/module-info.java)
        - ui/ - inneholder kildekoden til frontend-koden:   
          - [vendmachApp.java](../../vendmachtrack/ui/src/main/java/ui/App.java) 
          - [VendAppController.java](../../vendmachtrack/ui/src/main/java/ui/vendAppController.java)
      - resources/ - inneholder fxml filen: [App.fxml](../../vendmachtrack/ui/src/main/resources/ui/App.fxml)  
    - test/java/ui/ - inneholder tester:
      - [vendmachAppTest.java](../../vendmachtrack/ui/src/test/java/ui/vendmachAppTest.java) - inneholder testene til ui mappen


<br>

- [Brukerhistorier](Brukerhistorier.md) - inneholder brukerhistoriene til prosjektet

<br>
