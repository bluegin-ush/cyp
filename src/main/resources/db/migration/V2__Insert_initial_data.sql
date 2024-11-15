INSERT INTO usuario (id,nombre,usuario,clave) VALUES
(1,'administrador','admin','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92'),
(2,'daniel aguil','daniel','bd3dae5fb91f88a4f0978222dfd58f59a124257cb081486387cbae9df11fb879'),
(3,'invitado','invitado','bd3dae5fb91f88a4f0978222dfd58f59a124257cb081486387cbae9df11fb879');
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
(10,'desa-ingresosBrutos','123456/7'),
(11,'desa-razonSocial','ASOCIACION CAZA Y PESCA USHUAIA'),
(12,'desa-domicilioComercial','Av. Maipú 822'),
(13,'desa-fechaInicioActividad','01/09/1960'),
(14,'modo','desa'),
(15,'prod-endpoint',    'https://'),
(16,'prod-certPath',    'certificados/afip-cyp-produccion.pem'),
(17,'prod-keyPath',     'certificados/clave-produccion'),
(18,'prod-service',     'wsfe'),
(19,'prod-threshold',   '12'),
(20,'prod-expiration',  '12'),
(21,'prod-dn',          'SERIALNUMBER=CUIT 30675803702, CN=cyp'),
(22,'prod-cuitEmisor',  '30675803702'),
(23,'prod-puntoDeVenta','3'),
(24,'prod-ingresosBrutos','116842/8'),
(25,'prod-razonSocial','ASOCIACION CAZA Y PESCA USHUAIA'),
(26,'prod-domicilioComercial','Av. Maipú 822'),
(27,'prod-fechaInicioActividad','01/09/1960'),
(28,'recargar-configuraciones','false'),
(29,'utiliza-saldo-para-facturar','false');


ALTER SEQUENCE configuracion_seq RESTART WITH 30;

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
(15, '001', 'Cuota Social', 15000),
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