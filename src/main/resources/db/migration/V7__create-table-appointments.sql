CREATE TABLE appointments (

    id BIGINT NOT NULL AUTO_INCREMENT,
    id_doctor BIGINT NOT NULL,
    id_patient BIGINT NOT NULL,
    date DATETIME NOT NULL,
    reason_cancellation VARCHAR(100),

    PRIMARY KEY(id),

    CONSTRAINT fk_appointments_doctor_id FOREIGN KEY(id_doctor) REFERENCES doctors(id),
    CONSTRAINT fk_appointments_patient_id FOREIGN KEY(id_patient) REFERENCES patients(id)
);
