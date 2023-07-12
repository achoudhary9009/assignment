package com.zivver.assignment

import cats.effect.IO
import fs2.text
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityEncoder.*
import org.http4s.dsl.io.*
import org.http4s.server.Router

object Routes:
  def wordListsRoutes(wordLists: WordLists): HttpRoutes[IO] =
    HttpRoutes.of {
      case GET -> Root =>
        for
          wls <- wordLists.all()
          resp <- Ok(wls)
        yield resp

      case GET -> Root / name =>
        for
          wl <- wordLists.get(name).value
          resp <- wl.fold(NotFound())(Ok(_))
        yield resp

      case req @ POST -> Root / name / "matches" =>
        for
          text <- req.bodyText.compile.string
          matches <- wordLists.matches(name, text).value
          resp <- matches.fold(NotFound())(Ok(_))
        yield resp
    }

  def allRoutes(wordLists: WordLists): HttpRoutes[IO] =
    Router(
      "/" -> Routes.wordListsRoutes(wordLists)
    )
