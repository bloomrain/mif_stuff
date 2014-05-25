
CREATE TABLE customer (
    cus_id integer DEFAULT nextval('cus_seq'::regclass) NOT NULL,
    cus_lname character varying(255) NOT NULL,
    cus_city character varying(128) NOT NULL,
    cus_gender character(1) NOT NULL,
    cus_fname character varying(128) NOT NULL,
    cus_country character varying(128) NOT NULL
);

CREATE TABLE product (
    pro_id integer DEFAULT nextval('pro_seq'::regclass) NOT NULL,
    pro_brand character varying(255) NOT NULL,
    pro_name character varying(255) NOT NULL
);

CREATE TABLE sales (
    sal_pro_id integer NOT NULL,
    sal_time timestamp without time zone,
    sal_cus_id integer NOT NULL,
    sal_cost double precision NOT NULL,
    sal_units integer NOT NULL,
    sal_id integer DEFAULT nextval('sal_seq'::regclass) NOT NULL
);

ALTER TABLE ONLY customer
    ADD CONSTRAINT customer_pk PRIMARY KEY (cus_id);

ALTER TABLE ONLY product
    ADD CONSTRAINT pro_pk PRIMARY KEY (pro_id);

ALTER TABLE ONLY sales
    ADD CONSTRAINT sal_pk PRIMARY KEY (sal_id);

ALTER TABLE ONLY sales
    ADD CONSTRAINT sal_cus_fk FOREIGN KEY (sal_cus_id) REFERENCES customer(cus_id);

ALTER TABLE ONLY sales
    ADD CONSTRAINT sal_pro_fk FOREIGN KEY (sal_pro_id) REFERENCES product(pro_id);
