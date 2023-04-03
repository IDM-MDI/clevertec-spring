CREATE TABLE gift_certificate (
                                  id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                  name VARCHAR(255) UNIQUE NOT NULL,
                                  description VARCHAR(255) NOT NULL,
                                  price DECIMAL(10, 2) NOT NULL,
                                  duration BIGINT NOT NULL,
                                  create_date TIMESTAMP WITH TIME ZONE NOT NULL,
                                  update_date TIMESTAMP WITH TIME ZONE NOT NULL,
                                  status VARCHAR(255) NOT NULL
);

CREATE TABLE tag (
                     id BIGINT PRIMARY KEY AUTO_INCREMENT,
                     name VARCHAR(255) UNIQUE NOT NULL,
                     status VARCHAR(255) NOT NULL
);

CREATE TABLE gift_tag (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          gift_id BIGINT NOT NULL,
                          tag_id BIGINT NOT NULL,
                          CONSTRAINT unique_constraint_name UNIQUE (gift_id, tag_id),
                          CONSTRAINT gift_tag_giftID_fkey FOREIGN KEY (gift_id) REFERENCES gift_certificate (id) ON DELETE CASCADE,
                          CONSTRAINT gift_tag_tagID_fkey FOREIGN KEY (tag_id) REFERENCES tag (id) ON DELETE CASCADE
);
