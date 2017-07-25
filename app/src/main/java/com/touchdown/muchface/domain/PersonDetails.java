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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PersonDetails that = (PersonDetails) o;

    if (name != null ? !name.equals(that.name) : that.name != null) return false;
    if (ssn != null ? !ssn.equals(that.ssn) : that.ssn != null) return false;
    if (imageUrl != null ? !imageUrl.equals(that.imageUrl) : that.imageUrl != null) return false;
    if (birthDate != null ? !(birthDate.getTime() == that.birthDate.getTime())
        : that.birthDate != null) {
      return false;
    }
    return description != null ? description.equals(that.description) : that.description == null;
  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (ssn != null ? ssn.hashCode() : 0);
    result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
    result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
    result = 31 * result + (description != null ? description.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "PersonDetails{"
        + "name='"
        + name
        + '\''
        + ", ssn='"
        + ssn
        + '\''
        + ", imageUrl='"
        + imageUrl
        + '\''
        + ", birthDate="
        + birthDate
        + ", description='"
        + description
        + '\''
        + '}';
  }
}
