package com.jvmhater.moduticket.model.query

import com.jvmhater.moduticket.model.vo.ConcertCategory

data class ConcertSearchQuery(val category: ConcertCategory, val searchText: String)
