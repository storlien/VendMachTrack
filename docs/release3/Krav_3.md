# Krav til release 3:
Trinn 3 er en kombinasjon av (nødvendig) forbedring av kvaliteten på koden fra trinn 2 og videreutvikling.

Videreutviklingen er todelt - for det første er å bygge et REST-API og for det andre kan enten være å utvide JavaFX-app-en med mer funksjonalitet iht. en brukerhistorie eller å lage en ny klient med annen teknologi som bruker REST-API-et, f.eks. mobil-klient for Android eller web-klient med React eller normal HTML, CSS og Javascript. Dere må selv vurdere hvor mye som innsats dere vil legge i kvalitetsforbedring, på bekostning av videreutvikling.


Det er altså HELT GREIT å fortsette med javafx applikasjonen dere allerede har og utvide denne med ny funksjonalitet, samt øke kodekvaliteten. Det kan bli mye arbeid å bytte teknologi, så ta en beslutningen på om dere som gruppe synes det er verdt det. Om dere velger å lage en ny klient eller å jobbe videre med JavaFX vil bli vurdert på samme måte, og med lik vekting. I tillegg vil arbeidsvaner være viktig i dette trinnet, med godt bruk av utviklingsoppgaver (issues) og hensiktsmessig bruk av git-grener. Det er viktig å huske at tidligere kriterier fra trinn 1 og 2 vil også være gjeldende for trinn 3.

**Trinn 3 er tredelt og i hovedsak legges det vekt på:**

- Kvalitet på eksisterende systemer (som REST-API-et deres)
- Ny funksjonalitet på eksisterende system ELLER utvikling av ny klient som matcher tidligere funksjonalitet
- Arbeidsvaner som bruk av Git, strukturering av oppgaver osv.


**Arkitektur:**

- Et såkalt REST-api skal bygges rundt kjernelogikken og tilbys av en web-server
- UI-et skal endres til å bruke dette REST API-et

**Funksjonalitet:**
- Applikasjonen skal utvides med ny funksjonalitet fra trinn 2
    - Hvis dere skriver om klientsiden med ny teknologi, så er det nok å ha samme funksjonalitet som før ombyggingen av klientsiden
- Applikasjonen skal benytte eksisterende REST-API (også om det lages en ny frontend)
- Maven skal brukes for å bygge systemet, men det kan brukes npm for å bygge for eksempel React-delen av applikasjonen
- Det skal skrives tester, uavhengig om man utvider med ny funksjonalitet eller skriver ny klient.
- Kvaliteten på koden skal fortsatt sjekkes av for eksempel CheckStyle og Spotbugs
    - Ved alternativ frontend skal kodekvalitet også sjekkes, ved bruk av f.eks. eslint eller prettier

**Arbeidsvaner:**
- Dere skal bruke utviklingsoppgaver aktivt, og de skal tydelig være utgangspunkt for alt arebid
- Koden dere skriver skal knyttes til utviklingsoppgaver, gjennom for eksempel commit-meldinger og tydelige navn på grener.
- Commit-meldinger skal være beskrivende
- Dere skal benytte merge requests, hvor dere også kan knytte koden deres til issues
- Bruk kodevurdering (code review) for å forbedre hverandres kodekvalitet innad i gruppen

**Dokumentasjon:**
- I tillegg til dokumentasjon i samsvar med tidligere krav skal dere denne gangen lage UML diagram som forelest.
- Et pakkedigram for løsningen
- Et klassediagram for viktigste deler av systemet
- Et sekvensdiagram for et viktig brukstilfelle, som viser koblingen mellom brukerinteraksjon og hva som skjer inni systemet inkl. REST-kall.
- Dokumentasjon av REST-tjenesten, altså (format for) forespørslene som støttes.
dokumentasjon for hver release må plasseres i en egen mappe, så den tredje innleveringsdokumentasjonen må plasseres i gr23nn/docs/release3

I tillegg til overnevnte krav vil alle tidligere krav også være viktige, så se over tidligere kriterier, og bruk dem også!

**Så leverer dere prosjektet på gitlab**
- Prosjektet må være che-klart, så vi kan åpne det i eclipse che fra en lenke i readme i repoet 
- Maven skal brukes til kjøring av applikasjonen, testene og av  kodekvalitet sjekker
- Prosjektet må ha configurasjonen  for å lage shippable produkt  (med jpackage og jlink hvis nødvending)
- Kode for vurdering må leveres i mastergreina av standard repo for gruppen (https://gitlab.stud.idi.ntnu.no/it1901/groups-2023/gr23nn/gr23nn)