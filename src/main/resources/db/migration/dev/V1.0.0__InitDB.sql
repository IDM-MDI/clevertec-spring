CREATE TABLE gift_certificate (
                                  id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                  name VARCHAR(255) NOT NULL,
                                  description VARCHAR(255) NOT NULL,
                                  price DECIMAL(10, 2) NOT NULL,
                                  duration BIGINT NOT NULL,
                                  create_date TIMESTAMP WITH TIME ZONE NOT NULL,
                                  update_date TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE gift_tag (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          giftID BIGINT NOT NULL,
                          tagID BIGINT NOT NULL,
                          CONSTRAINT gift_tag_giftID_fkey FOREIGN KEY (giftID) REFERENCES gift_certificate (id) ON DELETE CASCADE,
                          CONSTRAINT gift_tag_tagID_fkey FOREIGN KEY (tagID) REFERENCES tag (id) ON DELETE CASCADE
);

CREATE TABLE tag (
                     id BIGINT PRIMARY KEY AUTO_INCREMENT,
                     name VARCHAR(255) NOT NULL,
                     status VARCHAR(255) NOT NULL
);
