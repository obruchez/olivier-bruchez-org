package controllers

import actors.Cache
import models.{Books, BookNotes}
import play.api.libs.concurrent.Execution.Implicits._
import play.api.mvc._
import util.MarkdownContent

object BookNotesController extends Controller with FileHelper {
  def bookNotes(slug: String) = Action.async {
    Cache.books map { books =>
      books.notesFromSlug(slug) match {
        case Some(notes) =>
          notes.url.fileType match {
            case Markdown =>
              // @todo clean this (use cache)
              val htmlContent = MarkdownContent(notes.url).map(_.withoutHeadingTitle).flatMap(_.toHtmlContent).get

              Ok(views.html.markdown(books.pageFromNotes(notes), introductionOption = None, htmlContent))
            case _ =>
              // @todo implement this
              NotImplemented
          }
        case None =>
          NotFound
      }
    }
  }

  implicit class BooksOps(books: Books) {
    def pageFromNotes(bookNotes: BookNotes): Page = {
      val book = books.books.find(_.notes.contains(bookNotes)).get

      Page(
        title = s"${book.author} - ${book.title} (${bookNotes.description.getOrElse("Notes")})",
        url = routes.BookNotesController.bookNotes(bookNotes.slug).url,
        icon = Sitemap.books.icon)
    }
  }
}
