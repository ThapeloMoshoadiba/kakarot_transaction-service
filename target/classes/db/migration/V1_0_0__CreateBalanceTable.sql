CREATE TABLE balance
(
    id                                  UUID PRIMARY KEY,
    created_at                          TIMESTAMP WITH TIME ZONE,
    updated_at                          TIMESTAMP WITH TIME ZONE,
    account_number                      VARCHAR(17) NOT NULL,
    balance                             VARCHAR(40)
);

CREATE INDEX balance_account_number_idx ON client (account_number);
