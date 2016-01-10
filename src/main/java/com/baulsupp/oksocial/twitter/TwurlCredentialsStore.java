package com.baulsupp.oksocial.twitter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import java.io.IOException;

public class TwurlCredentialsStore implements CredentialsStore {
  private final File file;

  public TwurlCredentialsStore(File file) {
    this.file = file;
  }

  public TwurlRc readTwurlRc() throws IOException {
    if (file.isFile()) {
      ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
      objectMapper.setPropertyNamingStrategy(
          PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);

      return objectMapper.readValue(file, TwurlRc.class);
    } else {
      return null;
    }
  }

  public TwitterCredentials readDefaultCredentials() throws IOException {
    TwurlRc twurlRc = readTwurlRc();

    if (twurlRc != null) {
      String username = twurlRc.defaultProfile().get(0);
      String consumerKey = twurlRc.defaultProfile().get(1);

      return twurlRc.readCredentials(username, consumerKey);
    } else {
      return null;
    }
  }

  public TwitterCredentials readCredentials(String username, String consumerKey)
      throws IOException {
    TwurlRc twurlRc = readTwurlRc();

    if (twurlRc != null) {
      return twurlRc.readCredentials(username, consumerKey);
    } else {
      return null;
    }
  }

  @Override public void storeCredentials(TwitterCredentials credentials) throws IOException {
    throw new UnsupportedOperationException();
  }
}
