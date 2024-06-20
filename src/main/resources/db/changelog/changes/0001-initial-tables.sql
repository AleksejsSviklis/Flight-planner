--liquibase formatted sql

--changeset init:1


CREATE TABLE airport(
    id          serial primary key,
    country     varchar(255) not null,
    city        varchar(255) not null,
    airport_name     varchar(255) not null,
    UNIQUE (country, city, airport_name)
);


CREATE TABLE flight(
    id          serial primary key,
    from_id     int not null,
    to_id       int not null,
    carrier     varchar(255) not null,
    departure_time timestamp not null,
    arrival_time timestamp not null,
    CONSTRAINT fk_from_id FOREIGN KEY(from_id) REFERENCES airport(id),
    CONSTRAINT fk_to_id FOREIGN KEY(to_id) REFERENCES airport(id),
    UNIQUE (from_id, to_id, carrier, departure_time, arrival_time)
);