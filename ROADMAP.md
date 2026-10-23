# Project Roadmap

Last reviewed: 2026-10-23

This file records the current state of the project after reviewing `README.md`,
the Maven build, the Java sources, the FXML/CSS resources, and the PostgreSQL
scripts. “Done” means that the implementation or documentation is present;
it does not necessarily mean that the behavior has automated test coverage.

---

## What has been done

### Application and UI

- [x] Added a JavaFX application entry point in `src/main/Main.java`.
- [x] Added a login screen with doctor/patient role selection.
- [x] Added doctor and patient account forms.
- [x] Added separate doctor and patient dashboards.
- [x] Added FXML views and CSS styles for the current screens.
- [x] Added image and icon resources used by the interface.
- [x] Added navigation between login, forms, and dashboards.
- [x] Added screens and controller flows for patient records, appointments,
  medications, and medical tests.
- [x] Translated the visible application/documentation content to English.

### Persistence and features

- [x] Added Java model classes and DAO classes for doctors, patients,
  doctor-patient records, appointments, medications, and medical tests.
- [x] Added JDBC connection handling through environment variables:
  `DB_URL`, `DB_USER`, and `DB_PASSWORD`.
- [x] Migrated the declared runtime dependency and schema from MySQL to
  PostgreSQL.
- [x] Added PostgreSQL tables, identity columns, uniqueness constraints,
  check constraints, timestamps, and doctor-owned cascading deletes.
- [x] Added a sample dataset script.
- [x] Used prepared statements for the normal parameterized DAO queries,
  reducing direct SQL-injection risk in those queries.

### Build and documentation

- [x] Added a Maven build targeting Java 21.
- [x] Declared JavaFX 21.0.2 and the PostgreSQL JDBC driver in `pom.xml`.
- [x] Configured the JavaFX Maven plugin to launch `main.Main`.
- [x] Documented prerequisites, PostgreSQL setup, environment variables,
  schema/data initialization, compile, run, and manual verification steps in
  `README.md`.
- [x] Verified that `mvn clean test` completes successfully.
- [x] Confirmed that the successful build currently runs zero automated tests.

---

## What is left to do

### Tests and verification

- [ ] Add a real test source layout and configure it explicitly alongside the
  current custom `src` production source root.
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

### Security

- [ ] Replace plaintext password storage and SQL comparison with a standard,
  salted password-hashing algorithm.
- [ ] Define how existing plaintext accounts are migrated, reset, or disabled.
- [ ] Do not load or retain password fields in general-purpose model objects
  and `SELECT *` queries.
- [ ] Validate and normalize national IDs, email addresses, phone numbers,
  dates, statuses, and free-text lengths before persistence.
- [ ] Review authorization: ensure a logged-in doctor or patient can only
  access records they are allowed to view or modify.
- [ ] Ensure technical database errors are logged safely without exposing
  credentials, SQL details, or personal medical data in the UI or console.
- [ ] Remove or verify leftover local/database artifacts such as the ignored
  `lib/` MySQL connector and local configuration files.
- [ ] Add a documented secret-management policy for development, CI, and
  packaged deployments.

### Reliability and logic

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
- [ ] Make the schema initialization safe to run in a controlled, repeatable
  development workflow.

### UI and user experience

- [x] Restore the icon assets required by the FXML views.
- [ ] Reuse the current window when opening a patient’s medical record.
- [ ] Replace console-only success/failure messages with clear UI feedback.
- [ ] Clear validation styles and stale error messages when input becomes
  valid.
- [ ] Prevent duplicate form submissions and disable controls while a request
  is running.
- [ ] Add confirmation and cancellation behavior for destructive operations.
- [ ] Improve accessibility: keyboard navigation, labels, focus order,
  contrast, and useful screen-reader text.
- [ ] Add empty-state, loading-state, and database-error views to dashboards.
- [ ] Review layout behavior at different window sizes and screen resolutions.
- [ ] Add screenshots or a short user guide to the README.

### Features still missing or incomplete

- [ ] Add import/export of patient medical information, subject to access
  control and privacy requirements.
- [ ] Add complete CRUD behavior for every appointment, medication, and test
  field exposed by the schema.
- [ ] Add filtering, sorting, and searching for large doctor/patient lists.
- [ ] Add appointment editing, cancellation, conflict detection, and clearer
  status transitions.
- [ ] Add medication duration validation and treatment-history views.
- [ ] Add medical-test editing, result history, and patient-facing visibility
  rules.
- [ ] Add account update and password-reset/change flows.
- [ ] Add logout/session cleanup and a consistent current-user/session model.
- [ ] Decide whether notifications, audit history, and multi-doctor support
  are required, then document the decision.

### Structure, build, and operations

- [x] move icons to dedicated folder from src/resources to src/assets/icons
- [ ] Make Maven the single supported build/run/test path.
- [ ] Replace or update any machine-specific VS Code/manual `javac` workflow
  so it uses the same dependencies as Maven, or remove it from the project.
- [ ] Consider migrating to the standard Maven layout:
  `src/main/java`, `src/main/resources`, and `src/test/java`; if not, document
  and test the custom source/resource configuration.
- [ ] Add versioned database migrations instead of treating
  `database.sql` as a one-time initializer.
- [ ] Document a safe MySQL-to-PostgreSQL data export/import procedure if
  legacy data must be retained.
- [ ] Add dependency/version update checks and resolve the Maven effective-model
  warnings reported during the build.
- [ ] Package installers or documented distribution artifacts for supported
  operating systems.
- [ ] Review `.gitignore` so essential project documentation and reproducible
  development files are not accidentally excluded.

---

## Issues encountered

| # | Problem | Solution found | Closed |
|---|---|---|---|
| 1 | The application was migrated from MySQL, but the runtime, Maven dependencies, and schema needed to use PostgreSQL consistently. | PostgreSQL JDBC dependency, connection defaults, schema, and README setup instructions were updated. | *Yes* |
| 2 | Database credentials were previously unsuitable for a shared repository when embedded in local configuration. | `DBConnection` now reads `DB_URL`, `DB_USER`, and `DB_PASSWORD` from the environment, and the README warns not to publish credentials. | Partly: storage location addressed; password hashing and secret policy remain |
| 3 | The repository has no automated test sources. | The current build was verified with `mvn clean test`; it passes but reports “No tests to run.” | No |
| 4 | The custom `src` source root does not follow the standard Maven layout and can make test placement ambiguous. | Maven currently compiles the custom layout; a standard layout migration or explicit test configuration is still needed. | No |
| 5 | DAO error handling often catches SQL errors, prints a stack trace, and returns `null`, an empty list, or `0`. | No complete solution yet; errors need a consistent propagation/result strategy and UI handling. | No |
| 6 | Passwords are stored and compared as plaintext in doctor and patient authentication. | No solution implemented yet; hashing and an existing-account migration/reset plan are required. | No |
| 7 | The patient update query contains an invalid placeholder in its SQL `SET` clause, so that update path is expected to fail at runtime. | The defect is identified, but it has not been fixed or covered by a test. | No |
| 8 | Patient-linked medical records use national-ID strings instead of foreign keys to `Patient`, allowing orphaned or inconsistent references. | No final data-model decision or migration has been implemented. | No |
| 9 | Database work is performed from controller actions, risking a frozen JavaFX UI during slow queries. | No solution implemented yet; move persistence calls to JavaFX background tasks/services. | No |
| 10 | The README documents Maven, while ignored/local VS Code or `lib/` workflows may use different classpaths and a leftover MySQL connector. | Maven is the verified path; the alternate workflow and leftover artifact still need cleanup or explicit deprecation. | Partly |
| 11 | The schema script is an initialization script and is not a versioned, repeatable migration mechanism. | The limitation is documented in the README; migrations and legacy-data import remain outstanding. | Partly |
| 12 | The project was translated and the UI was updated, but the current state has no automated regression coverage for those changes. | The implementation is present; regression tests and a manual smoke-test record are still needed. | Partly |
| 13 | Maven emits warnings while building the effective model for JavaFX dependencies. | The build still succeeds, but the dependency/plugin configuration should be reviewed and the warnings resolved. | No |
| 14 | FXML views referenced icon files that were not present in the assets directory. | Added `3669476_add_circle_ic_icon.png` and `9110784_circle_x_icon.png` under `src/assets/icons/` and verified all referenced asset paths exist. | Yes |
| 15 | New collaborators had no safe, tracked configuration template while real `config.env` contains local credentials. | Added and documented `config.env.example`; the real `config.env` remains ignored. | Yes |
| 16 | `ROADMAP.md` was ignored, so roadmap changes were not available to collaborators through normal Git tracking. | Removed the `ROADMAP.md` ignore rule. | Yes |
| 17 | README documentation still described a manually managed `lib/` directory even though Maven resolves the declared dependencies. | Removed `lib/` from the documented project structure and identified Maven as the dependency source. | Yes |

---

## Recommended implementation order

1. Fix the patient update SQL and add focused DAO/error-path tests.
2. Establish the test layout and a disposable PostgreSQL integration database.
3. Implement password hashing, authorization checks, and a safe account migration
   or reset path.
4. Standardize DAO error handling and move database operations off the UI thread.
5. Decide and enforce the patient/doctor data relationships.
6. Make Maven/CI/migrations reproducible before adding larger features.
7. Improve UI feedback and then implement the remaining feature requests.
