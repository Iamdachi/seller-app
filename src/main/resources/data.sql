-- USERS (seller + commenters)
INSERT INTO users (id, username, email, password)
VALUES (1, 'sellerA', 'a@mail.com', 'x'),
       (2, 'sellerB', 'b@mail.com', 'x'),
       (3, 'john', 'john@mail.com', 'x'),
       (4, 'mike', 'mike@mail.com', 'x');

-- SELLER RATINGS TABLE
INSERT INTO seller_ratings (seller_id, avg_rating, comment_count, updated_at)
VALUES (1, 4.5, 2, CURRENT_TIMESTAMP),
       (2, 3.0, 1, CURRENT_TIMESTAMP);

-- COMMENTS
INSERT INTO comments (id, seller_id, author_id, message, rating, created_at)
VALUES (1, 1, 3, 'Great seller', 5, CURRENT_TIMESTAMP),
       (2, 1, 4, 'Pretty good', 4, CURRENT_TIMESTAMP),
       (3, 2, 3, 'Average experience', 3, CURRENT_TIMESTAMP);
