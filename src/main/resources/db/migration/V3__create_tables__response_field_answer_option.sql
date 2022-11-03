CREATE SEQUENCE  IF NOT EXISTS response_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE response (
  id BIGINT NOT NULL DEFAULT nextval('response_id_seq'),
   CONSTRAINT pk_response PRIMARY KEY (id)
);

ALTER SEQUENCE response_id_seq OWNED BY response.id;


CREATE SEQUENCE  IF NOT EXISTS field_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE field (
  id BIGINT NOT NULL DEFAULT nextval('field_id_seq'),
   label TEXT NOT NULL,
   type TEXT NOT NULL,
   is_required BOOLEAN DEFAULT FALSE,
   is_active BOOLEAN DEFAULT TRUE,
   CONSTRAINT pk_field PRIMARY KEY (id)
);

ALTER SEQUENCE field_id_seq OWNED BY field.id;


CREATE SEQUENCE  IF NOT EXISTS answer_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE answer (
  id BIGINT NOT NULL DEFAULT nextval('answer_id_seq'),
   answer TEXT NOT NULL,
   response_id BIGINT,
   field_id BIGINT,
   CONSTRAINT pk_answer PRIMARY KEY (id)
);

ALTER TABLE answer ADD CONSTRAINT FK_ANSWER_ON_FIELD FOREIGN KEY (field_id) REFERENCES field (id);
ALTER TABLE answer ADD CONSTRAINT FK_ANSWER_ON_RESPONSE FOREIGN KEY (response_id) REFERENCES response (id);

ALTER SEQUENCE answer_id_seq OWNED BY answer.id;


CREATE SEQUENCE  IF NOT EXISTS option_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE option (
  id BIGINT NOT NULL DEFAULT nextval('option_id_seq'),
   title TEXT NOT NULL,
   field_id BIGINT,
   CONSTRAINT pk_option PRIMARY KEY (id)
);

ALTER TABLE option ADD CONSTRAINT FK_OPTION_ON_FIELD FOREIGN KEY (field_id) REFERENCES field (id);

ALTER SEQUENCE option_id_seq OWNED BY option.id;
