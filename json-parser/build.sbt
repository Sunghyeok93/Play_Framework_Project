name := "jsonParser"
 
version := "1.0" 
      
lazy val `jsonparser` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"
      
scalaVersion := "2.12.2"

libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice )
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.0"
unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

      