/** SUPPLIER */
DROP TABLE Supplier;
CREATE TABLE Supplier(
	supplierId VARCHAR(10) NOT NULL PRIMARY KEY,
	supplierName VARCHAR(20) NOT NULL,
	phone VARCHAR(11) NOT NULL
);
select * FROM Supplier;

SELECT supplierId, supplierName, phone
FROM Supplier order by supplierId 
OFFSET 2 ROWS FETCH NEXT 5 ROWS ONLY 

UPDATE Supplier  
SET supplierName=?,phone=?
WHERE supplierId = 'OPT_000003'
--------------------------------------------------------------------------
--------------------------------------------------------------------------
/** CUSTOMER */
select customerId,isAdmin,sphinxId,password,name,address,phone,del 
FROM Customer;
select MAX(customerId) 
FROM Customer;


insert into Customer
(customerId,isAdmin,sphinxId,password,name,address,phone,del) 
values();

UPDATE Customer  
SET isAdmin=true,password=?,name=?,address=?,phone=?,del=?
WHERE customerId = 'CUST000001'

--------------------------------------------------------------------------
--------------------------------------------------------------------------
/**CATEGORY*/
DROP TABLE CATEGORY;
create table category(
	categoryId  VARCHAR(10) NOT NULL PRIMARY KEY,
	categoryName  VARCHAR(30) NOT NULL ,
	parentId  VARCHAR(10) NOT NULL ,
	del BOOLEAN DEFAULT false NOT NULL,
	categoryGroup VARCHAR(3) NOT NULL
)
select * FROM CATEGORY;
DELETE FROM CATEGORY WHERE CATEGORYid ='SUP_200000';
insert into CATEGORY(categoryId,categoryName,parentId,categoryGroup) 
	values('CLO_100000','의류', '0', 'CLO');
insert into CATEGORY(categoryId,categoryName,parentId,categoryGroup) 
	values('CLO_110000','상의', 'CLO_100000', 'CLO');
insert into CATEGORY(categoryId,categoryName,parentId,categoryGroup) 
	values('CLO_120000','한벌옷', 'CLO_100000', 'CLO');
insert into CATEGORY(categoryId,categoryName,parentId,categoryGroup) 
	values('SUPP100000','용품', '0', 'SUPP');
SELECT categoryId, categoryName, parentId, categoryGroup FROM category WHERE parentId = '0' AND del=false
SELECT categoryId, categoryName, parentId, categoryGroup FROM category	WHERE categoryId = '1' AND del=false
--------------------------------------------------------------------------
--------------------------------------------------------------------------
/**PRODUCT*/
DROP TABLE PRODUCT;
CREATE TABLE PRODUCT(
	productId  VARCHAR(10) NOT NULL PRIMARY KEY,
	productName  VARCHAR(50) NOT NULL,
	price  DOUBLE DEFAULT 0.0 NOT NULL,
	regDate  DATE NOT NULL,
	del  BOOLEAN DEFAULT false NOT NULL,
	content  VARCHAR(2000) NOT NULL,
	fileName  VARCHAR(200) NOT NULL,
	comment  VARCHAR(100) NOT NULL
)
select productId,productName,price,regDate,content,fileName,comment, del 
FROM PRODUCT;
DELETE FROM hopyorder where orderID = 'O_00000002';
INSERT INTO PRODUCT(productId,productName,price,regDate,content,fileName,comment)
	VALUES ('P0001BLK', '짱 귀여운 고양이 옷',200000,CURRENT_DATE,
	'경로와 글', '고양이 옷','이걸 입을 고양이는 슈퍼스타!!')
	
UPDATE PRODUCT SET productName=?,content=?,fileName=?,comment=?
WHERE productId = ?

select productId, productName,price,regDate,content,fileName,comment
from product, category where productName like ('%멍멍이%')
order by regDate desc offset 0 rows fetch next 5 rows only

select COUNT(productId) from product where productName like ('%멍멍%')
-------------------------------------------------------------------------
--------------------------------------------------------------------------
/** OPTION */
select optionId,productId,optionName,del FROM HopyOption
WHERE optionId='OPT_000001';

select HopyOption.optionId,optionName,OptionValue,addPrice 
FROM HopyOption,OptionValue 
WHERE HopyOption.optionId='OPT_000001';

select optionId,productId,optionName,del FROM HopyOption
WHERE productId='P_0002_PNK';


INSERT INTO HopyOption(optionId,productId,optionName,del)
	VALUES ('OPT_000001', 'P_0002_PNK', '사이즈', false);
	


UPDATE HopyOption  
SET optionName='수정된 옵션 이름',del='false'
WHERE optionId = 'OPT_000003'
--------------------------------------------------------------------------
--------------------------------------------------------------------------
/** OptionValue */
select OptionValueId,optionId,OptionValue,addPrice FROM OptionValue
WHERE optionId='OPT_000001';

INSERT INTO OptionValue(OptionValueId,optionId,OptionValue,addPrice)
	VALUES ('OPTV000001', 'OPT_000001', '220', 2000);
INSERT INTO OptionValue(OptionValueId,optionId,OptionValue,addPrice)
	VALUES ('OPTV000002', 'OPT_000001', '230', 0);
INSERT INTO OptionValue(OptionValueId,optionId,OptionValue,addPrice)
	VALUES ('OPTV000003', 'OPT_000001', '240', 3000);
	
/** optionValueId없이 그냥 해볼까요*/
select optionId,OptionValue,addPrice FROM OptionValue;

INSERT INTO OptionValue(optionId,OptionValue,addPrice)
	VALUES ('OPT_000007', '220', 2000);
INSERT INTO OptionValue(optionId,OptionValue,addPrice)
	VALUES ('OPT_000001', '230', 0);
INSERT INTO OptionValue(optionId,OptionValue,addPrice)
	VALUES ('OPT_000001', '240', 3000);
	
UPDATE OptionValue  
SET OptionValue=?,addPrice=?, del=?
WHERE optionId = 'OPT_000003'
--------------------------------------------------------------------------
--------------------------------------------------------------------------
/**categoryProductJoin*/
create table categoryProductJoin(
	categoryId  VARCHAR(10) NOT NULL,
	productId  VARCHAR(10) NOT NULL
)
select categoryId, productId FROM categoryProductJoin;

INSERT INTO CATEGORYPRODUCTJoin(categoryId, productId) values('category01','P_0001_BLK')
INSERT INTO categoryProductJoin(categoryId, productId)
	values('CLO_110000', 'P_0001_BLK');
	
select productId FROM categoryProductJoin where categoryid='SUPP100000';
select categoryId FROM category where categoryGroup='CLO';

select product.productId, productName, price,regDate,content,fileName,comment 
from product, categoryProductJoin 
where (categoryid='SUPP100000' OR categoryid='CLO_110000')
AND categoryProductJoin.productId = product.productId
order by regDate desc offset 0 rows fetch next 5 rows only

select product.productId, productName, price,regDate,content,fileName,comment 
from product, categoryProductJoin 
where categoryid IN 
(select categoryId FROM category where categoryGroup='CLO')
AND categoryProductJoin.productId = product.productId
order by regDate desc offset 0 rows fetch next 5 rows only

select COUNT(product.productId) 
from product, categoryProductJoin 
where categoryid IN 
(select categoryId FROM category where categoryGroup='CLO')
AND categoryProductJoin.productId = product.productId
				
				
select c.categoryId, c.categoryName, c.parentId, c.categoryGroup 
from category c, categoryProductJoin j
where c.categoryId = j.categoryId and j.productId='P_0001_BLK' and c.del='false';

select p.productId, p.productName from product p, categoryProductJoin j
where p.productId = j.productId and j.categoryId='cate01' and p.del='false';

DELETE FROM categoryProductJoin where productId = 'P_0001_BLK';

SELECT COUNT(productId) FROM categoryProductJoin WHERE categoryId='CLO_110000';
-------------------------------------------------------------------------
-------------------------------------------------------------
DROP TABLE ORDERITEM;
create table orderItem(
	orderItemId varchar(10) not null primary key,
	quantity integer default 1 not null,
	del BOOLEAN DEFAULT false NOT NULL,
	productId varchar(10) not null,
	OrderId varchar(10) not null,
	CONSTRAINT orderItem_productId_FK
	FOREIGN KEY(productId) references product(ProductID),
	CONSTRAINT orderItem_OrderId_FK
	FOREIGN KEY(OrderId) references HOPYOrder(OrderId)
)
insert into orderItem (orderItemid, quantity, productId, orderId) 
values ('oitem2', 4, 'P_0001_BLK', '주문1') 
select * from orderItem;

SELECT orderItemId, quantity, productId, orderId
FROM orderItem WHERE del=false AND orderItemid='OI_0000117'

-------------------------------------------------------------------
-------------------------------------------------------------------
DROP TABLE HOPYORDER;
CREATE TABLE HOPYORDER(
	ORDERId varchar(10) not null primary key,
	orderDate Date not null,
	state varchar(10) not null,
	totalprice  DOUBLE DEFAULT 0.0 NOT NULL,
	customerId VARCHAR(10) NOT NULL,
	del boolean default false not null,
	recipient varchar(10) not null,
	recipientAddr varchar(50) not null,
	memo varchar(30) not null,
	CONSTRAINT HOPYORDER_customerId_FK
	FOREIGN KEY(customerId) references customer(customerId)
)
Insert into hopyorder (orderId, orderDate, state,totalprice, customerId,
recipient, recipientAddr, memo) values('주문9','2017-01-01','준비중',30000,'cust01','manager','manager주소','경비실에 맡겨주세요')
select * from hopyorder;

SELECT count(*) as orderCount FROM HOPYORDER WHERE customerId = 'cust01';
select * from HOPYORDER;

select p.productId, productName,price,regDate,content,fileName,comment from product p, categoryProductJoin cpj  where cpj.categoryid='cate01' order by regDate desc offset 0 rows fetch next 5 rows only
select orderId, orderDate from hopyorder where customerid='cust01' and orderDate>='2017-01-01' and orderDate<='2018-01-25' 
order by orderDate desc offset 5 rows fetch next 5 rows only
offset은 건너뛰자 쿼리문은 몇 개를 보여줄 지 계산
페이지 처리는 서비스 

SELECT COUNT(*) FROM hopyorder
	WHERE orderDate>='0001-01-01' AND orderDate<='2018-02-21'

	SELECT orderItemId FROM orderItem
	WHERE productId = 'PRO_000033'
	
	SELECT orderId FROM orderItem
	WHERE orderItemId = 'OI_0000117'
	
	SELECT orderId FROM orderItem
	WHERE orderItemId IN 
	(SELECT orderItemId FROM orderItem WHERE productId = 'PRO_000033')
	
SELECT hopyorder.orderId, orderDate,state,totalprice,
customerId,recipient,recipientAddr,memo
FROM hopyorder WHERE hopyorder.orderId
IN (SELECT orderId FROM orderItem
	WHERE orderItemId IN 
	(SELECT orderItemId FROM orderItem WHERE productId = 'PRO_000033'))
ORDER BY orderDate DESC offset 0 ROWS fetch next 5 rows only

SELECT COUNT(*)
FROM hopyorder WHERE hopyorder.orderId
IN (SELECT orderId FROM orderItem
	WHERE orderItemId IN 
	(SELECT orderItemId FROM orderItem WHERE productId = 'PRO_000033'))

/** IdGeneratorTables */
--------------------------------------------------------------------------
/** productIdTable */
DROP TABLE productIdTable;
CREATE TABLE productIdTable(
	id 	VARCHAR(10) NOT NULL PRIMARY KEY,
	occupancy 		BOOLEAN 	NOT NULL
);
select id, occupancy FROM productIdTable;
select MAX(id) FROM productIdTable;
select MIN(id) FROM productIdTable WHERE occupancy=false;
INSERT INTO productIdTable(id,occupancy)
	VALUES ('PRO_000001', false);

--------------------------------------------------------------------------
--------------------------------------------------------------------------
/** optionIdTable */
DROP TABLE optionIdTable;
CREATE TABLE optionIdTable(
	id 	VARCHAR(10) NOT NULL PRIMARY KEY,
	occupancy 		BOOLEAN 	NOT NULL
);
select * FROM optionIdTable;
INSERT INTO optionIdTable(id,occupancy)
	VALUES ('OPT_000001', false);

--------------------------------------------------------------------------
	--------------------------------------------------------------------------
/** testTable */
DROP TABLE testTable;
CREATE TABLE testTable(
	id 	VARCHAR(10) NOT NULL PRIMARY KEY,
	occupancy 		BOOLEAN 	NOT NULL
);
select * FROM optionIdTable;
INSERT INTO optionIdTable(id,occupancy)
	VALUES ('OPT_000001', false);

--------------------------------------------------------------------------
--------------------------------------------------------------------------
/** orderItemIdTable */
DROP TABLE orderItemIdTable;
CREATE TABLE orderItemIdTable(
	id 	VARCHAR(10) NOT NULL PRIMARY KEY,
	occupancy 		BOOLEAN 	NOT NULL
);
select * FROM orderItemIdTable;
INSERT INTO optionIdTable(id,occupancy)
	VALUES ('OPT_000001', false);
--------------------------------------------------------------------------
--------------------------------------------------------------------------
/** orderIdTable */
DROP TABLE orderIdTable;
CREATE TABLE orderIdTable(
	id 	VARCHAR(10) NOT NULL PRIMARY KEY,
	occupancy 		BOOLEAN 	NOT NULL
);
select * FROM orderIdTable;
INSERT INTO optionIdTable(id,occupancy)
	VALUES ('OPT_000001', false);
--------------------------------------------------------------------------
--------------------------------------------------------------------------
/** customerIdTable */
DROP TABLE customerIdTable;
CREATE TABLE customerIdTable(
	id 	VARCHAR(10) NOT NULL PRIMARY KEY,
	occupancy 		BOOLEAN 	NOT NULL
);
select * FROM customerIdTable;
INSERT INTO customerIdTable(id,occupancy)
	VALUES ('CUST000012',true);

--------------------------------------------------------------------------