package com.zivver.assignment

import cats.data.OptionT
import cats.effect.IO
import cats.effect.Resource
import cats.implicits.*
import fs2.Chunk
import fs2.Stream
import io.circe.Encoder

import java.io.File
import scala.io.Source

trait WordLists:
  def all(): IO[List[WordLists.WordList]]
  def get(name: String): OptionT[IO, WordLists.WordList]
  def matches(name: String, text: String): OptionT[IO, List[WordLists.Match]]

object WordLists:
  final case class Match(word: String, wordListName: String)
      derives Encoder.AsObject

  final case class WordList(name: String, words: List[String])
      derives Encoder.AsObject:
    def matches(text: String): IO[List[Match]] =
      IO {
        val textWords = text.split("\\s+").toList
        textWords
          .filter(w => words.contains(w.toLowerCase()))
          .map(w => Match(w, name))
      }

  def loadFromResources: IO[Seq[WordList]] =
    val fileExt = ".txt"
    for
      dir <- IO {
        File(getClass.getResource("wordlists/").getPath)
      }
      files <- IO {
        dir.listFiles().toSeq.filter { f =>
          f.isFile && f.getName.endsWith(fileExt)
        }
      }
      wordLists <- files.traverse { file =>
        val name = file.getName.stripSuffix(fileExt)
        Resource
          .fromAutoCloseable {
            IO(Source.fromFile(file))
          }
          .use { source =>
            IO {
              val words = source.getLines.map(_.toLowerCase()).toList
              WordList(name, words)
            }
          }
      }
    yield wordLists

  def make(load: IO[Seq[WordList]]): IO[WordLists] =
    for
      wordLists <- load
      wordListMap = wordLists.map(wl => wl.name -> wl).toMap
    yield
      new WordLists:
        def all(): IO[List[WordList]] =
          wordListMap.values.toList.pure

        def get(name: String): OptionT[IO, WordList] =
          OptionT.fromOption(wordListMap.get(name))

        def matches(name: String, text: String): OptionT[IO, List[Match]] =
          get(name).semiflatMap(_.matches(text))

  def make: IO[WordLists] = make(loadFromResources)
