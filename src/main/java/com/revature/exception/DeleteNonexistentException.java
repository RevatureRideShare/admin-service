package com.revature.exception;

/**
 * This is a custom exception class for when an object that does not exist in the database is
 * attempting to be deleted.
 *
 * @author Jane Shin
 * @author Roberto Rodriguez
 */
public class DeleteNonexistentException extends RuntimeException {

  /**
   * This is a generated serialVersionUID.
   */
  private static final long serialVersionUID = 1008615752607938931L;

  public DeleteNonexistentException(String message) {
    super(message);
  }
}
