
BEGIN;

-- =========================================================
-- 1. DOCTORS
-- Test accounts: doctor1 / doctor2 / doctor3
-- =========================================================

INSERT INTO doctor
    (id, name, firstName, nationalId, specialty, clinic_name,
     phone, clinic_address, password)
VALUES
    (1, 'Smith', 'Olivia', 'DOC-TEST-001', 'Cardiology',
     'Heart Care Clinic', '0612345001',
     '12 Victoria Street, London', 'doctor1'),

    (2, 'Brown', 'Lucas', 'DOC-TEST-002', 'Dermatology',
     'Derm Plus Clinic', '0612345002',
     '8 Republic Avenue, Manchester', 'doctor2'),

    (3, 'Taylor', 'Adam', 'DOC-TEST-003', 'General Medicine',
     'Central Medical Center', '0612345003',
     '25 National Road, Birmingham', 'doctor3');


-- =========================================================
-- 2. PATIENT ACCOUNTS
-- Test accounts: patient1 through patient6
-- =========================================================

INSERT INTO patient
    (id, name, firstName, nationalId, password, birth_date,
     city, phone, address, gender, account_created, email)
VALUES
    (1, 'Wilson', 'Emma', 'PAT-TEST-001', 'patient1',
     '1995-04-12', 'London', '0620000001',
     '4 Lily Street, London', 'F', TRUE, 'emma.test@example.com'),

    (2, 'Johnson', 'Henry', 'PAT-TEST-002', 'patient2',
     '1982-11-03', 'Manchester', '0620000002',
     '7 Pasteur Street, Manchester', 'M', TRUE, 'henry.test@example.com'),

    (3, 'Davis', 'Lucy', 'PAT-TEST-003', 'patient3',
     '2001-07-21', 'Birmingham', '0620000003',
     '19 Solferino Street, Birmingham', 'F', TRUE, 'lucy.test@example.com'),

    (4, 'Miller', 'James', 'PAT-TEST-004', 'patient4',
     '1970-02-15', 'London', '0620000004',
     '3 James Avenue, London', 'M', TRUE, 'james.test@example.com'),

    (5, 'Thompson', 'Grace', 'PAT-TEST-005', 'patient5',
     '2010-09-08', 'Manchester', '0620000005',
     '22 Victoria Street, Manchester', 'F', TRUE, 'grace.test@example.com'),

    (6, 'Anderson', 'Noah', 'PAT-TEST-006', 'patient6',
     '1988-12-30', 'Birmingham', '0620000006',
     '15 National Road, Birmingham', 'M', TRUE, 'noah.test@example.com');


-- =========================================================
-- 3. DOCTOR-PATIENT ASSOCIATIONS
-- Each doctor has their own patient records.
-- Patients 1 and 2 are followed by multiple doctors.
-- =========================================================

INSERT INTO DoctorPatient
    (id_doctor, name, firstName, nationalId, birth_date,
     city, phone, address, gender, note)
VALUES
    (1, 'Wilson', 'Emma', 'PAT-TEST-001', '1995-04-12',
     'London', '0620000001', '4 Lily Street, London', 'F',
     'Regular cardiology follow-up.'),

    (1, 'Johnson', 'Henry', 'PAT-TEST-002', '1982-11-03',
     'Manchester', '0620000002', '7 Pasteur Street, Manchester', 'M',
     'Blood pressure check.'),

    (1, 'Miller', 'James', 'PAT-TEST-004', '1970-02-15',
     'London', '0620000004', '3 James Avenue, London', 'M',
     'Cardiovascular monitoring.'),

    (2, 'Wilson', 'Emma', 'PAT-TEST-001', '1995-04-12',
     'London', '0620000001', '4 Lily Street, London', 'F',
     'Dermatology consultation.'),

    (2, 'Davis', 'Lucy', 'PAT-TEST-003', '2001-07-21',
     'Birmingham', '0620000003', '19 Solferino Street, Birmingham', 'F',
     'Skin follow-up.'),

    (2, 'Thompson', 'Grace', 'PAT-TEST-005', '2010-09-08',
     'Manchester', '0620000005', '22 Victoria Street, Manchester', 'F',
     'Follow-up consultation.'),

    (3, 'Johnson', 'Henry', 'PAT-TEST-002', '1982-11-03',
     'Manchester', '0620000002', '7 Pasteur Street, Manchester', 'M',
     'General medical follow-up.'),

    (3, 'Davis', 'Lucy', 'PAT-TEST-003', '2001-07-21',
     'Birmingham', '0620000003', '19 Solferino Street, Birmingham', 'F',
     'Annual health check.'),

    (3, 'Anderson', 'Noah', 'PAT-TEST-006', '1988-12-30',
     'Birmingham', '0620000006', '15 National Road, Birmingham', 'M',
     'General consultation.');


-- =========================================================
-- 4. APPOINTMENTS
-- Includes past and future dates, different times and statuses.
-- =========================================================

INSERT INTO Appointment
    (id_doctor, patient_national_id, appointment_date, time, reason, status)
VALUES
    (1, 'PAT-TEST-001', '2026-10-20', '09:00',
     'Cardiology check-up', 'upcoming'),

    (1, 'PAT-TEST-002', '2026-10-21', '10:30',
     'Blood pressure check', 'upcoming'),

    (1, 'PAT-TEST-004', '2026-10-22', '14:00',
     'Cardiovascular assessment', 'upcoming'),

    (1, 'PAT-TEST-001', '2026-10-05', '09:00',
     'Follow-up consultation', 'present'),

    (2, 'PAT-TEST-003', '2026-10-19', '11:00',
     'Dermatology examination', 'upcoming'),

    (2, 'PAT-TEST-005', '2026-10-23', '15:30',
     'Dermatology check-up', 'upcoming'),

    (2, 'PAT-TEST-001', '2026-10-07', '16:00',
     'Skin evaluation', 'absent'),

    (3, 'PAT-TEST-002', '2026-10-24', '08:30',
     'General consultation', 'upcoming'),

    (3, 'PAT-TEST-003', '2026-10-25', '13:00',
     'Annual health check', 'upcoming'),

    (3, 'PAT-TEST-006', '2026-10-06', '10:00',
     'General consultation', 'present'),

    (3, 'PAT-TEST-006', '2026-10-27', '10:00',
     'Cardiovascular follow-up', 'upcoming'),

    (1, 'PAT-TEST-004', '2026-10-28', '11:30',
     'Cardiovascular follow-up', 'upcoming');


-- =========================================================
-- 5. MEDICATIONS
-- Period is assumed to be the number of days.
-- =========================================================

INSERT INTO Medication
    (id_doctor, patient_national_id, name, start_date, duration, description)
VALUES
    (1, 'PAT-TEST-001', 'Aspirin', '2026-10-01', 30,
     'Sample medication for cardiology follow-up.'),

    (1, 'PAT-TEST-002', 'Lisinopril', '2026-10-10', 14,
     'Sample medication for a two-week course.'),

    (1, 'PAT-TEST-004', 'Atorvastatin', '2026-09-20', 60,
     'Sample medication for cardiovascular follow-up.'),

    (2, 'PAT-TEST-001', 'Hydrocortisone cream', '2026-10-08', 14,
     'Sample topical application.'),

    (2, 'PAT-TEST-003', 'Cetirizine', '2026-10-12', 7,
     'Sample dermatology treatment.'),

    (2, 'PAT-TEST-005', 'Clotrimazole cream', '2026-10-15', 10,
     'Sample short-term treatment.'),

    (3, 'PAT-TEST-002', 'Paracetamol', '2026-10-05', 30,
     'Sample general treatment.'),

    (3, 'PAT-TEST-003', 'Ibuprofen', '2026-10-11', 5,
     'Sample short-term treatment.'),

    (3, 'PAT-TEST-006', 'Amoxicillin', '2026-10-06', 20,
     'Sample general treatment.'),

    (1, 'PAT-TEST-001', 'Metoprolol', '2026-10-14', 7,
     'Second sample medication for the same patient.');


-- =========================================================
-- 6. MEDICAL TESTS
-- Status values must be 'pending' or 'completed'.
-- doctor_type must be 'internal' or 'external'.
-- =========================================================

INSERT INTO MedicalTest
    (id_doctor, patient_national_id, test_type, result,
     test_date, doctor_type, status)
VALUES
    (1, 'PAT-TEST-001', 'Electrocardiogram',
     'Sample result: normal.', '2026-10-05', 'internal', 'completed'),

    (1, 'PAT-TEST-002', 'Blood test',
     'Sample result: follow-up recommended.', '2026-10-10',
     'external', 'completed'),

    (1, 'PAT-TEST-004', 'Echocardiogram',
     NULL, '2026-10-22', 'external', 'pending'),

    (2, 'PAT-TEST-003', 'Skin analysis',
     'Fictional result: no abnormality detected.', '2026-10-12',
     'internal', 'completed'),

    (2, 'PAT-TEST-005', 'Allergy test',
     NULL, '2026-10-23', 'external', 'pending'),

    (2, 'PAT-TEST-001', 'Dermatology examination',
     NULL, '2026-10-20', 'internal', 'pending'),

    (3, 'PAT-TEST-002', 'Metabolic panel',
     'Sample result: values should be monitored.', '2026-10-06',
     'external', 'completed'),

    (3, 'PAT-TEST-006', 'Blood test',
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
