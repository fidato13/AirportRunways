import AssemblyKeys._

name := "Lunatech"
 
version := "0.1"
 
scalaVersion := "2.10.5"
 
scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers += "Local Maven Repository" at "file:///"+Path.userHome+"/.m2/repository"

libraryDependencies ++= {
  Seq(
  "org.scalatest" %% "scalatest" % "2.0.M7" % "test",
  "junit" % "junit" % "4.11"
  )
}

assemblySettings