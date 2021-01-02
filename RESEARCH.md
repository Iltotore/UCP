# Research concepts
This is a show of some experimental concepts that should be added in future versions of UCP.

# Command DSL
## ScalaTest-like parameters
```scala
//Old
MiscParams.sequence(GenericParam("receiver")(nameToReceiver), GenericParam.raw("msg")), MiscParam.optional(GenericParam("color")(nameToColor))

//New
nameToReceiver as "receiver" and Param.Optional(nameToColor as "color") and  _ => _ as "msg"
```
