# Release 3


Vi har i denne iterasjonen jobbet ut ifra [brukerhistorie US-1](/docs/Brukerhistorier.md). // TODO

Se [arbeidsflyt](/docs/release2/Arbeidsflyt_2.md) for å lese om hvordan arbeidsflyten i gruppen har vært med tanke på arbeidsvaner, metodikk og kodekvalitet. // TODO


### Hva er nytt i denne releasen?

// TODO


### Merknader

// TODO

Ville implementert TypeAdapter som en løsning på å slippe "opens" til Gson

UI-en kan oppfattes som fryst hvis serveren bruker lang tid på å svare på forespørseler. Dette burde løses ved å sende forespørseler i egne threads så UI heller kan oppdateres fortløpende uten at det henger seg opp. Dette er kun implementert for instansieringen av Access-objektet, altså når det er stor sannsynlighet for at serveren bruker lang tid på å svare eller ikke er tilgjengelig i det hele tatt.

Ha MachineTrackerAccess interface i UI-modulen slik at det abstraheres bort om applikasjonen gjør endringeer på fil lokalt eller gjennom REST API.

Hvis server og fil er utilgjengelig, hva med å lage ny fil hvis den ikke eksisterer?
(synes UI fungerer godt nok uten dette implementert)

Legge til flere checks/moduler i checkstyle config

Dårlig sikkerhet å ha det hashede passordet som fil i javaprosjektet. Burde ha dette i API-et, samt autentisering der.

// TODO model pakke i springboot