package com.zivver.assignment

import cats.effect.IO
import munit.CatsEffectSuite
import org.http4s.Request
import org.http4s.client.Client

class ServerSuite extends CatsEffectSuite:
  test("Server.httpRoutes responds to GET / with 200") {
    for
      routes <- Server.httpRoutes
      resp <- routes.run(Request()).value
      _ = assert(resp.isDefined)
      _ = resp.map { resp =>
        assertEquals(resp.status.code, 200)
      }
    yield ()
  }

  test("Server.httpApp responds to GET / with 200") {
    for
      app <- Server.httpApp
      client = Client.fromHttpApp(app)
      _ <- client.run(Request()).use { resp =>
        IO {
          assertEquals(resp.status.code, 200)
        }
      }
    yield ()
  }
