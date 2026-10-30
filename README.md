# Heart Indicator + PlayerPlace (Fabric 1.21.4)

Jeden mod kliencki łączący:

- **Heart Indicator 2.2.0** by spunkyinsaan (MIT) – gotowy jar w `libs/`, dołączany przy buildzie,
- **PlayerPlace** by Adixu_YT – kod w `src/`. Działa **zawsze**: bez klawisza, bez przełącznika, bez wpisu w GUI.

## PlayerPlace
Gdy celownik jest na innym graczu i klikniesz PPM, trafienie jest na chwilę podmieniane na blok za graczem.
Zasady serwera działają bez zmian, nie zmienia hitboxów ani zasięgu.

## Build
Wymagane: Java 21.

```bash
./gradlew build
```

Gotowy mod: `build/libs/heartindicator-playerplace-2.2.0.jar`
(plik z `-unmerged` to półprodukt – nie używaj go).

Wrzuć do `.minecraft/mods` razem z Fabric API dla 1.21.4.

## Licencje
Heart Indicator – MIT (`LICENSE_heartindicator` jest w jarze).
