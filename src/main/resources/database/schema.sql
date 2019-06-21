CREATE SEQUENCE place_seq
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  START WITH 1
  NO CYCLE;

CREATE TABLE place_model (
  placeId int NOT NULL DEFAULT nextval('place_seq'),
  cityId int NOT NULL,
  cityName VARCHAR(16),
  postalCode varchar(8),
  placeTypeId int NOT NULL,
  placeName VARCHAR(128) NOT NULL,
  mapX decimal,
  mapY decimal,
  adress VARCHAR(128),
  normalPrice decimal(5,2) not null,
  hash VARCHAR(136),
  openingTime time,
  closingTime time,
  avgTimeSpent time,
  rating decimal(5,2),
  visitorsNo int,
  followersNo int,
  likesNo int
);