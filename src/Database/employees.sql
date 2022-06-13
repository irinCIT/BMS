-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jun 13, 2022 at 09:34 PM
-- Server version: 5.7.31
-- PHP Version: 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `employees`
--

-- --------------------------------------------------------

--
-- Table structure for table `employers`
--

DROP TABLE IF EXISTS `employers`;
CREATE TABLE IF NOT EXISTS `employers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `full_name` varchar(45) NOT NULL,
  `pass` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employers`
--

INSERT INTO `employers` (`id`, `full_name`, `pass`) VALUES
(1, 'Name Surname', 'secret');

-- --------------------------------------------------------

--
-- Table structure for table `registrario`
--

DROP TABLE IF EXISTS `registrario`;
CREATE TABLE IF NOT EXISTS `registrario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `full_name` varchar(45) NOT NULL,
  `password` varchar(10) NOT NULL,
  `balance` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `registrario`
--

INSERT INTO `registrario` (`id`, `full_name`, `password`, `balance`) VALUES
(13, 'Kelvin Berami', 'secretpass', 23012),
(12, 'Irin Vokopola', 'secret', 136600);

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
CREATE TABLE IF NOT EXISTS `transactions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `subject_id` int(11) NOT NULL,
  `date` date NOT NULL,
  `time` time NOT NULL,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transactions`
--

INSERT INTO `transactions` (`id`, `subject_id`, `date`, `time`, `type`) VALUES
(7, 12, '2022-06-05', '14:16:59', 'WITHDRAW - 11'),
(6, 13, '2022-06-05', '14:15:41', 'DEPOSIT + 12'),
(8, 12, '2022-06-05', '14:26:53', 'DEPOSIT + 123400'),
(9, 13, '2022-06-05', '14:33:59', 'DEPOSIT + 12345'),
(10, 13, '2022-06-05', '14:33:59', 'WITHDRAW - 12345'),
(13, 12, '2022-06-05', '22:39:43', 'DEPOSIT + 12345'),
(14, 12, '2022-06-05', '22:39:43', 'WITHDRAW - 12345'),
(15, 12, '2022-06-06', '15:31:49', 'WITHDRAW - 1234'),
(16, 12, '2022-06-06', '15:31:49', 'DEPOSIT + 1234'),
(17, 12, '2022-06-07', '17:27:29', 'DEPOSIT + 1234'),
(18, 12, '2022-06-07', '17:32:20', 'DEPOSIT + 12'),
(19, 12, '2022-06-07', '17:32:20', 'WITHDRAW - 12'),
(20, 12, '2022-06-07', '17:35:26', 'DEPOSIT + 56'),
(21, 12, '2022-06-07', '17:35:26', 'WITHDRAW - 79'),
(24, 12, '2022-06-09', '22:41:27', 'DEPOSIT + 1234'),
(25, 12, '2022-06-09', '22:41:27', 'WITHDRAW - 1234'),
(26, 12, '2022-06-13', '11:56:11', 'DEPOSIT + 123'),
(27, 12, '2022-06-13', '11:56:11', 'WITHDRAW - 123');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
