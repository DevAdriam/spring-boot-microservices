-- Ensure the UUID extension exists
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create the `orderstatus` enum type if it doesn't exist
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'orderstatus') THEN
        CREATE TYPE orderstatus AS ENUM (
            'PENDING',
            'CONFIRMED',
            'ONPROGRESS',
            'DELIVERING',
            'SUCCESS',
            'CANCELLED',
            'ERROR'
        );
    END IF;
END $$;

-- Create the `orders` table
CREATE TABLE IF NOT EXISTS orders (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    order_no VARCHAR(255) NOT NULL,
    total DOUBLE PRECISION NOT NULL,
    sub_total DOUBLE PRECISION NOT NULL,
    customername VARCHAR(255),
    customeremail VARCHAR(255),
    customerphone VARCHAR(255),
    location VARCHAR(255) NOT NULL,
    order_status orderstatus NOT NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create the `order_items` table
CREATE TABLE IF NOT EXISTS order_items (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    product_code VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    per_price DOUBLE PRECISION NOT NULL,
    total_price DOUBLE PRECISION NOT NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    order_id UUID NOT NULL,
    CONSTRAINT FK_order_items_orders FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE
);

-- Optionally, you can create indexes on foreign keys for performance
CREATE INDEX IF NOT EXISTS idx_order_items_order_id ON order_items(order_id);
