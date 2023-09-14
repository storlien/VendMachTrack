# Krav til innleveringen:
* Kodingsprosjektet skal ligge i kodelageret på gitlab (det vi har satt opp for dere), slik at det er lett for oss å klone repoet.

* Dokumentasjon for hver release må plasseres i en egen mappe, så den første innleveringsdokumentasjonen må plasseres i ...groups-2023/gr23nn/gr23nn/docs/release1

* En milepæl (milestone) i Gitlab skal referere til dokumentasjonen og utviklingsoppgavene (issues) dere etterhvert legger inn.

* Prosjektet skal være konfigurert til å bygge med Maven, slik at det er lett å importere i VSCode, IntelliJ eller Eclipse og bygge og kjøre det ved å utføre Maven-kommandoer.

* En README.md-fil på rotnivå i kodelageret skal beskrive innholdet, spesielt hvilken mappe inni kodelageret som utgjør kodingsprosjektet.

* En README.md-fil (evt. en fil som README.md lenker til) inni kodingsprosjektet skal beskrive hva appen handler om og er ment å gjøre (når den er mer eller mindre ferdig).

* Ha med et illustrerende skjermbilde, så det er lettere å forstå. Det må også være minst én brukerhistorie for funksjonaliteten dere starter med.

* Det må ligge inne (i gitlab) utviklingsoppgaver (issues) tilsvarende brukerhistorien, hvor hver utviklingsoppgave må være egnet til å utføres som en egen enhet. De som er påbegynt må være tilordnet det gruppemedlemmet som har ansvaret.

* Vi stiller ikke krav om at dere er kommet så langt, men det må i hvert fall være noe i hvert av de tre arkitekturlagene, domenelogikk, brukergrensesnitt (JavaFX-GUI) og persistens (fillagring, men ikke nødvendigvis JSON), slik at appen kan kjøres og vise frem "noe". For at det skal være overkommelig, er det viktig at domenet er veldig enkelt i første omgang. Det er viktigere at det som er kodet er ordentlig gjort. Koden som er sjekket inn bør være knyttet til tilsvarende utviklingsoppgave.


* Maven  skal være konfigurert så en kan kjøre app-en vha. mvn javafx:run 

* Det må finnes minst én test som kan kjøres med Maven  (mvn test) . Bygget skal være rigget til å rapportere testdekningsgrad, som derfor skal være over 0%.

* Prosjektet skal være konfigurert for eclipse che og kan åpnes i  eclipse che med lenke i README.md-fila. (mer info kommer snart)