-- Table: ps.brands

DROP TABLE IF EXISTS ps.brands;

CREATE TABLE IF NOT EXISTS ps.brands
(
    id SERIAL NOT NULL,
    name character(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT brands_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS ps.brands
    OWNER to postgres;