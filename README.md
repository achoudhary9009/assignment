# Zivver Scala assignment

## Introduction

We have provided you with a lightweight implementation of a service with an
HTTP API, inspired by a real part of Zivver's stack. When a user writes a
Zivver message, this service is called to check if the message contains any
sensitive words, so that we can warn the user about potentially leaking
private data.

Your tasks, described below, involve improving and extending the service.
Make any changes necessary to complete them.

Treat this codebase as production software. That means:
- Write code that will be understood by your colleagues.
- Preserve full backwards compatibility of the API.
- Ensure all changes are adequately covered by automated tests.

This assignment is intended to take you around two hours.

We're still tuning this assignment, so all feedback is appreciated.

## Tasks

1. A customer reported that we missed a sensitive word in one of her
    messages. The customer won't share the message text because it is
    confidential, but we have already confirmed that the word appears
    exactly in the word list. Find the bug and fix it.

2. Metrics show concerningly high response times when checking for matches in
    large word lists. Figure out why and make it faster.

3. The current implementation uses fixed word lists loaded from disk. Our
    support staff want to be able to modify word lists by adding and removing
    words, in real time, through the internal support application. Design and
    implement new API endpoints to enable this.
