-- SQL script to test [ pk_taskDependencies ] constraint
INSERT INTO Tasks (id, name) VALUES (1, 'T1');
INSERT INTO Tasks (id, name) VALUES (2, 'T2');
INSERT INTO Tasks (id, name) VALUES (3, 'T3');
INSERT INTO TasksDependencies (idTask, idDependency) VALUES (1, 2);
INSERT INTO TasksDependencies (idTask, idDependency) VALUES (1, 2);