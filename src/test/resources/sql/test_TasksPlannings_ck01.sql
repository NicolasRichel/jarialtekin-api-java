-- SQL script to test [ ck_tasksPlannings_01 ] constraint
INSERT INTO Tasks (id, name) VALUES (1, 'T1');
INSERT INTO TasksPlannings (idTask, startDate, startTime, endDate, endTime) VALUES (1, '2018-01-02', '09:00:00', '2018-01-01', '10:00:00');