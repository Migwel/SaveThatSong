-- Insert a user with username "user" and password "password"
with inserted_users as (
  insert into users (username, password, enabled)
   values
   ('user', '{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW', true)
   returning username
)
insert into authorities (username, authority)
select username, 'ROLE_USER'
from inserted_users;