-- SQL script to test [ pk_parentTasks ] constraint
INSERT INTO Tasks (id, name) VALUES (1, 'T1');
INSERT INTO Tasks (id, name) VALUES (2, 'T2');
INSERT INTO Tasks (id, name) VALUES (3, 'T3');
INSERT INTO ParentTasks (idTask, idParent) VALUES (1, 2);
INSERT INTO ParentTasks (idTask, idParent) VALUES (1, 3);