--exec [efoxsfcgetTencentSendOdmPart] 'PA10002707'

ALTER PROCEDURE [dbo].[efoxsfcgetTencentSendOdmPart]
(
 @pallent VARCHAR(MAX)
)
AS

DECLARE @sendComponent TABLE(
	Id INT identity(1,1),
	category	VARCHAR(100),
	PartType NVARCHAR(100),
	PuchaseNumber VARCHAR(100),
	PartModel VARCHAR(100),
	PartSN  VARCHAR(100),
	IdcpName  NVARCHAR(500),
	IdcpAdress  NVARCHAR(500)
)

--电源	MG42	抓供應商PN
--Cable	MG95	抓供應商PN
--风扇	MG67	抓供應商PN
--网卡	MG59	抓供應商PN
--RAID卡	MG65	抓描述
--RAID卡電池	MG28SI	抓描述

--TencentAsetcode


DECLARE @sntable TABLE(
	SN	VARCHAR(100),
	CSN	VARCHAR(100),
	partno	VARCHAR(100),
	category	VARCHAR(100),
	vendorpart	VARCHAR(100),
	DESCRIPTION	NVARCHAR(500)
)
--SET @pallent ='PA10002707'
SET @pallent='QSP183327717'

INSERT INTO @sntable
SELECT	A.sysserialno,B.cserialno,B.partno,
		B.categoryname ,C.manufacturerpn,C.description
FROM	mfsysproduct  A 
		LEFT JOIN mfsyscserial B ON A.sysserialno = B.sysserialno 
		LEFT JOIN mmprodmaster C ON B.partno = C.partno  
WHERE	location IN (
SELECT Value FROM dbo.fn_Split(@pallent,',')
)
select * from @sntable



INSERT INTO @sendComponent(category,PartType,PuchaseNumber,PartModel,PartSN,IdcpName,IdcpAdress)
SELECT  a.category,B.CONTROLDESC,'EPO','',SN,'',''
FROM	@sntable a, ECONTROLVALUE b 
WHERE	b.CONTROLNAME LIKE 'tecent_mapping_category_to_name_%'
		AND 'tecent_mapping_category_to_name_'+A.category= B.CONTROLNAME
		
update	@sendComponent set PartModel=B.DESCRIPTION  
FROM	@sendComponent A, @sntable b WHERE A.category= B.category
		AND A.PartType=N'RAID卡電池'

SELECT id,PartType,PuchaseNumber,PartModel,PartSN,IdcpName,IdcpAdress
FROM @sendComponent

