  INSERT
  INTO
    field
    (label, type, is_required, is_active)
  VALUES
    ('Full Name', 'SINGLE_LINE_TEXT', TRUE, TRUE),
    ('Email', 'SINGLE_LINE_TEXT', TRUE, TRUE),
    ('Sex', 'RADIO_BUTTON', FALSE, TRUE),
    ('Date of birth', 'DATE', FALSE, TRUE),
    ('Sample combobox', 'COMBOBOX', FALSE, TRUE);


  INSERT
  INTO
    option
    (title, field_id)
  VALUES
    ('Male', 3),
    ('Female', 3),
    ('Option 1', 5),
    ('Option 2', 5),
    ('Option 3', 5),
    ('Option 4', 5);


  INSERT
  INTO
  response
     (id)
  VALUES
     (1),
     (2);


  INSERT
  INTO
    answer
    (answer, response_id, field_id)
  VALUES
    ('John Doe', 1, 1),
    ('johndoe@gmail.com', 1, 2),
    ('Male', 1, 3),
    ('1984-01-12', 1, 4),
    ('Option 1', 1, 5),

    ('John Doe', 2, 1),
    ('johndoe@gmail.com', 2, 2),
    ('Female', 2, 3),
    ('1992-08-23', 2, 4);
