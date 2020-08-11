USE walletdb;

DROP TABLE IF EXISTS wallets;

DROP TABLE IF EXISTS application_users;

CREATE TABLE application_users (
  user_id bigint(20) NOT NULL AUTO_INCREMENT,
  login varchar(15) NOT NULL,
  PRIMARY KEY (user_id)
);


CREATE TABLE wallets (
  wallet_id bigint(20) NOT NULL AUTO_INCREMENT,
  user_id bigint(20) NOT NULL,
  currency varchar(3) NOT NULL,
  balance decimal(15,2) NOT NULL DEFAULT 0,
  PRIMARY KEY (wallet_id),
  KEY FK_wallets_application_users (user_id),
  CONSTRAINT FK_wallets_application_users FOREIGN KEY (user_id) REFERENCES application_users (user_id)
        ON DELETE CASCADE
);

INSERT INTO application_users(user_id, login) VALUES (1, "user1");
INSERT INTO application_users(user_id, login) VALUES (2, "user2");
INSERT INTO application_users(user_id, login) VALUES (3, "user3");
INSERT INTO application_users(user_id, login) VALUES (4, "user4");
INSERT INTO application_users(user_id, login) VALUES (5, "user5");

INSERT INTO wallets(wallet_id, user_id, currency, balance) VALUES (21, 1, "USD", "0");
INSERT INTO wallets(wallet_id, user_id, currency, balance) VALUES (22, 1, "EUR", "0");
INSERT INTO wallets(wallet_id, user_id, currency, balance) VALUES (23, 1, "GBP", "0");
INSERT INTO wallets(wallet_id, user_id, currency, balance) VALUES (24, 2, "USD", "0");
INSERT INTO wallets(wallet_id, user_id, currency, balance) VALUES (25, 2, "EUR", "0");
INSERT INTO wallets(wallet_id, user_id, currency, balance) VALUES (26, 2, "GBP", "0");
INSERT INTO wallets(wallet_id, user_id, currency, balance) VALUES (27, 3, "USD", "0");
INSERT INTO wallets(wallet_id, user_id, currency, balance) VALUES (28, 3, "EUR", "0");
INSERT INTO wallets(wallet_id, user_id, currency, balance) VALUES (29, 3, "GBP", "0");
INSERT INTO wallets(wallet_id, user_id, currency, balance) VALUES (30, 4, "USD", "0");
INSERT INTO wallets(wallet_id, user_id, currency, balance) VALUES (31, 4, "EUR", "0");
INSERT INTO wallets(wallet_id, user_id, currency, balance) VALUES (32, 4, "GBP", "0");

