package com.lambdaschool.dogsinitial.controller

import com.lambdaschool.dogsinitial.model.Dog

interface CheckDog {
    fun test(d: Dog): Boolean
}
