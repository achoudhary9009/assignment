val catsVersion = "2.7.0"
val catsEffectVersion = "3.3.7"
val http4sVersion = "1.0.0-M31"

lazy val assignment = (project in file(".")).settings(
  organization := "com.zivver",
  name := "scala-assignment",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "3.1.1",
  libraryDependencies ++= Seq(
    "co.fs2" %% "fs2-core" % "3.2.5",
    "com.comcast" %% "ip4s-core" % "3.1.2",
    "io.circe" %% "circe-core" % "0.15.0-M1",
    "org.http4s" %% "http4s-circe" % http4sVersion,
    "org.http4s" %% "http4s-core" % http4sVersion,
    "org.http4s" %% "http4s-dsl" % http4sVersion,
    "org.http4s" %% "http4s-ember-server" % http4sVersion,
    "org.http4s" %% "http4s-server" % http4sVersion,
    "org.typelevel" %% "case-insensitive" % "1.2.0",
    "org.typelevel" %% "cats-core" % catsVersion,
    "org.typelevel" %% "cats-effect" % catsEffectVersion,
    "org.typelevel" %% "cats-effect-kernel" % catsEffectVersion,

    // Test
    "org.http4s" %% "http4s-client" % http4sVersion % Test,
    "org.scalameta" %% "munit" % "0.7.29" % Test,
    "org.typelevel" %% "munit-cats-effect-3" % "1.0.7" % Test,

    // Runtime
    "ch.qos.logback" % "logback-classic" % "1.2.11" % Runtime,
  ),
)
