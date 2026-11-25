CREATE DATABASE railway_reservation;

USE railway_reservation;

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('USER', 'ADMIN') DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE trains (
    train_id INT AUTO_INCREMENT PRIMARY KEY,
    train_number VARCHAR(10) UNIQUE NOT NULL,
    train_name VARCHAR(100) NOT NULL,
    source VARCHAR(100) NOT NULL,
    destination VARCHAR(100) NOT NULL
);


CREATE TABLE schedules (
    schedule_id INT AUTO_INCREMENT PRIMARY KEY,
    train_id INT NOT NULL,
    journey_date DATE NOT NULL,
    departure_time TIME NOT NULL,
    arrival_time TIME NOT NULL,

    FOREIGN KEY (train_id)
        REFERENCES trains(train_id)
        ON DELETE CASCADE,

    UNIQUE (train_id, journey_date)
);


CREATE TABLE seats (
    seat_id INT AUTO_INCREMENT PRIMARY KEY,
    schedule_id INT NOT NULL,
    seat_number VARCHAR(10) NOT NULL,
    seat_class VARCHAR(30) NOT NULL,
    status ENUM('AVAILABLE', 'BOOKED') DEFAULT 'AVAILABLE',

    FOREIGN KEY (schedule_id)
        REFERENCES schedules(schedule_id)
        ON DELETE CASCADE,

    UNIQUE (schedule_id, seat_number)
);


CREATE TABLE passengers (
    passenger_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    gender ENUM('MALE', 'FEMALE', 'OTHER') NOT NULL
);


CREATE TABLE bookings (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    schedule_id INT NOT NULL,
    pnr VARCHAR(15) UNIQUE NOT NULL,
    booking_status ENUM(
        'CONFIRMED',
        'RAC',
        'WAITLIST',
        'CANCELLED'
    ) DEFAULT 'CONFIRMED',
    booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10,2) NOT NULL,

    FOREIGN KEY (user_id)
        REFERENCES users(user_id),

    FOREIGN KEY (schedule_id)
        REFERENCES schedules(schedule_id)
);


CREATE TABLE booking_passengers (
    booking_id INT NOT NULL,
    passenger_id INT NOT NULL,
    seat_id INT NULL,

    PRIMARY KEY (booking_id, passenger_id),

    FOREIGN KEY (booking_id)
        REFERENCES bookings(booking_id)
        ON DELETE CASCADE,

    FOREIGN KEY (passenger_id)
        REFERENCES passengers(passenger_id),

    FOREIGN KEY (seat_id)
        REFERENCES seats(seat_id),

    UNIQUE (seat_id)
);


CREATE TABLE payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    booking_id INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_status ENUM(
        'PENDING',
        'SUCCESS',
        'FAILED',
        'REFUNDED'
    ) DEFAULT 'PENDING',
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (booking_id)
        REFERENCES bookings(booking_id)
);

ALTER TABLE users
RENAME COLUMN password TO password_hash;

ALTER TABLE booking_passengers
DROP FOREIGN KEY booking_passengers_ibfk_3;

ALTER TABLE booking_passengers
DROP INDEX seat_id;

ALTER TABLE booking_passengers
ADD CONSTRAINT booking_passengers_ibfk_3
FOREIGN KEY (seat_id)
REFERENCES seats(seat_id);

ALTER TABLE bookings
MODIFY booking_status ENUM(
    'PENDING',
    'CONFIRMED',
    'RAC',
    'WAITLIST',
    'CANCELLED'
) DEFAULT 'PENDING';

UPDATE users
SET role = 'ADMIN'
WHERE user_id = 3;
SELECT *
FROM seats
WHERE schedule_id = (
    SELECT schedule_id
    FROM schedules
    WHERE train_id = 1
    AND journey_date = '2026-11-22'
);

