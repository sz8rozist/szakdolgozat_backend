-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Gép: localhost
-- Létrehozás ideje: 2024. Dec 08. 08:08
-- Kiszolgáló verziója: 10.4.11-MariaDB
-- PHP verzió: 7.4.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Adatbázis: `fitness`
--

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `diet`
--

CREATE TABLE `diet` (
  `id` int(11) NOT NULL,
  `date` date DEFAULT NULL,
  `eated` tinyint(4) DEFAULT 0,
  `quantity` int(11) NOT NULL,
  `type` enum('BREAKFAST','DINNER','LUNCH','SNACK') DEFAULT NULL,
  `food_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- A tábla adatainak kiíratása `diet`
--

INSERT INTO `diet` (`id`, `date`, `eated`, `quantity`, `type`, `food_id`) VALUES
(5, '2024-12-05', 0, 400, 'BREAKFAST', 1),
(6, '2024-12-05', 0, 300, 'LUNCH', 2),
(7, '2024-12-05', 0, 100, 'DINNER', 4),
(8, '2024-12-05', 0, 400, 'BREAKFAST', 1),
(9, '2024-12-05', 0, 300, 'LUNCH', 2),
(10, '2024-12-05', 0, 100, 'DINNER', 4),
(11, '2024-12-10', 0, 100, 'DINNER', 1),
(12, '2024-12-10', 0, 400, 'DINNER', 5);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `diet_guest`
--

CREATE TABLE `diet_guest` (
  `id` int(11) NOT NULL,
  `diet_id` int(11) DEFAULT NULL,
  `guest_id` int(11) DEFAULT NULL,
  `trainer_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- A tábla adatainak kiíratása `diet_guest`
--

INSERT INTO `diet_guest` (`id`, `diet_id`, `guest_id`, `trainer_id`) VALUES
(5, 5, 4, NULL),
(6, 6, 4, NULL),
(7, 7, 4, NULL),
(8, 8, 4, NULL),
(9, 9, 4, NULL),
(10, 10, 4, NULL),
(11, 11, 4, NULL),
(12, 12, 4, NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `diet_recommedation`
--

CREATE TABLE `diet_recommedation` (
  `id` int(11) NOT NULL,
  `body_weight` float NOT NULL,
  `calorie` float NOT NULL,
  `carbonhydrate` float NOT NULL,
  `date` date DEFAULT NULL,
  `fat` float NOT NULL,
  `protein` float NOT NULL,
  `guest_id` int(11) DEFAULT NULL,
  `trainer_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- A tábla adatainak kiíratása `diet_recommedation`
--

INSERT INTO `diet_recommedation` (`id`, `body_weight`, `calorie`, `carbonhydrate`, `date`, `fat`, `protein`, `guest_id`, `trainer_id`) VALUES
(3, 60, 1402, 70, '2024-12-11', 8, 105, 4, 2),
(4, 60, 1402, 70, '2024-12-10', 8, 105, 4, 2);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `exercise`
--

CREATE TABLE `exercise` (
  `id` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `targeted_body_part` enum('ABS','ARMS','BACK','CHEST','LEGS','SHOULDER') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- A tábla adatainak kiíratása `exercise`
--

INSERT INTO `exercise` (`id`, `description`, `name`, `targeted_body_part`) VALUES
(2, 'Mell izom gyakorlat', 'Fekvenyomás', 'CHEST'),
(3, 'Láb gyakorlat', 'Lábtolás', 'LEGS'),
(4, 'Láb gyakorlat', 'Guggolás', 'LEGS'),
(5, 'Mell gyakorlat', 'Tárogatás', 'CHEST'),
(6, 'Hát gyakorlat', 'Felhúzás', 'BACK'),
(7, 'Hát gyakorlat', 'Széles lehúzás', 'BACK'),
(8, 'Kar gyakorlat', 'Bicepsz franciarúddal', 'ARMS'),
(9, 'Kar gyakorlat', 'Váltott kezes bicepsz', 'ARMS'),
(10, 'Kar gyakorlat', 'Lórugás', 'ARMS');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `food`
--

CREATE TABLE `food` (
  `id` int(11) NOT NULL,
  `calorie` float NOT NULL,
  `carbonhydrate` float NOT NULL,
  `fat` float NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `protein` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- A tábla adatainak kiíratása `food`
--

INSERT INTO `food` (`id`, `calorie`, `carbonhydrate`, `fat`, `name`, `protein`) VALUES
(1, 256, 30, 10, 'Csirkemell filé', 25),
(2, 20, 50, 10, 'Jázmin rizs', 50),
(3, 200, 23, 10, 'Bulgur', 50),
(4, 200, 40, 10, 'Burgonya', 40),
(5, 100, 100, 100, 'Édesburgonya', 100),
(6, 200, 30, 10, 'Lazac filé', 30),
(7, 20, 20, 20, 'Purpur kenyér', 20),
(8, 20, 20, 30, 'Görög joghurt', 30),
(9, 120, 120, 120, 'Csirkecomb', 120),
(10, 100, 1001, 100, 'Omlett', 100);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `guest`
--

CREATE TABLE `guest` (
  `id` int(11) NOT NULL,
  `age` int(11) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `gender` tinyint(4) DEFAULT 0,
  `height` float DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `weight` float DEFAULT NULL,
  `trainer_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- A tábla adatainak kiíratása `guest`
--

INSERT INTO `guest` (`id`, `age`, `email`, `first_name`, `gender`, `height`, `last_name`, `weight`, `trainer_id`, `user_id`) VALUES
(2, NULL, 'vendeg.david@gmail.com', 'Vendég', 1, 0, 'Dávid', 0, NULL, 9),
(3, NULL, 'vendeg.mate@gmail.com', 'Vendég', 1, 0, 'Máté', 0, NULL, 10),
(4, 20, 'vendeg.erika@gmail.com', 'Vendég', 0, 170, 'Erika', 80, 2, 11),
(5, NULL, 'vendeg.ivett@gmail.com', 'Vendég', 0, 0, 'Ivett', 0, NULL, 12);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `message`
--

CREATE TABLE `message` (
  `id` int(11) NOT NULL,
  `date_time` varchar(255) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `readed` bit(1) DEFAULT NULL,
  `receiver_user_id` int(11) DEFAULT NULL,
  `sender_user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- A tábla adatainak kiíratása `message`
--

INSERT INTO `message` (`id`, `date_time`, `message`, `readed`, `receiver_user_id`, `sender_user_id`) VALUES
(4, '2024-12-08', 'Szia Béla! Szeretnék a vendéged lenni, amennyiben van rá kapacitásod.', b'1', 6, 11),
(5, '2024-12-08', 'Szia! már fel is vettelek', b'0', 11, 6);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `notification`
--

CREATE TABLE `notification` (
  `id` int(11) NOT NULL,
  `date` date DEFAULT curdate(),
  `message` varchar(255) DEFAULT NULL,
  `type` enum('DIET','EXERCISE','FEEDBACK') DEFAULT NULL,
  `viewed` tinyint(4) DEFAULT 0,
  `guest_id` int(11) DEFAULT NULL,
  `trainer_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `role`
--

CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `role` enum('GUEST','TRAINER') DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- A tábla adatainak kiíratása `role`
--

INSERT INTO `role` (`id`, `role`, `user_id`) VALUES
(6, 'TRAINER', 6),
(7, 'TRAINER', 7),
(8, 'TRAINER', 8),
(9, 'GUEST', 9),
(10, 'GUEST', 10),
(11, 'GUEST', 11),
(12, 'GUEST', 12);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `trainer`
--

CREATE TABLE `trainer` (
  `id` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- A tábla adatainak kiíratása `trainer`
--

INSERT INTO `trainer` (`id`, `email`, `first_name`, `last_name`, `type`, `user_id`) VALUES
(2, 'edzo.bela@gmail.com', 'Edző', 'Béla', 'Erőnléti edző', 6),
(3, 'edzo.janos@gmail.com', 'Edző', 'János', NULL, 7),
(4, 'edzo.aniko@gmail.com', 'Edző', 'Anikó', NULL, 8);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `online` tinyint(4) DEFAULT 0,
  `password` varchar(255) DEFAULT NULL,
  `profile_picture_name` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- A tábla adatainak kiíratása `user`
--

INSERT INTO `user` (`id`, `online`, `password`, `profile_picture_name`, `username`) VALUES
(6, 1, '$2a$10$i7JzjzQQEIdL4VOyUAZH8OstvcxFcHLfxPGQyPacgZzl4znpqKD5a', 'fad8aa00-f5e5-404b-a2b8-f19d8d02b0f0_avatar1-1024x576.jpg', 'edzo.bela'),
(7, 0, '$2a$10$0qR7uSsKRNagCEVZF/EPTuT6ZckR33r0ERwz.usiMc4g4JzccsgLm', NULL, 'edzo.janos'),
(8, 0, '$2a$10$yCkeSvgCY1BHWTbWMZO7Mu965eps3uDxsQzY5ZTAGSt3.1.b9e.0.', NULL, 'edzo.aniko'),
(9, 0, '$2a$10$Zej7XhkgJYfjsW0W3/.Yp.CA8otPR59c1DstF8lXkcT9iHbNFMl1O', NULL, 'vendeg.david@gmail.com'),
(10, 0, '$2a$10$ImlMpWLUjJorfDjVIMyRtugpWQZwQNo8qYGwmTNGdIuJMv8xYKcjy', NULL, 'vendeg.mate'),
(11, 1, '$2a$10$hZx0sc902TbhUfDtkRa70eMzBQx79PofbHOHg8ysGT58Evik8oj6S', '5cf41671-d861-4598-b7b3-0dbb8e1647cd_avatar1-1024x576.jpg', 'vendeg.erika'),
(12, 0, '$2a$10$tkeL42HeLeEWOApcFbf90OgbMJlyjaw9NBCcE1joCvJP5mniEVccm', NULL, 'vendeg.ivett');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `workout`
--

CREATE TABLE `workout` (
  `id` int(11) NOT NULL,
  `date` date DEFAULT NULL,
  `done` tinyint(4) DEFAULT 0,
  `repetitions` int(11) NOT NULL,
  `sets` int(11) NOT NULL,
  `exercise_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- A tábla adatainak kiíratása `workout`
--

INSERT INTO `workout` (`id`, `date`, `done`, `repetitions`, `sets`, `exercise_id`) VALUES
(3, '2024-12-05', 0, 10, 3, 2),
(4, '2024-12-06', 0, 10, 3, 3),
(5, '2024-12-06', 0, 12, 3, 9),
(6, '2024-12-06', 0, 12, 3, 10);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `workout_guest`
--

CREATE TABLE `workout_guest` (
  `id` int(11) NOT NULL,
  `guest_id` int(11) DEFAULT NULL,
  `trainer_id` int(11) DEFAULT NULL,
  `workout_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- A tábla adatainak kiíratása `workout_guest`
--

INSERT INTO `workout_guest` (`id`, `guest_id`, `trainer_id`, `workout_id`) VALUES
(3, 4, NULL, 3),
(4, 4, NULL, 4),
(5, 4, NULL, 5),
(6, 4, NULL, 6);

--
-- Indexek a kiírt táblákhoz
--

--
-- A tábla indexei `diet`
--
ALTER TABLE `diet`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKekwdser3y7iwtdqk5dh0j9q1j` (`food_id`);

--
-- A tábla indexei `diet_guest`
--
ALTER TABLE `diet_guest`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKjah9yl2lccs65jlolropaekv8` (`diet_id`),
  ADD KEY `FKil1wh9agam9bnq7hdvrm9j3bk` (`guest_id`),
  ADD KEY `FKbgt3ivtrd2k7eluutnlk0opd2` (`trainer_id`);

--
-- A tábla indexei `diet_recommedation`
--
ALTER TABLE `diet_recommedation`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK9m9245p3u6yy1mfwnr3gnwyi8` (`guest_id`),
  ADD KEY `FKmoo0ejpa7t74fcaa7g2o0f5tt` (`trainer_id`);

--
-- A tábla indexei `exercise`
--
ALTER TABLE `exercise`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `food`
--
ALTER TABLE `food`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `guest`
--
ALTER TABLE `guest`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_chdaocwoknpkpjjcb6dyv8os8` (`user_id`),
  ADD KEY `FK66p723qo5k1q8161bgtvoeirw` (`trainer_id`);

--
-- A tábla indexei `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKh4xy93vijopygnpjqbe0c9iew` (`receiver_user_id`),
  ADD KEY `FK80flimpheqbm2ex5r6ng1iodk` (`sender_user_id`);

--
-- A tábla indexei `notification`
--
ALTER TABLE `notification`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKgn96bwik5hltymyqmej84vk1k` (`guest_id`),
  ADD KEY `FKcrin3dmrrnutxfydnaecem7mq` (`trainer_id`);

--
-- A tábla indexei `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK61g3ambult7v7nh59xirgd9nf` (`user_id`);

--
-- A tábla indexei `trainer`
--
ALTER TABLE `trainer`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_geuofcxp00v2rcu9fj9g7olue` (`user_id`);

--
-- A tábla indexei `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `workout`
--
ALTER TABLE `workout`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKkq7p5kgarl06to8age6x62ahn` (`exercise_id`);

--
-- A tábla indexei `workout_guest`
--
ALTER TABLE `workout_guest`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKn6cpcn5bjyq7q7nv905va7gcw` (`guest_id`),
  ADD KEY `FK8eh5bee15v9wmbnuoxs1ugp95` (`trainer_id`),
  ADD KEY `FK64dtmcuh7w8uasifjyw8s39t3` (`workout_id`);

--
-- A kiírt táblák AUTO_INCREMENT értéke
--

--
-- AUTO_INCREMENT a táblához `diet`
--
ALTER TABLE `diet`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT a táblához `diet_guest`
--
ALTER TABLE `diet_guest`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT a táblához `diet_recommedation`
--
ALTER TABLE `diet_recommedation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT a táblához `exercise`
--
ALTER TABLE `exercise`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT a táblához `food`
--
ALTER TABLE `food`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT a táblához `guest`
--
ALTER TABLE `guest`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT a táblához `message`
--
ALTER TABLE `message`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT a táblához `notification`
--
ALTER TABLE `notification`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT a táblához `role`
--
ALTER TABLE `role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT a táblához `trainer`
--
ALTER TABLE `trainer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT a táblához `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT a táblához `workout`
--
ALTER TABLE `workout`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT a táblához `workout_guest`
--
ALTER TABLE `workout_guest`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Megkötések a kiírt táblákhoz
--

--
-- Megkötések a táblához `diet`
--
ALTER TABLE `diet`
  ADD CONSTRAINT `FKekwdser3y7iwtdqk5dh0j9q1j` FOREIGN KEY (`food_id`) REFERENCES `food` (`id`);

--
-- Megkötések a táblához `diet_guest`
--
ALTER TABLE `diet_guest`
  ADD CONSTRAINT `FKbgt3ivtrd2k7eluutnlk0opd2` FOREIGN KEY (`trainer_id`) REFERENCES `trainer` (`id`),
  ADD CONSTRAINT `FKil1wh9agam9bnq7hdvrm9j3bk` FOREIGN KEY (`guest_id`) REFERENCES `guest` (`id`),
  ADD CONSTRAINT `FKjah9yl2lccs65jlolropaekv8` FOREIGN KEY (`diet_id`) REFERENCES `diet` (`id`);

--
-- Megkötések a táblához `diet_recommedation`
--
ALTER TABLE `diet_recommedation`
  ADD CONSTRAINT `FK9m9245p3u6yy1mfwnr3gnwyi8` FOREIGN KEY (`guest_id`) REFERENCES `guest` (`id`),
  ADD CONSTRAINT `FKmoo0ejpa7t74fcaa7g2o0f5tt` FOREIGN KEY (`trainer_id`) REFERENCES `trainer` (`id`);

--
-- Megkötések a táblához `guest`
--
ALTER TABLE `guest`
  ADD CONSTRAINT `FK66p723qo5k1q8161bgtvoeirw` FOREIGN KEY (`trainer_id`) REFERENCES `trainer` (`id`),
  ADD CONSTRAINT `FKake2867xxr6o753o6kqc4rott` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Megkötések a táblához `message`
--
ALTER TABLE `message`
  ADD CONSTRAINT `FK80flimpheqbm2ex5r6ng1iodk` FOREIGN KEY (`sender_user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKh4xy93vijopygnpjqbe0c9iew` FOREIGN KEY (`receiver_user_id`) REFERENCES `user` (`id`);

--
-- Megkötések a táblához `notification`
--
ALTER TABLE `notification`
  ADD CONSTRAINT `FKcrin3dmrrnutxfydnaecem7mq` FOREIGN KEY (`trainer_id`) REFERENCES `trainer` (`id`),
  ADD CONSTRAINT `FKgn96bwik5hltymyqmej84vk1k` FOREIGN KEY (`guest_id`) REFERENCES `guest` (`id`);

--
-- Megkötések a táblához `role`
--
ALTER TABLE `role`
  ADD CONSTRAINT `FK61g3ambult7v7nh59xirgd9nf` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Megkötések a táblához `trainer`
--
ALTER TABLE `trainer`
  ADD CONSTRAINT `FKbfajghb0yafuemho3rlbsa68h` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Megkötések a táblához `workout`
--
ALTER TABLE `workout`
  ADD CONSTRAINT `FKkq7p5kgarl06to8age6x62ahn` FOREIGN KEY (`exercise_id`) REFERENCES `exercise` (`id`);

--
-- Megkötések a táblához `workout_guest`
--
ALTER TABLE `workout_guest`
  ADD CONSTRAINT `FK64dtmcuh7w8uasifjyw8s39t3` FOREIGN KEY (`workout_id`) REFERENCES `workout` (`id`),
  ADD CONSTRAINT `FK8eh5bee15v9wmbnuoxs1ugp95` FOREIGN KEY (`trainer_id`) REFERENCES `trainer` (`id`),
  ADD CONSTRAINT `FKn6cpcn5bjyq7q7nv905va7gcw` FOREIGN KEY (`guest_id`) REFERENCES `guest` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
