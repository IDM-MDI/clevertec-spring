CREATE TABLE gift_certificate (
                                  id SERIAL PRIMARY KEY,
                                  name VARCHAR(255) NOT NULL,
                                  description VARCHAR(255) NOT NULL,
                                  price NUMERIC(10, 2) NOT NULL,
                                  duration BIGINT NOT NULL,
                                  create_date TIMESTAMP WITH TIME ZONE NOT NULL,
                                  update_date TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE gift_tag (
                          id SERIAL PRIMARY KEY,
                          giftID BIGINT NOT NULL,
                          tagID BIGINT NOT NULL,
                          CONSTRAINT gift_tag_giftID_fkey FOREIGN KEY (giftID) REFERENCES gift_certificate (id) ON DELETE CASCADE,
                          CONSTRAINT gift_tag_tagID_fkey FOREIGN KEY (tagID) REFERENCES tag (id) ON DELETE CASCADE
);

CREATE TABLE tag (
                     id SERIAL PRIMARY KEY,
                     name VARCHAR(255) NOT NULL,
                     status VARCHAR(255) NOT NULL
);
