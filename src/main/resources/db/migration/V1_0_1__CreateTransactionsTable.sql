CREATE TABLE transactions
(
    transaction_id                  UUID PRIMARY KEY,
    account_number                  UUID NOT NULL,
    timestamp                       TIMESTAMP WITH TIME ZONE,
    amount                          VARCHAR(17) NOT NULL,
    initiator                       VARCHAR(10) NOT NULL
);

CREATE INDEX transactions_account_number_idx ON account (account_number);
