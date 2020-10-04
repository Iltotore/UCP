package io.github.iltotore.ucp.spigot

import io.github.iltotore.ucp.spigot.SpigotResult.Translations
import net.md_5.bungee.api.chat.{BaseComponent, TranslatableComponent}

case class SpigotResult(valid: Boolean,
                        message: Option[BaseComponent],
                        affectedBlocks: Option[Int],
                        affectedEntities: Option[Int],
                        affectedItems: Option[Int],
                        successCount: Option[Int]) {

  val translatedMessages: Seq[TranslatableComponent] = {
    val attrs = Seq(affectedBlocks, affectedEntities, affectedItems, successCount)
    for(i <- attrs.indices if attrs(i).isDefined) yield new TranslatableComponent(Translations(i), attrs(i))
  }
}

object SpigotResult {

  def empty(): SpigotResult = SpigotResult(true, Option.empty, Option.empty, Option.empty, Option.empty, Option.empty)

  def apply(valid: Boolean): SpigotResult.Builder = new Builder(valid)

  implicit def autoBuild(builder: Builder, result: SpigotResult): SpigotResult = builder.build

  val Translations: Seq[String] = Seq.empty

  class Builder(valid: Boolean) {
    private var messageOpt: Option[BaseComponent] = Option.empty
    private var affectedBlocksOpt: Option[Int] = Option.empty
    private var affectedEntitiesOpt: Option[Int] = Option.empty
    private var affectedItemsOpt: Option[Int] = Option.empty
    private var successCountOpt: Option[Int] = Option.empty

    def message: Option[BaseComponent] = messageOpt

    def message_=(baseComp: BaseComponent): Unit = messageOpt = Option(baseComp)

    def affectedBlocks: Option[Int] = affectedBlocksOpt

    def affectedBlocks_=(value: Int): Unit = affectedBlocksOpt = Option(value)

    def affectedEntities: Option[Int] = affectedEntitiesOpt

    def affectedEntities_=(value: Int): Unit = affectedEntitiesOpt = Option(value)

    def affectedItems: Option[Int] = affectedItemsOpt

    def affectedItems_=(value: Int): Unit = affectedItemsOpt = Option(value)

    def successCount: Option[Int] = successCountOpt

    def successCount_=(value: Int): Unit = successCountOpt = Option(value)

    def build: SpigotResult = SpigotResult(valid, message, affectedBlocks, affectedEntities, affectedItems, successCount)
  }
}