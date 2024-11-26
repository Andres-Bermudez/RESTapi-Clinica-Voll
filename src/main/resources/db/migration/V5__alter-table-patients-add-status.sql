ALTER TABLE patients
ADD active_status TINYINT;

UPDATE patients SET active_status = 1;
