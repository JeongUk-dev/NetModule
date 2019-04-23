package com.kt.ar.supporter.supporttools.data

data class User(
	var id: Int,
	var createDT: String,
	var updateDT: String,
	var userId: String,
	var userName: String,
	var role: String,
	var section: String,
	var position: String,
	var profileImgUrl: String
) {

	enum class Role(val rawValue: String) {
		Worker("W") {
			override fun role() = Worker
		},
		Supporter("S") {
			override fun role() = Supporter
		},
		Admin("A") {
			override fun role() = Admin
		};

		abstract fun role(): Role

		companion object {
			fun get(rawValue: String) = when (rawValue) {
				Worker.rawValue -> Worker
				Supporter.rawValue -> Supporter
				Admin.rawValue -> Admin
				else -> null
			}
		}
	}

	fun getRole() = Role.get(role)

	fun setRole(role: Role) {
		this.role = role.rawValue
	}
}