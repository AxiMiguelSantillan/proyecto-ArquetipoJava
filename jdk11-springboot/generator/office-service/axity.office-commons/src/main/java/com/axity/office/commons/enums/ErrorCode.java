package com.axity.office.commons.enums;

/**
 * Error code enumeration
 * 
 * @author username@axity.com
 */
public enum ErrorCode
{

  UNKNOWN_ERROR(0), REQUIRED_FIELD(1), EXCEEDS_MAX_LENGTH(2),

  // Role errors
  ROLE_NOT_FOUND(10),
  
  //User errors
  USER_ALREADY_EXISTS(11),

  // Validation errors
  OFFICE_ALREADY_EXISTS(100), OFFICE_NOT_FOUND(101);

  private int code;

  private ErrorCode( int code )
  {
    this.code = code;
  }

  /**
   * @return the code
   */
  public int getCode()
  {
    return code;
  }

}
