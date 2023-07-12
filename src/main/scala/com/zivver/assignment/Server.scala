package com.zivver.assignment

import cats.effect.IO
import cats.effect.Resource
import cats.implicits.*
import com.comcast.ip4s.ipv4
import com.comcast.ip4s.port
import fs2.Stream
import org.http4s.HttpApp
import org.http4s.HttpRoutes
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.middleware.Logger

object Server:
  def httpRoutes: IO[HttpRoutes[IO]] =
    for wordLists <- WordLists.make
    yield Routes.allRoutes(wordLists)

  def httpApp: IO[HttpApp[IO]] =
    httpRoutes
      .map(_.orNotFound)
      .map { app =>
        Logger.httpApp(
          logHeaders = true,
          logBody = false,
        )(app)
      }

  def stream: Stream[IO, Nothing] = {
    for
      httpApp <- Stream.eval(httpApp)

      exitCode <- Stream.resource(
        EmberServerBuilder
          .default[IO]
          .withHost(ipv4"0.0.0.0")
          .withPort(port"8080")
          .withHttpApp(httpApp)
          .build >>
          Resource.eval(IO.never)
      )
    yield exitCode
  }.drain
