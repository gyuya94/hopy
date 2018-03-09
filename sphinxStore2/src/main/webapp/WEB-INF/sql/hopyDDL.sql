/** SUPPLIER */
DROP TABLE Supplier;
DROP SCHEMA SPHINXSTORE RESTRICT;
SET CURRENT SCHEMA= SPHINX_DERBY
CREATE TABLE Supplier(
	supplierId VARCHAR(10) NOT NULL PRIMARY KEY,
	supplierName VARCHAR(20) NOT NULL,
	phone VARCHAR(11) NOT NULL,
	del boolean NOT NULL
);
select * FROM Supplier;
SELECT COUNT(*) FROM supplier;
<<<<<<< .mine
--------------------------------------------------------------------------
--------------------------------------------------------------------------
/** CUSTOMER */
DROP TABLE Customer;
CREATE TABLE Customer(
	customerId VARCHAR(10) NOT NULL PRIMARY KEY,
	isAdmin BOOLEAN NOT NULL,
	sphinxId VARCHAR(15) NOT NULL ,
	password VARCHAR(15) NOT NULL,
	name VARCHAR(15) NOT NULL,
	address VARCHAR(100) NOT NULL,
	phone VARCHAR(11) NOT NULL,
	del BOOLEAN NOT NULL,
	joinDate DATE NOT NULL
);
Insert into Customer(customerId, isAdmin, sphinxId,password, name, address,
phone, del, joinDate) values('cust01',false,'gyu','gyu','hoho','주소','324-1032',false,current_date)
select * FROM Customer;
--------------------------------------------------------------------------||||||| .r1051
--------------------------------------------------------------------------=======
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
insert into CATEGORY(categoryId,categoryName,parentId,categoryGroup) values('CLO_000001','의의류', '0', 'CLO')
SELECT categoryId, categoryName, parentId, categoryGroup FROM category WHERE categoryName like '%의류%' AND del=false
SELECT categoryId, categoryName, parentId, categoryGroup FROM category WHERE parentId = '0' AND del=false
SELECT categoryId, categoryName, parentId, categoryGroup FROM category	WHERE categoryId = '1' AND del=false
--------------------------------------------------------------------------
/**PRODUCT*/
DROP TABLE PRODUCT;
CREATE TABLE PRODUCT(
	productId	VARCHAR(10)	NOT NULL 		PRIMARY KEY,
	productName	VARCHAR(50) NOT NULL,
	price  		DOUBLE 		DEFAULT 0.0		NOT NULL,
	regDate  	DATE 		NOT NULL,
	del  		BOOLEAN		DEFAULT false 	NOT NULL,
	content  	VARCHAR(2000) NOT NULL,
	fileName 	VARCHAR(200) NOT NULL,
	comment 	VARCHAR(100) NOT NULL
)
select * FROM PRODUCT;
INSERT INTO PRODUCT(productId,productName,price,regDate,content,fileName,comment)
	VALUES ('P_0002_PNK', '엘레강스 고양이옷',800000,CURRENT_DATE,'글', 'B001835753-3.jpg','무도회에 가고 싶은 고양이는 이걸 입으세요!!')
UPDATE PRODUCT SET fileName='B001835753-3.jpg' WHERE productId = 'P_0001_BLK';
UPDATE PRODUCT SET fileName='etcitemban20171222150604.JPEG' WHERE productId = 'P_0002_PNK';
	
--------------------------------------------------------------------------
--------------------------------------------------------------------------
/** OPTION */
DROP TABLE HopyOption;
CREATE TABLE HopyOption(
	optionId 	VARCHAR(10) NOT NULL PRIMARY KEY,
	productId 	VARCHAR(10) NOT NULL,
	optionName 	VARCHAR(20) NOT NULL,
	del 		BOOLEAN 	NOT NULL,
	
	CONSTRAINT HopyOption_productId_FK 
	FOREIGN KEY(productId) REFERENCES Product(productId)
);
select * FROM HopyOption;
INSERT INTO HopyOption(optionId,productId,optionName,del)
	VALUES ('OPT_000002', 'P_0002_PNK', '사이즈', false);

--------------------------------------------------------------------------
--------------------------------------------------------------------------
/** OptionValue */
DROP TABLE OptionValue;
CREATE TABLE OptionValue(
	OptionValueId	INTEGER 	NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	optionId		VARCHAR(10) NOT NULL,
	OptionValue		VARCHAR(10) NOT NULL,
	addPrice		DOUBLE		NOT NULL,
	del				BOOLEAN		NOT NULL,
	
	CONSTRAINT OptionValue_optionId_FK 
	FOREIGN KEY(optionId) REFERENCES HopyOption(optionId)
);
INSERT INTO OptionValue(optionId,OptionValue,addPrice,del)
	VALUES ('OPT_000002', 'S, M, L',0, false);
select * FROM OptionValue;
--------------------------------------------------------------------------
/**categoryProductJoin*/
DROP TABLE categoryProductJoin;
create table categoryProductJoin(
	categoryId  VARCHAR(10) NOT NULL,
	productId  VARCHAR(10) NOT NULL
)
select * from categoryProductJoin;
INSERT INTO CATEGORYPRODUCTJoin(categoryId, productId) values('cate01','P_0001_BLK')

select c.categoryId, c.categoryName, c.parentId, c.categoryGroup 
from category c, categoryProductJoin j
where c.categoryId = j.categoryId and j.productId='P_0001_BLK' and c.del='false';
/**coupon*/
create table coupon(
	couponId VARCHAR(10) NOT NULL PRIMARY KEY,
	couponName VARCHAR(20) NOT NULL,
	expirationDate Date NOT NULL,
	discountPercent DOUBLE DEFAULT 0.0 NOT NULL,
	visibility BOOLEAN DEFAULT TRUE NOT NULL,
	categoryId varchar(10) not null,
		CONSTRAINT coupon_categoryId_FK 
	FOREIGN KEY(categoryId) REFERENCES category(categoryId)
)
CREATE TABLE IssuedCoupons(
	IssuedCouponsId VARCHAR(10) NOT NULL PRIMARY KEY,
	couponId VARCHAR(10) NOT NULL ,	
	couponNumber VARCHAR(10) NOT NULL ,	
	del BOOLEAN DEFAULT false NOT NULL,
	id VARCHAR(10) NOT NULL, 이건 유저 연결해야합니다요 유저 테이블은 한별씨에게 있을 겁니다요
	
	CONSTRAINT IssuedCoupons_couponId_FK 
	FOREIGN KEY(couponId) REFERENCES Coupon(couponId),
	
	CONSTRAINT IssuedCoupons_id_FK 이것도 이름 바꿔주시구요
	FOREIGN KEY(id) REFERENCES HohoUser(id)
);
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



/** IdGeneratorTables */
--------------------------------------------------------------------------
/** productIdTable */
DROP TABLE productIdTable;
CREATE TABLE productIdTable(
	id 	VARCHAR(10) NOT NULL PRIMARY KEY,
	occupancy 		BOOLEAN 	NOT NULL
);
select * FROM productIdTable;
INSERT INTO productIdTable(id,occupancy)
	VALUES ('PRO_000001', false);

--------------------------------------------------------------------------
/** optionIdTable */
DROP TABLE optionIdTable;
CREATE TABLE optionIdTable(
	id 	VARCHAR(10) NOT NULL PRIMARY KEY,
	occupancy 		BOOLEAN 	NOT NULL
);
select * FROM optionIdTable;
INSERT INTO optionIdTable(id,occupancy)
	VALUES ('OPT_000001', 'P_0002_PNK', '사이즈', false);

--------------------------------------------------------------------------
--------------------------------------------------------------------------
/** orderItemIdTable */
DROP TABLE orderIdTable;
CREATE TABLE orderIdTable(
	id 	VARCHAR(10) NOT NULL PRIMARY KEY,
	occupancy 		BOOLEAN 	NOT NULL
);
select * FROM orderIdTable;
INSERT INTO orderItemIdTable(id,occupancy)
	VALUES ('OPT_000001', 'P_0002_PNK', '사이즈', false);

--------------------------------------------------------------------------
--------------------------------------------------------------------------
/** orderItemIdTable */
DROP TABLE orderItemIdTable;
CREATE TABLE orderItemIdTable(
	id 	VARCHAR(10) NOT NULL PRIMARY KEY,
	occupancy 		BOOLEAN 	NOT NULL
);
select * FROM orderItemIdTable;
INSERT INTO orderItemIdTable(id,occupancy)
	VALUES ('OPT_000001', 'P_0002_PNK', '사이즈', false);

--------------------------------------------------------------------------
--------------------------------------------------------------------------
/** optionIdTable */
DROP TABLE orderIdTable;
CREATE TABLE orderIdTable(
	id 	VARCHAR(10) NOT NULL PRIMARY KEY,
	occupancy 		BOOLEAN 	NOT NULL
);
select * FROM orderIdTable;
INSERT INTO orderIdTable(id,occupancy)
	VALUES ('OPT_000001', 'P_0002_PNK', '사이즈', false);

--------------------------------------------------------------------------
--------------------------------------------------------------------------
/** optionIdTable */
DROP TABLE customerIdTable;
CREATE TABLE customerIdTable(
	id 	VARCHAR(10) NOT NULL PRIMARY KEY,
	occupancy 		BOOLEAN 	NOT NULL
);
select * FROM customerIdTable;
INSERT INTO customerIdTable(id,occupancy)
	VALUES ('OPT_000001',false);

--------------------------------------------------------------------------
	--------------------------------------------------------------------------
/** supplierIdTable */
DROP TABLE supplierIdTable;
CREATE TABLE supplierIdTable(
	id 	VARCHAR(10) NOT NULL PRIMARY KEY,
	occupancy 		BOOLEAN 	NOT NULL
);
select * FROM supplierIdTable;
INSERT INTO supplierIdTable(id,occupancy)
	VALUES ('OPT_000001',false);

--------------------------------------------------------------------------