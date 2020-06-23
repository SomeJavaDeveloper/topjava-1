DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_user_seq RESTART WITH 100000;
ALTER SEQUENCE global_meal_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals(user_id, date_time, description, calories) VALUES
  (100000, '2020-11-19 21:47', '123', 123),
  (100001, '2020-12-19 14:10', '456', 456);

