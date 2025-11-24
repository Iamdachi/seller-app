-- USERS (seller + commenters)
INSERT INTO users (id, first_name, last_name, email, password, role, email_confirmed, admin_approved, created_at)
VALUES
    (1, 'Seller', 'A', 'a@mail.com', 'x', 'SELLER', TRUE, TRUE, CURRENT_TIMESTAMP),
    (2, 'Seller', 'B', 'b@mail.com', 'x', 'SELLER', TRUE, TRUE, CURRENT_TIMESTAMP),
    (3, 'John', 'Doe', 'john@mail.com', 'x', 'USER', TRUE, TRUE, CURRENT_TIMESTAMP),
    (4, 'Mike', 'Smith', 'mike@mail.com', 'x', 'USER', TRUE, TRUE, CURRENT_TIMESTAMP);

-- SELLER RATINGS TABLE
INSERT INTO seller_ratings (seller_id, avg_rating, comment_count, updated_at)
VALUES
    (1, 4.5, 2, CURRENT_TIMESTAMP),
    (2, 3.0, 1, CURRENT_TIMESTAMP);

-- COMMENTS
INSERT INTO comments (seller_id, author_id, message, rating, created_at)
VALUES
    (1, 3, 'Great seller', 5, CURRENT_TIMESTAMP),
    (1, 4, 'Pretty good', 4, CURRENT_TIMESTAMP),
    (2, 3, 'Average experience', 3, CURRENT_TIMESTAMP);

