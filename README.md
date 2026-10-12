# hospital-manager

> Application de bureau de gestion d'un cabinet médical, développée en JavaFX avec une base de données PostgreSQL.

## Aperçu

Le dépôt `hospital-manager` contient une application de bureau destinée à faciliter la gestion des informations médicales et administratives d'un cabinet médical.

L'application propose des interfaces distinctes pour les médecins et les patients. Elle s'appuie sur JavaFX pour l'interface graphique et PostgreSQL pour le stockage des données.

Le projet a été développé dans le cadre d'un projet informatique et met en pratique la programmation orientée objet, la conception d'interfaces graphiques, l'accès aux bases de données relationnelles et l'organisation d'une application Java.

## Fonctionnalités

### Gestion des médecins

- Consultation et gestion des informations des médecins.
- Interfaces dédiées aux fonctionnalités du médecin.
- Accès aux informations des patients suivis.

### Gestion des patients

- Gestion des informations personnelles des patients.
- Connexion à un compte patient.
- Consultation d'un tableau de bord personnel.
- Consultation des médecins associés au patient.

### Suivi médical

- Gestion des rendez-vous médicaux.
- Gestion des médicaments et des traitements.
- Gestion des tests médicaux.
- Accès aux informations de suivi médical depuis les interfaces prévues à cet effet.

### Interface graphique

- Formulaires de connexion et de gestion des utilisateurs.
- Tableaux de bord dédiés aux médecins et aux patients.
- Interfaces FXML et feuilles de style CSS.
- Plusieurs ressources graphiques sont stockées dans `src/resources/` et utilisées par les interfaces.

## Technologies utilisées

| Composant | Technologie |
|---|---|
| Langage | Java |
| Interface graphique | JavaFX |
| Description des interfaces | FXML |
| Styles graphiques | CSS |
| Base de données | PostgreSQL |
| Connectivité SQL | JDBC / PostgreSQL JDBC Driver |
| Gestion de projet et dépendances | Maven |
| Conception des interfaces | Scene Builder |
| Environnement de développement | Visual Studio Code |

## Architecture

L'application suit une organisation inspirée du modèle MVC (*Model-View-Controller*), avec une séparation entre les interfaces, les contrôleurs et les données.

```text
                  Utilisateur
                       |
                       v
                Vue JavaFX / FXML
                       |
                       v
                  Contrôleur
                       |
                       v
                  Modèle / DAO
                       |
                       v
                  JDBC / PostgreSQL
                       |
                       v
                  Base de données
```

- **View (`view/`)** : contient les interfaces FXML.
- **Controller (`controller/`)** : gère les interactions entre l'utilisateur et l'application.
- **Model (`model/`)** : contient les objets métier, les classes d'accès aux données (DAO) et la connexion à la base de données.
- **Style (`style/`)** : contient les feuilles de style CSS.
- **Resources (`resources/`)** : contient les icônes et les images utilisées par l'interface.
- **Utils (`utils/`)** : contient notamment le script SQL de la base de données.

Cette organisation facilite la séparation des responsabilités entre l'interface graphique, le traitement des interactions et la persistance des données.

## Prérequis

Avant d'installer le projet, assurez-vous de disposer des éléments suivants :

- Un JDK 21.
- Maven.
- Un serveur PostgreSQL.
- Visual Studio Code ou un autre environnement compatible avec Java.
- Scene Builder, si vous souhaitez modifier visuellement les interfaces FXML.

Le projet utilise le pilote JDBC PostgreSQL pour établir la connexion entre Java et PostgreSQL. Maven résout les dépendances nécessaires à la compilation et à l'exécution.

## Installation

### 1. Cloner le dépôt

```bash
git clone <URL_DU_DEPOT>
cd hospital-manager
```

Remplacez `<URL_DU_DEPOT>` par l'URL réelle du dépôt Git.

### 2. Vérifier les outils

```bash
java -version
javac -version
mvn -version
psql --version
pg_isready
```

Vérifiez que les versions installées sont compatibles avec celles déclarées dans `pom.xml`.
`pg_isready` vérifie que le serveur PostgreSQL répond. S'il n'est pas démarré sous Linux, démarrez-le avec `sudo systemctl start postgresql`.

### 3. Préparer la base de données

Le script SQL du projet se trouve dans :

```text
src/utils/Database.sql
```

Vous devez créer un rôle PostgreSQL et la base de données avant de lancer l'application. Sur Linux, les commandes suivantes créent le rôle `hospital_app` et demandent son mot de passe, puis créent une base vide dont ce rôle est propriétaire :

```bash
sudo -u postgres createuser --pwprompt hospital_app
sudo -u postgres createdb --owner=hospital_app cabinet_medical
```

Conservez le mot de passe choisi pour `hospital_app`. Ensuite, depuis la racine du projet, créez les tables dans la base. `psql` demandera le mot de passe du rôle :

```bash
psql -h localhost -p 5432 -U hospital_app -d cabinet_medical -W \
  --single-transaction -v ON_ERROR_STOP=1 \
  -f src/utils/Database.sql
```

Ce script initialise les tables d'une base vide. Il ne supprime pas la base, ne crée pas le rôle et ne copie pas les données d'une ancienne base MySQL. Ne le relancez pas sur une base déjà initialisée : les tables existent déjà et la création échouera. Pour migrer les données MySQL existantes, suivez une procédure d'export/import distincte et vérifiez les données avant le basculement.

### 4. Configurer la connexion à PostgreSQL

L'application ne demande pas le nom d'utilisateur ni le mot de passe de PostgreSQL dans une boîte de dialogue. La classe `src/model/DBConnection.java` lit ces valeurs dans les variables d'environnement au démarrage :

| Variable | Valeur par défaut / exigence |
|---|---|
| `DB_URL` | `jdbc:postgresql://localhost:5432/cabinet_medical` |
| `DB_USER` | Obligatoire : rôle PostgreSQL |
| `DB_PASSWORD` | Obligatoire : mot de passe du rôle (peut être vide si votre PostgreSQL l'autorise) |

Définissez les variables dans le même terminal que celui utilisé pour démarrer l'application. L'exemple suivant demande le mot de passe sans l'afficher, puis lance l'application :

```bash
export DB_URL='jdbc:postgresql://localhost:5432/cabinet_medical'
export DB_USER='hospital_app'
read -rsp "PostgreSQL password: " DB_PASSWORD; echo
export DB_PASSWORD
mvn javafx:run
```

Les variables définies de cette façon ne sont disponibles que dans ce terminal et ses processus enfants; redéfinissez-les dans un nouveau terminal. Vous pouvez aussi configurer ces variables dans le profil d'exécution de votre IDE. Ne publiez pas de véritables identifiants dans le dépôt Git.

### 5. Résoudre les dépendances et compiler

À la racine du projet, exécutez :

```bash
mvn clean compile
```

Cette commande nettoie les fichiers générés par Maven puis compile le projet.

Si la compilation échoue, vérifiez la configuration de JavaFX, les dépendances dans `pom.xml` et l'organisation des répertoires sources.

## Configuration

Le fichier `pom.xml` déclare JavaFX 21.0.2, le pilote JDBC PostgreSQL et le plugin Maven JavaFX configuré pour lancer `main.Main`.

Le pilote JDBC est résolu par Maven; il n'est pas nécessaire d'ajouter manuellement un JAR au classpath.

Pour un environnement de développement sous VS Code, les configurations propres à l'éditeur peuvent être placées dans `.vscode/`.

## Utilisation

Après avoir configuré la base de données et compilé le projet :

1. Lancez l'application depuis la classe principale `main.Main`.
2. Accédez à l'interface de connexion.
3. Connectez-vous avec un compte correspondant au rôle souhaité.
4. Utilisez le tableau de bord correspondant pour accéder aux fonctionnalités disponibles.
5. Consultez ou gérez les informations accessibles selon les fonctionnalités implémentées.

Si vous avez déjà défini `DB_USER` et `DB_PASSWORD` dans le terminal conformément à l'étape précédente, lancez l'application avec Maven :

```bash
mvn javafx:run
```

### Accéder aux données PostgreSQL

Vous pouvez utiliser le client en ligne de commande `psql` pour vous connecter à la base :

```bash
psql -h localhost -p 5432 -U hospital_app -d cabinet_medical -W
```

Après avoir saisi le mot de passe, utilisez `\dt` pour lister les tables, puis des requêtes SQL pour consulter les enregistrements :

```sql
\dt
SELECT * FROM patient;
SELECT * FROM doctor;
```

Les noms de tables non entourés de guillemets sont convertis en minuscules par PostgreSQL. pgAdmin est une interface graphique optionnelle pour parcourir le même serveur et les mêmes tables; il n'est pas nécessaire pour utiliser l'application.

## Tests

Le dépôt présenté ne contient pas de répertoire de tests identifié dans son arborescence.

Pour le moment, vérifiez manuellement les principaux scénarios :

- démarrage de l'application ;
- connexion à la base de données ;
- authentification des utilisateurs ;
- navigation entre les interfaces ;
- consultation et modification des données ;
- gestion des erreurs de connexion et des entrées invalides.

L'ajout de tests automatisés permettrait de fiabiliser davantage les opérations d'accès aux données et les principales règles métier.

## Structure du projet

```text
hospital-manager/
├── src/
│   ├── controller/   # Contrôleurs JavaFX
│   ├── main/         # Point d'entrée de l'application
│   ├── model/        # Modèles, DAO et connexion SQL
│   ├── resources/    # Icônes et images
│   ├── style/        # Feuilles de style CSS
│   ├── utils/        # Script SQL
│   └── view/         # Interfaces FXML
├── lib/              # Bibliothèques externes
├── bin/              # Classes compilées (générées)
├── target/           # Fichiers générés par Maven
├── pom.xml            # Configuration Maven
├── LICENSE            # Licence du projet
└── README.md          # Documentation
```

Les répertoires `bin/` et `target/` contiennent des fichiers générés. Les fichiers sources à maintenir se trouvent dans `src/`.

## Pistes d'amélioration

Les évolutions suivantes pourraient être envisagées en fonction de l'état actuel du projet :

- Ajouter un outil de migration de données automatisé depuis MySQL.
- Renforcer la gestion des erreurs et la validation des formulaires.
- Ajouter des tests automatisés pour les DAO et les fonctionnalités métier.
- Améliorer la gestion sécurisée des mots de passe.
- Documenter le schéma relationnel de la base de données.
- Ajouter des captures d'écran des interfaces principales.
- Mettre en place une procédure de compilation et de vérification automatisée avec GitHub Actions.

## Contribuer

Les contributions, signalements de bugs et suggestions sont les bienvenus.

1. Forkez le dépôt.
2. Créez une branche pour vos modifications.
3. Faites et testez vos changements.
4. Validez votre travail avec un message descriptif.
5. Ouvrez une demande de tirage (pull request) en décrivant les modifications.

Si un fichier `CONTRIBUTING.md` est ajouté plus tard, référez-vous à ce fichier pour des directives détaillées de contribution.

## Licence

Consultez le fichier [LICENSE](LICENSE) pour connaître les conditions de réutilisation, de modification et de distribution du projet.
