package com.jaydev.netmodulesample.data

import com.kt.ar.supporter.supporttools.data.User

data class Task(
	var workUser: User,
	var supportUser: User,
	var subject: String,
	var content: String,
	var planedStartDT: String,
	var planedEndDT: String,
	var arContentUrl: String?,
	var startDT: String?,
	var endDT: String?,
	var status: String,
	var workUserId: Int,
	var supportUserId: Int,
	var timeStamp: String,
	var createDT: String,
	var updateDT: String,
	var id: Int
) {

	enum class TaskStatus(val rawValue: String) {
		Standby("standby") {
			override fun status() = Standby
		},
		Ing("ing") {
			override fun status() = Ing
		},
		Complete("complete") {
			override fun status() = Complete
		};

		abstract fun status(): TaskStatus

		companion object {
			fun get(rawValue: String) = when (rawValue) {
				TaskStatus.Standby.rawValue -> Standby
				TaskStatus.Ing.rawValue -> Ing
				TaskStatus.Complete.rawValue -> Complete
				else -> null
			}
		}
	}

	fun getStatus() = TaskStatus.get(status)

	fun setStatus(status: TaskStatus) {
		this.status = status.rawValue
	}
}