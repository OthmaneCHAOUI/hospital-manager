# hospital-manager

> Desktop application for managing a medical practice, developed with JavaFX and a PostgreSQL database.

## Overview

The `hospital-manager` repository contains a desktop application designed to simplify the management of a medical practice's medical and administrative information.

The application provides separate interfaces for doctors and patients. It uses JavaFX for the graphical interface and PostgreSQL for data storage.

The project was developed as part of a computer science project and applies object-oriented programming, graphical interface design, relational database access, and Java application organization.

## Features

### Doctor management

- View and manage doctor information.
- Interfaces dedicated to doctor features.
- Access information about monitored patients.

### Patient management

- Manage patients' personal information.
- Log in to a patient account.
- View a personal dashboard.
- View the doctors associated with the patient.

### Medical monitoring

- Manage medical appointments.
- Manage medications and treatments.
- Manage medical tests.
- Access medical monitoring information from the interfaces provided for this purpose.

### Graphical interface

- Login and user management forms.
- Dashboards dedicated to doctors and patients.
- FXML interfaces and CSS stylesheets.
- Several graphical resources are stored in `src/assets/icons/` and used by the interfaces.

## Tech stack

| Component | Technology |
|---|---|
| Language | Java |
| Graphical interface | JavaFX |
| Interface descriptions | FXML |
| Graphical styles | CSS |
| Database | PostgreSQL |
| SQL connectivity | JDBC / PostgreSQL JDBC Driver |
| Project and dependency management | Maven |
| Interface design | Scene Builder |
| Development environment | Visual Studio Code & IntelliJ IDEA |

## Architecture

The application follows an organization inspired by the MVC (*Model-View-Controller*) pattern, with separate interfaces, controllers, and data layers.

```text
                  User
                   |
                   v
            JavaFX / FXML view
                   |
                   v
               Controller
                   |
                   v
                Model / DAO
                   |
                   v
              JDBC / PostgreSQL
                   |
                   v
                Database
```

- **View (`src/main/resources/view/`)**: contains the FXML interfaces.
- **Controller (`src/main/java/controller/`)**: handles interactions between the user and the application.
- **Model (`src/main/java/model/`)**: contains business objects, data access classes (DAO), and the database connection.
- **Style (`src/main/resources/style/`)**: contains CSS stylesheets.
- **Assets (`src/main/resources/assets/`)**: contains the icons and images used by the interface.
- **SQL scripts (`src/main/resources/`)**: initialize the database and load its dataset.

This organization facilitates the separation of responsibilities between the graphical interface, interaction processing, and data persistence.

## Requirements

Before installing the project, make sure you have the following:

- JDK 21.
- Maven.
- A PostgreSQL server.
- Visual Studio Code or another Java-compatible development environment.
- Scene Builder, if you want to visually edit the FXML interfaces.

The project uses the PostgreSQL JDBC driver to connect Java and PostgreSQL. Maven resolves the required dependencies for compilation and execution.

## Installation

### 1. Clone the repository

```bash
git clone <REPOSITORY_URL>
cd hospital-manager
```

Replace `<REPOSITORY_URL>` with the actual Git repository URL.

### 2. Check the tools

```bash
java -version
javac -version
mvn -version
psql --version
pg_isready
```

Check that the installed versions are compatible with those declared in `pom.xml`.
`pg_isready` checks whether the PostgreSQL server is responding. If it is not running on Linux, start it with `sudo systemctl start postgresql`.

### 3. Prepare the database

The project's SQL scripts are located at:

```text
src/main/resources/database/database.sql
src/main/resources/database/dataset.sql
```

You must create a PostgreSQL role and database before starting the application. On Linux, the following commands create the `medical_center` role and prompt for its password, then create an empty `hospital_manager` database owned by that role:

```bash
sudo -u postgres createuser --pwprompt medical_center
sudo -u postgres createdb --owner=medical_center hospital_manager
```

Keep the password chosen for `medical_center`. Then, from the project root, create the tables in the database. `psql` will request the role password:

```bash
psql -h localhost -p 5432 -U medical_center -d hospital_manager -W \
  -v ON_ERROR_STOP=1 \
  -f src/main/resources/database/database.sql
```

Then load the dataset :

```bash
psql -h localhost -p 5432 -U medical_center -d hospital_manager -W \
  -v ON_ERROR_STOP=1 \
  -f src/main/resources/database/dataset.sql
```

This script initializes the tables in an empty database. It does not drop the database, create the role, or copy data from an old MySQL database. Do not run it again on an already initialized database: the tables already exist and creation will fail. To migrate existing MySQL data, follow a separate export/import procedure and verify the data before switching over.

### 4. Configure the PostgreSQL connection

The application does not request the PostgreSQL username or password in a dialog box. The `src/main/java/model/DBConnection.java` class reads these values from `config.env` at startup. Explicit environment variables override values in that file:

| Variable | Default value / requirement |
|---|---|
| `DB_URL` | `jdbc:postgresql://localhost:5432/hospital_manager` |
| `DB_USER` | Required: PostgreSQL role |
| `DB_PASSWORD` | Required: role password (may be empty if your PostgreSQL configuration allows it) |

Create `config.env` in the project root by copying the tracked example:

```bash
cp config.env.example config.env
```

Then edit `config.env` with the credentials for your local PostgreSQL role.
The example file contains placeholders only and is safe to keep in version
control. The real `config.env` is ignored by Git.

Alternatively, define the variables in the same terminal used to start the application. This overrides `config.env`:

```bash
export DB_URL='jdbc:postgresql://localhost:5432/hospital_manager'
export DB_USER='medical_center'
read -rsp "PostgreSQL password: " DB_PASSWORD; echo
export DB_PASSWORD
mvn javafx:run
```

The application looks for `config.env` relative to its working directory. You can also configure the variables in your IDE's run configuration. Do not publish real credentials in the Git repository.

### 5. Resolve dependencies and compile

From the project root, run:

```bash
mvn clean compile
```

This command removes Maven-generated files and then compiles the project.

If compilation fails, check the JavaFX configuration, the dependencies in `pom.xml`, and the source directory layout.

## Configuration

The `pom.xml` file declares JavaFX 21.0.2, the PostgreSQL JDBC driver, and the Maven JavaFX plugin configured to launch `main.Main`.

Maven resolves the JDBC driver; you do not need to add a JAR manually to the classpath.

For a development environment using VS Code, editor-specific configurations can be placed in `.vscode/`.

## Usage

After configuring the database and compiling the project:

1. Start the application from the `main.Main` main class.
2. Open the login interface.
3. Log in with an account matching the desired role.
4. Use the corresponding dashboard to access the available features.
5. View or manage the information available through the implemented features.

If you have already defined `DB_USER` and `DB_PASSWORD` in the terminal according to the previous step, start the application with:

```bash
mvn javafx:run
```

### Accessing PostgreSQL data

You can use the `psql` command-line client to connect to the database:

```bash
psql -h localhost -p 5432 -U medical_center -d hospital_manager -W
```

After entering the password, use `\dt` to list the tables, then run SQL queries to view records:

```sql
\dt
SELECT * FROM patient;
SELECT * FROM doctor;
```

Table names not enclosed in quotation marks are converted to lowercase by PostgreSQL. pgAdmin is an optional graphical interface for browsing the same server and tables; it is not required to use the application.

## Tests

The project has a standard `src/test/java` directory and JUnit Jupiter is
configured in `pom.xml`, but no test classes have been added yet. Running
`mvn clean test` currently compiles the application successfully and reports
that there are no tests to run.

For now, manually check the main scenarios:

- application startup;
- database connection;
- user authentication;
- navigation between interfaces;
- viewing and editing data;
- handling connection errors and invalid input.

Adding automated tests would improve the reliability of data access operations
and the main business rules.

## Project structure

```text
hospital-manager/
├── src/
│   ├── main/java/    # Java source files
│   ├── main/resources/ # FXML, CSS, images, and SQL scripts
│   └── test/java/    # Test source files
├── bin/              # Compiled classes (generated)
├── target/           # Maven-generated files
├── config.env.example # Safe configuration template
├── pom.xml           # Maven configuration
├── ROADMAP.md        # Current status and planned work
├── LICENSE           # Project license
└── README.md         # Documentation
```

The `bin/` and `target/` directories contain generated files. The source files
to maintain are located in `src/`. Maven resolves external libraries from
`pom.xml`; no manually managed `lib/` directory is required.

## Potential improvements

The following changes could be considered depending on the current state of the project:

- Add an automated data migration tool from MySQL.
- Strengthen error handling and form validation.
- Add automated tests for DAOs and business features.
- Improve secure password handling.
- Document the database relational schema.
- Add screenshots of the main interfaces.
- Set up an automated build and verification workflow with GitHub Actions.
- Add packaging for different operating systems.

## Contributing

Contributions, bug reports, and suggestions are welcome.

1. Fork the repository.
2. Create a branch for your changes.
3. Make and test your changes.
4. Commit your work with a descriptive message.
5. Open a pull request describing the changes.

If a `CONTRIBUTING.md` file is added later, refer to it for detailed contribution guidelines.

## License

See the [LICENSE](LICENSE) file for the project's reuse, modification, and distribution terms.
