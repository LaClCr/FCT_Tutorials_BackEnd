INSERT INTO category(name) VALUES ('Eurogames');
INSERT INTO category(name) VALUES ('Ameritrash');
INSERT INTO category(name) VALUES ('Familiar');

INSERT INTO author(name, nationality) VALUES ('Alan R. Moon', 'US');
INSERT INTO author(name, nationality) VALUES ('Vital Lacerda', 'PT');
INSERT INTO author(name, nationality) VALUES ('Simone Luciani', 'IT');
INSERT INTO author(name, nationality) VALUES ('Perepau Llistosella', 'ES');
INSERT INTO author(name, nationality) VALUES ('Michael Kiesling', 'DE');
INSERT INTO author(name, nationality) VALUES ('Phil Walker-Harding', 'US');

INSERT INTO game(title, age, category_id, author_id) VALUES ('On Mars', '14', 1, 2);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Aventureros al tren', '8', 3, 1);
INSERT INTO game(title, age, category_id, author_id) VALUES ('1920: Wall Street', '12', 1, 4);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Barrage', '14', 1, 3);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Los viajes de Marco Polo', '12', 1, 3);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Azul', '8', 3, 5);

INSERT INTO client(name) VALUES ('Laia Clemente');
INSERT INTO client(name) VALUES ('Alba Pla');
INSERT INTO client(name) VALUES ('Jordi Joanes');
INSERT INTO client(name) VALUES ('Sara Chirivella');
INSERT INTO client(name) VALUES ('Raquel Pastor');

INSERT INTO loan(start_date, end_date, client_id, game_id) 
VALUES ('2024-03-28', '2024-04-04', 1, 1);

INSERT INTO loan(start_date, end_date, client_id, game_id) 
VALUES ('2024-03-29', '2024-04-05', 2, 2);

INSERT INTO loan(start_date, end_date, client_id, game_id) 
VALUES ('2024-03-30', '2024-04-06', 3, 3);

INSERT INTO loan(start_date, end_date, client_id, game_id) 
VALUES ('2025-03-31', '2025-04-07', 4, 4);

