                                              Table "public.investees"
        Column         |            Type             | Collation | Nullable |                Default                
-----------------------+-----------------------------+-----------+----------+---------------------------------------
 id                    | bigint                      |           | not null | nextval('investees_id_seq'::regclass)
 inv_coun_abbreviation | character varying(50)       |           | not null | 
 company_name          | character varying(255)      |           | not null | 
 investee_type_id      | bigint                      |           | not null | 
 network_id            | bigint                      |           |          | 
 address               | character varying(255)      |           | not null | 
 region_id             | bigint                      |           | not null | 
 country_id            | bigint                      |           | not null | 
 website               | character varying(255)      |           |          | 
 financial_year_end_id | bigint                      |           | not null | 
 io_username           | character varying(50)       |           | not null | 
 io_back_up_username   | character varying(50)       |           | not null | 
 document_id           | bigint                      |           | not null | 
 follow_up             | character varying(800)      |           | not null | 
 follow_up_deadline    | timestamp without time zone |           | not null | 
Indexes:
    "investees_pkey" PRIMARY KEY, btree (id)
    "investees_company_name_key" UNIQUE CONSTRAINT, btree (company_name)
Foreign-key constraints:
    "investees_country_id_fkey" FOREIGN KEY (country_id) REFERENCES countries(id)
    "investees_document_id_fkey" FOREIGN KEY (document_id) REFERENCES documents(id)
    "investees_financial_year_end_id_fkey" FOREIGN KEY (financial_year_end_id) REFERENCES months(id)
    "investees_investee_type_id_fkey" FOREIGN KEY (investee_type_id) REFERENCES investee_types(id)
    "investees_network_id_fkey" FOREIGN KEY (network_id) REFERENCES investees(id)
    "investees_region_id_fkey" FOREIGN KEY (region_id) REFERENCES regions(id)
Referenced by:
    TABLE "investees" CONSTRAINT "investees_network_id_fkey" FOREIGN KEY (network_id) REFERENCES investees(id)

