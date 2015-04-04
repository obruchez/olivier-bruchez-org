package controllers

import actors.Cache
import play.api.libs.concurrent.Execution.Implicits._
import play.api.mvc._

object Application extends Controller {
  def home = Action { Ok(views.html.home()) }

  def profile = Action.async { Cache.profile.map(profile => Ok(views.html.profile(profile))) }

  def about = Action.async {
    Page.introductionsFromPages(Sitemap.about.children) map { pagesAndIntroductions =>
      Ok(views.html.menu(Sitemap.about, pagesAndIntroductions, groupSize = 3, colSize = 4))
    }
  }

  def lifePrinciples = Action.async {
    Cache.lifePrinciples.map(lifePrinciples => Ok(views.html.lifeprinciples(lifePrinciples)))
  }

  def worldview = Action.async { Cache.worldview.map(worldview => Ok(views.html.worldview(worldview))) }

  def lifelogging = Action.async {
    Page.introductionsFromPages(Sitemap.lifelogging.children) map { pagesAndIntroductions =>
      Ok(views.html.menu(Sitemap.lifelogging, pagesAndIntroductions, groupSize = 3, colSize = 4))
    }
  }

  def books = Action.async { Cache.books.map(books => Ok(views.html.books(books))) }

  def concerts =  Action.async { Cache.concerts.map(concerts => Ok(views.html.concerts(concerts))) }

  def courses =  Action.async { Cache.courses.map(courses => Ok(views.html.courses(courses))) }

  def crashes = Action.async { Cache.crashes.map(crashes => Ok(views.html.crashes(crashes))) }

  def exhibitions = Action.async { Cache.exhibitions.map(exhibitions => Ok(views.html.exhibitions(exhibitions))) }

  def hikes = Action.async { Cache.hikes.map(hikes => Ok(views.html.hikes(hikes))) }

  def movies = Action.async { Cache.movies.map(movies => Ok(views.html.movies(movies))) }

  def plays = Action.async { Cache.plays.map(plays => Ok(views.html.plays(plays))) }

  def seenOnTv = Action {
    // @todo
    NotImplemented
  }

  def trips = Action.async { Cache.trips.map(trips => Ok(views.html.trips(trips))) }

  def votes = Action {
    // @todo
    NotImplemented
  }

  def booksToRead = Action.async {
    Cache.booksToRead map { booksToRead =>
      Ok(views.html.markdown(Sitemap.booksToRead, booksToRead.introduction, booksToRead.content))
    }
  }

  def moviesToWatch = Action.async {
    Cache.moviesToWatch map { moviesToWatch =>
      Ok(views.html.markdown(Sitemap.moviesToWatch, moviesToWatch.introduction, moviesToWatch.content))
    }
  }

  def tripsToTake = Action.async {
    Cache.tripsToTake map { tripsToTake =>
      Ok(views.html.markdown(Sitemap.tripsToTake, tripsToTake.introduction, tripsToTake.content))
    }
  }

  def toDo = Action {
    // @todo
    NotImplemented
  }

  def cv = Action.async {
    Page.introductionsFromPages(Sitemap.cv.children) map { pagesAndIntroductions =>
      Ok(views.html.menu(Sitemap.cv, pagesAndIntroductions, groupSize = 3, colSize = 4))
    }
  }

  def contact = Action {
    // @todo
    NotImplemented
  }

  def externalLinks = Action.async {
    Page.introductionsFromPages(Sitemap.externalLinks.children) map { pagesAndIntroductions =>
      Ok(views.html.menu(Sitemap.externalLinks, pagesAndIntroductions, groupSize = 3, colSize = 4))
    }
  }
}
