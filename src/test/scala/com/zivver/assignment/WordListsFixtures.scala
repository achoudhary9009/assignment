package com.zivver.assignment

import cats.effect.IO
import cats.effect.Resource
import cats.effect.SyncIO
import munit.CatsEffectSuite

trait WordListsFixtures:
  this: CatsEffectSuite =>

  val wordListOne: WordLists.WordList = WordLists.WordList(
    "test_one",
    List("foo", "bar"),
  )

  val wordListTwo: WordLists.WordList = WordLists.WordList(
    "test_two",
    List("baz"),
  )

  val wordListResources: WordLists.WordList = WordLists.WordList(
    "test_resources",
    List("testresourcesfoo", "testresourcesbar"),
  )

  val wordLists: SyncIO[FunFixture[WordLists]] =
    ResourceFixture(
      Resource.eval(WordLists.make(IO.pure(Seq(wordListOne, wordListTwo))))
    )
