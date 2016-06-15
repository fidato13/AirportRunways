package com.lunatech.assessment

import com.lunatech.assessment.Information._
import scala.util.Try

/**
 * @author Tarun
 */
object Report {

  /**
   * A choice can be made here, if this vals need to be inside a function or need to be initialized just once - use case and size of data dependent
   */
  
  
  val airportDataList: List[String] = airportsData.toList
  val runwaysDataList: List[String] = runwaysData.toList
  
  /**
   * groupByMapAirportsCount - A List with Tuple2 of country name and the count of airports
   */
  val groupByMapAirportsCount: List[(String, Int)] = airportDataList.map(_.split(",")(8)).groupBy(identity).mapValues { _.size }.toList

  /**
   * topTenAirportsCountries - sorted top ten values
   * bottomTenAirportsCountries - sorted bottom ten values
   */
  val topTenAirportsCountries: List[(String, Int)] = groupByMapAirportsCount.sortWith(_._2 > _._2).take(10).map { case (code, count) => (getCountryFromCountryCode(code), count) }
  val bottomTenAirportsCountries: List[(String, Int)] = groupByMapAirportsCount.sortWith(_._2 < _._2).take(10).map { case (code, count) => (getCountryFromCountryCode(code), count) }

  /**
   * A map of runway id with the types
   */
  val mapRunwaysType: Map[String, String] = runwaysDataList.map { x => (x.split(",")(2), x.split(",")(5)) }.toMap

  /**
   * A list with tuple2 containing country with runway type utilizing mapRunwaysType
   */
  val listCountryType: List[(String, String)] = airportDataList.map { x => (getCountryFromCountryCode(x.split(",")(8)), Try { mapRunwaysType.get(x.split(",")(1)).get }.getOrElse("")) }

  /**
   * grouing of above list structure to obtain a country and set of runways
   */
  val runwaysTypePerCountry: Map[String, Set[String]] = listCountryType groupBy (_._1) mapValues { x => x.filterNot { _._2 == "" }.map(_._2).toSet }

  /**
   * Top 10 most common runway latitude (indicated in "le_ident" column)
   */
  val mostCommonRunways: List[String] = runwaysDataList.map(x => Try { x.split(",")(8) }.getOrElse("")).groupBy(identity).mapValues { _.size }.toList.sortWith(_._2 > _._2).take(10).map { case (id, count) => id }

  /**
   * To print the report data
   */
  def printReportData  {

    println("===================================================")
    println("Top ten Countries in terms of highest number of airports : (Name, Count)")
    topTenAirportsCountries.foreach { x => println(x._1 + ", " + x._2) }
    println("===================================================")
    println("Bottom ten Countries in terms of lowest number of airports : (Name, Count)")
    bottomTenAirportsCountries.foreach { x => println(x._1 + ", " + x._2) }
    println("===================================================")
    println("Top 10 most common runway latitude : (Le_ident)")
    mostCommonRunways.foreach { println }
    println("===================================================")
    println("Country, Set(Types of runways)")
    runwaysTypePerCountry.foreach { x => println(x._1 + ", " + x._2) }

  }
}