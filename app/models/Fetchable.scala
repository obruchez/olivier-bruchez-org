package models

import java.net.URL
import util.HtmlContent
import scala.concurrent.duration._
import scala.util.Try

case class Introduction(shortVersion: HtmlContent, fullVersion: HtmlContent)

trait Cacheable {
  def introduction: Introduction
  def subFetchables: Seq[Fetchable] = Seq()
}

trait Fetchable {
  type C <: Cacheable

  def name: String
  def sourceUrl: URL
  def fetchPeriod: FiniteDuration = 60.seconds
  def fetch(): Try[C]
}

object Fetchable {
  val allFetchables = Seq(
    Books, BooksToRead, Concerts, Courses, Crashes, Exhibitions, Hikes, LifePrinciples,
    Movies, MoviesToWatch, Plays, Profile, SeenOnTv, Trips, TripsToTake, Votes, Worldview)
}