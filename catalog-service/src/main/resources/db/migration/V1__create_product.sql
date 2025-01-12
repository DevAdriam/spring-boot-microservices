CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create table products (
        id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
        name varchar(255) not null,
        code varchar(255) not null unique,
        description varchar(255),
        price float(53) not null,
        image_url varchar(255),
        created_at timestamp(6) not null DEFAULT CURRENT_TIMESTAMP,
        updated_at timestamp(6) not null DEFAULT CURRENT_TIMESTAMP
    )