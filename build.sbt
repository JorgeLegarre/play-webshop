name := "webshopp"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  //javaJdbc,
  //javaEbean,
  javaJpa,
  cache
)     

libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.3"

libraryDependencies += "org.hibernate" % "hibernate-entitymanager" % "4.2.8.Final"

play.Project.playJavaSettings
