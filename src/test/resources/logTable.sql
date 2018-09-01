CREATE TABLE efoxSFCTencentSendLog(
	logDate		DATETIME,
	apiname		VARCHAR(100),
	msgId		VARCHAR(200),
	msgType		VARCHAR(200),--recevie/return/request/response
	data		VARCHAR(MAX)
)