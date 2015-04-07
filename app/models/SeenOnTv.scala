package models

import java.net.URL
import scala.util._
import util.{Configuration, HtmlContent, MarkdownContent}

case class SeenOnTv(override val introduction: Introduction, content: HtmlContent) extends Cacheable

object SeenOnTv extends Fetchable {
  type C = SeenOnTv

  override val name = "Seen on TV"
  override val sourceUrl = Configuration.url("url.seenontv").get

  override def fetch(): Try[SeenOnTv] = apply(sourceUrl)

  def apply(url: URL): Try[SeenOnTv] = for {
    markdownContent <- MarkdownContent(url)
    (introduction, content) <- markdownContent.toIntroductionAndMainContent
  } yield SeenOnTv(introduction, content)
}
