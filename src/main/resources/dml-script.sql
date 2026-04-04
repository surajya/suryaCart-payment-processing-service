-- Payment Method
INSERT INTO payments.payment_method
(id, name, status, creation_date)
VALUES (1, 'APM', 1, '2025-03-03 21:29:49.25');

-- Payment Type
INSERT INTO payments.payment_type
(id, type, status, creation_date)
VALUES (1, 'SALE', 1, '2025-03-03 21:30:42.05');

-- Provider
INSERT INTO payments.provider
(id, provider_name, status, creation_date)
VALUES (1, 'STRIPE', 1, '2025-03-03 21:31:28.08');

-- Transaction Status
INSERT INTO payments.transaction_status
(id, name, status, creation_date)
VALUES (1, 'CREATED', 1, '2025-03-03 21:33:39.84');

INSERT INTO payments.transaction_status
(id, name, status, creation_date)
VALUES (2, 'INITIATED', 1, '2025-03-03 21:33:39.84');

INSERT INTO payments.transaction_status
(id, name, status, creation_date)
VALUES (3, 'PENDING', 1, '2025-03-03 21:33:39.84');

INSERT INTO payments.transaction_status
(id, name, status, creation_date)
VALUES (4, 'SUCCESS', 1, '2025-03-03 21:33:39.84');

INSERT INTO payments.transaction_status
(id, name, status, creation_date)
VALUES (5, 'FAILED', 1, '2025-03-03 21:33:39.84');