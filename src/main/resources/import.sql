-- Utente base
--INSERT INTO users (id, name, email, surname) VALUES (1, 'Flaminia', 'Balduini', 'flamy003@gmail.com');
--INSERT INTO credentials (id, username, password, role, user_id)VALUES (1, 'mimi', '{noop}password', 'ADMIN', 1);


--ADMIN e UTENTE base 
INSERT INTO users (id, name, surname, email) VALUES (1, 'Flaminia', 'Balduini', 'flamy003@gmail.com');
INSERT INTO credentials (id, username, password, role, user_id)VALUES (1, 'mimi', '$2a$10$m2BM9nTPD/J30EDNTsG0D.COClTQOP1WbNL14LeEs1ek2ph2AkYd.', 'ADMIN', 1);
INSERT INTO users (id, name, surname, email) VALUES (2, 'Luca', 'Bussi', 'luca.bussi@hotmail.it');
INSERT INTO credentials (id, username, password, role, user_id)VALUES (2, 'kuca', '$2a$10$m2BM9nTPD/J30EDNTsG0D.COClTQOP1WbNL14LeEs1ek2ph2AkYd.', 'DEFAULT', 2);
INSERT INTO users (id, name, surname, email) VALUES (3, 'Jhon', 'Herrera', 'jhon30.herrera@gmail.com');
INSERT INTO credentials (id, username, password, role, user_id)VALUES (3, 'jem', '$2a$10$m2BM9nTPD/J30EDNTsG0D.COClTQOP1WbNL14LeEs1ek2ph2AkYd.', 'DEFAULT', 3);
INSERT INTO users (id, name, surname, email) VALUES (4, 'Valeria', 'Cinicia', 'valeria.cinicia@gmail.com');
INSERT INTO credentials (id, username, password, role, user_id)VALUES (4, 'val', '$2a$10$m2BM9nTPD/J30EDNTsG0D.COClTQOP1WbNL14LeEs1ek2ph2AkYd.', 'DEFAULT', 4);

-- Autori
INSERT INTO author (id, name, surname, date_of_birth, date_of_death, nationality, description, image_file_name) VALUES (1, 'George', 'Orwell', '1903-06-25', '1950-01-21', 'Britannica', 'Scrittore britannico, autore di romanzi distopici e saggi politici.', 'orwell.jpg');
INSERT INTO author (id, name, surname, date_of_birth, date_of_death, nationality, description, image_file_name) VALUES (2, 'Antoine', 'de Saint-Exupéry', '1900-06-29', '1944-07-31', 'Francese', 'Scrittore e aviatore francese, celebre per Il piccolo principe.', 'saint_exupery.jpg');
INSERT INTO author (id, name, surname, date_of_birth, date_of_death, nationality, description, image_file_name) VALUES (3, 'Umberto', 'Eco', '1932-01-05', '2016-02-19', 'Italiana', 'Semiologo e scrittore italiano, noto per Il nome della rosa.', 'eco.jpg');
INSERT INTO author (id, name, surname, date_of_birth, date_of_death, nationality, description, image_file_name) VALUES (4, 'J.R.R.', 'Tolkien', '1892-01-03', '1973-09-02', 'Britannica', 'Autore fantasy inglese, creatore del mondo della Terra di Mezzo.', 'tolkien.jpg');
INSERT INTO author (id, name, surname, date_of_birth, date_of_death, nationality, description, image_file_name) VALUES (5, 'Jane', 'Austen', '1775-12-16', '1817-07-18', 'Britannica', 'Scrittrice inglese, pioniera del romanzo di costume.', 'austen.jpg');
INSERT INTO author (id, name, surname, date_of_birth, date_of_death, nationality, description, image_file_name) VALUES (6, 'Dante', 'Alighieri', '1265-01-01', '1321-09-14', 'Italiana', 'Poeta italiano, autore della Divina Commedia.', 'alighieri.jpg');
INSERT INTO author (id, name, surname, date_of_birth, date_of_death, nationality, description, image_file_name) VALUES (7, 'Alessandro', 'Manzoni', '1785-03-07', '1873-05-22', 'Italiana', 'Romanziere italiano, autore de I Promessi Sposi.', 'manzoni.jpg');
INSERT INTO author (id, name, surname, date_of_birth, date_of_death, nationality, description, image_file_name) VALUES (8, 'Luigi', 'Pirandello', '1867-06-28', '1936-12-10', 'Italiana', 'Drammaturgo italiano, Premio Nobel per la letteratura.', 'pirandello.jpg');
INSERT INTO author (id, name, surname, date_of_birth, date_of_death, nationality, description, image_file_name) VALUES (9, 'Agatha', 'Christie', '1890-09-15', '1976-01-12', 'Britannica', 'Giallista inglese, regina del mistero, creatrice di Poirot e Miss Marple.', 'christie.jpg');
INSERT INTO author (id, name, surname, date_of_birth, date_of_death, nationality, description, image_file_name) VALUES (10, 'Albert', 'Camus', '1913-11-07', '1960-01-04', 'Francese', 'Scrittore e filosofo francese, autore esistenzialista, premio Nobel per la letteratura.', 'camus.jpg');
INSERT INTO author (id, name, surname, date_of_birth, date_of_death, nationality, description, image_file_name) VALUES (11, 'J.K.', 'Rowling', '1965-07-31', null, 'Britannica', 'Scrittrice britannica, creatrice della celebre saga di Harry Potter.', 'rowling.jpg');
INSERT INTO author (id, name, surname, date_of_birth, date_of_death, nationality, description, image_file_name) VALUES (12, 'Giovanni', 'Verga', '1840-09-02', '1922-01-27', 'Italiana', 'Scrittore verista italiano, autore de I Malavoglia.', 'verga.jpg');
INSERT INTO author (id, name, surname, date_of_birth, date_of_death, nationality, description, image_file_name) VALUES (13, 'Fyodor', 'Dostoevskij', '1821-11-11', '1881-02-09', 'Russa', 'Scrittore russo, maestro del romanzo psicologico e morale.', 'dostoevskij.jpg');
INSERT INTO author (id, name, surname, date_of_birth, date_of_death, nationality, description, image_file_name) VALUES (15, 'Oscar', 'Wilde', '1854-10-16', '1900-11-30', 'Britannica', 'Scrittore e drammaturgo irlandese, maestro del paradosso e dell’estetismo.', 'wilde.jpg');
INSERT INTO author (id, name, surname, date_of_birth, date_of_death, nationality, description, image_file_name) VALUES (16, 'Virginia', 'Woolf', '1882-01-25', '1941-03-28', 'Britannica', 'Scrittrice modernista inglese, pioniera del monologo interiore e dell’esplorazione psicologica.', 'woolf.jpg');
INSERT INTO author (id, name, surname, date_of_birth, date_of_death, nationality, description, image_file_name) VALUES (17, 'Italo', 'Svevo', '1861-12-19', '1928-09-13', 'Italiana', 'Scrittore italiano, noto per l’introspezione psicologica.', 'svevo.jpg');
INSERT INTO author (id, name, surname, date_of_birth, date_of_death, nationality, description, image_file_name) VALUES (18, 'William', 'Shakespeare', '1564-04-26', '1616-04-23', 'Britannica', 'Drammaturgo e poeta inglese, considerato il più grande scrittore della lingua inglese.', 'shakespeare.jpg');
INSERT INTO author (id, name, surname, date_of_birth, date_of_death, nationality, description, image_file_name) VALUES (19, 'Charles', 'Dickens', '1812-02-07', '1870-06-09', 'Britannica', 'Romanziere inglese dell’epoca vittoriana, autore di Oliver Twist e David Copperfield.', 'dickens.jpg');
INSERT INTO author (id, name, surname, date_of_birth, date_of_death, nationality, description, image_file_name) VALUES (20, 'Mary', 'Shelley', '1797-08-30', '1851-02-01', 'Britannica', 'Scrittrice inglese, celebre per il romanzo gotico Frankenstein.', 'shelley.jpg');
INSERT INTO author (id, name, surname, date_of_birth, date_of_death, nationality, description, image_file_name) VALUES (21, 'Elena', 'Ferrante', '1943-01-01', null, 'Italiana', 'Autrice contemporanea italiana di fama internazionale, nota per la tetralogia de L’amica geniale.', 'ferrante.jpg');


-- Libri
INSERT INTO book (id, title, description, year, cover_image_file_name) VALUES (1, '1984', 'Romanzo distopico in cui il Grande Fratello controlla ogni aspetto della vita. Winston Smith tenta di ribellarsi.', 1949, '1984cover.jpg');
INSERT INTO book (id, title, description, year, cover_image_file_name) VALUES (2, 'Il piccolo principe', 'Racconto poetico sull’amicizia e il senso della vita, narrato da un aviatore che incontra un principe alieno.', 1943, 'piccolo_principe.jpg');
INSERT INTO book (id, title, description, year, cover_image_file_name) VALUES (3, 'Il nome della rosa', 'Giallo medievale ambientato in un monastero dove il frate Guglielmo indaga su misteriosi omicidi.', 1980, 'nome_della_rosa.jpg');
INSERT INTO book (id, title, description, year, cover_image_file_name) VALUES (4, 'Il Signore degli Anelli', 'Epico fantasy in cui Frodo cerca di distruggere l’Unico Anello e sconfiggere Sauron.', 1954, 'signore_degli_anelli.jpg');
INSERT INTO book (id, title, description, year, cover_image_file_name) VALUES (5, 'Orgoglio e pregiudizio', 'Elizabeth Bennet e Mr. Darcy si scontrano tra pregiudizi e differenze sociali nella campagna inglese.', 1813, 'orgoglio_pregiudizio.jpg');
INSERT INTO book (id, title, description, year, cover_image_file_name) VALUES (6, 'La Divina Commedia', 'Viaggio allegorico attraverso Inferno, Purgatorio e Paradiso scritto da Dante Alighieri.', 1320, 'divina_commedia.jpg');
INSERT INTO book (id, title, description, year, cover_image_file_name) VALUES (7, 'I Promessi Sposi', 'Romanzo storico su Renzo e Lucia e le difficoltà che affrontano per sposarsi.', 1827, 'promessi_sposi.jpg');
INSERT INTO book (id, title, description, year, cover_image_file_name) VALUES (8, 'Uno, nessuno e centomila', 'Il protagonista riflette sulla molteplicità dell’identità personale.', 1926, 'uno_nessuno_centomila.jpg');
INSERT INTO book (id, title, description, year, cover_image_file_name) VALUES (9, 'Dieci piccoli indiani', 'Dieci persone invitate su un’isola misteriosa muoiono una dopo l’altra secondo una filastrocca inquietante.', 1939, 'dieci_piccoli_indiani.jpg');
INSERT INTO book (id, title, description, year, cover_image_file_name) VALUES (10, 'Lo straniero', 'Romanzo simbolo dell’assurdo: Meursault, un uomo indifferente alla società, commette un omicidio senza ragione.', 1942, 'lo_straniero.jpg');
INSERT INTO book (id, title, description, year, cover_image_file_name) VALUES (11, 'Harry Potter e la Pietra Filosofale', 'Harry scopre di essere un mago e inizia la sua avventura a Hogwarts.', 1997, 'hp1.jpg');
INSERT INTO book (id, title, description, year, cover_image_file_name) VALUES (12, 'Harry Potter e la Camera dei Segreti', 'Una minaccia si aggira per la scuola, pietrificando gli studenti.', 1998, 'hp2.jpg');
INSERT INTO book (id, title, description, year, cover_image_file_name) VALUES (13, 'Harry Potter e il Prigioniero di Azkaban', 'Harry scopre la verità sul suo passato e su Sirius Black.', 1999, 'hp3.jpg');
INSERT INTO book (id, title, description, year, cover_image_file_name) VALUES (14, 'Harry Potter e il Calice di Fuoco', 'Harry partecipa al Torneo Tremaghi, un evento pericoloso e magico.', 2000, 'hp4.jpg');
INSERT INTO book (id, title, description, year, cover_image_file_name) VALUES (15, 'Harry Potter e l’Ordine della Fenice', 'Harry affronta l’isolamento e la nuova minaccia di Dolores Umbridge.', 2003, 'hp5.jpg');
INSERT INTO book (id, title, description, year, cover_image_file_name) VALUES (16, 'Harry Potter e il Principe Mezzosangue', 'Misteri del passato di Voldemort vengono rivelati a Hogwarts.', 2005, 'hp6.jpg');
INSERT INTO book (id, title, description, year, cover_image_file_name) VALUES (17, 'Harry Potter e i Doni della Morte', 'Harry affronta la battaglia finale contro Voldemort.', 2007, 'hp7.jpg');
INSERT INTO book (id, title, description, year, cover_image_file_name) VALUES (18, 'I Malavoglia', 'Famiglia di pescatori affronta il declino economico e morale.', 1881, 'malavoglia.jpg');
INSERT INTO book (id, title, description, year, cover_image_file_name) VALUES (19, 'Delitto e castigo', 'Romanzo psicologico e filosofico: Raskolnikov, giovane povero e tormentato, compie un omicidio convinto di avere un destino superiore.', 1866, 'delitto_castigo.jpg');
INSERT INTO book (id, title, description, year, cover_image_file_name) VALUES (20, 'Il ritratto di Dorian Gray', 'Romanzo gotico in cui un giovane mantiene l’eterna giovinezza mentre un ritratto invecchia al suo posto, riflettendo la sua anima corrotta.', 1890, 'dorian_gray.jpg');
INSERT INTO book (id, title, description, year, cover_image_file_name) VALUES (21, 'Gita al faro', 'Romanzo modernista che esplora la memoria, il tempo e le relazioni familiari durante una vacanza sull’isola di Skye.', 1927, 'gita_faro.jpg');
INSERT INTO book (id, title, description, year, cover_image_file_name) VALUES (22, 'La coscienza di Zeno', 'Zeno Cosini racconta la propria vita con ironia e introspezione.', 1923, 'coscienza_z.jpg');
INSERT INTO book (id, title, description, year, cover_image_file_name) VALUES (23, 'Amleto', 'Tragedia shakespeariana incentrata sulla vendetta del principe di Danimarca contro lo zio usurpatore.', 1603, 'amleto.jpg');
INSERT INTO book (id, title, description, year, cover_image_file_name) VALUES (24, 'Oliver Twist', 'Romanzo di formazione di un orfano nell’Inghilterra vittoriana, con forte critica sociale.', 1838, 'oliver_twist.jpg');
INSERT INTO book (id, title, description, year, cover_image_file_name) VALUES (25, 'Frankenstein', 'Un giovane scienziato dà vita a una creatura che si rivelerà la sua rovina.', 1818, 'frankenstein.jpg');
INSERT INTO book (id, title, description, year, cover_image_file_name) VALUES (26, 'L’amica geniale', 'Storia dell’amicizia intensa e complessa tra Elena e Lila, ambientata nella Napoli del dopoguerra.', 2011, 'amica_geniale.jpg');


-- Associazioni libro-autore
INSERT INTO author_books (books_id, authors_id) VALUES (1, 1);    -- Orwell
INSERT INTO author_books (books_id, authors_id) VALUES (2, 2);    -- Saint-Exupéry
INSERT INTO author_books (books_id, authors_id) VALUES (3, 3);    -- Eco
INSERT INTO author_books (books_id, authors_id) VALUES (4, 4);    -- Tolkien
INSERT INTO author_books (books_id, authors_id) VALUES (5, 5);    -- Austen
INSERT INTO author_books (books_id, authors_id) VALUES (6, 6);    -- Dante
INSERT INTO author_books (books_id, authors_id) VALUES (7, 7);    -- Manzoni
INSERT INTO author_books (books_id, authors_id) VALUES (8, 8);    -- Pirandello
INSERT INTO author_books (books_id, authors_id) VALUES (9, 9);    -- Christie
INSERT INTO author_books (books_id, authors_id) VALUES (10, 10);  -- Camus

-- J.K. Rowling (id = 11), 7 libri
INSERT INTO author_books (books_id, authors_id) VALUES (11, 11);
INSERT INTO author_books (books_id, authors_id) VALUES (12, 11);
INSERT INTO author_books (books_id, authors_id) VALUES (13, 11);
INSERT INTO author_books (books_id, authors_id) VALUES (14, 11);
INSERT INTO author_books (books_id, authors_id) VALUES (15, 11);
INSERT INTO author_books (books_id, authors_id) VALUES (16, 11);
INSERT INTO author_books (books_id, authors_id) VALUES (17, 11);

INSERT INTO author_books (books_id, authors_id) VALUES (18, 12);  -- Verga
INSERT INTO author_books (books_id, authors_id) VALUES (19, 13);  -- Dostoevskij
INSERT INTO author_books (books_id, authors_id) VALUES (20, 15);  -- Wilde
INSERT INTO author_books (books_id, authors_id) VALUES (21, 16);  -- Woolf
INSERT INTO author_books (books_id, authors_id) VALUES (22, 17);  -- Svevo
INSERT INTO author_books (books_id, authors_id) VALUES (23, 18);  -- Shakespeare
INSERT INTO author_books (books_id, authors_id) VALUES (24, 19);  -- Dickens
INSERT INTO author_books (books_id, authors_id) VALUES (25, 20);  -- Shelley
INSERT INTO author_books (books_id, authors_id) VALUES (26, 21);  -- Ferrante


-- RECENSIONI
-- Luca
INSERT INTO review (id, book_id, user_id, title, text, vote) VALUES (1, 1, 2, 'Distopico e potente', 'Un libro inquietante ma estremamente attuale.', 5);
INSERT INTO review (id, book_id, user_id, title, text, vote) VALUES (2, 2, 2, 'Dolce e malinconico', 'Il piccolo principe ti lascia qualcosa dentro.', 4);
INSERT INTO review (id, book_id, user_id, title, text, vote) VALUES (7, 4, 2, 'Avventura e amicizia', 'Il Signore degli Anelli è un viaggio epico pieno di emozione.', 5);
INSERT INTO review (id, book_id, user_id, title, text, vote) VALUES (8, 18, 2, 'Storia amara', 'Le difficoltà dei Malavoglia rispecchiano la dura realtà.', 4);
INSERT INTO review (id, book_id, user_id, title, text, vote) VALUES (13, 11, 2, 'Inizio magico', 'La Pietra Filosofale apre un mondo incantato.', 5);

-- Jhon
INSERT INTO review (id, book_id, user_id, title, text, vote) VALUES (3, 3, 3, 'Intrigante e profondo', 'Un giallo intellettuale davvero originale.', 5);
INSERT INTO review (id, book_id, user_id, title, text, vote) VALUES (4, 5, 3, 'Classico elegante', 'Orgoglio e Pregiudizio è molto più attuale di quanto pensassi.', 4);
INSERT INTO review (id, book_id, user_id, title, text, vote) VALUES (9, 10, 3, 'Esistenzialismo allo stato puro', 'Un libro che ti fa riflettere sulla tua identità.', 4);
INSERT INTO review (id, book_id, user_id, title, text, vote) VALUES (10, 13, 3, 'Fiaba filosofica', 'Una storia surreale che stimola la fantasia e il pensiero.', 5);
INSERT INTO review (id, book_id, user_id, title, text, vote) VALUES (14, 24, 3, 'Triste e potente', 'Oliver Twist è un pugno nello stomaco narrato con grazia.', 5);

-- Valeria
INSERT INTO review (id, book_id, user_id, title, text, vote) VALUES (5, 7, 4, 'Commovente', 'La storia di Renzo e Lucia è un capolavoro.', 5);
INSERT INTO review (id, book_id, user_id, title, text, vote) VALUES (6, 6, 4, 'Poesia pura', 'La Divina Commedia mi ha aperto un mondo.', 5);
INSERT INTO review (id, book_id, user_id, title, text, vote) VALUES (11, 19, 4, 'Realismo struggente', 'Una lettura intensa che racconta il Sud con onestà.', 4);
INSERT INTO review (id, book_id, user_id, title, text, vote) VALUES (12, 21, 4, 'Atmosfera inquieta', 'Un romanzo lento ma profondo, pieno di attesa e tensione.', 4);
INSERT INTO review (id, book_id, user_id, title, text, vote) VALUES (15, 26, 4, 'Verità femminile', 'L’amica geniale racconta l’amicizia in modo autentico e profondo.', 5);

SELECT setval('book_seq',       (SELECT MAX(id) FROM book));
SELECT setval('author_seq',     (SELECT MAX(id) FROM author));
SELECT setval('review_seq',(SELECT MAX(id) FROM review));
SELECT setval('users_seq',   (SELECT MAX(id) FROM users));
SELECT setval('credentials_seq',(SELECT MAX(id) FROM credentials));
