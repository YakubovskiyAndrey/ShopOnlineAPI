-- Table: ps.smartphones

DROP TABLE IF EXISTS ps.smartphones;

CREATE TABLE IF NOT EXISTS ps.smartphones
(
    id SERIAL NOT NULL,
    model character(50) COLLATE pg_catalog."default" NOT NULL,
    brand_id integer,
    colour character(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT smartphones_pkey PRIMARY KEY (id),
    CONSTRAINT brand_id FOREIGN KEY (brand_id)
        REFERENCES ps.brands (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS ps.smartphones
    OWNER to postgres;