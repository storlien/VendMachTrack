# Release 2


Vi har i denne iterasjonen jobbet ut ifra [brukerhistorie US-1](/docs/Brukerhistorier.md). Det er samme brukerhistorie som vi jobbet ut ifra for release 1. Vi har i denne releasen i all hovedsak forbedret koden og tester fremfor å utvikle ny funksjonalitet som krevde ny brukerhistorie.

Se [arbeidsflyt](/docs/release2/Arbeidsflyt_2.md) for å lese om hvordan arbeidsflyten i gruppen har vært med tanke på arbeidsvaner, metodikk og kodekvalitet.

### Hva er nytt i denne releasen?

- Feilmelding vises i brukergrensesnittet dersom filen ikke blir funnet eller kan leses ved oppstart
- Økt testdekningsgraden for alle moduler til å dekke store deler av koden
- Applikasjonen kan kjøres i Eclipse Che
- Forbedret innkapsling ved å stramme inn synligheten til modulene
- Implementert Maven Checkstyle og SpotBugs
- Øvrig forbedring av kodekvalitet generelt

- Laget [diagram](/docs/diagrams/ClassDiagram.wsd) i PlantUML

### Merknader

- I module-info.java-filen i core-modulen står følgende:
    ```bash
    opens core to com.google.gson;
    ```
    Det er ikke ønskelig mtp. god innkapsling å åpne core til Gson-biblioteket, men det har vært nødvendig for at Gson skal kunne aksessere feltene i MachineTracker-objektet. Det skal i utgangspunktet ikke være et problem ettersom vi har de nødvendige setters, getters og synlighet. Det har blitt jobbet mye på for å unngå dette, men eneste løsningen som ser ut til å fungere er å implementere TypeAdapter. Dette vil bli gjort i en senere release.

<br/>


[Skjermbilde:](/docs/release1/skjermbildeApp.png)
![Alt text](/docs/release1/skjermbildeApp.png)


- [Diagram i PlantUML](../docs/diagrams/ClassDiagram.wsd)