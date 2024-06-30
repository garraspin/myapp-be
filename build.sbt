import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

ThisBuild / scalaVersion := "3.3.1"
ThisBuild / organization := "myapp"
ThisBuild / version := "0.1.0"

lazy val `myapp-common` = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure).in(file("common"))
  .settings(
    libraryDependencies += "dev.zio" %% "zio-schema" % "1.1.1",
    libraryDependencies += "dev.zio" %% "zio-schema-json" % "1.1.1",
  )

// sbt 'myapp-be/run'
lazy val `myapp-be` = project.in(file("be"))
  .dependsOn(`myapp-common`.jvm)
  .settings(
    Compile / run / mainClass := Some("myapp.Main"),
    libraryDependencies += "dev.zio" %% "zio-http" % "3.0.0-RC6",
  )
