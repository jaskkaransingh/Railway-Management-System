USE railway_reservation;

INSERT INTO trains (train_number, train_name, source, destination)
VALUES
('12951', 'Mumbai Rajdhani', 'New Delhi', 'Mumbai'),
('12002', 'Shatabdi Express', 'New Delhi', 'Bhopal'),
('12301', 'Howrah Rajdhani', 'Howrah', 'New Delhi'),
('12627', 'Karnataka Express', 'New Delhi', 'Bangalore'),
('12424', 'Dibrugarh Rajdhani', 'New Delhi', 'Dibrugarh');

INSERT INTO schedules (train_id, journey_date, departure_time, arrival_time)
VALUES
(1, '2026-12-01', '16:55:00', '08:35:00'),
(2, '2026-12-01', '06:00:00', '14:25:00'),
(3, '2026-12-01', '16:50:00', '10:00:00'),
(4, '2026-12-01', '20:20:00', '06:40:00'),
(5, '2026-12-01', '16:25:00', '07:00:00');

