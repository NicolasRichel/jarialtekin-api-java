-- SQL script to test that startDate can not be null
INSERT INTO Tasks (id, name) VALUES (1, 'T1');
INSERT INTO TasksPlannings (idTask, startDate, startTime, endDate, endTime) VALUES (1, null, '09:00:00', '2018-01-01', '10:00:00');