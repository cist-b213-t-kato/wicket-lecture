-- messageテーブルを作る
CREATE TABLE message(
        id bigserial NOT NULL,
        name text,
        body text
);

-- messageテーブルにデータを挿入する
INSERT INTO message(name, body)
  VALUES('John', 'Hi, Im John. Please call me Aki.');