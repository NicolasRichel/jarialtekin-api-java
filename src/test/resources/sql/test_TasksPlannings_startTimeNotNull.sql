-- SQL script to test that startTime can not be null
INSERT INTO Tasks (id, name) VALUES (1, 'T1');
INSERT INTO TasksPlannings (idTask, startDate, startTime, endDate, endTime) VALUES (1, '2018-01-01', null, '2018-01-01', '10:00:00');