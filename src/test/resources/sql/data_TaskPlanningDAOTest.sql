-- ====================================
--  DB DATA FOR [ TaskPlanningDAOTest ]
-- ====================================

-- Tasks :
INSERT INTO Tasks (id, name, description, priority, status) VALUES (1, 'T1', 'Dev', 0, 0);
INSERT INTO Tasks (id, name, description, priority, status) VALUES (2, 'T2', 'Test', 0, 0);
INSERT INTO Tasks (id, name, description, priority, status) VALUES (3, 'T3', 'Deploy', 0, 0);
INSERT INTO Tasks (id, name, description, priority, status) VALUES (4, 'T4', null, null, null);
INSERT INTO Tasks (id, name, description, priority, status) VALUES (5, 'T5', null, null, null);

-- TasksPlannings :
INSERT INTO TasksPlannings (idTask, startDate, startTime, endDate, endTime) VALUES (1, '2018-01-01', '10:15:00', '2018-01-01', '11:52:00');
INSERT INTO TasksPlannings (idTask, startDate, startTime, endDate, endTime) VALUES (2, '2018-01-01', '13:30:00', '2018-01-02', '12:00:00');
INSERT INTO TasksPlannings (idTask, startDate, startTime, endDate, endTime) VALUES (3, '2018-01-01', '15:00:00', '2018-01-02', '10:00:00');
