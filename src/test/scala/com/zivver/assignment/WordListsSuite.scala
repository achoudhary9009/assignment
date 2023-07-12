package com.zivver.assignment

import fs2.Stream
import munit.CatsEffectSuite

class WordListsSuite extends CatsEffectSuite with WordListsFixtures:
  wordLists.test("get all word lists") { wordLists =>
    for
      allWordLists <- wordLists.all()
      _ = assertEquals(
        allWordLists.sortBy(_.name),
        List(wordListOne, wordListTwo).sortBy(_.name),
      )
    yield ()
  }

  wordLists.test("get one word list") { wordLists =>
    assertIO(
      wordLists.get(wordListOne.name).value,
      Some(wordListOne),
    )
  }

  wordLists.test("match text") { wordLists =>
    val word = wordListOne.words.head
    assertIO(
      wordLists
        .matches(
          wordListOne.name,
          s"""
            |hello this is a message
            |it has a $word in it
            |but I won't tell you where
            |""".stripMargin,
        )
        .value,
      Some(List(WordLists.Match(word, wordListOne.name))),
    )
  }

  test("load word lists from resources") {
    for
      loadedWordLists <- WordLists.loadFromResources
      _ = assertEquals(
        loadedWordLists.find(_.name == wordListResources.name),
        Some(wordListResources),
      )
    yield ()
  }

  test("default make loads word lists from resources") {
    for
      wordLists <- WordLists.make
      _ <- assertIO(
        wordLists.get("test_resources").value,
        Some(wordListResources),
      )
    yield ()
  }
