package com.lunatech.assessment

import org.scalatest.junit.JUnitRunner
import org.scalatest._
import org.junit.runner.RunWith


@RunWith(classOf[JUnitRunner])
class QueryTest extends FlatSpec {
  
  "deriveCountryCode" should "return Country Code " in {

    assert("IN" === Query.deriveCountryCode("India"))
    assert("IN" === Query.deriveCountryCode("Indi"))
    assert("AF" === Query.deriveCountryCode("Afghan"))
    assert("GB" === Query.deriveCountryCode("United Kingdom"))
    assert("GB" === Query.deriveCountryCode("GB"))
    
  }
  
  "getCountryFromPartialName" should "return Country Name " in {

    assert("India" === Query.getCountryFromPartialName("Indi"))
    assert("Netherlands" === Query.getCountryFromPartialName("Neth"))
    
    
  }
}