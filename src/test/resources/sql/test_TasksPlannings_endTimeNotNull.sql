-- SQL script to test that endTime can not be null
INSERT INTO Tasks (id, name) VALUES (1, 'T1');
INSERT INTO TasksPlannings (idTask, startDate, startTime, endDate, endTime) VALUES (1, '2018-01-01', '09:00:00', '2018-01-01', null);