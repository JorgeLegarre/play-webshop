name := "webshopp"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache
)     

libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.3"

play.Project.playJavaSettings
