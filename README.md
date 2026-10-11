# hospital-manager

> Application de bureau de gestion d'un cabinet médical, développée en JavaFX avec une base de données MySQL.

## Aperçu

Le dépôt `hospital-manager` contient une application de bureau destinée à faciliter la gestion des informations médicales et administratives d'un cabinet médical.

L'application propose des interfaces distinctes pour les médecins et les patients. Elle s'appuie sur JavaFX pour l'interface graphique et MySQL pour le stockage des données.

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
| Base de données | MySQL |
| Connectivité SQL | JDBC / MySQL Connector/J |
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
                  JDBC / MySQL
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
- Un serveur MySQL.
- Visual Studio Code ou un autre environnement compatible avec Java.
- Scene Builder, si vous souhaitez modifier visuellement les interfaces FXML.

Le projet utilise MySQL Connector/J pour établir la connexion entre Java et MySQL. Maven doit être configuré pour résoudre les dépendances nécessaires à la compilation et à l'exécution.

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
mysql --version
```

Vérifiez que les versions installées sont compatibles avec celles déclarées dans `pom.xml`.

### 3. Préparer la base de données

Le script SQL du projet se trouve dans :

```text
src/utils/Database.sql
```

Le script crée le schéma `cabinet_medical`. **Attention : il commence par supprimer ce schéma s'il existe déjà, ce qui efface toutes ses données.** Sauvegardez vos données et vérifiez que vous pouvez remplacer ce schéma avant d'exécuter le script.

Après avoir vérifié que cette suppression est sans risque, exécutez le script avec un compte MySQL autorisé :

```bash
mysql -u <UTILISATEUR_MYSQL> -p < src/utils/Database.sql
```

### 4. Configurer la connexion à MySQL

La configuration de la connexion se trouve dans :

```text
src/model/DBConnection.java
```

Vérifiez notamment :

- l'adresse du serveur MySQL ;
- le port utilisé ;
- le nom de la base de données ;
- le nom d'utilisateur ;
- le mot de passe.

Utilisez vos propres paramètres de connexion. Ne publiez pas de véritables identifiants dans le dépôt Git.

### 5. Résoudre les dépendances et compiler

À la racine du projet, exécutez :

```bash
mvn clean compile
```

Cette commande nettoie les fichiers générés par Maven puis compile le projet.

Si la compilation échoue, vérifiez la configuration de JavaFX, les dépendances dans `pom.xml` et l'organisation des répertoires sources.

## Configuration

Le fichier `pom.xml` déclare JavaFX 21.0.2, MySQL Connector/J 9.3.0 et le plugin Maven JavaFX configuré pour lancer `main.Main`.

Le pilote JDBC est ainsi résolu par Maven. Une copie locale du pilote est également présente dans :

```text
lib/mysql-connector-j-9.3.0.jar
```

La commande Maven ci-dessous utilise la dépendance déclarée dans `pom.xml`; il n'est pas nécessaire d'ajouter manuellement le JAR au classpath pour ce mode d'exécution.

Pour un environnement de développement sous VS Code, les configurations propres à l'éditeur peuvent être placées dans `.vscode/`.

## Utilisation

Après avoir configuré la base de données et compilé le projet :

1. Lancez l'application depuis la classe principale `main.Main`.
2. Accédez à l'interface de connexion.
3. Connectez-vous avec un compte correspondant au rôle souhaité.
4. Utilisez le tableau de bord correspondant pour accéder aux fonctionnalités disponibles.
5. Consultez ou gérez les informations accessibles selon les fonctionnalités implémentées.

Pour lancer l'application avec Maven :

```bash
mvn javafx:run
```

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

- Externaliser les paramètres de connexion à la base de données.
- Renforcer la gestion des erreurs et la validation des formulaires.
- Ajouter des tests automatisés pour les DAO et les fonctionnalités métier.
- Améliorer la gestion sécurisée des mots de passe.
- Documenter le schéma relationnel de la base de données.
- Ajouter des captures d'écran des interfaces principales.
- Mettre en place une procédure de compilation et de vérification automatisée avec GitHub Actions.

## Contributing

Contributions, bug reports, and suggestions are welcome.

1. Fork the repository.
2. Create a branch for your changes.
3. Make and test your changes.
4. Commit your work with a descriptive message.
5. Open a pull request describing the changes.

If a `CONTRIBUTING.md` file is added later, refer to it here for detailed contribution guidelines.

## Licence

Consultez le fichier [LICENSE](LICENSE) pour connaître les conditions de réutilisation, de modification et de distribution du projet.
