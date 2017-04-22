-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jul 18, 2016 at 08:00 PM
-- Server version: 10.1.13-MariaDB
-- PHP Version: 5.5.35

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `etbara3`
--

-- --------------------------------------------------------

--
-- Table structure for table `cause`
--

CREATE TABLE `cause` (
  `cause_id` int(11) NOT NULL,
  `org_id` int(11) NOT NULL,
  `name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `cause`
--

INSERT INTO `cause` (`cause_id`, `org_id`, `name`) VALUES
(1, 1, 'Orphans'),
(2, 1, 'Elders'),
(3, 1, 'Sadaka'),
(4, 1, 'Medical Care'),
(5, 1, 'Special Abilities'),
(6, 1, 'Aid'),
(7, 1, 'Blind Care'),
(8, 2, 'Money Zakat'),
(9, 2, 'Sponsor a Child'),
(10, 2, 'Corporate Giving'),
(11, 3, 'Health'),
(12, 3, 'Research'),
(13, 3, 'Education'),
(14, 3, 'Social Solidarity'),
(15, 4, 'Health'),
(16, 4, 'Education'),
(17, 4, 'Poverty'),
(18, 4, 'Development');

-- --------------------------------------------------------

--
-- Table structure for table `city`
--

CREATE TABLE `city` (
  `city_id` int(11) NOT NULL,
  `name` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `city`
--

INSERT INTO `city` (`city_id`, `name`) VALUES
(3, 'Alexandria'),
(5, 'Aswan'),
(1, 'Cairo'),
(2, 'Giza'),
(7, 'Luxor'),
(4, 'Mansoura'),
(6, 'Portsaid');

-- --------------------------------------------------------

--
-- Table structure for table `event`
--

CREATE TABLE `event` (
  `event_id` int(11) NOT NULL,
  `org_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `date` varchar(100) NOT NULL,
  `description` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `event`
--

INSERT INTO `event` (`event_id`, `org_id`, `name`, `date`, `description`) VALUES
(2, 1, 'Workshop', '2016-8-29', 'Workshop to teach kids basic math'),
(3, 2, 'Workshop', '2016-8-27', 'Workshop to enhance kids'' thinking skills'),
(5, 3, 'Awareness', '2016-8-27', 'Health Awareness'),
(7, 4, 'Workshop', '2016-7-27', 'Workshop to teach kids history');

-- --------------------------------------------------------

--
-- Table structure for table `organization`
--

CREATE TABLE `organization` (
  `org_id` int(11) NOT NULL,
  `name` varchar(20) NOT NULL,
  `description` varchar(500) NOT NULL,
  `phone` varchar(50) NOT NULL,
  `photo` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `organization`
--

INSERT INTO `organization` (`org_id`, `name`, `description`, `phone`, `photo`) VALUES
(1, 'Resala', 'Resala Society for charity does a lot of charity-related activities in Egypt, was founded in 1999 as a result of a students initiative in the Faculty of Engineering, Cairo University and then decided to legalize the Society in 29/5/2000 and have many branches that are spread all over the country, represented in more than 60 branches.', '+20 19450', 'http://10.0.2.2/etbara3/images/resala.png'),
(2, 'Hospital 57357', 'Building a sustainable foundation to prevent and combat cancer through Research, Smart education and Quality healthcare provided with passion and justice to alleviate the su?ering  of children  with  cancer  and  their families Free of charge.', '+20 19057', 'http://10.0.2.2/etbara3/images/hospital57357.jpg'),
(3, 'Masr ElKheer', 'An Egyptian non-governmental organization, established in 1993, that aims to serve all Egyptian community needy without any religious or political discrimination, in such a developed and institutional technique . It highly emphasizes importance of Civil work and confirms its influence of the growth of the community economically and socially. The organization relies solely on donations from the Egyptians.', '+20 11-5556-6988', 'http://10.0.2.2/etbara3/images/masrelkheer.jpg'),
(4, 'ElOrman', 'This organization heavily focuses on human development, areas of health, scientific research, social solidarity, and walks of life, in the hope of eliminating unemployment, illiteracy, poverty and diseases.', '+20 16140', 'http://10.0.2.2/etbara3/images/elorman.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `profile`
--

CREATE TABLE `profile` (
  `profile_id` int(11) NOT NULL,
  `city_id` int(11) NOT NULL,
  `email` varchar(20) NOT NULL,
  `name` varchar(15) NOT NULL,
  `password` varchar(15) NOT NULL,
  `phone` int(11) NOT NULL,
  `address` varchar(20) NOT NULL,
  `admin` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `profile`
--

INSERT INTO `profile` (`profile_id`, `city_id`, `email`, `name`, `password`, `phone`, `address`, `admin`) VALUES
(3, 1, 'karimatwa@hot', 'Karim Atwa', 'karimatwa', 1118068525, 'Ali Tawfeek', 0),
(4, 2, 'karimatwa@hot2', 'karim atwa', 'karimatwa', 2255, 'hash', 0),
(5, 1, 'karimatwahdhh', 'jsdjk kdfh', 'mkkk', 255887, 'kaka', 0),
(6, 5, 'as', 'Ahmed Samir', 'asd', 1111, 'Home', 1),
(10, 1, 'mm', 'mina adel', 'mmm', 123456, 'nasr city', 0);

-- --------------------------------------------------------

--
-- Table structure for table `requestmoney`
--

CREATE TABLE `requestmoney` (
  `request_id` int(11) NOT NULL,
  `org_id` int(11) NOT NULL,
  `cause_id` int(11) NOT NULL,
  `profile_id` int(11) NOT NULL,
  `amount` varchar(100) NOT NULL,
  `date` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `requestmoney`
--

INSERT INTO `requestmoney` (`request_id`, `org_id`, `cause_id`, `profile_id`, `amount`, `date`) VALUES
(6, 1, 1, 3, '122', '2016-7-18');

-- --------------------------------------------------------

--
-- Table structure for table `requesttruck`
--

CREATE TABLE `requesttruck` (
  `request_id` int(11) NOT NULL,
  `org_id` int(11) NOT NULL,
  `profile_id` int(11) NOT NULL,
  `description` varchar(500) NOT NULL,
  `date` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `requesttruck`
--

INSERT INTO `requesttruck` (`request_id`, `org_id`, `profile_id`, `description`, `date`) VALUES
(3, 2, 3, 'Truck Please ', '2016-7-28'),
(18, 2, 6, 'Send a truck please to help move winter Clothes', '2016-7-28');

-- --------------------------------------------------------

--
-- Table structure for table `statistics`
--

CREATE TABLE `statistics` (
  `statistic_id` int(11) NOT NULL,
  `data` varchar(600) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `statistics`
--

INSERT INTO `statistics` (`statistic_id`, `data`) VALUES
(1, 'At least 80% of humanity lives on less than $10 a day.\\n\r\nMore than 80 percent of the world population lives in countries where income differentials are widening.\\n\r\nNearly a billion people entered the 21st century unable to read a book or sign their names.\\n\r\nAccording to UNICEF, 22,000 children die each day due to poverty.They die quietly in some of the poorest villages on earth, far removed from the scrutiny and the conscience of the world. Being meek and weak in life makes these dying multitudes even more invisible in death.\\n');

-- --------------------------------------------------------

--
-- Table structure for table `verification`
--

CREATE TABLE `verification` (
  `email` varchar(20) NOT NULL,
  `code` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `volunteer`
--

CREATE TABLE `volunteer` (
  `volunteer_id` int(11) NOT NULL,
  `org_id` int(11) NOT NULL,
  `event_id` int(11) NOT NULL,
  `profile_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `volunteer`
--

INSERT INTO `volunteer` (`volunteer_id`, `org_id`, `event_id`, `profile_id`) VALUES
(6, 4, 7, 3),
(7, 3, 5, 6),
(8, 1, 2, 3);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cause`
--
ALTER TABLE `cause`
  ADD PRIMARY KEY (`cause_id`);

--
-- Indexes for table `city`
--
ALTER TABLE `city`
  ADD PRIMARY KEY (`city_id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `event`
--
ALTER TABLE `event`
  ADD PRIMARY KEY (`event_id`);

--
-- Indexes for table `organization`
--
ALTER TABLE `organization`
  ADD PRIMARY KEY (`org_id`);

--
-- Indexes for table `profile`
--
ALTER TABLE `profile`
  ADD PRIMARY KEY (`profile_id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `requestmoney`
--
ALTER TABLE `requestmoney`
  ADD PRIMARY KEY (`request_id`);

--
-- Indexes for table `requesttruck`
--
ALTER TABLE `requesttruck`
  ADD PRIMARY KEY (`request_id`);

--
-- Indexes for table `statistics`
--
ALTER TABLE `statistics`
  ADD PRIMARY KEY (`statistic_id`);

--
-- Indexes for table `volunteer`
--
ALTER TABLE `volunteer`
  ADD PRIMARY KEY (`volunteer_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cause`
--
ALTER TABLE `cause`
  MODIFY `cause_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
--
-- AUTO_INCREMENT for table `city`
--
ALTER TABLE `city`
  MODIFY `city_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `event`
--
ALTER TABLE `event`
  MODIFY `event_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `organization`
--
ALTER TABLE `organization`
  MODIFY `org_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `profile`
--
ALTER TABLE `profile`
  MODIFY `profile_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT for table `requestmoney`
--
ALTER TABLE `requestmoney`
  MODIFY `request_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `requesttruck`
--
ALTER TABLE `requesttruck`
  MODIFY `request_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
--
-- AUTO_INCREMENT for table `statistics`
--
ALTER TABLE `statistics`
  MODIFY `statistic_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `volunteer`
--
ALTER TABLE `volunteer`
  MODIFY `volunteer_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
