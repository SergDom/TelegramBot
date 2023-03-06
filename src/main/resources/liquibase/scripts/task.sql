-- liquibase formatted sql

-- changeset sdomanov:1

CREATE TABLE notification_task
(
    id SERIAL PRIMARY KEY,
    chat_id BIGINT,
    message TEXT,
    time TIMESTAMP
);