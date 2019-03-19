package com.jaydev.netmodulesample

import com.jaydev.netmodule.netInterface.NetCallParams

data class TestParam(val id:String): NetCallParams<TestParam> {

    override fun getParams() = this

    override fun clone(): NetCallParams<TestParam> {
        return TestParam(id)
    }
}