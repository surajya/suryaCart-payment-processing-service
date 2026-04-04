-- Drop schema (PostgreSQL doesn't use DROP DATABASE like this in scripts)
DROP SCHEMA IF EXISTS payments CASCADE;

-- Create schema
CREATE SCHEMA payments;

-- Create user
DO $$
BEGIN
   IF NOT EXISTS (SELECT FROM pg_roles WHERE rolname = 'payments') THEN
      CREATE USER payments WITH PASSWORD 'cptraining';
   END IF;
END
$$;

-- Grant privileges
GRANT ALL PRIVILEGES ON SCHEMA payments TO payments;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA payments TO payments;

---------------------------------------------------
-- Tables
---------------------------------------------------

CREATE TABLE payments.payment_method (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    status SMALLINT DEFAULT 1,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE payments.payment_type (
    id INT PRIMARY KEY,
    type VARCHAR(100) NOT NULL,
    status SMALLINT DEFAULT 1,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE payments.provider (
    id INT PRIMARY KEY,
    provider_name VARCHAR(100) NOT NULL,
    status SMALLINT DEFAULT 1,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE payments.transaction_status (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    status SMALLINT DEFAULT 1,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE payments.transactions (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    payment_method_id INT NOT NULL,
    provider_id INT NOT NULL,
    payment_type_id INT NOT NULL,
    txn_status_id INT NOT NULL,
    amount DECIMAL(19,2) DEFAULT 0.00,
    currency VARCHAR(3) NOT NULL,
    merchant_txn_reference VARCHAR(100) NOT NULL,
    txn_reference VARCHAR(100) UNIQUE NOT NULL,
    provider_reference VARCHAR(100),
    error_code VARCHAR(500),
    error_message VARCHAR(1000),
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    retry_count INT DEFAULT 0,

    CONSTRAINT fk_payment_method
        FOREIGN KEY (payment_method_id)
        REFERENCES payments.payment_method(id),

    CONSTRAINT fk_provider
        FOREIGN KEY (provider_id)
        REFERENCES payments.provider(id),

    CONSTRAINT fk_txn_status
        FOREIGN KEY (txn_status_id)
        REFERENCES payments.transaction_status(id),

    CONSTRAINT fk_payment_type
        FOREIGN KEY (payment_type_id)
        REFERENCES payments.payment_type(id)
);

CREATE TABLE payments.transaction_log (
    id SERIAL PRIMARY KEY,
    transaction_id INT NOT NULL,
    txn_from_status VARCHAR(50) DEFAULT '-1',
    txn_to_status VARCHAR(50) DEFAULT '-1',
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_transaction_log
        FOREIGN KEY (transaction_id)
        REFERENCES payments.transactions(id)
);