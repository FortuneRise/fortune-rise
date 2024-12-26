CREATE SEQUENCE promotion_id_seq START WITH 1 INCREMENT BY 1;

-- Insert rows into parent table `promotion`
INSERT INTO promotion (id, trigger_scenario, DTYPE) VALUES (1, 'BET', 'FREE_BET');
INSERT INTO promotion (id, trigger_scenario, DTYPE) VALUES (2, 'DEPOSIT', 'EXTRA_MONEY');

-- Insert rows into child table `free_bet_promotion`
INSERT INTO free_bet_promotion (id, amount) VALUES (1, 5.00);

-- Insert rows into child table `extra_money_promotion`
INSERT INTO extra_money_promotion (id, amount, threshold) VALUES (2, 5.00, 50.00);

-- Update sequence values
SELECT setval('promotion_id_seq', (SELECT MAX(id) FROM promotion) + 1);
