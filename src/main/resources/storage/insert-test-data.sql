INSERT INTO rental_profiles (address, pics) 
VALUES
    ('123 King Street, London', '{"pic1", "pic2"}'),
    ('492 First Ave, Hamburg', '{}'),
    ('693 MLK, New Salsburry', '{"pic"}');

INSERT INTO comments
    (comment_id, address, user_name, comment_text, tags)
VALUES
    (1203, '123 King Street, London', 'Janice', 'comment text', '{pet_friendly, parking}'),
    (1303, '492 First Ave, Hamburg', 'Ted', 'comment text', '{shared_walls, mold}'),
    (156, '693 MLK, New Salsburry', 'Janice', 'other comment', '{}'),
    (1204, '123 King Street, London', 'Janice', 'extra comment info', '{mold}');
        
INSERT INTO users
    (user_name, email)
VALUES
    ('Janice', 'janice@hotmail.com'),
    ('Ted', 'ted@compuserve.net');
