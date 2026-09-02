-- Orders
INSERT INTO orders (total, status)
VALUES
    (55000.00, 'CONFIRMED'),
    (25700.00, 'PENDING'),
    (18200.00, 'DELIVERED'),
    (4200.00, 'CANCELLED'),
    (75000.00, 'CONFIRMED');

-- Order Items
INSERT INTO order_iteam (product_id, quantity, order_id)
VALUES
    (1, 1, 1),
    (4, 2, 1),

    (2, 1, 2),
    (8, 2, 2),

    (3, 2, 3),
    (6, 1, 3),

    (5, 1, 4),
    (9, 1, 4),

    (1, 1, 5),
    (2, 1, 5);