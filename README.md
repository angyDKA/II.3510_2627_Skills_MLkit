# SwapCampus Mobile ML

Démonstration Android du sujet 11 : suggestion de catégorie depuis une photo et lecture de code-barres. Base du binôme : https://github.com/angyDKA/SwapCampus_MLKit

## Ouvrir et lancer

Ouvrir ce dossier dans Android Studio, attendre la synchronisation Gradle, puis lancer le module `app` sur un appareil Android. Installer le SDK de compilation Android 37.0 si demandé. L’émulateur de vérification utilise Android API 36 ; il n’a pas besoin d’avoir la même version que le SDK de compilation.

Le module `shared` déclare des cibles Android et iOS. Cette démonstration fournit seulement une interface Android.

## Comptes de test

- Alice : `alice@isep.fr` / `alice123`
- Bob : `bob@isep.fr` / `bob123`
- Administration : `admin@isep.fr` / `admin123`

Utiliser Alice ou Bob pour publier une annonce. Les identifiants sont fictifs.

## Démonstration

1. Se connecter, ouvrir My Listings, puis Post a listing.
2. Suggest category from photo choisit une image de l’objet et présélectionne une catégorie si la correspondance est assez fiable.
3. Scan barcode from photo choisit une autre image, contenant un code. La photo de l’objet n’est pas remplacée.
4. Vérifier le champ Barcode / ISBN, cliquer sur Add to description, puis remplir l’annonce et Save.

Les images d’essai sont dans `demo/` : `ISBN_demo.png` doit donner `9780134685991`, `Sans_code.png` ne contient aucun code. Les copier dans la galerie de l’appareil avant la démonstration. Le parcours complet avec le sélecteur de photos est à rejouer sur l’appareil de l’oral.

Formats configurés : EAN-13, QR Code, Code 128. Le scan lit une valeur ; il n’effectue pas de recherche de titre ou de prix et n’utilise pas la caméra en continu. Les annonces restent en mémoire.

## Organisation

- `app/src/main/java/com/isep/swapcampus/ListingFormScreen.kt` : formulaire Compose et ViewModel.
- `app/src/main/java/com/isep/swapcampus/ml/ImageLabelerHelper.kt` : appel ML Kit pour les étiquettes d’image.
- `app/src/main/java/com/isep/swapcampus/ml/BarcodeHelper.kt` : appel ML Kit pour les codes.
- `shared/src/commonMain/kotlin/com/isep/shared/domain/CategorySuggester.kt` : correspondance entre étiquettes et catégories.
- `shared/src/commonMain/kotlin/com/isep/shared/domain/BarcodeText.kt` : ajout de la référence à la description sans doublon.

Les bibliothèques ML Kit sont embarquées dans l’APK. Les appels au détecteur restent dans le module Android.

## Vérifications

Compilation de l’APK et sept tests métier ajoutés : réussis. Deux tests instrumentés du vrai détecteur sur Android API 36 : réussis (EAN-13 et image sans code). Ces tests ne couvrent pas tout le parcours utilisateur.

```sh
./gradlew :app:assembleDebug :shared:testAndroidHostTest :app:testDebugUnitTest
./gradlew :app:connectedDebugAndroidTest
```

La deuxième commande demande un appareil ou émulateur connecté. Java peut être fourni par Android Studio. `local.properties` est propre à chaque ordinateur et n’est pas inclus. La clé `debug.keystore` sert uniquement aux builds de test.
