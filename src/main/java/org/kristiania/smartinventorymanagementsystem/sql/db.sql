# CREATE PRODUCT TABLE
CREATE TABLE product (
                         id BIGINT NOT NULL AUTO_INCREMENT,
                         name VARCHAR(255) NOT NULL,
                         quantity INT NOT NULL,
                         price DOUBLE NOT NULL,
                         supplier_id BIGINT NOT NULL,
                         description VARCHAR(255),
                         category VARCHAR(255),
                         PRIMARY KEY (id),
                         FOREIGN KEY (supplier_id) REFERENCES supplier(id)
);
# CREATE SUPPLIER TABLE
CREATE TABLE supplier (
                          id BIGINT NOT NULL AUTO_INCREMENT,
                          name VARCHAR(255) NOT NULL,
                          contact_details VARCHAR(255) NOT NULL,
                          address VARCHAR(255) NOT NULL,
                          supplier_history VARCHAR(255) NOT NULL,
                          PRIMARY KEY (id)
);

# CREATE USER TABLE
CREATE TABLE user (
                      id BIGINT NOT NULL AUTO_INCREMENT,
                      username VARCHAR(255) NOT NULL,
                      password VARCHAR(255) NOT NULL,
                      roles VARCHAR(255) NOT NULL,
                      PRIMARY KEY (id),
                      UNIQUE (username)
);

# CREATE SALE TABLE
CREATE TABLE sale (
                      id BIGINT NOT NULL AUTO_INCREMENT,
                      product_id BIGINT NOT NULL,
                      quantity_sold INT NOT NULL,
                      total_price DOUBLE NOT NULL,
                      sale_date TIMESTAMP NOT NULL,
                      customer VARCHAR(255) NOT NULL,
                      discount DOUBLE NOT NULL,
                      PRIMARY KEY (id),
                      FOREIGN KEY (product_id) REFERENCES product(id)
);