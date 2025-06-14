CREATE TABLE IF NOT EXISTS franchise (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS branch (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    franchise_id INTEGER NOT NULL,
    FOREIGN KEY (franchise_id) REFERENCES franchise(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS branch_product (
    id SERIAL PRIMARY KEY,
    branch_id INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    stock INTEGER NOT NULL DEFAULT 0,
    FOREIGN KEY (branch_id) REFERENCES branch(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE,
    UNIQUE(branch_id, product_id) -- Prevent duplicates
);



