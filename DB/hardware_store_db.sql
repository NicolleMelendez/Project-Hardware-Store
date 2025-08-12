-- Para order_detail
ALTER TABLE order_detail DROP PRIMARY KEY;
ALTER TABLE order_detail ADD PRIMARY KEY (id);

-- Para sale_detail
ALTER TABLE sale_detail DROP PRIMARY KEY;
ALTER TABLE sale_detail ADD PRIMARY KEY (id);
order_detail