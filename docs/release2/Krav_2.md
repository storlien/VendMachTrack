# Krav til release 2:
- modularisering 
    - kodingsprosjektet skal deles opp i egnede moduler med avhengigheter mellom (og til andre, relevante moduler)
    - bygging styres (fortsatt) av maven og hver modul har hensiktmessig konfigurasjon

- arkitektur (full tre-lags arkitektur)
    - kjernelogikken og ui-et skal skilles fra hverandre (domenelaget + brukergrensesnittlaget)
    - persistens til fil med JSON vha. et egnet biblioteket (Jackson el.l.)
        - definere/dokumentere filformat for brukerdata og evt. innstillinger
        - reflektere over og velge mellom dokumentmetafor (desktop) og implisitt lagring (app)
    - alle tre lagene spiller sammen
- kodekvalitet
    - tester skal skrives for all modulene 
    - testdekningsgrad  (Jacoco) og (annen) kodekvalitet skal sjekkes av egne tillegg (for eksempel maven-tillegg for Checkstyle og Spotbugs) 
    - rimelig god testdekning av alle lagene
- dokumentasjon
    - dokumentasjon (readme filer og kommentarer) må oppdateres
    - dokumentasjon for hver release må plasseres i en egen mappe, så den andre innleveringsdokumentasjonen må plasseres i gr23nn/docs/release2
    - dokumentere arkitektur med minst et diagram (bruk PlantUML) i tillegg til teksten i readme
    - dokumentere valg knyttet til arbeidsvaner, arbeidsflyt og kodekvalitet (f.eks. tilnærming til testing, verktøy for sjekking av kodekvalitet og innstillinger for dem)
- arbeidsvaner
    - kodingsoppgaver skal være utgangspunktet for alt arbeid (Issues for alle ting som skal gjøres)
    - greiner (branch) samler arbeid for hver kodingsoppgave
    - bruk milepæl (milestones) knyttet til innleveringen
    - dere jobber i par og bytter på å kode (dokumenter det i commit meldinger - bruk "Co-authored-by:" footer)


Dere kan utvide med flere brukerhistorier om dere ønsker (evt. bytte domene, hvis det viser seg at det opprinnelige ikke fungerer), men det skal ikke gå på bekostning av kvalitet. Alt skal være på stell før applikasjonen utvides med nye funksjoner. Når arkitekturen blir mer omfattende og en må skrive tester, så krever hver nye funksjon mer arbeid. Én del av arkitekturen skal ikke ligge langt foran en annen.


Så leverer dere prosjektet på gitlab


- prosjektet må være Eclipse Che -klart, så vi kan åpne det i Eclipse Che med en lenke  i README-fila på rotnivå
- maven skal brukes til kjøring av applikasjonen, kjøring av testene og sjekking av kodekvalitet
- kode for vurdering må leveres i master-greina av standard repo for gruppen (https://gitlab.stud.idi.ntnu.no/it1901/groups-2023/gr23nn/gr23nn)