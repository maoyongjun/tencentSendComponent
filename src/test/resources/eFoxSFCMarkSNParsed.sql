--EXEC eFoxSFCMarkSNParsed
ALTER PROCEDURE eFoxSFCMarkSNParsed

AS 
	DECLARE @lastPareDate DATE
	SET @lastPareDate = '1990-01-01 00:00:00.000'
	--SET @lastPareDate = '2018-08-09 01:21:15.413'
	
	SELECT  TOP 1 @lastPareDate = lasteditdt 
	FROM	efoxSFCTencentComponent 
	ORDER BY lasteditdt DESC

	UPDATE	eFoxSFCUpdateSSNStatusBySSN_tencent_input 
	SET		WebUrl='' 
	WHERE	Field9 ='' AND WebUrl='' and lasteditdt>@lastPareDate


	--UPDATE	eFoxSFCUpdateSSNStatusBySSN_tencent_input
	--SET		WebUrl=1
	--WHERE 	Field9 <>'' AND WebUrl='' and lasteditdt>@lastPareDate
	SELECT * FROM (
		SELECT   ROW_NUMBER() OVER (PARTITION BY SSN  ORDER BY lasteditdt DESC)AS RN  ,*
		FROM (	SELECT	SSN,Field9 AS JSON,lasteditdt 
				FROM	eFoxSFCUpdateSSNStatusBySSN_tencent_input
				WHERE 	Field9 <>'' AND WebUrl='' and lasteditdt>@lastPareDate
		) AS F1
	) AS F2 WHERE F2.RN=1 
	ORDER BY SSN,F2.RN,lasteditdt
	
	--INSERT INTO efoxSFCTencentComponent(lasteditdt) VALUES(GETDATE()-30)
	--TRUNCATE TABLE efoxSFCTencentComponent

	
