# Vending Machine Tracker REST API

Base-filsti: `vendmachtrack`

---

### Sjekk om server er tilgjengelig

GET `/` (uten base-filsti)

Response body: "Running"

Response code: OK (200)

**Eksempel på respons:**
```
GET http://localhost:8080/health
```

```json
Running
```
---

### Hent alle brusautomater

GET `/`

Response body: En liste over brusautomater med deres ID-er og plasseringer.

Response code: OK (200)

**Eksempel på respons:**
```
GET http://localhost:8080/vendmachtrack 
```

```json
{
    "1": "Trondheim",
    "2": "Oslo",
    "3": "Bergen",
    "4": "Stavanger",
    "5": "Tromsø"
}
```
---

### Hent brusautomatens plassering

GET `/{id}/name`

Parametere:
- `id` (heltall): ID-en til brusautomaten.

Response body: Plassering av brusautomaten.

Response code: OK (200)

**Eksempel på respons:**
```
GET http://localhost:8080/vendmachtrack/1/name
```

```json
Trondheim
```
---

### Hent brusautomatens varebeholdning

GET `/{id}`

Parametere:
- `id` (heltall): ID-en til brusautomaten.

Response body: Varer og deres antall.

Response code: OK (200)

**Eksempel på respons:**
```
GET http://localhost:8080/vendmachtrack/3
```

```json
{
    "Pepsi": 1,
    "Cola": 7,
    "Fanta": 7,
    "Hansa": 92,
    "Sprite": 3
}
```
---

### Legg til varer i brusautomat

PUT `/{id}/add`

Parametere:
- `id` (heltall): ID-en til brusautomaten.

Spørringsparametere:
- `item` (tekst): Navnet på varen.
- `quantity` (heltall): Mengden av varen som skal legges til.

Response body: Oppdatert varebeholdning.

Response code: OK (200)

**Eksempel på respons:**
```
PUT http://localhost:8080/vendmachtrack/3/add?item=Paraply&quantity=2
```

```json
{
    "Paraply": 2,
    "Pepsi": 1,
    "Cola": 7,
    "Fanta": 7,
    "Hansa": 92,
    "Sprite": 3
}
```
---

### Fjern varer fra varebeholdning

PUT `/{id}/remove`

Parametere:
- `id` (heltall): ID-en til brusautomaten.

Spørringsparametere:
- `item` (tekst): Navnet på varen.
- `quantity` (heltall): Antall av varen som skal fjernes.

Response body: Oppdatert varebeholdning.

Response code: OK (200)

**Eksempel på respons:**
```
GET http://localhost:8080/vendmachtrack/3/remove?item=Paraply&quantity=1
```

```json
{
    "Paraply": 1,
    "Pepsi": 1,
    "Cola": 7,
    "Fanta": 7,
    "Hansa": 92,
    "Sprite": 3
}
```
---

### Legg til en ny brusautomat

POST `/add`

Spørringsparametere:
- `id` (heltall): ID-en til den nye brusautomaten.
- `location` (tekst): Plasseringen av den nye brusautomaten.

Response body: Oppdatert liste over brusautomater med deres ID-er og plasseringer.

Response code: OK (200)

**Eksempel på respons:**
```
POST http://localhost:8080/vendmachtrack/add?id=6&location=Bodø
```

```json
{
    "1": "Trondheim",
    "2": "Oslo",
    "3": "Bergen",
    "4": "Stavanger",
    "5": "Tromsø",
    "6": "Bodø"
}
```
---

### Fjern en brusautomat

DELETE `/{id}`

Parametere:
- `id` (heltall): ID-en til brusautomaten som skal fjernes.

Response body: Oppdatert liste over brusautomater med deres ID-er og plasseringer.

Response code: OK (200)

**Eksempel på respons:**
```
DELETE http://localhost:8080/vendmachtrack/6
```

```json
{
    "1": "Trondheim",
    "2": "Oslo",
    "3": "Bergen",
    "4": "Stavanger",
    "5": "Tromsø"
}
```
---

### Endre plassering på brusautomat

PUT `/{id}`

Parametere:
- `id` (heltall): ID-en til brusautomaten.

Spørringsparametere:
- `location` (tekst): Den nye plasseringen.

Response body: Oppdatert liste over brusautomater med deres ID-er og plasseringer.

Response code: OK (200)

**Eksempel på respons:**
```
PUT http://localhost:8080/vendmachtrack/1?location="Throndhjem"
```

```json
{
    "1": "Throndhjem",
    "2": "Oslo",
    "3": "Bergen",
    "4": "Stavanger",
    "5": "Tromsø"
}
```