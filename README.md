# Házi feladat specifikáció

Információk [itt](https://viauac00.github.io/laborok/hf)

## Mobil- és webes szoftverek
### 2023.10.22.
### DateNotes
### Galgóczy Gábor - (QS2WJR)
### ggabor2002@gmail.com
### Laborvezető: Kövesdán Gábor

## Bemutatás

Gyakran megesik velem, hogy TODO alkalmazások nélkül egyszerűen nem tudnék mindent fejben tartani. Viszont nem találtam még olyan alkalmazást, amely jól ötvözi ezt az ötletet egy naptárnézettel is. A *DateNotes* nevű alkalmazás éppen erre született.

## Főbb funkciók

Az alkalmazás TODO-k felvételére épül. Ezeket az alkalmazás helyileg tárolja.

#### Egy TODO tartalma:
- Cím
- Leírás (opcionális)
- Állapot (kész / nem kész)
- Végső dátum (opcionális)
- Aktiválási dátum (opcionális) (ha van, akkor az adott dátumig el van rejtve a TODO)
- Emlékeztető dátumok (0 vagy több) (minden dátumhoz tartozhat rövid leírás is)

Az alkalmazásnak két fő nézete van. Egy szokásos lista nézet és egy naptár nézet.

Alapvetően lista nézetben elsőnek a végső dátum nélküli TODO-k jelennek meg, majd utána a többi, végső dátum szerinti sorrendben.

Naptár nézetben az adott napok alatt kerülnek felsorolásra a végső dátummal rendelkező TODO-k.

Amennyiben egy TODO-hoz aktiválási dátum is tartozik, úgy az csak annak leteltétől fog megjelenni.

Emlékeztető dátummal rendelkező TODO-k után értesítést küld az alkalmazás az adott pillanatban. Ha ehhez rövid leírás is tartozik, akkor az bele kerül az értesítés szövegébe. 

#### Nézetek & projektek

Külön kategóriákat - úgynevezett projekteket is létrehozhatunk, amelyekhez TODO-kat lehet rendelni. Az adott nézeten belül kiválasztható, hogy melyik projekthez tartozó TODO-k legyenek éppen megjelenítve. Egy adott projektnek a hozzá tartozó TODO-kon kívül csak neve van, ami szerkeszthető. Projektet törölni is lehet, ekkor viszont a hozzá tartozó TODO-k nem feltétlenül törlődnek. 

Az egyes nézetekben az is beállítható, hogy milyen TODO-k kerüljenek megjelenítésre az áttekinthetőség kedvéért.
Megnézhetjük a már teljesített / nem teljesített / mind a két állapotban lévő elemeket.
Megjeleníthetők az egyébként aktiválási dátum miatt nem látható elemek is.

Egy adott TODO-t kiválasztva minden információ elérhetővé válik róla, és ezeket mind szerkeszteni is lehet.

Ha egy TODO-tól szeretnénk végleg megválni, úgy teljesen törölhető is az.

## Választott technológiák:

- UI
- Lista nézet
- Naptár nézet
- Navigáció
- Perzisztens adattárolás
- Értesítéskezelés

# IMSc Dokumentáció

Az alkalmazás a specifikációban írtakat valósítja meg modern technológiákkal.
- UI kezeléshez Jetpack Compose-t használ.
- Az adatbázis (Room) és a felhasználói felület összekötése MVVM architektúrára épül, és Flow-t illetve LiveData-t használ.
- Az értesítéskezelést az Android Jetpack WorkManager API-ával oldottam meg.
