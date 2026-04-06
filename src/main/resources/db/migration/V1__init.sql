create table if not exists persons (
	id varchar(255) primary key,
	nombre varchar(255) not null,
	telefono varchar(50) not null,
	correo_electronico varchar(255) not null,
	direccion varchar(500) not null,
	referencias varchar(500)
);

create table if not exists shipments (
	id_envio bigserial primary key,
	sender_id varchar(255),
	recipient_id varchar(255),
	tipo_servicio varchar(100) not null,
	nivel_prioridad integer not null,
	codigo_rastreo varchar(100) not null unique,
	estado_actual varchar(50) not null,
	fecha_envio date not null,
	fecha_estimada date,
	fecha_actualizacion date,
	costo_total numeric(12,2) not null,
	instrucciones_envio varchar(1000),
	constraint fk_shipments_sender foreign key (sender_id) references persons(id),
	constraint fk_shipments_recipient foreign key (recipient_id) references persons(id)
);

create table if not exists packages (
	id_paquete bigserial primary key,
	shipment_id bigint not null unique,
	peso numeric(10,2) not null,
	largo numeric(10,2) not null,
	ancho numeric(10,2) not null,
	alto numeric(10,2) not null,
	descripcion varchar(500),
	constraint fk_packages_shipments foreign key (shipment_id) references shipments(id_envio)
);

create table if not exists logistic_events (
	id_evento bigserial primary key,
	shipment_id bigint not null,
	tipo_evento varchar(100) not null,
	estado_resultante varchar(50) not null,
	ubicacion varchar(255) not null,
	observacion varchar(1000),
	fecha_evento timestamp not null,
	constraint fk_logistic_events_shipments foreign key (shipment_id) references shipments(id_envio)
);

create index if not exists idx_shipments_sender_id on shipments(sender_id);
create index if not exists idx_shipments_recipient_id on shipments(recipient_id);
create index if not exists idx_packages_shipment_id on packages(shipment_id);
create index if not exists idx_logistic_events_shipment_id on logistic_events(shipment_id);
