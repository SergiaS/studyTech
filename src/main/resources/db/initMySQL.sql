DROP TABLE IF EXISTS customers;

CREATE TABLE customers (
    id  int(11) NOT NULL AUTO_INCREMENT,
    first_name  VARCHAR(45) DEFAULT NULL,
    last_name  VARCHAR(45) DEFAULT NULL,
    email  VARCHAR(45) DEFAULT NULL,
    PRIMARY KEY (id)
);

INSERT INTO customers VALUES
    (1, 'David', 'Adams', 'david@luv2code.com'),
    (2, 'John', 'Doe', 'john@luv2code.com'),
    (3, 'Ajay', 'Rao', 'ajay@luv2code.com'),
    (4, 'Mary', 'Public', 'ajay@luv2code.com'),
    (5, 'Maxwell', 'Dixon', 'max@luv2code.com');

