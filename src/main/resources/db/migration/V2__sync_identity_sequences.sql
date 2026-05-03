select setval(pg_get_serial_sequence('shipments', 'id_envio'), coalesce((select max(id_envio) from shipments), 0) + 1, false);
select setval(pg_get_serial_sequence('packages', 'id_paquete'), coalesce((select max(id_paquete) from packages), 0) + 1, false);
select setval(pg_get_serial_sequence('logistic_events', 'id_evento'), coalesce((select max(id_evento) from logistic_events), 0) + 1, false);
