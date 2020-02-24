
INSERT INTO public.users (id, email, first_name, last_name, password, role, username, daily_norm_calories) VALUES (2, 'admin@ft.co', 'John', 'Doe', 'admin', 'ADMIN', 'admin', 3663.01);
INSERT INTO public.users (id, email, first_name, last_name, password, role, username, daily_norm_calories) VALUES (6, 'sabrinae@mail.com', 'Sabrina', 'Elswick', 'sabrina', 'USER', 'sabrina', 2523.15);
INSERT INTO public.users (id, email, first_name, last_name, password, role, username, daily_norm_calories) VALUES (4, 'jess@mail.com', 'Jessika', 'Gaudet ', 'jess', 'USER', 'jess', 2477.89);

INSERT INTO public.biometrics (id, age, height, lifestyle, sex, weight, user_id) VALUES (1, 33.00, 180.00, 'SEDENTARY', 'MALE', 120.00, 2);
INSERT INTO public.biometrics (id, age, height, lifestyle, sex, weight, user_id) VALUES (5, 37.00, 168.00, 'MODERATE', 'FEMALE', 67.00, 6);
INSERT INTO public.biometrics (id, age, height, lifestyle, sex, weight, user_id) VALUES (3, 45.00, 175.00, 'SEDENTARY', 'FEMALE', 89.00, 4);

INSERT INTO public.foods (id, calories, name, user_id) VALUES (11, 35.00, 'Green beens', null);
INSERT INTO public.foods (id, calories, name, user_id) VALUES (12, 470.00, 'Dried Frozen Tofu', null);
INSERT INTO public.foods (id, calories, name, user_id) VALUES (13, 36.00, 'Vanilla Coffee', null);
INSERT INTO public.foods (id, calories, name, user_id) VALUES (14, 48.00, 'Frozen Apples', null);
INSERT INTO public.foods (id, calories, name, user_id) VALUES (15, 264.00, 'Grilled Beef', null);
INSERT INTO public.foods (id, calories, name, user_id) VALUES (16, 580.00, 'Roasted Pistachio Nuts', null);
INSERT INTO public.foods (id, calories, name, user_id) VALUES (17, 82.00, 'White Wine', null);
INSERT INTO public.foods (id, calories, name, user_id) VALUES (19, 36.00, 'Vanilla Coffee', 6);
INSERT INTO public.foods (id, calories, name, user_id) VALUES (20, 470.00, 'Dried Frozen Tofu', 6);
INSERT INTO public.foods (id, calories, name, user_id) VALUES (21, 4.00, 'Energy Drink', 6);
INSERT INTO public.foods (id, calories, name, user_id) VALUES (22, 304.00, 'Honey', 6);
INSERT INTO public.foods (id, calories, name, user_id) VALUES (27, 48.00, 'Frozen Apples', 4);
INSERT INTO public.foods (id, calories, name, user_id) VALUES (28, 612.00, 'Butternuts', 4);
INSERT INTO public.foods (id, calories, name, user_id) VALUES (29, 63.00, 'Milk', 4);
INSERT INTO public.foods (id, calories, name, user_id) VALUES (30, 97.00, 'Greek Yogurt', 4);
INSERT INTO public.foods (id, calories, name, user_id) VALUES (40, 227.00, 'Mint Chocolate Chip', 4);

INSERT INTO public.days (id, date, calories_consumed, user_id) VALUES (7, '2020-02-24', 344.00, 6);
INSERT INTO public.days (id, date, calories_consumed, user_id) VALUES (26, '2020-02-22', 2993.50, 4);
INSERT INTO public.days (id, date, calories_consumed, user_id) VALUES (37, '2020-02-23', 902.00, 4);
INSERT INTO public.days (id, date, calories_consumed, user_id) VALUES (42, '2020-02-24', 0.00, 4);

INSERT INTO public.consumed_foods (id, amount, name, time, total_calories, day_id) VALUES (23, 1.00, 'Honey', '10:12:12', 304.00, 7);	
INSERT INTO public.consumed_foods (id, amount, name, time, total_calories, day_id) VALUES (24, 1.00, 'Energy Drink', '10:12:17', 4.00, 7);
INSERT INTO public.consumed_foods (id, amount, name, time, total_calories, day_id) VALUES (25, 1.00, 'Vanilla Coffee', '10:12:23', 36.00, 7);
INSERT INTO public.consumed_foods (id, amount, name, time, total_calories, day_id) VALUES (31, 1.50, 'Greek Yogurt', '10:16:39', 145.50, 26);
INSERT INTO public.consumed_foods (id, amount, name, time, total_calories, day_id) VALUES (32, 3.00, 'Butternuts', '10:16:43', 1836.00, 26);
INSERT INTO public.consumed_foods (id, amount, name, time, total_calories, day_id) VALUES (33, 1.00, 'Milk', '10:16:49', 63.00, 26);
INSERT INTO public.consumed_foods (id, amount, name, time, total_calories, day_id) VALUES (34, 1.00, 'Greek Yogurt', '10:16:52', 97.00, 26);
INSERT INTO public.consumed_foods (id, amount, name, time, total_calories, day_id) VALUES (35, 5.00, 'Frozen Apples', '10:16:58', 240.00, 26);
INSERT INTO public.consumed_foods (id, amount, name, time, total_calories, day_id) VALUES (36, 1.00, 'Butternuts', '10:17:02', 612.00, 26);
INSERT INTO public.consumed_foods (id, amount, name, time, total_calories, day_id) VALUES (38, 1.00, 'Butternuts', '10:18:33', 612.00, 37);
INSERT INTO public.consumed_foods (id, amount, name, time, total_calories, day_id) VALUES (39, 1.00, 'Milk', '10:18:34', 63.00, 37);
INSERT INTO public.consumed_foods (id, amount, name, time, total_calories, day_id) VALUES (41, 1.00, 'Mint Chocolate Chip', '10:19:12', 227.00, 37);
