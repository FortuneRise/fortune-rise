INSERT INTO wallet (id, balance) VALUES (1, 100.00);
INSERT INTO wallet (id, balance) VALUES (2, 101.01);
INSERT INTO user_table (id, name, surname, user_name, wallet_id) VALUES (1, 'Andraž', 'Hribernik', 'andh', 1);
INSERT INTO user_table (id, name, surname, user_name, wallet_id) VALUES (2, 'Jan', 'Rutar', 'rut3r', 2);
SELECT setval('wallet_id_seq', (SELECT MAX(id) FROM wallet) + 1);
SELECT setval('user_table_id_seq', (SELECT MAX(id) FROM user_table) + 1);