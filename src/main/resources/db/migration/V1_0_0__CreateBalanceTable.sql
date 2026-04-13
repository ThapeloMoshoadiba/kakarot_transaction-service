CREATE TABLE balance
(
    id                                  UUID PRIMARY KEY,
    created_at                          TIMESTAMP NOT NULL,
    updated_at                          TIMESTAMP,
    account_number                      UUID NOT NULL,
    balance                             NUMERIC(18, 2) NOT NULL
);

CREATE INDEX balance_account_number_idx ON balance (account_number);
