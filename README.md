# UniversalCommandParser
A flexible command system written in Scala.

# Description
UCP is a Scala library allowing users to easily register and parse custom commands
with custom syntax. It uses Scala's implicits as syntactic sugar for command instantiating.

# Add to project
UCP is published to the [Sonatype Repository](https://oss.sonatype.org/) and indexed by Scaladex.
<details>
<summary>Using Gradle</summary>

```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation 'io.github.iltotore:ucp_scalaVersion:version'
}
```
</details>

<details>
<summary>Using SBT</summary>

```sbt
libraryDependencies += "io.github.iltotore" %% "ucp" % "version"
```
</details>

# Basic usage
## Create registry
You firstly need to create a new CommandRegistry.
UCP provides a default prefixed implementation named PrefixedCommandRegistry.

```scala
val registry = new CommandRegistry[S](ListBuffer(), "!" /*prefix*/)
```
Note: `S` is the generic type for the CommandSender. It can represent a String or a more complex class.

## Declaring command
UCP provides syntactic sugar for declaring command, based on Scala's implicit classes.
```scala
val cmd = "myCommand" describedAs "A beautiful command" executing (println(_)) requring (boolArg casting(_.toBoolean))
```

Now, you can register this newly created CommandElement using `registry.register(cmd)`

## Complex command example
Here is a command setting the "time" argument to a number of day as int.

```scala
def dummyExecutor(sender: String, context: CommandContext[String]): GeneralResult = {
  context.getFirst[Int]("time").foreach(println(_)) //Prints the time if present
  SUCCESS whilst "calculating time passed" //The returned CommandResult
}


val aPermission: String => Boolean = name => name.equals("Il_totore") //Only for Il_totore <3
val dayBranch: BranchElement[String] = label("day") of("time" casting(_.toInt)) //The branch is identified by the label "day"
val weekBranch: BranchElement[String] = label("week") of("time" casting(_.toInt * 7)) //A week = 7 days
val secondArg: NodeElement[String] = "unit" choosing dayBranch or weekBranch //Choose the branch day or week
"days" describedAs "A test command" withPermission aPermission executing dummyExecutor requiring secondArg
//Example: !days week 1 | !days day 7
```