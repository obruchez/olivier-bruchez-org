package util

import com.github.rjeschke.txtmark._
import java.net.URL
import models.Introduction
import scala.io.{Codec, Source}
import scala.util._

case class MarkdownContent(lines: Seq[String]) {
  def withoutHeadingTitle: MarkdownContent = {
    val linesWithoutHeadingTitle = lines.
      dropWhile(_.trim.isEmpty).
      dropWhile(_.startsWith(MarkdownContent.HeadingCharacter.toString)).
      dropWhile(_.trim.isEmpty)

    MarkdownContent(lines = linesWithoutHeadingTitle)
  }

  def toHtmlContent: Try[HtmlContent] = Try {
    val markdownString = lines.dropWhile(_.trim.isEmpty).reverse.dropWhile(_.trim.isEmpty).reverse.mkString("\n")
    val htmlString = Processor.process(markdownString)
    HtmlContent(htmlString).withNonBreakingSpaces.withoutRootParagraph
  }

  def toIntroductionAndMainContent: Try[(Introduction, HtmlContent)] = Try {
    val withoutHeadingTitle = this.withoutHeadingTitle

    val indexOfContent = withoutHeadingTitle.lines.indexWhere(_.startsWith("## "))

    val (introductionLines, mainContentLines) = if (indexOfContent >= 0)
      (withoutHeadingTitle.lines.take(indexOfContent), withoutHeadingTitle.lines.drop(indexOfContent))
    else
      (withoutHeadingTitle.lines, Seq())

    val introductionHtml = MarkdownContent(introductionLines).toHtmlContent.get
    val contentHtml =
      MarkdownContent(mainContentLines).toHtmlContent.get.
      withCssClassAddedToHeadings(
        heading = "h2",
        cssClass = MarkdownContent.HeadlineClass)

    (Introduction(shortVersion = introductionHtml, fullVersion = introductionHtml), contentHtml)
  }
}

object MarkdownContent {
  def apply(string: String): MarkdownContent =
    MarkdownContent(string.split("\\r?\\n").toSeq)

  def apply(url: URL): Try[MarkdownContent] = Try {
    val markdownString = Source.fromURL(url)(Codec("UTF-8")).mkString
    apply(markdownString)
  }

  val HeadingCharacter = '#'

  val HeadlineClass = "headline"
}
