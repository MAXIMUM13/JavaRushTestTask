-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Хост: 127.0.0.1
-- Время создания: Май 22 2016 г., 22:18
-- Версия сервера: 10.1.9-MariaDB
-- Версия PHP: 5.6.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `test`
--

-- --------------------------------------------------------

--
-- Структура таблицы `todos`
--

CREATE TABLE `todos` (
  `id` int(11) NOT NULL DEFAULT '0',
  `description` varchar(255) DEFAULT NULL,
  `done` bit(1) NOT NULL DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Дамп данных таблицы `todos`
--

INSERT INTO `todos` (`id`, `description`, `done`) VALUES
(9, 'todo4', b'0'),
(15, 'todo10', b'0'),
(17, 'todo12', b'0'),
(18, 'todo13', b'1'),
(19, 'todo14', b'0'),
(20, 'todo15', b'0'),
(21, 'todo16', b'0'),
(22, 'todo17', b'0'),
(23, 'todo18', b'0'),
(24, 'todo19', b'0'),
(25, 'todo20', b'0'),
(26, 'todo21', b'1'),
(27, 'todo22', b'0'),
(28, 'todo23', b'0'),
(29, 'todo24', b'0'),
(30, 'todo25', b'0'),
(31, 'todo26', b'0'),
(32, 'todo27', b'0'),
(36, 'todo31', b'0'),
(38, 'df', b'1'),
(39, '123', b'0'),
(40, 'heart', b'1'),
(41, 'two 2', b'0'),
(42, 'one', b'0'),
(43, 'one', b'0'),
(44, 'one', b'0'),
(45, 'one', b'0'),
(46, 'one', b'0'),
(47, 'one', b'0'),
(48, 'two', b'0'),
(49, 'nm', b'0'),
(50, '2', b'0'),
(51, '187', b'0'),
(52, '3', b'0'),
(53, '5', b'0'),
(54, '2', b'0'),
(55, '1', b'0'),
(56, '3', b'0'),
(57, '3', b'0'),
(60, 'me', b'0'),
(84, '1', b'0'),
(85, '2', b'0'),
(87, '1', b'0'),
(89, '123', b'1'),
(91, 'nu', b'1'),
(92, 'hp0', b'0'),
(93, 'n', b'0'),
(94, '123', b'0'),
(95, '13', b'1'),
(96, 'London is the capital of Great Britain =)', b'0'),
(97, '1209', b'0'),
(98, 'lkj', b'0');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
