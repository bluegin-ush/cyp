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
INSERT INTO usuario (id,nombre,usuario,clave,rol) VALUES
(1,'administrador','admin','c8b61d46c9a5f81d0aba1b6cf98a405c8d69b19c973d0db713a4e34f5aa83640','admin'),
(2,'silvia','silvia','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','usuario'),
(3,'invitado','invitado','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','invitado');
ALTER SEQUENCE usuario_seq RESTART WITH 4;


INSERT INTO configuracion (id,clave, valor) VALUES
(1,'desa-endpoint',    'https://wsaahomo.afip.gov.ar/ws/services/LoginCms'),
(2,'desa-certPath',    'certificados/afip-cyp.pem'),
(3,'desa-keyPath',     'certificados/clave-prueba'),
(4,'desa-service',     'wsfe'),
(5,'desa-threshold',   '12'),
(6,'desa-expiration',  '12'),
(7,'desa-dn',          'SERIALNUMBER=CUIT 20290833869, CN=cyp'),
(8,'desa-cuitEmisor',  '20290833869'),
(9,'desa-puntoDeVenta','1'),
(10,'modo','desa'),
(11,'prod-endpoint',    'https://'),
(12,'prod-certPath',    'certificados/afip-cyp.pem'),
(13,'prod-keyPath',     'certificados/clave-prueba'),
(14,'prod-service',     ''),
(15,'prod-threshold',   '12'),
(16,'prod-expiration',  '12'),
(17,'prod-dn',          'SERIALNUMBER=CUIT 20290833869, CN=cyp'),
(18,'prod-cuitEmisor',  '2012345678'),
(19,'prod-puntoDeVenta','10');


ALTER SEQUENCE configuracion_seq RESTART WITH 20;

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

--
INSERT INTO entidadCrediticia(id,tipo, nombre, archivo, cuit, contacto) VALUES
--  51	MASTER AUTOMATICO   (2)
--  52	VISA AUTOMATICO     (1)
--  53	T.D.F. AUTOMATICO   (3)

(1,'tarjeta-credito','visa',true,0,'sin datos de contacto'),
(2,'tarjeta-credito','master card',true,0,'sin datos de contacto'),
(3,'tarjeta-credito','tdf',true,0,'sin datos de contacto'),
(4,'tarjeta-credito','visa',false,0,'sin datos de contacto'),
(5,'tarjeta-credito','master card',false,0,'sin datos de contacto'),
(6,'tarjeta-credito','american express',false,0,'sin datos de contacto'),
(7,'tarjeta-credito','tdf',false,0,'sin datos de contacto'),
(8,'tarjeta-debito','maestro',false,0,'sin datos de contacto'),
(9,'tarjeta-debito','electron',false,0,'sin datos de contacto');

ALTER SEQUENCE entidadCrediticia_seq RESTART WITH 10;

