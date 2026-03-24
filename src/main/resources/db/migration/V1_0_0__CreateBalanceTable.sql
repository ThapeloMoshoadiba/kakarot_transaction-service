CREATE TABLE balance
(
    id                                  UUID PRIMARY KEY,
    created_at                          TIMESTAMP,
    updated_at                          TIMESTAMP,
    account_number                      UUID NOT NULL,
    balance                             NUMERIC(18, 2)
);

CREATE INDEX balance_account_number_idx ON balance (account_number);
