# UniversalCommandParser
A flexible command system written in Scala.

# Description
UCP is a Scala library allowing users to easily register and parse custom commands
with custom syntax.

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
You firstly need to create a new `CommandRegistry[C, R]` where `C <: CommandContext` is the custom context and `R` a custom result type.
UCP provides a default prefixed implementation named `CommandRegistry.Prefixed`.

```scala
val registry = new CommandRegistry.Prefixed[CommandContext.Mapped, String]("/")
```

## Declaring command
UCP provides a simple DSL to declare a command.
```scala
registry += Command(
  name = CommandName("give", "g", "anotherAlias"),
  executor = GiveExecutor, //An instance of CommandExecutor[C, R]
  root = GenericParam.Raw("item")
)
```

Here is a CommandExecutor example:
```scala
object GiveExecutor extends CommandExecutor[CommandContext.Mapped, String] {
  
  override def apply(context: CommandContext.Mapped): String = {
    s"You earned ${context.get[String]("item").get}"
  }
}
```

## Parsing Command
You can now create your custom commands. Let's parse an incoming String to a Command.

Firstly create an implicit Tokenizer:
```scala
implicit val tokenizer: Tokenizer = new Tokenizer.Regex(" ") //All command parts are separated by a space
```

Now parse the command using CommandRegistry#parseString

```scala
registry.parseString(new CommandContext.Mapped(mutable.Map.empty), "/give apple") //You earned apple
```

## Complex command example
Here is a command "giving" an item with an optional amount to the player

<details>
<summary>Command declaration</summary>

```scala
registry += Command(
  name = CommandName("give", "earn"),
  executor = GiveExecutor,
  root = new MiscParam.Sequence(
    GenericParam.Raw("item"),
    new MiscParam.Optional(GenericParam.Int("amount"))
  )
)
```
</details>

<details>
<summary>GiveExecutor</summary>

```scala
object GiveExecutor extends CommandExecutor[CommandContext.Mapped, String] {
  
  override def apply(context: CommandContext.Mapped): String = {
    val item: String = context.get("item").get
    val amount: Int = context.get("amount").getOrElse(1)
    s"You earned $item x$amount"
  }
}
```
</details>

<details>
<summary>Some results</summary>

```
/give apple
You earned apple x1

/give apple 2
You earned apple x2

/give apple --amount 2
You eanred apple x2

/give --item apple --amount 2
You earned apple x2

/give --amount 2 --item apple
You earned apple x2

/give --amount 2
ParsingException.MissingArgument: item
```
</details>