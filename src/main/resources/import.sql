-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;
--insert into person (name,birth) values('Dany','2022-03-10');
--insert into person (name,status) values('Otro dany',0);
--INSERT INTO person (id, birth, name, status) VALUES (1, '1995-09-12', 'Emily Brown', 0);
--ALTER SEQUENCE person_seq RESTART WITH 2;

--
INSERT INTO servicio (id, codigo, descripcion, costo) VALUES
(1, '205', 'Parador - (viernes, sábado, domingo y feriados)', 0),
(2, '2050', 'Parador - (lunes, martes, miércoles y jueves)', 0),
(3, '207', 'Casa Bosque - (viernes, sábado, domingo y feriados)', 0),
(4, '2070', 'Casa Bosque - (lunes, martes, miércoles y jueves)', 0),
(5, '206', 'Contenedores - (viernes, sábado, domingo y feriados)', 0),
(6, '2060', 'Contenedores - (lunes, martes, miércoles y jueves)', 0),
(7, '208', 'Invitados', 0),
(8, '101', 'Guardería Embarcaciones', 0),
(9, '102', 'Guardería de Casillas', 0),
(10, '201', 'Quincho', 0),
(11, '998', 'Limpieza Quincho', 0),
(12, '993', 'Control Remoto', 0),
(13, '997', 'Roturas Varias', 0),
(14, '000', 'Cuota Inscripción', 0),
(15, '001', 'Cuota Social', 0),
(16, '1022', 'Estacionamiento', 0);

-- Actualizar la secuencia para que el próximo ID sea el siguiente disponible
--SELECT setval('servicio_seq', (SELECT MAX(id) FROM servicio) + 1);
ALTER SEQUENCE servicio_seq RESTART WITH 17;