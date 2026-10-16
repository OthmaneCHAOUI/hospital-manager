
BEGIN;

-- =========================================================
-- 1. DOCTORS
-- Test accounts: doctor1 / doctor2 / doctor3
-- =========================================================

INSERT INTO doctor
    (id, name, firstName, nationalId, specialty, clinic_name,
     phone, clinic_address, password)
VALUES
    (1, 'Martin', 'Sophie', 'DOC-TEST-001', 'Cardiologie',
     'Cabinet du Coeur', '0612345001',
     '12 rue Victor Hugo, Paris', 'doctor1'),

    (2, 'Bernard', 'Lucas', 'DOC-TEST-002', 'Dermatologie',
     'Dermato Plus', '0612345002',
     '8 avenue de la Republique, Lyon', 'doctor2'),

    (3, 'Petit', 'Amine', 'DOC-TEST-003', 'Medecine generale',
     'Centre Medical Central', '0612345003',
     '25 rue Nationale, Lille', 'doctor3');


-- =========================================================
-- 2. PATIENT ACCOUNTS
-- Test accounts: patient1 through patient6
-- =========================================================

INSERT INTO patient
    (id, name, firstName, nationalId, password, birth_date,
     city, phone, address, gender, account_created, email)
VALUES
    (1, 'Dupont', 'Emma', 'PAT-TEST-001', 'patient1',
     '1995-04-12', 'Paris', '0620000001',
     '4 rue des Lilas, Paris', 'F', TRUE, 'emma.test@example.com'),

    (2, 'Leroy', 'Hugo', 'PAT-TEST-002', 'patient2',
     '1982-11-03', 'Lyon', '0620000002',
     '7 rue Pasteur, Lyon', 'M', TRUE, 'hugo.test@example.com'),

    (3, 'Moreau', 'Lea', 'PAT-TEST-003', 'patient3',
     '2001-07-21', 'Lille', '0620000003',
     '19 rue Solferino, Lille', 'F', TRUE, 'lea.test@example.com'),

    (4, 'Garcia', 'Adam', 'PAT-TEST-004', 'patient4',
     '1970-02-15', 'Paris', '0620000004',
     '3 avenue Jean Jaures, Paris', 'M', TRUE, 'adam.test@example.com'),

    (5, 'Roux', 'Ines', 'PAT-TEST-005', 'patient5',
     '2010-09-08', 'Lyon', '0620000005',
     '22 rue Victor Hugo, Lyon', 'F', TRUE, 'ines.test@example.com'),

    (6, 'Fournier', 'Noah', 'PAT-TEST-006', 'patient6',
     '1988-12-30', 'Lille', '0620000006',
     '15 rue Nationale, Lille', 'M', TRUE, 'noah.test@example.com');


-- =========================================================
-- 3. DOCTOR-PATIENT ASSOCIATIONS
-- Each doctor has their own patient records.
-- Patients 1 and 2 are followed by multiple doctors.
-- =========================================================

INSERT INTO DoctorPatient
    (id_doctor, name, firstName, nationalId, birth_date,
     city, phone, address, gender, note)
VALUES
    (1, 'Dupont', 'Emma', 'PAT-TEST-001', '1995-04-12',
     'Paris', '0620000001', '4 rue des Lilas, Paris', 'F',
     'Suivi cardiologique regulier.'),

    (1, 'Leroy', 'Hugo', 'PAT-TEST-002', '1982-11-03',
     'Lyon', '0620000002', '7 rue Pasteur, Lyon', 'M',
     'Controle de la tension arterielle.'),

    (1, 'Garcia', 'Adam', 'PAT-TEST-004', '1970-02-15',
     'Paris', '0620000004', '3 avenue Jean Jaures, Paris', 'M',
     'Surveillance cardiovasculaire.'),

    (2, 'Dupont', 'Emma', 'PAT-TEST-001', '1995-04-12',
     'Paris', '0620000001', '4 rue des Lilas, Paris', 'F',
     'Consultation dermatologique.'),

    (2, 'Moreau', 'Lea', 'PAT-TEST-003', '2001-07-21',
     'Lille', '0620000003', '19 rue Solferino, Lille', 'F',
     'Suivi cutane.'),

    (2, 'Fournier', 'Ines', 'PAT-TEST-005', '2010-09-08',
     'Lyon', '0620000005', '22 rue Victor Hugo, Lyon', 'F',
     'Consultation de controle.'),

    (3, 'Leroy', 'Hugo', 'PAT-TEST-002', '1982-11-03',
     'Lyon', '0620000002', '7 rue Pasteur, Lyon', 'M',
     'Suivi medical general.'),

    (3, 'Moreau', 'Lea', 'PAT-TEST-003', '2001-07-21',
     'Lille', '0620000003', '19 rue Solferino, Lille', 'F',
     'Bilan de sante annuel.'),

    (3, 'Fournier', 'Noah', 'PAT-TEST-006', '1988-12-30',
     'Lille', '0620000006', '15 rue Nationale, Lille', 'M',
     'General consultation.');


-- =========================================================
-- 4. APPOINTMENTS
-- Includes past and future dates, different times and statuses.
-- =========================================================

INSERT INTO Appointment
    (id_doctor, patient_national_id, appointment_date, time, reason, status)
VALUES
    (1, 'PAT-TEST-001', '2026-10-20', '09:00',
     'Controle cardiologique', 'upcoming'),

    (1, 'PAT-TEST-002', '2026-10-21', '10:30',
     'Controle de tension', 'upcoming'),

    (1, 'PAT-TEST-004', '2026-10-22', '14:00',
     'Bilan cardiovasculaire', 'upcoming'),

    (1, 'PAT-TEST-001', '2026-10-05', '09:00',
     'Follow-up consultation', 'present'),

    (2, 'PAT-TEST-003', '2026-10-19', '11:00',
     'Examen dermatologique', 'upcoming'),

    (2, 'PAT-TEST-005', '2026-10-23', '15:30',
     'Controle dermatologique', 'upcoming'),

    (2, 'PAT-TEST-001', '2026-10-07', '16:00',
     'Skin evaluation', 'absent'),

    (3, 'PAT-TEST-002', '2026-10-24', '08:30',
     'General consultation', 'upcoming'),

    (3, 'PAT-TEST-003', '2026-10-25', '13:00',
     'Bilan annuel', 'upcoming'),

    (3, 'PAT-TEST-006', '2026-10-06', '10:00',
     'General consultation', 'present'),

    (3, 'PAT-TEST-006', '2026-10-27', '10:00',
     'Follow-up consultation', 'upcoming'),

    (1, 'PAT-TEST-004', '2026-10-28', '11:30',
     'Suivi cardiovasculaire', 'upcoming');


-- =========================================================
-- 5. MEDICATIONS
-- Period is assumed to be the number of days.
-- =========================================================

INSERT INTO Medication
    (id_doctor, patient_national_id, name, start_date, duration, description)
VALUES
    (1, 'PAT-TEST-001', 'Traitement A', '2026-10-01', 30,
     'Traitement de test pour le suivi cardiologique.'),

    (1, 'PAT-TEST-002', 'Traitement B', '2026-10-10', 14,
     'Traitement de test avec duree de deux semaines.'),

    (1, 'PAT-TEST-004', 'Traitement C', '2026-09-20', 60,
     'Traitement de test pour le suivi cardiovasculaire.'),

    (2, 'PAT-TEST-001', 'Creme de test', '2026-10-08', 14,
     'Application locale fictive.'),

    (2, 'PAT-TEST-003', 'Traitement D', '2026-10-12', 7,
     'Traitement dermatologique fictif.'),

    (2, 'PAT-TEST-005', 'Creme de controle', '2026-10-15', 10,
     'Traitement fictif de courte duree.'),

    (3, 'PAT-TEST-002', 'Traitement E', '2026-10-05', 30,
     'Traitement general de test.'),

    (3, 'PAT-TEST-003', 'Traitement F', '2026-10-11', 5,
     'Traitement fictif de courte duree.'),

    (3, 'PAT-TEST-006', 'Traitement G', '2026-10-06', 20,
     'Traitement general fictif.'),

    (1, 'PAT-TEST-001', 'Traitement H', '2026-10-14', 7,
     'Deuxieme Medication fictif pour le meme patient.');


-- =========================================================
-- 6. MEDICAL TESTS
-- Status values must be 'pending' or 'completed'.
-- doctor_type must be 'internal' or 'external'.
-- =========================================================

INSERT INTO MedicalTest
    (id_doctor, patient_national_id, test_type, result,
     test_date, doctor_type, status)
VALUES
    (1, 'PAT-TEST-001', 'Electrocardiogramme',
     'Resultat fictif : normal.', '2026-10-05', 'internal', 'completed'),

    (1, 'PAT-TEST-002', 'Bilan sanguin',
     'Resultat fictif : controle recommande.', '2026-10-10',
     'external', 'completed'),

    (1, 'PAT-TEST-004', 'Echocardiographie',
     NULL, '2026-10-22', 'external', 'pending'),

    (2, 'PAT-TEST-003', 'Analyse cutanee',
     'Fictional result: no abnormality detected.', '2026-10-12',
     'internal', 'completed'),

    (2, 'PAT-TEST-005', 'Test allergologique',
     NULL, '2026-10-23', 'external', 'pending'),

    (2, 'PAT-TEST-001', 'Examen dermatologique',
     NULL, '2026-10-20', 'internal', 'pending'),

    (3, 'PAT-TEST-002', 'Bilan metabolique',
     'Resultat fictif : valeurs a surveiller.', '2026-10-06',
     'external', 'completed'),

    (3, 'PAT-TEST-006', 'Bilan sanguin',
     NULL, '2026-10-27', 'internal', 'pending');


-- =========================================================
-- 7. SYNCHRONIZE IDENTITY SEQUENCES
-- Necessary because doctor and patient IDs were explicit.
-- =========================================================

SELECT setval(
    pg_get_serial_sequence('doctor', 'id'),
    (SELECT MAX(id) FROM doctor)
);

SELECT setval(
    pg_get_serial_sequence('patient', 'id'),
    (SELECT MAX(id) FROM patient)
);

COMMIT;
