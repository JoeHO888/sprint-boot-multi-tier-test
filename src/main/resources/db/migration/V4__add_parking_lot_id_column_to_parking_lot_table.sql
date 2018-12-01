alter table if exists parking_lot
add column if not exists parking_lot_id varchar(64)  default '' not null