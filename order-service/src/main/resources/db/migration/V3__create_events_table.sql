
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'eventstatus') THEN
        CREATE TYPE eventstatus AS ENUM (
        'ORDER_CREATED',
        'ORDER_DELIVERED',
        'ORDER_CANCELLED',
        'ORDER_PROCESS_FAILED'
        );
    END IF;
END $$;

CREATE TABLE order_events (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    event_status eventstatus NOT NULL ,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP 
);

ALTER TABLE orders 
ADD COLUMN order_events_id UUID UNIQUE, 
ADD CONSTRAINT fk_order_events FOREIGN KEY (order_events_id) REFERENCES order_events(id);
