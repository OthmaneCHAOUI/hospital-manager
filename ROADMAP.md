# Project Roadmap

Last reviewed: 2026-10-26

This roadmap reflects the current state of the JavaFX/PostgreSQL application.
Completed items indicate that the implementation or documentation exists; they
do not necessarily have automated test coverage.

## Application and UI

### DONE

- [x] Add the JavaFX application entry point in `src/main/Main.java`.
- [x] Add login with doctor/patient role selection.
- [x] Add doctor and patient account forms.
- [x] Add separate doctor and patient dashboards.
- [x] Add FXML views and CSS styles for the current screens.
- [x] Add navigation between login, forms, and dashboards.
- [x] Add screens and controller flows for patient records, appointments,
  medications, and medical tests.
- [x] Translate the visible application and documentation content to English.
- [x] Restore the icon assets required by the FXML views.

### TODO

- [ ] Add other possibility of login for nerses and secreteries with proper permissions
- [ ] Reuse the current window when opening a patient's medical record.
- [ ] Replace console-only success/failure messages with clear UI feedback.
- [ ] Clear validation styles and stale error messages when input becomes valid.
- [ ] Prevent duplicate form submissions and disable controls while a request
  is running.
- [ ] Add confirmation and cancellation behavior for destructive operations.
- [ ] Improve accessibility: keyboard navigation, labels, focus order,
  contrast, and useful screen-reader text.
- [ ] Add empty-state, loading-state, and database-error views to dashboards.
- [ ] Review layout behavior at different window sizes and screen resolutions.
- [ ] Add screenshots or a short user guide to the README.

## Persistence and features

### DONE

- [x] Add Java model classes and DAO classes for doctors, patients,
  doctor-patient records, appointments, medications, and medical tests.
- [x] Add JDBC connection handling through `DB_URL`, `DB_USER`, and
  `DB_PASSWORD`.
- [x] Migrate the declared runtime dependency and schema from MySQL to
  PostgreSQL.
- [x] Add PostgreSQL tables, identity columns, uniqueness constraints,
  check constraints, timestamps, and doctor-owned cascading deletes.
- [x] Add a sample dataset script.
- [x] Use prepared statements for the normal parameterized DAO queries.

### TODO

- [ ] Add complete CRUD behavior for every appointment, medication, and test
  field exposed by the schema.
- [ ] Add filtering, sorting, and searching for large doctor/patient lists.
- [ ] Add appointment editing, cancellation, conflict detection, and clearer
  status transitions.
- [ ] Add medication duration validation and treatment-history views.
- [ ] Add medical-test editing, result history, and patient-facing visibility
  rules.
- [ ] Add import/export of patient medical information, subject to access
  control and privacy requirements.
- [ ] Add account update and password-reset/change flows.
- [ ] Add logout/session cleanup and a consistent current-user/session model.
- [ ] Decide whether notifications, audit history, and multi-doctor support
  are required, then document the decision.

## Tests and verification

### DONE

- [x] Configure Maven to target Java 21.
- [x] Configure JavaFX 21.0.2 and the PostgreSQL JDBC driver.
- [x] Configure the JavaFX Maven plugin to launch `main.Main`.
- [x] Configure JUnit Jupiter and Maven Surefire for future automated tests.
- [x] Document manual checks for startup, database connection,
  authentication, navigation, data operations, connection errors, and invalid
  input.
- [x] Verify that `mvn clean test` completes successfully.
- [x] Confirm that the current build runs zero automated tests.
- [x] Verify that all FXML/controller asset references point to existing files.

### TODO

- [ ] Add unit tests for validation, authentication decisions, model behavior,
  date/time handling, and business rules without starting JavaFX.
- [ ] Add DAO integration tests against a disposable PostgreSQL database.
- [ ] Test successful and failed login for both roles, including invalid input,
  duplicate national IDs, and database-unavailable scenarios.
- [ ] Test create, read, update, and delete flows for every managed record type.
- [ ] Test database constraints, cascading deletes, duplicate appointments,
  invalid statuses, and malformed/incomplete records.
- [ ] Add UI/controller tests or a testable service layer for navigation and
  form error states.
- [ ] Add CI that runs a clean Maven build and all tests on every change.
- [ ] Run a full manual smoke test with a freshly initialized PostgreSQL
  database and record the result.

## Security

### DONE

- [x] Keep local database credentials outside normal source files.
- [x] Allow explicit environment variables to override local configuration.
- [x] Use prepared statements for the normal parameterized DAO queries.
- [x] Add a safe `config.env.example` containing placeholders only.
- [x] Document that real credentials must not be published to the repository.

### TODO

- [ ] Replace plaintext password storage and SQL comparison with a standard,
  salted password-hashing algorithm.
- [ ] Define how existing plaintext accounts are migrated, reset, or disabled.
- [ ] Do not load or retain password fields in general-purpose model objects
  and `SELECT *` queries.
- [ ] Validate and normalize national IDs, email addresses, phone numbers,
  dates, statuses, and free-text lengths before persistence.
- [ ] Review authorization so doctors and patients can only access permitted
  records.
- [ ] Ensure technical database errors do not expose credentials, SQL details,
  or personal medical data.
- [ ] Add a documented secret-management policy for development, CI, and
  packaged deployments.

## Reliability and logic

### DONE

- [x] Use try-with-resources for the existing JDBC connection, statement, and
  result-set handling in the implemented DAO paths.
- [x] Add database uniqueness and check constraints for several core fields.
- [x] Document that the SQL scripts initialize an empty database and are not a
  legacy-data migration.

### TODO

- [ ] Stop swallowing `SQLException` instances and returning `null`, empty
  lists, or success-shaped defaults. Use explicit results or propagate errors
  to a user-facing error handler.
- [ ] Fix and test the patient update SQL statement, which currently contains
  an invalid placeholder in its `SET` clause.
- [ ] Audit every DAO for consistent resource handling, return values,
  transactions, and duplicate/constraint error behavior.
- [ ] Replace `SELECT *` with explicit column lists.
- [ ] Add transactions where one user action writes multiple related records.
- [ ] Move JDBC work off the JavaFX application thread and show loading,
  success, and failure states without freezing the UI.
- [ ] Decide whether `DoctorPatient` is a snapshot/clinical record or a link to
  `Patient`; enforce that decision consistently in the schema and DAOs.
- [ ] Add patient foreign keys for appointments, medications, and medical
  tests, or document and enforce the intentional alternative.
- [ ] Add appropriate indexes and conflict handling for common lookup paths.
- [ ] Make schema initialization safe to run in a controlled, repeatable
  development workflow.

## Structure, build, and operations

### DONE

- [x] Move the project to the standard Maven structure:
  `src/main/java`, `src/main/resources`, and `src/test/java`.
- [x] Configure Maven as the supported build/run/test path.
- [x] Move icons to `src/main/resources/assets/icons`.
- [x] Add `config.env.example` as a safe configuration template.
- [x] Remove the `ROADMAP.md` ignore rule so roadmap changes can be tracked.
- [x] Document the standard source/resource layout.
- [x] Document PostgreSQL setup, environment variables, schema/data
  initialization, compilation, and running the application.

### TODO

- [ ] Replace or update any machine-specific VS Code/manual `javac` workflow
  so it uses the same dependencies as Maven, or remove it.
- [ ] Add versioned database migrations instead of treating `database.sql` as a
  one-time initializer.
- [ ] Document a safe MySQL-to-PostgreSQL data export/import procedure if
  legacy data must be retained.
- [ ] Add dependency/version update checks and resolve the Maven effective-model
  warnings reported during the build.
- [ ] Package installers or documented distribution artifacts for supported
  operating systems.
- [ ] Review `.gitignore` so essential project documentation and reproducible
  development files are not accidentally excluded.

## Issues

| Severity | Issue | Solution if found | Status |
|---|---|---|---|
| **High** | Passwords are stored and compared as plaintext. | No complete solution yet; implement password hashing and an account migration or reset path. | **Open** |
| **High** | DAO errors are often swallowed and converted to `null`, empty lists, or default results. | No complete solution yet; standardize error propagation and user-facing handling. | **Open** |
| **High** | The patient update query contains an invalid placeholder in its `SET` clause. | The defect has been identified but not fixed or tested. | **Open** |
| **High** | Patient-linked records use national-ID strings instead of patient foreign keys. | No final data-model decision or migration has been implemented. | **Open** |
| **Medium** | Database work is performed from controller actions and may freeze the JavaFX UI. | Move persistence work to background tasks/services. | **Open** |
| **Medium** | The project has no automated test sources. | `mvn clean test` succeeds, but reports no tests to run. | **Open** |
| **Medium** | The project previously used a non-standard `src` source root, making test placement ambiguous. | Moved production code/resources to `src/main` and added `src/test/java`; JUnit/Surefire are configured. | **Closed** |
| **Medium** | The schema script is not a versioned or repeatable migration mechanism. | The limitation is documented; migrations remain outstanding. | **Partly** |
| **Medium** | The project has no documented legacy MySQL-to-PostgreSQL data migration process. | The README warns that the current scripts initialize a new database only. | **Partly** |
| **Medium** | FXML views previously referenced missing icon files. | Added the missing icons under `src/assets/icons/` and verified all references. | **Closed** |
| **Low** | New collaborators lacked a safe tracked configuration template. | Added and documented `config.env.example`; the real `config.env` remains ignored. | **Closed** |
| **Low** | `ROADMAP.md` was ignored and could not be tracked normally. | Removed the ignore rule. | **Closed** |
| **Low** | README documentation described a manually managed `lib/` directory although Maven resolves dependencies. | Removed the obsolete `lib/` documentation and identified Maven as the dependency source. | **Closed** |
| **Low** | Maven reports effective-model warnings for JavaFX dependencies. | The build succeeds, but the dependency/plugin configuration still needs review. | **Open** |
