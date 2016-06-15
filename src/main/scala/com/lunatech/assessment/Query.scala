package com.lunatech.assessment

import com.lunatech.assessment.Information._
import scala.util.Try

/**
 * @author Tarun
 */
object Query {

  /**
   * A method to interpret the users input 
   */
  def userInputCountryCode(inputCodeMessage: String = COUNTRY_CODE_DEFAULT_MESSAGE): String = readLine(inputCodeMessage) match {
    case null => userInputCountryCode(inputCodeMessage)
    case x    => deriveCountryCode(x.toUpperCase())
  }

  /**
   * A method to derive the country code from supplied country code, country name or partial name using fuzzy mathcing
   * As of now just a uniqeu fuzzy name matching is implemented
   */
  def deriveCountryCode(passedInput: String): String = passedInput.length match {
    case 2 => passedInput
    case _ => getCountryCodeFromCountryName(getCountryFromPartialName(passedInput)) // its either a country name or requires fuzzy logic matching
  }

  /**
   * A fuzzy matching logic method to interpret/find the name from partial/full name supplied
   */
  def getCountryFromPartialName(passedInput: String, matchLogic: String = "FULL"): String = matchLogic match {
    case "FULL"    => Try { countriesData.find { _.split(",")(2).toLowerCase() == passedInput.toLowerCase() }.map { _.split(",")(2) }.toList.head }.getOrElse(getCountryFromPartialName(passedInput, "PARTIAL"))
    case "PARTIAL" => Try { countriesData.find { _.split(",")(2).substring(0, passedInput.length).toLowerCase() == passedInput.toLowerCase() }.map { _.split(",")(2) }.toList.head }.getOrElse(getCountryFromPartialName(passedInput, "UNMATCHED"))
    case _         => ""
  }

  /**
   *  A method to traverse, merge and combine all required information together
   */
  def fetchAirportsWithRunways(inputCountryCode: String): List[AirportWithRunways] = {

    val countryFromCountryCode: String = getCountryFromCountryCode(inputCountryCode)

    val airportList: List[AirportWithRunways] = airportsData.filter { _.split(",")(8) == inputCountryCode }.map { x => AirportWithRunways(countryFromCountryCode, x.split(",")(3), x.split(",")(1)) }.toList

    val listRequiredAirports: List[String] = airportList.map { x => x.airportIdentifier }
    val listRequiredRunways: List[String] = runwaysData.filter { x => listRequiredAirports.contains(x.split(",")(2)) }.toList

    airportList.map { x =>

      val runwaysList: List[String] = listRequiredRunways.filter { _.split(",")(2) == x.airportIdentifier }.toList
      x.copy(runways = runwaysList)
    }

  }

  /**
   *  prints the information 
   */
  def printAirportAndRunways(listAirports: List[AirportWithRunways]) = {

    listAirports.size match {
      case 0 => println("Please check the supplied input and try again...")
      case _ => listAirports.foreach { obj => println("Country : " + obj.country + ", Airport : " + obj.airportName + ", Runways : " + obj.runways) }
    }

  }
}