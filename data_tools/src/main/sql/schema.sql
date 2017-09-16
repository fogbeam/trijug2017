-- create database tables for our historical data which we will use for ML training


-- orders table
drop table if exists orders;

create table orders (
  id bigint,
  user_id bigint,
  promotion_id bigint,
  total_amount bigint,
  submitted_time timestamp,
  promotion_added boolean
);

-- promotions table
drop table if exists promotions;

create table promotions(
  id bigint,
  primary_color text,
  secondary_color text,
  offer_type_id bigint,
  offer_category_id bigint
);


-- users table
drop table if exists users;

create table users (
  id bigint,
  username text,
  state_id bigint
);


-- offer types
drop table if exists offer_types;

create table offer_types(

	id bigint,
	offer_type_name text
);

-- offer product category
drop table if exists offer_product_categories;

create table offer_product_categories (
    id bigint,
    offer_product_category_name text
);


-- states
drop table if exists states;
create table states(

	fips_code int,
	name text,
	abbreviation text
);
