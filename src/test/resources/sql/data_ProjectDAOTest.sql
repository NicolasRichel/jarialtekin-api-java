-- ===============================
--  DB DATA FOR [ ProjectDAOTest ]
-- ===============================

-- Tasks :
INSERT INTO Tasks (id, name, description, priority, status) VALUES (1, 'T1', 'La tâche 1', 0, 0);
INSERT INTO Tasks (id, name, description, priority, status) VALUES (2, 'T2', 'La tâche 2', 0, 0);
INSERT INTO Tasks (id, name, description, priority, status) VALUES (3, 'T3', 'La tâche 3', 0, 0);

-- Projects :
INSERT INTO Projects (id, name, description, startDate, endDate) VALUES (1, 'P1', 'Le projet 1', '2018-01-01', '2018-01-10');
INSERT INTO Projects (id, name, description, startDate, endDate) VALUES (2, 'P2', 'Le projet 2', '2018-01-05', '2018-01-15');

-- ProjectsTasks :
INSERT INTO ProjectsTasks (idProject, idTask) VALUES (1, 1);
INSERT INTO ProjectsTasks (idProject, idTask) VALUES (1, 2);
INSERT INTO ProjectsTasks (idProject, idTask) VALUES (1, 3);