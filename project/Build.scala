import sbt._
import sbt.Keys._
import sbtassembly.Plugin._
import AssemblyKeys._

object BuildSettings {
  val buildOrganization = "ch.hsr.uint.slibrary"
  val buildVersion = "0.0.1"

  val buildSettings = Defaults.defaultSettings ++ Seq(
    organization := buildOrganization,
    version := buildVersion,
    shellPrompt := ShellPrompt.buildShellPrompt,
    exportJars := true,
    unmanagedBase <<= baseDirectory {
      base => base / "custom_lib"
    },
    javacOptions ++= Seq("-encoding", "UTF-8")
  )
}

object Resolvers {
  val sunrepo = "Sun Maven2 Repo" at "http://download.java.net/maven/2"
  val sunrepoGF = "Sun GF Maven2 Repo" at "http://download.java.net/maven/glassfish"
  val oraclerepo = "Oracle Maven2 Repo" at "http://download.oracle.com/maven"

  //val localRepo = Resolver.file("u3-file-repo", file("repo"))(Patterns("[artifact]-[revision].[ext]"))

  val oracleResolvers = Seq(sunrepo, sunrepoGF, oraclerepo)
}

object Dependencies {
  val junitinterface = "com.novocode" % "junit-interface" % "0.10" % "test"

  val jgoodiescommon = "com.jgoodies" % "jgoodies-common" % "1.6.0"
  val jgoodiesform = "com.jgoodies" % "jgoodies-forms" % "1.7.1"
  val swingxautocompletion = "org.swinglabs.swingx" % "swingx-autocomplete" % "1.6.5-1"

}

object SLibraryBuild extends Build {

  import Resolvers._
  import Dependencies._
  import BuildSettings._

  // Sub-project specific dependencies
  val commonDeps = Seq(
  )

  val slibrarySPADeps = Seq(
    junitinterface
  )

  val slibraryGUIDeps = Seq(
    junitinterface,
    swingxautocompletion
  )

  lazy val uintbooks = Project(
    "slibrary",
    file("."),
    settings = buildSettings ++ assemblySettings ++ Seq(
      mainClass in assembly := Some("ch.hsr.slibrary.AppSLibrary"),
      jarName in assembly := "slibrary.jar",
      test in assembly := {}
    )
  ) dependsOn (slibraryGUI, slibrarySPA)

  lazy val slibraryGUI = Project(
    "slibrary-gui",
    file("gui"),
    settings = buildSettings ++ Seq(libraryDependencies ++= slibraryGUIDeps)
  ) dependsOn (slibrarySPA)

  lazy val slibrarySPA = Project(
    "slibrary-spa",
    file("spa"),
    settings = buildSettings ++ Seq(libraryDependencies ++= slibrarySPADeps,
      testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v"))
  )
}