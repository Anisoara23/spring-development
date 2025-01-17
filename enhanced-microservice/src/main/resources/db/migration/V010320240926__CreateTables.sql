CREATE TABLE explorecali.security_role (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  description varchar(100) DEFAULT NULL,
  role_name varchar(100) DEFAULT NULL
);


CREATE TABLE explorecali.security_user (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  first_name varchar(255) NOT NULL,
  last_name varchar(255) NOT NULL
);


CREATE TABLE explorecali.user_role (
  user_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL,
  CONSTRAINT FK_SECURITY_USER_ID FOREIGN KEY (user_id) REFERENCES security_user (id),
  CONSTRAINT FK_SECURITY_ROLE_ID FOREIGN KEY (role_id) REFERENCES security_role (id)
);

CREATE TABLE explorecali.tour_package(
  code CHAR(2) NOT NULL UNIQUE,
  name VARCHAR(50) NOT NULL
);

CREATE TABLE explorecali.tour (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  tour_package_code CHAR(2) NOT NULL,
  title VARCHAR(100) NOT NULL,
  description VARCHAR(2000) NOT NULL,
  blurb VARCHAR(2000) NOT NULL,
  bullets VARCHAR(2000) NOT NULL,
  price VARCHAR(10) not null,
  duration VARCHAR(32) NOT NULL,
  difficulty VARCHAR(16) NOT NULL,
  region VARCHAR(20) NOT NULL,
  keywords VARCHAR(100)
);
ALTER TABLE explorecali.tour ADD FOREIGN KEY (tour_package_code) REFERENCES tour_package(code);


CREATE TABLE explorecali.tour_rating (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tour_id BIGINT,
    customer_id BIGINT,
    score INT,
    comment VARCHAR(100));

ALTER TABLE explorecali.tour_rating ADD FOREIGN KEY (tour_id) REFERENCES tour(id);
ALTER TABLE explorecali.tour_rating ADD CONSTRAINT  MyConstraint UNIQUE(tour_id, customer_id);