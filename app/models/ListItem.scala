package models

import org.joda.time.ReadablePartial
import util.Date._
import util.{Fetchable, Slug}

abstract class ListItem(val date: ReadablePartial,
                        val shortRepresentation: String,
                        val itemSlug: Option[String],
                        val itemUrl: Option[String]) {
  type T <: ListItem

  def withSlug(slug: Option[String]): T
  def withUrl(url: Option[String]): T
}

object ListItem {
  def slug(listItem: ListItem, allListItems: Seq[ListItem]): String = {
    val candidateSlug = listItem.date.yyyymmddString

    val listItemsWithSameDate = allListItems.reverse.filter(_.date.yyyymmddString == candidateSlug)

    if (listItemsWithSameDate.size == 1)
      candidateSlug
    else
      candidateSlug + "-" + (listItemsWithSameDate.indexOf(listItem) + 1)
  }
}

case class ListItems(listItems: Seq[ListItem], fetchable: Fetchable) {
  import ListItems._

  def withSlugs: ListItems = copy(listItems = listItems.withSlugs)

  def withUrls(page: Page): ListItems = copy(listItems = listItems.withUrls(page))
}

object ListItems {
  implicit class ListItemsOps[L <: ListItem](listItems: Seq[L]) {
    def withSlugs: Seq[L#T] = for {
      listItem <- listItems
      slug = ListItem.slug(listItem, listItems)
    } yield listItem.withSlug(Some(slug))

    def withUrls(page: Page): Seq[L#T] = for {
      listItem <- listItems
      url = listItem.itemSlug.map(itemSlug => Slug.withBaseUrl(page.url, itemSlug))
    } yield listItem.withUrl(url)
  }
}
