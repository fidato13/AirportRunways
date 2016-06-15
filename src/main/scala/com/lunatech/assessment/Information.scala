package com.lunatech.assessment

import scala.io.Source
import scala.util.Try
import java.io.File
import com.lunatech.assessment.Query._
import com.lunatech.assessment.Report._

/**
 * {{{
 * Below you'll find an explanation of the code assessment. The code assessment is not a puzzle; it's not a hard or tricky assignment. It's just a regular piece of code; you know the details of what you programmed, and we know the details about what we asked so we hopefully have a common understanding. If you have any questions do not hesitate to contact me, I'll be glad to answer them.
 *
 * I'd like to schedule the review on Wednesday 22nd June. Please let me know if that suits you.
 *
 * This is the assessment:
 *
 * 1. Fork the repo https://github.com/vijaykiran/random-repo - there are 3 CSV files in the repository. The files contain data for countries, airports and runway information.
 * 2. Write a program in Scala that will ask the user for two options - Query or Reports.
 * 2.1 Query Option will ask the user for the country name or code and print the airports & runways at each airport. The input can be country code or country name.
 * For bonus points make the test partial/fuzzy. e.g. entering zimb will result in Zimbabwe :)
 * 2.2 Choosing Reports will print the following:
 * • 10 countries with highest number of airports (with count) and countries  with lowest number of airports.
 * • Type of runways (as indicated in "surface" column) per country
 * • Bonus: Print the top 10 most common runway latitude (indicated in "le_ident" column)
 * • Feel free to use any library/framework as necessary.
 *
 * Please write the code as if you are writing production code, possibly with tests.
 *
 * Regards
 * }}}
 */

/**
 * A case class to represent Country , with airports name and a list of runways against those airports , A list of this case class a complete set of information for any country
 */
case class AirportWithRunways(country: String, airportName: String, airportIdentifier: String, runways: List[String] = List[String]())

object Information  {

  /**
   * Loading file resources
   */
  val airportsFile: String = "src/main/resources/airports.csv"
  val countriesFile: String = "src/main/resources/countries.csv"
  val runwaysFile: String = "src/main/resources/runways.csv"

  /**
   * declaring Constants
   */
  val QUERY: String = "QUERY"
  val REPORT: String = "REPORT"

  /**
   * Loading resources into Memory
   */
  val airportsData: Iterator[String] = Source.fromFile(new File(airportsFile), "ISO-8859-1").getLines.drop(1)
  val countriesData: List[String] = Source.fromFile(new File(countriesFile), "ISO-8859-1").getLines.drop(1).toList
  val runwaysData: Iterator[String] = Source.fromFile(new File(runwaysFile), "ISO-8859-1").getLines.drop(1)

  /**
   * Creating country lookup map
   */
  val CodetoCountryMap: Map[String, String] = countriesData.map { x => (x.split(",")(1), x.split(",")(2)) }.toMap
  val CountryToCodeMap: Map[String, String] = CodetoCountryMap.map { case (code, country) => (country, code) }

  /**
   * Standard User Interaction messages
   */
  val COUNTRY_CODE_DEFAULT_MESSAGE: String = "Please Enter a Country Code or Country Name \n"
  val QUERY_REPORT_DEFAULT_MESSAGE: String = "Please Enter Query or Report \n"
  
  
  /**
   * Gets Country code for a passed Country Name
   */
  def getCountryCodeFromCountryName(countryName: String): String = Try { CountryToCodeMap.get(countryName).get }.getOrElse("")

  /**
   * Gets Country name for a passed Country Code
   */
  def getCountryFromCountryCode(countryCode: String): String = Try { CodetoCountryMap.get(countryCode).get }.getOrElse("")

  /**
   * Reads the input Query or report option from the user, and makes sure that user only inputs out of these options
   */
  def inputOptionFromUser: String = readLine(QUERY_REPORT_DEFAULT_MESSAGE) match {
    case null => inputOptionFromUser
    case x if (x.toUpperCase == QUERY | x.toUpperCase == REPORT) => x.toUpperCase
    case _ => inputOptionFromUser
  }

  def main( args:Array[String] ):Unit = {
  //prompt user for input for either a Query or a Report
  inputOptionFromUser match {
    case QUERY  => printAirportAndRunways(fetchAirportsWithRunways(userInputCountryCode()))
    case REPORT => printReportData
    case _      => println("Wrong Option, Please try again...")
  }
  
  }

}