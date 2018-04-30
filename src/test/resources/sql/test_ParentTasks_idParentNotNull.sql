-- SQL script to test that idParent can not be null
INSERT INTO Tasks (id , name) VALUES (1, 'T1');
INSERT INTO ParentTasks (idTask, idParent) VALUES (1, null);