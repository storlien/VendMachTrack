Ville implementert TypeAdapter som en løsning på å slippe "opens" til Gson

UI-en kan oppfattes som fryst hvis serveren bruker lang tid på å svare på forespørseler. Dette burde løses ved å sende forespørseler i egne threads så UI heller kan oppdateres fortløpende uten at det henger seg opp. Dette er kun implementert for instansieringen av Access-objektet, altså når det er stor sannsynlighet for at serveren bruker lang tid på å svare eller ikke er tilgjengelig i det hele tatt.

Ha MachineTrackerAccess interface i UI-modulen slik at det abstraheres bort om applikasjonen gjør endringeer på fil lokalt eller gjennom REST API.
