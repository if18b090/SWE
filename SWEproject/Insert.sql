DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `insertRandom3`()
BEGIN
DECLARE mytime timestamp;

    SET mytime := '2009-01-01 00:00:00';
DROP TABLE IF EXISTS temp_table;
CREATE TABLE temp_table (id serial primary key, temp_val int not null, datum timestamp not null);
 test_loop : LOOP
    IF mytime >= now() THEN
        LEAVE  test_loop;
    END  IF;
        SET mytime = TIMESTAMPADD(HOUR,8,mytime);
        insert into temp_table(temp_val, datum) values((select (rand()*100)-10), mytime);
  END LOOP;
END$$
DELIMITER ;

call insertRandom3();
