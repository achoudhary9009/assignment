package com.zivver.assignment

import cats.effect.ExitCode
import cats.effect.IO
import cats.effect.IOApp

object Main extends IOApp:
  def run(args: List[String]): IO[ExitCode] =
    Server.stream.compile.drain.as(ExitCode.Success)
