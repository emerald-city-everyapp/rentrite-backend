INSERT INTO rental_profiles (address, pics) 
VALUES
    ('123 King Street, London', '{"pic1", "pic2"}'),
    ('492 First Ave, Hamburg', '{}'),
    ('693 MLK, New Salsburry', '{"pic"}');

INSERT INTO comments
    (address, user_name, comment_text, tags)
VALUES
    ('123 King Street, London', 'Janice', 'comment text', '{pet_friendly, parking}'),
    ('492 First Ave, Hamburg', 'Ted', 'comment text', '{shared_walls, mold}'),
    ('693 MLK, New Salsburry', 'Janice', 'other comment', '{}'),
    ('123 King Street, London', 'Janice', 'extra comment info', '{mold}');
        
INSERT INTO users
    (user_name, email)
VALUES
    ('Janice', 'janice@hotmail.com'),
    ('Ted', 'ted@compuserve.net');
