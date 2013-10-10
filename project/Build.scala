import sbt._
import sbt.Keys._

object BuildSettings {
  val buildOrganization = "ch.hsr.uint.slibrary"
  val buildVersion = "0.0.1"

  val buildSettings = Defaults.defaultSettings ++ Seq(
    organization := buildOrganization,
    version := buildVersion,
    shellPrompt := ShellPrompt.buildShellPrompt,
    unmanagedBase <<= baseDirectory {
      base => base / "custom_lib"
    }
  )
}

// Shell prompt which show the current project,
// git branch and build version
object ShellPrompt {

  object devnull extends ProcessLogger {
    def info(s: => String) {}

    def error(s: => String) {}

    def buffer[T](f: => T): T = f
  }

  def currBranch = (
    ("git status -sb" lines_! devnull headOption)
      getOrElse "-" stripPrefix "## "
    )

  val buildShellPrompt = {
    (state: State) => {
      val currProject = Project.extract(state).currentProject.id
      "%s:%s:%s> ".format(
        currProject, currBranch, BuildSettings.buildVersion
      )
    }
  }
}

object Resolvers {
  val sunrepo = "Sun Maven2 Repo" at "http://download.java.net/maven/2"
  val sunrepoGF = "Sun GF Maven2 Repo" at "http://download.java.net/maven/glassfish"
  val oraclerepo = "Oracle Maven2 Repo" at "http://download.oracle.com/maven"

  //val localRepo = Resolver.file("u3-file-repo", file("repo"))(Patterns("[artifact]-[revision].[ext]"))

  val oracleResolvers = Seq(sunrepo, sunrepoGF, oraclerepo)
}

object Dependencies {
  val jgoodiescommon = "com.jgoodies" % "jgoodies-common" % "1.6.0"
  val jgoodiesform = "com.jgoodies" % "jgoodies-forms" % "1.7.1"
}

object SLibraryBuild extends Build {

  import Resolvers._
  import Dependencies._
  import BuildSettings._

  // Sub-project specific dependencies
  val commonDeps = Seq(
  )

  val slibrarySPADeps = Seq(
  )

  val slibraryGUIDeps = Seq(
  )

  lazy val uintbooks = Project(
    "slibrary",
    file("."),
    settings = buildSettings
  ) aggregate(slibraryGUI, slibrarySPA)

  lazy val slibraryGUI = Project(
    "slibrary-gui",
    file("gui"),
    settings = buildSettings ++ Seq(libraryDependencies ++= slibraryGUIDeps)
  ) dependsOn (slibrarySPA)

  lazy val slibrarySPA = Project(
    "slibrary-spa",
    file("spa"),
    settings = buildSettings ++ Seq(libraryDependencies ++= slibrarySPADeps)
  )
}