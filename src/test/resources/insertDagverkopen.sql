insert into dagverkopen(snackId, datum, aantal) values ((select id from snacks where naam='test'), curdate(), 7);
