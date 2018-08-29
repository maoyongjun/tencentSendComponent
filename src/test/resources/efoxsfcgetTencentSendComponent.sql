--exec [efoxsfcgetTencentSendComponent] 

alter PROCEDURE [dbo].[efoxsfcgetTencentSendComponent]
AS

DECLARE @sendComponent TABLE(
	Id INT identity(1,1),
	SvrAssetId VARCHAR(100),
	SvrSN VARCHAR(100),
	PartType NVARCHAR(100),
	OriPartPN  VARCHAR(100),
	OriPartSN  VARCHAR(100),
	FW  VARCHAR(100),
	ScanPartPN  VARCHAR(100),
	ScanPartSN  VARCHAR(100)
)

INSERT INTO @sendComponent(SvrAssetId,SvrSN,PartType,OriPartPN,OriPartSN,FW,ScanPartPN,ScanPartSN)
SELECT '','7CE829P6HG',CTYPE,PN,SN,FW,'',''  FROM efoxSFCTencentComponent  
WHERE PARENTID='48ccf095-7931-4f3f-8405-fc2f46c3bcd6'
AND NOT  (SN='' AND PN='' AND FW='')
INSERT INTO @sendComponent(SvrAssetId,SvrSN,PartType,OriPartPN,OriPartSN,FW,ScanPartPN,ScanPartSN)
SELECT  '',SN,DESCRIPTIONS,PN,CSN,'',PN,CSN from DBO.fn_getTencentSendComponentAssyDetail('7CE829P6HG')

SELECT * FROM @sendComponent