package io.github.iltotore.ucp.spigot

import net.md_5.bungee.api.chat.BaseComponent

class SpigotResult(valid: Boolean) {
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
}

object SpigotResult {
    def affectedBlocks(count: Int): SpigotResult =
        new SpigotResult(true) {
            affectedBlocks = count
        }

    def affectedEntities(count: Int): SpigotResult =
        new SpigotResult(true) {
            affectedEntities = count
        }

    def affectedItems(count: Int): SpigotResult =
        new SpigotResult(true) {
            affectedItems = count
        }
    
    def empty(): SpigotResult =
        new SpigotResult(true)

    def apply(valid: Boolean): SpigotResult =
        new SpigotResult(valid)


}