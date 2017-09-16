-- dummied up data to train our ML models

-- states
               
insert into states values(1,'Alabama','AL' );
insert into states values(2,'Alaska','AK' );
insert into states values(4,'Arizona','AZ' );
insert into states values(5,'Arkansas','AR' );
insert into states values(6,'California','CA' );
insert into states values(8,'Colorado','CO' );
insert into states values(9,'Connecticut','CT' );
insert into states values(10,'Delaware','DE' );
insert into states values(11,'District of Columbia','DC' );
insert into states values(12,'Florida','FL' );
insert into states values(13,'Georgia','GA' );
insert into states values(15,'Hawaii','HI' );
insert into states values(16,'Idaho','ID' );
insert into states values(17,'Illinois','IL' );
insert into states values(18,'Indiana','IN' );
insert into states values(19,'Iowa','IA' );
insert into states values(20,'Kansas','KS' );
insert into states values(21,'Kentucky','KY' );
insert into states values(22,'Louisiana','LA' );
insert into states values(23,'Maine','ME' );
insert into states values(24,'Maryland','MD' );
insert into states values(25,'Massachusetts','MA' );
insert into states values(26,'Michigan','MI' );
insert into states values(27,'Minnesota','MN' );
insert into states values(28,'Mississippi','MS' );
insert into states values(29,'Missouri','MO' );
insert into states values(30,'Montana','MT' );
insert into states values(31,'Nebraska','NE' );
insert into states values(32,'Nevada','NV' );
insert into states values(33,'New Hampshire','NH' );
insert into states values(34,'New Jersey','NJ' );
insert into states values(35,'New Mexico','NM' );
insert into states values(36,'New York','NY' );
insert into states values(37,'North Carolina','NC' );
insert into states values(38,'North Dakota','ND' );
insert into states values(39,'Ohio','OH' );
insert into states values(40,'Oklahoma','OK' );
insert into states values(41,'Oregon','OR' );
insert into states values(42,'Pennsylvania','PA' );
insert into states values(44,'Rhode Island','RI' );
insert into states values(45,'South Carolina','SC' );
insert into states values(46,'South Dakota','SD' );
insert into states values(47,'Tennessee','TN' );
insert into states values(48,'Texas','TX' );
insert into states values(49,'Utah','UT' );
insert into states values(50,'Vermont','VT' );
insert into states values(51,'Virginia','VA' );
insert into states values(53,'Washington','WA' );
insert into states values(54,'West Virginia','WV' );
insert into states values(55,'Wisconsin','WI' );
insert into states values(56,'Wyoming','WY' );


-- offer types
insert into offer_types values( 1, 'DISCOUNT');
insert into offer_types values( 2, 'FREE_SHIPPING');

-- offer product categories
insert into offer_product_categories values( 1, 'ICE FISHING');
insert into offer_product_categories values( 2, 'BASS FISHING');
insert into offer_product_categories values( 3, 'HUNTING');
insert into offer_product_categories values( 4, 'CAMPING');
insert into offer_product_categories values( 5, 'RUNNING');
insert into offer_product_categories values( 6, 'WRESTLING');
insert into offer_product_categories values( 7, 'POWERLIFTING');


-- users
insert into users values (1, 'prhodes', 37 );

-- promotions
insert into promotions values( 1, 'FFC3D4', '000000', 1, 7 );


-- orders
insert into orders values(1, 1, 1, 23420, '2017-09-09 11:54:23', false);

