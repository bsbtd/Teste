create table BatchEngineExportTask (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	batchEngineExportTaskId LONG not null primary key,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	callbackURL VARCHAR(75) null,
	className VARCHAR(255) null,
	content BLOB,
	contentType VARCHAR(75) null,
	endTime DATE null,
	errorMessage VARCHAR(1000) null,
	fieldNames VARCHAR(75) null,
	executeStatus VARCHAR(75) null,
	parameters TEXT null,
	startTime DATE null,
	taskItemDelegateName VARCHAR(75) null
);

create table BatchEngineImportTask (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	batchEngineImportTaskId LONG not null primary key,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	batchSize LONG,
	callbackURL VARCHAR(75) null,
	className VARCHAR(255) null,
	content BLOB,
	contentType VARCHAR(75) null,
	endTime DATE null,
	errorMessage VARCHAR(1000) null,
	executeStatus VARCHAR(75) null,
	fieldNameMapping TEXT null,
	operation VARCHAR(75) null,
	parameters TEXT null,
	startTime DATE null,
	taskItemDelegateName VARCHAR(75) null
);