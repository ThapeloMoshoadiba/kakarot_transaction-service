CREATE TABLE transactions
(
    transaction_id                  UUID PRIMARY KEY,
    account_number                  UUID NOT NULL,
    transactionType                 VARCHAR(10) NOT NULL,
    entryType                       VARCHAR(10) NOT NULL,
    timestamp                       TIMESTAMP,
    amount                          NUMERIC(18, 2) NOT NULL,
    initiator                       VARCHAR(20) NOT NULL
);

CREATE INDEX transactions_account_number_idx ON transactions (account_number);
