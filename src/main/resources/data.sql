INSERT INTO address (id, street, street_number, post_code, city, country) VALUES
(1, 'Al. Krakowska', '110/114', '02-256', 'Warsaw', 'POLAND'),
(2, 'Piłsudskiego', '33A', '05-300', 'Minsk Mazowiecki', 'POLAND'),
(3, 'Myśleniecka', '111', '02-412', 'Warsaw', 'POLAND'),
(4, 'Długa', '325', '02-653', 'Krzywonogi', 'POLAND'),
(5, 'Mitchell Road', '1', 'MK43 0AL', 'Bedfordshire', 'United Kingdom');

INSERT INTO company (id, company_name, vat_id_number, address_id) VALUES
(1, 'BUDIMEX S.A.','894-321-123', 3),
(2, 'WARBUD S.A.','823-234-543', 1);

INSERT INTO user(id, email, phone_number, password, first_name, last_name, address_id, company_id, user_type) VALUES
(1, 'adam.poniekad@budimex.pl', '111-222-333', '123abc', 'Adam', 'Poniekąd', 1, 1, 'ADMIN'),
(2, 'grazyna.idzzesz@warbud.pl', '987-654-321', '12345', 'Grażyna', 'Idźżesz', 3, 2, 'VIEWER'),
(3, 'janina.rambo@warbud.pl', '123-456-789', 'abcd1234', 'Janina', 'Rambo', 2, 2, 'CUSTOMER');