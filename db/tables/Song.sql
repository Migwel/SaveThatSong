create sequence song_seq;
CREATE TABLE Song (
    id BIGINT NOT NULL DEFAULT nextval('song_seq'),
    username varchar(50) not null,
    artist VARCHAR,
    title VARCHAR,
    raw_data VARCHAR,
    creation_date TIMESTAMP WITH TIME ZONE DEFAULT now(),

	constraint fk_song_users foreign key(username) references users(username)
);