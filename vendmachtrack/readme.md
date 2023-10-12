# Vending Machine Tracker (Vendmachtrack)

Vending Machine Tracker er en applikasjon for å hjelpe bedrifter holde oversikt over sine brusautomater. Brukere av applikasjonen skal kunne se informasjon som for eksempel varebeholdning på de ulike automatene.

En brusautomat oppdaterer i utgangspunktet sin varebeholdning ved hjelp av eksterne tjenester som ikke er implementert i denne applikasjonen, for eksempel:
- Kortterminal som håndterer salg av varer (trekker fra antall i varebeholdningen)
- Påfylling av varer (legger til antall i varebeholdningen)

Likevel er det behov for at en bruker av applikasjonen selv kan oppdatere varebeholdningen uten å måtte bruke eksterne tjenester.

Applikasjonen vil lytte til endringer i filen og oppdatere brukergrensesnittet dersom det kommer endringer utført av eksterne tjenester. Brukergrensesnittet vil også ha muligheten til å endre varebeholdningen og dermed filen. De eksterne tjenestene vil, på lik linje, lytte til filen dersom den skulle bli endret av brukergrensesnittet til applikasjonen.

Se [brukerhistorier](/docs/Brukerhistorier.md) for konkrete scenarier.

## Funksjonalitet ved ferdig applikasjon

Brukeren skal kunne:

- Se en oversikt over sine brusautomater
- Trykke seg inn på en enkelt brusautomat og se informasjon om denne:
  - Hvor brusautomaten er plassert
  - Hvor mye av hver vare som er igjen i brusautomaten
- Oppdatere varebeholdning
- Hente oversikt fra skytjeneste og fil
- Lagre oversikt til skytjeneste og fil


### Lagring av data

Applikasjonen tar i bruk Google sitt Java-bibliotek, Gson, for oversette ("parse") objektene til JSON-data. Et MachineTracker-objekt blir gjort om til JSON og dermed lagret til fil, enten lokalt eller på server. Eksempel på hvordan JSON-dataen til et MachineTracker-objekt ser ut:

```json
{"machines":[

  {"status":{"Cola":5,"Pepsi":3},
    "id":1,
    "location":"Trondhjem"},

  {"status":{"Tuborg":1},
    "id":2,
    "location":"Oslo"},

  {"status":{"Hansa":100,"Regnvann":10},
    "id":3,
    "location":"Bergen"}

]}
```
Applikasjonen er laget for å lese/skrive til én enkelt fil ettersom bedrifter antas å ville samle alle deres brusautomater i én oversikt (ett MachineTracker-objekt). Dermed benytter applikasjonen seg av såkalt implisitt lagring der brukeren ikke har et valg om hvilken fil som skal brukes, annet enn å legge inn ny fil manuelt.

### Bilder og diagrammer


Skjermbilde av applikasjonen pr. release 1:

![Skjermbilde av app](../docs/release1/skjermbildeApp.png)


- [Diagram i PlantUML](../docs/diagrams/ClassDiagram.wsd)
