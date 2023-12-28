create sequence song_seq;
CREATE TABLE Song (
    id BIGINT NOT NULL DEFAULT nextval('song_seq'),
    artist VARCHAR,
    title VARCHAR,
    raw_data VARCHAR
);