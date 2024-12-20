create sequence Archivo_SEQ start with 1 increment by 50;
create sequence Auditoria_SEQ start with 1 increment by 50;
create sequence Configuracion_SEQ start with 1 increment by 50;
create sequence EntidadCrediticia_SEQ start with 1 increment by 50;
create sequence Factura_SEQ start with 1 increment by 50;
create sequence Item_SEQ start with 1 increment by 50;
create sequence LoteFactura_SEQ start with 1 increment by 50;
create sequence NotaDeCredito_SEQ start with 1 increment by 50;
create sequence Pago_SEQ start with 1 increment by 50;
create sequence Person_SEQ start with 1 increment by 50;
create sequence Registro_SEQ start with 1 increment by 50;
create sequence Salida_SEQ start with 1 increment by 50;
create sequence Servicio_SEQ start with 1 increment by 50;
create sequence Socio_SEQ start with 1 increment by 50;
create sequence Usuario_SEQ start with 1 increment by 50;
create table Archivo (importeTotal numeric(38,2), entidadCrediticia_id bigint, fechaGeneracion timestamp(6), id bigint not null, estado varchar(255) check (estado in ('GENERADO','ENVIADO','PROCESADO','ERROR')), archivo oid, detalleErrores oid, idFacturasProcesadas bigint array, idFacturasRechazadas bigint array, primary key (id));
create table Auditoria (entrada boolean, fecha timestamp(6), id bigint not null, cuerpo TEXT, headers TEXT, metodo varchar(255), queryParams varchar(255), ruta varchar(255), usuario varchar(255), primary key (id));
create table Configuracion (id bigint not null, clave varchar(255), valor TEXT, primary key (id));
create table EntidadCrediticia (archivo boolean, cuit bigint, id bigint not null, contacto varchar(255), nombre varchar(255), tipo varchar(255), primary key (id));
create table Factura (orden integer, total numeric(38,2), vtoCae date, archivo_id bigint, fecha timestamp(6), id bigint not null, loteFactura_id bigint, nroComprobante bigint, socio_id bigint, cae varchar(255), estado varchar(255) check (estado in ('PRE_EMITADA','EMITIDA','PAGADA','CANCELADA','PARCIALMENTE_CANCELADA')), qr TEXT, tipo varchar(255) check (tipo in ('C')), primary key (id));
create table Item (cantidad integer, precio numeric(38,2), factura_id bigint, id bigint not null, concepto varchar(255), primary key (id));
create table LoteFactura (estado smallint check (estado between 0 and 2), progreso integer not null, fechaGeneracion timestamp(6), id bigint not null, idFacturasEmitidas bigint array, idFacturasErroneas bigint array, primary key (id));
create table NotaDeCredito (total numeric(38,2), vtoCae date, factura_id bigint, fecha timestamp(6), id bigint not null, nroComprobante bigint, cae varchar(255), motivo varchar(255), primary key (id));
create table Pago (monto numeric(38,2), factura_id bigint, fecha timestamp(6), id bigint not null, medioDePago varchar(255) check (medioDePago in ('EFECTIVO','TARJETA_CREDITO','TARJETA_DEBITO','TRANSFERENCIA_BANCARIA','CTACTE')), primary key (id));
create table Person (birth date, status smallint check (status between 0 and 1), id bigint not null, name varchar(255), primary key (id));
create table Registro (fecha date, id bigint not null, socio_id bigint, motivo varchar(255), tipo varchar(255) check (tipo in ('ALTA','BAJA')), primary key (id));
create table Salida (monto numeric(38,2), fecha timestamp(6), id bigint not null, descripcion varchar(255), tipo varchar(255) check (tipo in ('EFECTIVO','TRANSFERENCIA','DEBITO')), primary key (id));
create table Servicio (costo numeric(38,2), id bigint not null, codigo varchar(255) unique, descripcion varchar(255), primary key (id));
create table Socio (activo boolean, ctacte numeric(38,2), entidadCrediticia_id bigint, id bigint not null, nro bigint, numDoc bigint, apellido varchar(255), correo varchar(255), domicilio varchar(255), nombre varchar(255), tarjetaNum varchar(255), tarjetaVto varchar(255), telefono varchar(255), tipoDoc varchar(255), primary key (id));
create table socio_servicio (servicio_id bigint not null, socio_id bigint not null);
create table Usuario (id bigint not null, clave varchar(255), nombre varchar(255), rol varchar(255), usuario varchar(255), primary key (id));
alter table if exists Archivo add constraint FKshefelrwa175ai0k313c59pvl foreign key (entidadCrediticia_id) references EntidadCrediticia;
alter table if exists Factura add constraint FKkko7t4higd0opc11vqho3ko8p foreign key (archivo_id) references Archivo;
alter table if exists Factura add constraint FK8b016koc4pi9cajttqaq1h2uf foreign key (loteFactura_id) references LoteFactura;
alter table if exists Factura add constraint FKsndh5pg1iawu5jhqakdoak3e7 foreign key (socio_id) references Socio;
alter table if exists Item add constraint FKixl6lepmkimd1nane1as753nq foreign key (factura_id) references Factura;
alter table if exists NotaDeCredito add constraint FKfr2kwttc5d9tkiidj028mpvfm foreign key (factura_id) references Factura;
alter table if exists Pago add constraint FK9fgbjd1lfotumdl3s2i4gcxub foreign key (factura_id) references Factura;
alter table if exists Registro add constraint FKepng2nxka5baiwqhdxmhhirqe foreign key (socio_id) references Socio;
alter table if exists Socio add constraint FK1md65er7r22by5kt8grul2w71 foreign key (entidadCrediticia_id) references EntidadCrediticia;
alter table if exists socio_servicio add constraint FKnevkq5r6w0aahsd2qpfuuinm7 foreign key (servicio_id) references Servicio;
alter table if exists socio_servicio add constraint FKh2y6pkspg3shu6404l0xrnbnt foreign key (socio_id) references Socio;