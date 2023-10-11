# Vending machine tracker (Vendmachtrack)

Velkommen til Vendmachtrack repository! Denne appen er designet for å hjelpe eiere av flere brusautomater holde oversikt og analysere deres brusautomat bedrift.

[open in Eclipse Che](https://che.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2023/gr2338/gr2338?new)

## Innhold

- [Lenker til andre dokumenter](#lenker-til-andre-dokumenter) 
- [Dokumentajson fra ulike releases](#dokumentasjon-fra-ulike-releases)
- [Hvordan starte](#hvordan-starte)  
- [Hvordan kjøre tester](#hvordan-kjøre-tester)
- [FAQ](#faq)

## Lenker til andre dokumenter

- [Alle brukerhistorier](/docs/Brukerhistorier.md)

## Dokumentasjon fra ulike releases

- release 1:
  - [readme.md](/docs/release1/readme.md)
  - [Brukerhistorier](/docs/release1/Brukerhistorier.md)
  - [Funksjonalitet](/docs/release1/Funksjonalitet.md)
  - [Prosjektstruktur](/docs/release1/Prosjektstruktur.md)
  - [Klassediagram](/docs/release1/Klassediagram%20venmachtrack%20øving%201%20(1)-1.png)
  - [Skjermbilde av appen](/docs/release1/skjermbildeApp.png) 

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


## Hvordan starte

1. Sørg for å ha installert riktig versjon av Java og Maven, du må ha:
    - Java versjon 17.0.5 eller nyere
    - Apache Maven 3.8.7 eller nyere
    - andre repoer som prosjektet er avhengig av vil man få automatisk ved hjelp av Maven 
2. Klon dette repositoret til din lokale maskin
3. Åpne en ønsket terminal (for eksempel i VSCode)
4. [Følg Instruksjoner på how_to_run for å kjøre appen](docs/how_to_run.md)

<br>

## Hvordan kjøre tester

- Tester skal kjøres ved hjelp av Maven.
- Sørg for at du befinner deg i rotmappen for prosjektet (gr2338/vendmachtrack)
- Skriv følgende kommando i terminalen:  

```
mvn clean install 
```

```
mvn test 
```

<br>

## FAQ

- Kommer snart
