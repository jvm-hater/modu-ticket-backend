ALTER TABLE users RENAME TO user;
ALTER TABLE user DROP COLUMN user_rank;

ALTER TABLE user ADD COLUMN user_id VARCHAR(255);
CREATE UNIQUE INDEX uidx_user_id ON user(user_id);