--EXEC efoxsfcgetTencentSendPallents 'SERVER'
ALTER PROCEDURE efoxsfcgetTencentSendPallents(
	@transtype	VARCHAR(100)
)
AS
BEGIN
	IF @transtype='SERVER'
	BEGIN
		DECLARE @lastDate DATETIME
		DECLARE	@pallents	VARCHAR(MAX)
		SET @lastDate ='1990-01-01 00:00:00.000' 
		SET @pallents=''
		IF EXISTS(SELECT 1 FROM efoxSFCTencentSendComponentPallent)
		BEGIN
			SELECT	@lastDate = MAX(PallentShipDate)
			FROM	efoxSFCTencentSendComponentPallent
			--WHERE	PallentShipDate>GETDATE()-15
		END

		DECLARE @sendPallent TABLE(
			pallent	VARCHAR(100),
			lastDate	DATETIME,
			logDate		DATETIME
		)
		
		INSERT INTO @sendPallent
		SELECT	top 2 
				a.packlistno,a.lasteditdt,GETDATE()
		FROM	sdshippack		a,
				mfsysproduct	b,
				mfworkstatus	c
		WHERE	a.packlistno= b.location 
				and b.sysserialno = c.sysserialno
				and a.packlistno <>'' 
				and a.packlistno not like '~%'
				and c.routeid like 'LXTE%'
				and a.lasteditdt>@lastDate
				
		INSERT INTO efoxSFCTencentSendComponentPallent
		SELECT * FROM @sendPallent
		
		--TRUNCATE TABLE efoxSFCTencentSendComponentPallent
		
		SELECT @pallents = @pallents+','+pallent FROM @sendPallent
		SELECT @pallents
	END
	ELSE IF  @transtype='ODM'
	BEGIN
		SELECT 'QSP183327717'
	END
END







