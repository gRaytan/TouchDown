package com.touchdown.muchface.domain;

import java.util.Date;

public class PersonDetails {

  private final String name;
  private final String ssn;
  private final String imageUrl;
  private final Date birthDate;
  private final String description;

  public PersonDetails(String name,
      String ssn,
      String imageUrl,
      String description,
      Date birthDate) {
    this.name = name;
    this.ssn = ssn;
    this.imageUrl = imageUrl;
    this.description = description;
    this.birthDate = birthDate;
  }

  public String getName() {
    return name;
  }

  public String getSsn() {
    return ssn;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public String getDescription() {
    return description;
  }

  public Date getBirthDate() {
    return birthDate;
  }
}
