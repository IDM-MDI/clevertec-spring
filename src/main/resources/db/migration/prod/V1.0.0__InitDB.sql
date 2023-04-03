CREATE TABLE gift_certificate (
                                  id SERIAL PRIMARY KEY,
                                  name VARCHAR(255) NOT NULL,
                                  description VARCHAR(255) NOT NULL,
                                  price NUMERIC(10, 2) NOT NULL,
                                  duration BIGINT NOT NULL,
                                  create_date TIMESTAMP WITH TIME ZONE NOT NULL,
                                  update_date TIMESTAMP WITH TIME ZONE NOT NULL,
                                  status VARCHAR(255) NOT NULL
);

CREATE TABLE tag (
                     id SERIAL PRIMARY KEY,
                     name VARCHAR(255) NOT NULL,
                     status VARCHAR(255) NOT NULL
);

CREATE TABLE gift_tag (
                          id SERIAL PRIMARY KEY,
                          gift_id BIGINT NOT NULL,
                          tag_id BIGINT NOT NULL,
                          CONSTRAINT unique_constraint_name UNIQUE (gift_id, tag_id),
                          CONSTRAINT gift_tag_giftID_fkey FOREIGN KEY (gift_id) REFERENCES gift_certificate (id) ON DELETE CASCADE,
                          CONSTRAINT gift_tag_tagID_fkey FOREIGN KEY (tag_id) REFERENCES tag (id) ON DELETE CASCADE
);
