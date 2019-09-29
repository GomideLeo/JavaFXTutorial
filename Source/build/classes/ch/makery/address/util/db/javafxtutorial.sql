-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Sep 29, 2019 at 11:13 PM
-- Server version: 5.7.24
-- PHP Version: 7.2.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `javafxtutorial`
--

-- --------------------------------------------------------

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
CREATE TABLE IF NOT EXISTS `person` (
  `FirstName` varchar(50) NOT NULL,
  `LastName` varchar(100) NOT NULL,
  `Street` varchar(250) NOT NULL,
  `PostalCode` int(11) NOT NULL,
  `City` varchar(100) NOT NULL,
  `Birthday` date NOT NULL,
  PRIMARY KEY (`FirstName`,`LastName`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `person`
--

INSERT INTO `person` (`FirstName`, `LastName`, `Street`, `PostalCode`, `City`, `Birthday`) VALUES
('Hans', 'Muster', 'street', 132, 'alala', '2019-09-10'),
('DUdinha', 'Pastel', 'asdcfd', 8563, 'asdfxcvfsrweq', '2019-09-01'),
('Leo', 'Gomide', 'asdfregtfdvbdrhtbrt', 8754120, 'qwertzguhgtfd', '2019-09-30'),
('awsdfghmjgfdsa', 'sfdghjgfdrsa', 'asdfghfdraws', 13131, 'ACDSVFBGNMGNFDBVSA', '2019-09-18'),
('lasadj', 'Muster', 'street', 1325202525, 'alala', '2019-09-10'),
('lasadjsad', 'Muster', 'street', 1325202525, 'alala', '2019-09-10'),
('DUdinhasad', 'Pastel', 'asdcfd', 1234567981, 'asdfxcvfsrweq', '2019-09-01'),
('lasadjsadad', 'Muster', 'street', 1325202525, 'alala', '2019-09-10'),
('DUdinhasadda', 'Pastel', 'asdcfd', 1234567981, 'asdfxcvfsrweq', '2019-09-01'),
('Leoad', 'Gomide', 'asdfregtfdvbdrhtbrt', 875412055, 'qwertzguhgtfd', '2019-09-30');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
