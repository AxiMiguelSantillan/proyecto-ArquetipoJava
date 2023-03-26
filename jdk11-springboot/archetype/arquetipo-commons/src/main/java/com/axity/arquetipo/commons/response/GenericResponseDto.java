package com.axity.arquetipo.commons.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase DTO de respuesta
 * 
 * @author guillermo.segura@axity.com
 */
@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class GenericResponseDto<T extends Serializable> implements Serializable
{
  private static final long serialVersionUID = -7923049268047269630L;

  private HeaderDto header;

  private T body;

  /**
   * Constructor default
   */
  public GenericResponseDto()
  {
    header = new HeaderDto();
  }

  /**
   * Constructor con cuerpo
   * 
   * @param t
   */
  public GenericResponseDto( T t )
  {
    header = new HeaderDto();
    this.body = t;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String toString()
  {
    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    return gson.toJson( this );
  }
}
