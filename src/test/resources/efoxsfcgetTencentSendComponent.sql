--exec [efoxsfcgetTencentSendComponent] 'PA10002707'

alter PROCEDURE [dbo].[efoxsfcgetTencentSendComponent]
(
 @pallent VARCHAR(MAX)
)
AS
DECLARE @SN VARCHAR(MAX)
SET @SN=''
DECLARE @sendComponent TABLE(
	Id INT identity(1,1),
	SvrAssetId VARCHAR(100),
	SvrSN VARCHAR(100),
	PartType NVARCHAR(100),
	OriPartPN  VARCHAR(100),
	OriPartSN  VARCHAR(100),
	FW  VARCHAR(100),
	ScanPartPN  VARCHAR(100),
	ScanPartSN  VARCHAR(100),
	NUM	INT
)

DECLARE @sendEfoxComponent TABLE(
	ASSETCODE	VARCHAR(100),
	parentsn	VARCHAR(100),
	ctype	nVARCHAR(100),
	pn		VARCHAR(100),
	sn		VARCHAR(100),
	NUM INT	
)
DECLARE @sendOsComponent TABLE(
	ASSETCODE	VARCHAR(100),
	parentsn	VARCHAR(100),
	o_ctype		nVARCHAR(100),
	o_pn		VARCHAR(100),
	o_sn		VARCHAR(100),
	fw			VARCHAR(100)	,
	NUM INT	
)

DECLARE @sntable TABLE(
	SN	VARCHAR(100)
)
--SET @pallent ='PA10002707'

INSERT INTO @sntable --VALUES('7CE830P1MG')
SELECT sysserialno FROM mfsysproduct WHERE location IN (
SELECT Value FROM dbo.fn_Split(@pallent,',')
)
SELECT @SN=@SN+','+SN FROM @sntable

--SELECT @SN

--从测试的OS中获取原SN原PN和FW等信息，关联获取assercode
insert into @sendOsComponent 
SELECT	T2.Assetcode,T1.* 
FROM	(	
			SELECT		A2.SN AS PARENTSN,A1.CTYPE,A1.PN,A1.SN,A1.FW ,
						ROW_NUMBER() over(PARTITION BY A1.PARENTID,A1.CTYPE order by A1.SN,A1.CTYPE,right(A1.sn,8)) AS NUM   
			FROM		efoxSFCTencentComponent  A1, efoxSFCTencentComponent A2
			WHERE		A1.PARENTID = A2.ID AND A2.SN IN (SELECT SN FROM @sntable)
						AND NOT  (A1.SN='' AND A1.PN='' AND A1.FW='')
) AS T1	LEFT JOIN  TencentAsetcode  T2 ON T1.PARENTSN = T2.SN

--SELECT * FROM @sendOsComponent

--从efox获取扫描的SN和PN等信息，关联获取assercode
INSERT INTO @sendEfoxComponent
SELECT  A2.ASSETCODE,A1.SN,A1.DESCRIPTIONS,A1.PN,A1.CSN,A1.num	
FROM	DBO.fn_getTencentSendComponentAssyDetail(@SN) A1 
		LEFT JOIN TencentAsetcode  A2 ON a1.SN = A2.SN

--SELECT * FROM @sendEfoxComponent


--写入要发送的元器件
insert into @sendComponent(SvrAssetId,SvrSN,PartType,OriPartPN,OriPartSN,FW,ScanPartPN,ScanPartSN,NUM)
SELECT A1.ASSETCODE,parentsn,o_ctype,o_pn,o_sn,fw,'','' ,NUM
FROM @sendOsComponent A1  
WHERE A1.o_ctype<>N'网卡'

UPDATE @sendComponent SET OriPartSN=A2.sn,A1.ScanPartSN = A2.sn
FROM   @sendComponent A1,@sendEfoxComponent A2
where	A1.PartType='CPU' AND A1.PartType=A2.ctype AND A1.NUM =A2.NUM AND A1.SvrSN= A2.parentsn

UPDATE @sendComponent SET OriPartSN=A2.sn, OriPartPN=A2.pn, A1.ScanPartSN = A2.sn
FROM   @sendComponent A1,@sendEfoxComponent A2
where A1.PartType IN (N'背板') AND A1.PartType=A2.ctype AND A1.NUM =A2.NUM AND A1.SvrSN= A2.parentsn


UPDATE @sendComponent SET A1.ScanPartSN = A2.sn
FROM   @sendComponent A1,@sendEfoxComponent A2
where A1.PartType IN (N'硬盘',N'内存',N'电源',N'主板') AND A1.PartType=A2.ctype AND A1.NUM =A2.NUM AND A1.SvrSN= A2.parentsn


--写入cable元器件
INSERT INTO @sendComponent(SvrAssetId,SvrSN,PartType,OriPartPN,OriPartSN,FW,ScanPartPN,ScanPartSN,NUM)
SELECT	B1.ASSETCODE,parentsn,ctype,B2.manufacturerpn,'','','','',NUM
FROM	@sendEfoxComponent B1,mmprodmaster  B2 
WHERE	ctype = 'Cable' AND B1.pn= B2.partno 


--写入风扇\Riser卡 元器件
INSERT INTO @sendComponent(SvrAssetId,SvrSN,PartType,OriPartPN,OriPartSN,FW,ScanPartPN,ScanPartSN,NUM)
SELECT	B1.ASSETCODE,parentsn,ctype,B2.manufacturerpn,B1.sn,'','',B1.sn,NUM
FROM	@sendEfoxComponent B1,mmprodmaster  B2 
WHERE	ctype IN (N'风扇') AND B1.pn= B2.partno 

--写入风扇 元器件
INSERT INTO @sendComponent(SvrAssetId,SvrSN,PartType,OriPartPN,OriPartSN,FW,ScanPartPN,ScanPartSN,NUM)
SELECT	B1.ASSETCODE,parentsn,ctype,B1.pn,B1.sn,'','',B1.sn,NUM
FROM	@sendEfoxComponent B1
WHERE	ctype IN (N'Riser卡')


--SELECT * FROM @sendEfoxComponent


SELECT ROW_NUMBER() over(PARTITION BY '' order by SvrSN,PartType) as id,SvrAssetId,SvrSN,PartType,OriPartPN,OriPartSN,FW,ScanPartPN,ScanPartSN 
FROM @sendComponent
ORDER BY SvrSN,PartType