package io.pivotal.literx.domain;

public class User {

  public static final User SKYLER = new User("swhite", "Skyler", "White");
  public static final User JESSE = new User("jpinkman", "Jesse", "Pinkman");
  public static final User WALTER = new User("wwhite", "Walter", "White");
  public static final User SAUL = new User("sgoodman", "Saul", "Goodman");
  public static final User SYLVAIN = new User("sgoodman", "Sylvain", "Goodman");
  public static final User JULIEN = new User("jhoarau", "Julien", null);

  public static final User UNKNOWN_USER = null;

  private final String username;

  private final String firstName;

  private final String lastName;

  public User(String username, String firstName, String lastName) {
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String getUsername() {
    return username;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    User user = (User) o;

    if (!username.equals(user.username)) {
      return false;
    }
    if (!firstName.equals(user.firstName)) {
      return false;
    }
    return lastName.equals(user.lastName);
  }

  @Override
  public int hashCode() {
    int result = username.hashCode();
    result = 31 * result + firstName.hashCode();
    result = 31 * result + lastName.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "Person{"
        + "username='"
        + username
        + '\''
        + ", firstname='"
        + firstName
        + '\''
        + ", lastname='"
        + lastName
        + '\''
        + '}';
  }
}
