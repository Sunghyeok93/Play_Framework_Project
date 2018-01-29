name := "practice_1"
 
version := "1.0" 
      
lazy val `practice_1` = (project in file(".")).enablePlugins(PlayScala)

//lazy val `root` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"
      
scalaVersion := "2.12.2"

libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice )

libraryDependencies ++= Seq( "org.webjars" % "bootstrap" % "3.1.1-2")

libraryDependencies ++= Seq( "net.sf.barcode4j" % "barcode4j" % "2.1")

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  
