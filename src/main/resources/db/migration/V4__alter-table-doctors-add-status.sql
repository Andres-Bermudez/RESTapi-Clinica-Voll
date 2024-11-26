ALTER TABLE doctors
ADD active_status TINYINT;

UPDATE doctors SET active_status = 1;
