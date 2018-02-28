package com.baulsupp.oksocial.services.twitter

import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class TwitterAuthInterceptorTest {
  internal var auth = TwitterAuthInterceptor()

  @Test
  fun testHosts() {
    assertTrue(auth.hosts().contains("api.twitter.com"))
  }
}
