package com.jaydev.netmodulesample.data

class PagingResponse<T> {
	var metaData: MetaData? = null
	var result: T? = null
}

data class MetaData(
	var totalItemCount: Int,
	var totalPageCount: Int,
	var pageIndex: Int,
	var pageSize: Int,
	var hasNextPage: Boolean,
	var hasPreviousPage: Boolean,
	var isFirstPage: Boolean,
	var isLastPage: Boolean
)

data class GetMyNoticeQuery(
	var curDT: String?,
	var sort: String,
	var page: Int?,
	var pageSize: Int?
)

data class GetMyTaskQuery(
	var gePlanedStartDT: String?,
	var ltplanedStartDT: String?,
	var gtId: Int?,
	var otherUserId: Int?,
	var sort: String?,
	var page: Int?,
	var pageSize: Int?
)