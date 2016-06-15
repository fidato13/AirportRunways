package com.lunatech.assessment

import org.scalatest.junit.JUnitRunner
import org.scalatest._
import org.junit.runner.RunWith


@RunWith(classOf[JUnitRunner])
class InformationTest extends FlatSpec {
  
  "getCountryCodeFromCountryName" should "return Country code" in {

    assert("IN" === Information.getCountryCodeFromCountryName("India"))
    assert("IE" === Information.getCountryCodeFromCountryName("Ireland"))

  }
  
  "getCountryFromCountryCode" should "return Country Name" in {

    assert("India" === Information.getCountryFromCountryCode("IN"))
    assert("Ireland" === Information.getCountryFromCountryCode("IE"))

  }
  
  
}